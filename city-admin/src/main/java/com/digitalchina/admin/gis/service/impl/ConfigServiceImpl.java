package com.digitalchina.admin.gis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.constant.AppBitCode;
import com.digitalchina.admin.constant.SRID;
import com.digitalchina.admin.constant.enums.GisType;
import com.digitalchina.admin.gis.dto.PartsAttribute;
import com.digitalchina.admin.gis.dto.VideoParts;
import com.digitalchina.admin.gis.entity.Config;
import com.digitalchina.admin.gis.mapper.ConfigMapper;
import com.digitalchina.admin.gis.service.ConfigService;
import com.digitalchina.admin.mid.dto.layer.LayerCondition;
import com.digitalchina.admin.mid.dto.layer.LayerConfig;
import com.digitalchina.admin.mid.dto.layer.LayerListRequest;
import com.digitalchina.admin.mid.dto.layer.LayerProp;
import com.digitalchina.admin.mid.dto.request.CommonRequest;
import com.digitalchina.admin.mid.dto.request.RequestCondition;
import com.digitalchina.admin.mid.entity.PartsCategory;
import com.digitalchina.admin.mid.service.PartsCategoryService;
import com.digitalchina.admin.utils.Wsg2GcjUtil;
import com.digitalchina.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.driver.OracleConnection;
import oracle.spatial.geometry.JGeometry;
import oracle.spatial.util.GeometryExceptionWithContext;
import oracle.spatial.util.WKT;
import oracle.sql.TIMESTAMP;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ryan
 * @since 2020-03-11
 */
@Service
@Slf4j
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Autowired
    private PartsCategoryService partsCategoryService;

    @Override
    public List<Config> getAllLayer() {
        return baseMapper.getAllLayer();
    }

    @Override
    public Page<Map<String, Object>> customPage(CommonRequest request, String module) {
        Page<Map<String, Object>> page = new Page<>(request.getCurrent(), request.getSize());
        // 查询时这个要去掉
        request.getProps().removeIf(prop -> "CATE_ID".equalsIgnoreCase(prop.getField()));

        RequestCondition cateIdCondition = request.getFiltrate().get("CATE_ID");
        request.getFiltrate().remove("CATE_ID");

        String cols = request.buildCols();
        String conds = request.buildConditions();

        String sql;
        switch (module) {
            case "parts":
                sql = String.format("SELECT %s FROM parts_base base join %s parts on base.parts_cate_code = '%s' and parts.objectid = base.refer_id %s",
                        cols, request.getCode(), request.getCode(), conds);
                break;
            case "parts_search": // 市政设施综合查询
                sql = String.format("select %s from parts_base base left join parts_warn_info warn on base.baseid = warn.refer_baseid %s ", cols, conds);
                break;
            case "gyld_ld":

                if (cateIdCondition != null && cateIdCondition.getValue() != null) {
                    sql = "select %s from %s %s and (cate_level_1_id=%s or cate_level_2_id=%s or cate_level_3_id=%s)";
                    sql = String.format(sql, cols, request.getCode(), conds, cateIdCondition.getValue()[0], cateIdCondition.getValue()[0], cateIdCondition.getValue()[0]);
                } else {
                    sql = String.format("SELECT %s FROM %s %s", cols, request.getCode(), conds);
                }
                // 查询结果按入库顺序倒序显示
                sql += " order by OBJECTID desc";
                break;
            default:
                sql = String.format("SELECT %s FROM %s %s", cols, request.getCode(), conds);
                // 查询结果按入库顺序倒序显示
                sql += " order by OBJECTID desc";
        }

        log.debug("==== " + module + " ==> " + sql);
        List<Map<String, Object>> data = baseMapper.customPage(page, sql);
        transTIMESTAMPToDate(data);
        page.setRecords(data);
        return page;
    }

    private void transTIMESTAMPToDate(List<Map<String, Object>> data) {
        if (data != null) {
            data.forEach(d -> {
                d.entrySet().forEach(e -> {
                    if (e.getValue() != null && e.getValue() instanceof TIMESTAMP) {
                        try {
                            e.setValue(((TIMESTAMP) e.getValue()).dateValue());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
            });
        }
    }

    @Override
    public List<Map<String, Object>> cntByDiv(String code, String field) {
        return baseMapper.customListAll(
                String.format("select %s div, count(%s) cnt from %s group by %s", field, field, code, field)
        );
    }

    @Override
    public void customEdit(LayerConfig config) {

        List<LayerProp> props = config.getProps();

        // props处理，空串置为null,dict关联置为editable
        preHandleEditProps(props);

        List<LayerProp> baseProps = filterBaseTableProps(props, "parts_base");
        List<LayerProp> otherProps = filterOtherProps(props);

        // 扩展表
        String id = getId(otherProps, "OBJECTID");
        String set = buildSet(otherProps);
        String sql = String.format("UPDATE %s SET %s WHERE OBJECTID = %s", config.getCode(), set, id);
        baseMapper.costomUpdate(sql);

        // base表
        if (baseProps != null && baseProps.size() > 0) {
            id = getId(baseProps, "BASEID");
            set = buildSet(baseProps);
            sql = String.format("UPDATE parts_base SET %s WHERE BASEID = %s", set, id);
            baseMapper.costomUpdate(sql);
        }
    }

    // props处理，空串置为null,dict关联置为editable
    private void preHandleEditProps(List<LayerProp> props) {
        props.forEach(prop -> {
            if (StringUtils.isBlank(prop.getVal())) {
                prop.setVal(null);
            }

            if (LayerProp.TYPE_DICT.equalsIgnoreCase(prop.getType()) || LayerProp.TYPE_TREE.equalsIgnoreCase(prop.getType())) {
                LayerProp relateProp = props.stream().filter(p -> {
                    return prop.getRelateNameField() != null && p.getField().equalsIgnoreCase(prop.getRelateNameField());
                }).findFirst().orElse(null);
                if (relateProp != null) {
                    relateProp.setEdit(true);
                }
            }
        });
    }

    @Override
    public void customInsert(LayerConfig config) {

        List<LayerProp> props = config.getProps();

        Map<String, String> causeFregment = buildCause(props);
        long objectId = baseMapper.selectSequenceVal("OBJECTID_SEQ");
        String extendSql = "insert into %s (objectid %s) values (%s %s)";
        extendSql = String.format(extendSql, config.getCode(), causeFregment.get("cols"), objectId, causeFregment.get("vals"));
        baseMapper.customInsert(extendSql);
    }

    @Override
    public void creatShu(LayerConfig config) {
        List<LayerProp> props = config.getProps();
        // 根据绿地ID查询行政区划
        Map<String, Object> xzqhMap = baseMapper.customSelectOne(
                String.format("select ADMDIV_CODE, ADMDIV_NAME from GYLD_LD where OBJECTID=%s",
                        props.stream().filter(prop -> "LD_OBJECTID".equalsIgnoreCase(prop.getField())).collect(Collectors.toList()).get(0).getVal())
        );

        props.add(new LayerProp("XZQH_CODE", LayerProp.TYPE_DICT, xzqhMap.get("ADMDIV_CODE").toString()));
        props.add(new LayerProp("XZQH", LayerProp.TYPE_TEXT, xzqhMap.get("ADMDIV_NAME").toString()));

        insertToObjectTable(config);
    }

    private long insertToObjectTable(LayerConfig config) {
        List<LayerProp> props = config.getProps();

        // 扩展表
        List<LayerProp> otherProps = filterOtherProps(props);
        Map<String, String> causeFregment = buildCause(otherProps);
        long objectId = baseMapper.selectSequenceVal("OBJECTID_SEQ");
        String extendSql = "insert into %s (objectid %s) values (%s %s)";
        extendSql = String.format(extendSql, config.getCode(), causeFregment.get("cols"), objectId, causeFregment.get("vals"));
        baseMapper.customInsert(extendSql);

        return objectId;
    }

    @Override
    public void customInsert(LayerConfig config, String module) {

        List<LayerProp> props = config.getProps();

        // 扩展表
        List<LayerProp> otherProps = filterOtherProps(props);
        Map<String, String> causeFregment = buildCause(otherProps);
        long objectId = baseMapper.selectSequenceVal("OBJECTID_SEQ");
        String extendSql = "insert into %s (objectid %s) values (%s %s)";
        extendSql = String.format(extendSql, config.getCode(), causeFregment.get("cols"), objectId, causeFregment.get("vals"));
        baseMapper.customInsert(extendSql);

        switch (module) {
            case "parts":
                List<LayerProp> baseProps = filterBaseTableProps(props, "parts_base");

                // 根据code查找parts_cate_top_id,parts_cate_top_name,parts_cate_id, parts_cate_name, 并构建到props
                List<PartsCategory> partsCategories = partsCategoryService.partsAncestors(config.getCode());
                PartsCategory cate = partsCategories.get(0); // 第一个是本级
                baseProps.add(new LayerProp("PARTS_CATE_CODE", LayerProp.TYPE_TEXT, config.getCode()));
                baseProps.add(new LayerProp("PARTS_CATE_ID", LayerProp.TYPE_DICT, String.valueOf(cate.getId())));
                baseProps.add(new LayerProp("PARTS_CATE_NAME", LayerProp.TYPE_TEXT, cate.getName()));
                PartsCategory topCate = partsCategories.get(1); // 第二个是当前父级，因为是二级结果，所以也是顶级分类
                baseProps.add(new LayerProp("PARTS_CATE_TOP_ID", LayerProp.TYPE_DICT, String.valueOf(topCate.getId())));
                baseProps.add(new LayerProp("PARTS_CATE_TOP_NAME", LayerProp.TYPE_TEXT, topCate.getName()));

                try {
                    // 当前日期
                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date now = new Date();
                    String nowStr = sdf.format(sdf.parse(sdf.format(now)));
                    baseProps.add(new LayerProp("DATE_CREATED", LayerProp.TYPE_DATE, nowStr));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 引用扩展表的ID
                baseProps.add(new LayerProp("REFER_ID", LayerProp.TYPE_NUMBER, String.valueOf(objectId)));


                causeFregment = buildCause(baseProps);
                String baseSql = "insert into parts_base (%s) values (%s)";
                baseSql = String.format(baseSql, causeFregment.get("cols").substring(1), causeFregment.get("vals").substring(1));
                baseMapper.customInsert(baseSql);
                break;
        }

    }

    @Override
    public void creatGreenbelt(LayerConfig config) {

        List<LayerProp> props = config.getProps();
        List<LayerProp> cateProps = new ArrayList<>();

        // 根据前端cateId填充各级cate信息
        Iterator<LayerProp> it = props.iterator();
        while (it.hasNext()) {
            LayerProp layerProp = it.next();

            if ("CATE_ID".equalsIgnoreCase(layerProp.getField())) {

                LayerProp[] cateLevelIds = new LayerProp[]{
                        new LayerProp("CATE_LEVEL_1_ID", LayerProp.TYPE_DICT, null),
                        new LayerProp("CATE_LEVEL_2_ID", LayerProp.TYPE_DICT, null),
                        new LayerProp("CATE_LEVEL_3_ID", LayerProp.TYPE_DICT, null)
                };
                LayerProp[] cateLevelNames = new LayerProp[]{
                        new LayerProp("CATE_LEVEL_1_NAME", LayerProp.TYPE_TEXT, null),
                        new LayerProp("CATE_LEVEL_2_NAME", LayerProp.TYPE_TEXT, null),
                        new LayerProp("CATE_LEVEL_3_NAME", LayerProp.TYPE_TEXT, null)
                };

                PartsCategory category = partsCategoryService.getById(Integer.valueOf(layerProp.getVal()));
                int level = category.getLevel();

                // 向上直到顶级
                for (int i = level - 1; i >= 0; --i) {
                    cateLevelIds[i].setVal(String.valueOf(category.getId()));
                    cateLevelNames[i].setVal(category.getName());

                    cateProps.add(cateLevelIds[i]);
                    cateProps.add(cateLevelNames[i]);

                    if (category.getParentId() != null) {
                        category = partsCategoryService.getById(Integer.valueOf(category.getParentId()));
                    }
                }
                it.remove();
            }
        }
        props.addAll(cateProps);

        // 扩展表
        List<LayerProp> otherProps = filterOtherProps(props);
        Map<String, String> causeFregment = buildCause(otherProps);
        long objectId = baseMapper.selectSequenceVal("OBJECTID_SEQ");
        String extendSql = "insert into %s (objectid %s) values (%s %s)";
        extendSql = String.format(extendSql, config.getCode(), causeFregment.get("cols"), objectId, causeFregment.get("vals"));
        baseMapper.customInsert(extendSql);
    }

    // insert 子句片段
    private Map<String, String> buildCause(List<LayerProp> props) {

        Map<String, String> result = new HashMap<>();

        StringBuilder cols = new StringBuilder();
        StringBuilder vals = new StringBuilder();

        if (props != null) {
            for (LayerProp prop : props) {
                if (prop.getVal() != null) { // 只处理有值字段，无值不拼接到insert sql上
                    cols.append(",").append(prop.getField());
                    vals.append(",").append(prop.getSqlCause());
                }
            }
        }

//        if (cols.lastIndexOf(",") > -1) {
//            cols.deleteCharAt(cols.length() - 1);
//        }
//
//        if (vals.lastIndexOf(",") > -1) {
//            vals.deleteCharAt(vals.length() - 1);
//        }

        result.put("cols", cols.toString());
        result.put("vals", vals.toString());

        return result;
    }

    @Override
    public Map<String, Object> getGisInfo(String code, long objectId) throws SQLException {
        Map<String, Object> resultMap = new HashMap<>();

        PartsCategory cate = partsCategoryService.getOne(Condition.<PartsCategory>create().eq("code", code));
        resultMap.put("type", cate.getGisType());

        Map<String, Object> resultSet = baseMapper.customSelectOne(String.format("select wkt,srid from %s where objectid=%s", code, objectId));

        if (resultSet != null) {
            resultMap.put("srid", resultSet.get("SRID"));
            resultMap.put("shape", resultSet.get("WKT"));
            //转换经纬度
            if (resultSet.get("WKT") != null && cate.getGisType() == GisType.point) {
                System.out.println(resultSet.get("WKT"));
                String[] shape = resultSet.get("WKT").toString().split(" ");
                String longitude = shape[1].substring(1); //原经度
                String latitudeAll = shape[2];
                String latitude = latitudeAll.substring(0, latitudeAll.length() - 1); //原纬度

                double[] wsg = new double[]{Double.valueOf(latitude), Double.valueOf(longitude)};
                double[] gcj = Wsg2GcjUtil.transform(wsg);
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append("GCJ (");
//                stringBuilder.append(gcj[0]);
//                stringBuilder.append(" ");
//                stringBuilder.append(gcj[1]+")");
                resultMap.put("latitude", gcj[0]);
                resultMap.put("longitude", gcj[1]);
            }
        }

        return resultMap;
    }

    private List<LayerProp> filterOtherProps(List<LayerProp> props) {
        return props.stream().filter(p -> p.getBaseTable() == null).collect(Collectors.toList());
    }

    private List<LayerProp> filterBaseTableProps(List<LayerProp> props, String baseTableName) {
        return props.stream().filter(p -> StrUtil.equalsIgnoreCase(baseTableName, p.getBaseTable())).collect(Collectors.toList());
    }


    private String getId(List<LayerProp> props, String idFieldName) {
        for (LayerProp p : props) {
            if (idFieldName.equals(p.getField())) {
                return p.getVal();
            }
        }
        throw new ServiceException("当前图层不存在OBJECT_ID,更新失败");
    }

    private String buildSet(List<LayerProp> props) {
        // 过滤不能编辑的字段,edit
        List<LayerProp> fps = props.stream().filter(p -> {
            return p.getEdit();
        }).collect(Collectors.toList());

        // 组装set语句
        StringBuilder sb = new StringBuilder();
        fps.stream().forEach(p -> {
            String setStr = null;

            if (p.getVal() == null) {
                setStr = ", %s = %s";
            } else switch (p.getType()) {
                case "text":
                case "file":
                    setStr = ", %s = '%s'";
                    break;
                case "number":
                case "dict":
                case "tree":
                    setStr = ", %s = %s";
                    break;
                case "date":
                case "this_date":
                    setStr = ", %s = to_date('%s', 'yyyy-mm-dd')";
                    break;
            }
            sb.append(String.format(setStr, p.getField(), p.getVal()));
        });
        return sb.toString().replaceFirst(",", "");
    }

    private String buildConds(List<LayerCondition> conditions) {
        StringBuilder sb = new StringBuilder();
        sb.append(" where 1=1 ");
        for (LayerCondition cd : conditions) {
            if ("date".equals(cd.getType())) {
                // 没有数据跳出
                if (null == cd.getData() || cd.getData().length() < 1)
                    continue;
                String[] date = cd.getData().split(",");
                String field = String.format("%s >= to_date('%s','YYYY-MM-DD') and %s <= to_date('%s','YYYY-MM-DD')", cd.getField(), date[0], cd.getField(), date[1]);
                sb.append(" and " + field);
            } else if ("text".equals(cd.getType())) {
                // 没有数据跳出
                if (null == cd.getData() || cd.getData().length() < 1)
                    continue;
                String field = String.format("%s like %s", cd.getField(), "'%" + cd.getData() + "%'");
                sb.append(" and " + field);
            }
        }
        return sb.toString();
    }

    @Override
    protected boolean retBool(Integer result) {
        return super.retBool(result);
    }

    @Override
    public void buildPartsBaseCondtions(List<LayerCondition> conditions) {
        conditions.add(new LayerCondition(
                "行政区划", "ADMDIV_CODE", "dict", "eq",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_admin_division",
                "id", "itemName"));
        conditions.add(new LayerCondition(
                "部件状态", "STATUS_CODE", "dict", "eq",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_parts_status",
                "id", "itemName"));
        conditions.add(new LayerCondition(
                "数据来源", "DATA_SOURCE_CODE", "dict", "eq",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_data_sources",
                "id", "itemName"));
        conditions.add(new LayerCondition(
                "权属单位", "OWNER_CODE", "dict", "eq",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_ownership_unit",
                "id", "itemName"));
//        conditions.add(new LayerCondition(
//                "部件名称", "PARTS_NAME", "text", "like"));
    }

    @Override
    public void buildPartsBaseProps(List<LayerProp> props) {
        List<LayerProp> baseProps = new ArrayList<>();
        baseProps.add(new LayerProp("BASEID", "BASEID", LayerProp.TYPE_NUMBER, 0., 0., 0., "parts_base"));
        baseProps.add(new LayerProp("部件标识码", "PARTS_CODE", LayerProp.TYPE_TEXT, 1., 1., 1., "parts_base"));
        baseProps.add(new LayerProp("部件分类", "PARTS_CATE_TOP_NAME", LayerProp.TYPE_TEXT, 1., 0., 0., "parts_base"));
        baseProps.add(new LayerProp("部件名称", "PARTS_CATE_NAME", LayerProp.TYPE_TEXT, 1., 0., 0., "parts_base"));
        baseProps.add(new LayerProp("部件类别码", "PARTS_CATE_CODE", LayerProp.TYPE_TEXT, 0., 0., 0., "parts_base"));
        baseProps.add(new LayerProp("权属单位", "OWNER_CODE", LayerProp.TYPE_DICT, 0., 1., 1., "parts_base",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_ownership_unit",
                "id", "itemName", "OWNER_NAME"));
        baseProps.add(new LayerProp("权属单位", "OWNER_NAME", LayerProp.TYPE_TEXT, 1., 0., 0., "parts_base"));

        baseProps.add(new LayerProp("部件状态", "STATUS_CODE", LayerProp.TYPE_DICT, 0., 1., 1., "parts_base",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_parts_status",
                "id", "itemName", "STATUS_NAME"));
        baseProps.add(new LayerProp("部件状态", "STATUS_NAME", LayerProp.TYPE_TEXT, 1., 0., 0., "parts_base"));

        baseProps.add(new LayerProp("养护单位", "MAINTAINER_CODE", LayerProp.TYPE_DICT, 0., 1., 1., "parts_base",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_maintain_unit",
                "id", "itemName", "MAINTAINER_NAME"));
        baseProps.add(new LayerProp("养护单位", "MAINTAINER_NAME", LayerProp.TYPE_TEXT, 0., 0., 0., "parts_base"));

        baseProps.add(new LayerProp("数据来源", "DATA_SOURCE_CODE", LayerProp.TYPE_DICT, 0., 1., 1., "parts_base",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_data_sources",
                "id", "itemName", "DATA_SOURCE_NAME"));
        baseProps.add(new LayerProp("数据来源", "DATA_SOURCE_NAME", LayerProp.TYPE_TEXT, 0., 0., 0., "parts_base"));

        baseProps.add(new LayerProp("行政区划", "ADMDIV_CODE", LayerProp.TYPE_DICT, 0., 1., 1., "parts_base",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_admin_division",
                "id", "itemName", "ADMDIV_NAME"));
        baseProps.add(new LayerProp("行政区划", "ADMDIV_NAME", LayerProp.TYPE_TEXT, 0., 0., 0., "parts_base"));

        baseProps.add(new LayerProp("现势性", "CURRENCY_CODE", LayerProp.TYPE_DICT, 0., 1., 1., "parts_base",
                "/cityadmin/dataDict/getDictName?dataDictCode=zhjg_current_situation",
                "id", "itemName", "CURRENCY_NAME"));
        baseProps.add(new LayerProp("现势性", "CURRENCY_NAME", LayerProp.TYPE_TEXT, 0., 0., 0., "parts_base"));

        baseProps.add(new LayerProp("建设日期", "DATE_PARTS_BUILD", LayerProp.TYPE_DATE, 0., 1., 1., "parts_base"));

        props.addAll(0, baseProps);
    }


    @Override
    public Page<Map<String, Object>> customPage(LayerListRequest request) {
        Page<Map<String, Object>> page = new Page<>(request.getCurrent(), request.getSize());
        LayerConfig cfg = request.getConfig();
        String cols = buildCols(cfg.getProps());
        String conds = buildConds(cfg.getConditions());
        String sql = String.format("SELECT %s FROM %s %s", cols, cfg.getCode(), conds);
        List<Map<String, Object>> data = baseMapper.customPage(page, sql);
        transTIMESTAMPToDate(data);
        page.setRecords(data);
        return page;
    }

    private String getId(LayerConfig config) {
        for (LayerProp p : config.getProps()) {
            if ("OBJECT_ID".equals(p.getField())) {
                return p.getVal();
            }
        }
        throw new ServiceException("当前图层不存在OBJECT_ID,更新失败");
    }

    private String buildCols(List<LayerProp> props) {
        StringBuilder sb = new StringBuilder();
        for (LayerProp pp : props) {
            if (pp.getTable()) {
                sb.append(", " + pp.getField());
            }
        }
        return sb.toString().replaceFirst(",", "");
    }

    @Autowired
    @Qualifier("gisDatasource")
    private DataSource gisDataSource;

    @Override
    public void updateShape(String code, Long objectId, String wktStr, int srid) throws GeometryExceptionWithContext {
        WKT wkt = new WKT();
        JGeometry geometry = wkt.toJGeometry(wktStr.getBytes());
        geometry.setSRID(srid);
        baseMapper.updateWkt(code, objectId, wktStr, srid);
        //try (Connection conn = gisDataSource.getConnection()) {
        //Struct struct = JGeometry.store(conn.unwrap(OracleConnection.class), geometry);
        //baseMapper.clearShape(code, objectId);
        //baseMapper.updateShape(code, objectId, wktStr, srid, null);
        //} catch (SQLException throwables) {
        //    throwables.printStackTrace();
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }


    @Override
    public List<Config> getVisibleCols(String code) {
        return getVisibleCols(code, AppBitCode.ZHJG);
    }

    @Override
    public List<Config> getPartsVisibleCols(String code) {
        return getPartsVisibleCols(code, AppBitCode.ZHJG);
    }

    @Override
    public List<Config> getGisCols(String module, String code, GisType gisType) {
        List<Config> resultList = new ArrayList<>();
        resultList.add(new Config("WKT", "wkt"));
        resultList.add(new Config("SRID", "srid"));
        switch (module) {
            case "parts":
                resultList.add(new Config("BASEID", "baseid"));
                resultList.add(new Config("WARNING_TYPE_VALUE", "预警状态"));
                break;
            case "hot":
                if ("SZSS_GLF".equalsIgnoreCase(code)) { // 锅炉房加运行情况
                    resultList.add(new Config("YXQK", "运行情况"));
                }
                if ("SZSS_GRFW".equalsIgnoreCase(code)) {
                    resultList.add(new Config("GRQY", "供热企业"));
                    resultList.add(new Config("DWQY", "等温区域"));
                }
                resultList.add(new Config("OBJECTID", "objectid"));
                break;
            case "flood":
                if ("SZSS_JSD".equalsIgnoreCase(code)) {
                    resultList.add(new Config("ZDMC", "积水站点名称"));
                    resultList.add(new Config("ZDBH", "积水站点编号"));
                    resultList.add(new Config("JSSD", "积水深度"));
                    resultList.add(new Config("JYL", "降雨量"));
                    resultList.add(new Config("YJDJ", "预警顶级"));
                }
                if ("SZSS_PSD".equalsIgnoreCase(code)) {
                    resultList.add(new Config("ZDMC", "排水站点名称"));
                    resultList.add(new Config("ZDBH", "排水站点编号"));
                    resultList.add(new Config("SW", "水位"));
                    resultList.add(new Config("LS", "流速"));
                    resultList.add(new Config("YJDJ", "预警顶级"));
                    resultList.add(new Config("PSTYPE", "排水分类"));
                }
                resultList.add(new Config("OBJECTID", "objectid"));
                break;
            case "gyld":
                if (gisType == GisType.polygon) {
                    resultList.add(new Config("NAME", "名称"));
                }
                resultList.add(new Config("OBJECTID", "objectid"));
            default:
                resultList.add(new Config("OBJECTID", "objectid"));
        }
        return resultList;
    }

    @Override
    public List<Config> getVisibleCols(String code, int appBit) {
        List<Config> configs = list(Condition.<Config>create().eq(Config.TB, code).orderByAsc("fdsort"));
        return configs.stream().filter(config -> {
            boolean ret =
                    // 仅本应用所需字段
                    (config.getVisibleMask() & appBit) == appBit &&
                            // 仅有意义的类型，code类的字段不用显示
                            !LayerProp.TYPE_TREE.equalsIgnoreCase(config.getType()) &&
                            !LayerProp.TYPE_DICT.equalsIgnoreCase(config.getType()) &&
                            // OBJECTID不用传
                            !StrUtil.equalsIgnoreCase(config.getFd(), "OBJECTID");
            return ret;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Config> getPartsVisibleCols(String code, int appBit) {
        List<Config> configs = new ArrayList<>();

        // 填加base表字段
        configs.add(new Config("ADMDIV_NAME", "行政区划"));
        configs.add(new Config("DATA_SOURCE_NAME", "数据来源"));
        configs.add(new Config("OWNER_NAME", "权属单位"));
        configs.add(new Config("DATE_CREATED", "入库日期"));
        configs.add(new Config("DATE_PARTS_BUILD", "建设日期"));

        configs.addAll(getVisibleCols(code, appBit));

        return configs;
    }

    @Override
    public List<List<PartsAttribute>> listGisDatas(String code, Map<String, RequestCondition> conditions, List<Config> configs) {
        return listGisDatas(code, conditions, configs, (cols, tb, conds) -> {
            return String.format("select %s from %s %s", cols, tb, conds);
        });
    }

    @Override
    public List<Map<String, Object>> listGreenbelts(Long cateId) {
        return baseMapper.customListAll(
                String.format("select objectid,name, wkt, srid from gyld_ld where wkt is not null "
                        + " and cate_level_1_id=%s or cate_level_2_id=%s or cate_level_3_id=%s", cateId, cateId, cateId)
        );
    }

    @Override
    public List<List<PartsAttribute>> listPartsGisDatas(String code, Map<String, RequestCondition> conditions, List<Config> configs) {
        return listGisDatas(code, conditions, configs, (cols, tb, conds) -> {
            return String.format("select %s from parts_base base join %s parts on base.parts_cate_code='%s' and base.refer_id=parts.objectid  %s", cols, tb, tb, conds);
        });
    }

    @Override
    public List<List<PartsAttribute>> listPartsGisDatasWithWarnInfo(String code, Map<String, RequestCondition> conditions, List<Config> configs) {
        return listGisDatas(code, conditions, configs, (cols, tb, conds) -> {
            return String.format(
                    "select %s from parts_base base "
                            + "join %s parts on base.parts_cate_code='%s' and base.refer_id=parts.objectid "
                            + "left join parts_warn_info warn on warn.config_tb='%s' and warn.refer_id=parts.objectid "
                            + " %s",
                    cols, tb, tb, tb, conds);
        });
    }

    @Override
    public List<List<PartsAttribute>> listPartsGisDatasJSD(String code, Map<String, RequestCondition> conditions, List<Config> configs) {
        return listGisDatas(code, conditions, configs, (cols, tb, conds) -> {
            return String.format("select objectId,zdmc,zdbh,jssd,jyl,wkt,srid from szss_jsd where wkt is not null");
        });
    }

    @Override
    public List<List<PartsAttribute>> listPartsGisDatasPSD(String code, Map<String, RequestCondition> conditions, List<Config> configs) {
        return listGisDatas(code, conditions, configs, (cols, tb, conds) -> {
            return String.format("select objectId,zdmc,zdbh,NVL(jssd,0) as sw,NVL(ll,0) as ll,'0' as ls,NVL(yjdj,'0') as yjdj,wkt,srid from szss_psd where wkt is not null");

//            "select zdbh, zdmc, objectId, LS,SW,wkt,srid " +
//                    "from (select psd.zdbh, psd.zdmc,psd.objectId,psd.wkt,psd.srid,d.LS,d.SW,to_char(tm, 'yyyy-mm-dd HH:mm:ss'), ROW_NUMBER() OVER (PARTITION BY psd.zdmc ORDER BY tm desc) as sortNo " +
//                    "from  SZSS_PSD psd left join SZSS_PSLL d on psd.zdbh = d.zdbh where psd.wkt is not null " +
//                    ") where sortNo = 1"
        });
    }

    private interface SQLFormatter {
        String formate(String cols, String code, String conds);
    }

    private List<List<PartsAttribute>> listGisDatas(String code, Map<String, RequestCondition> conditions, List<Config> configs, SQLFormatter sqlFormatter) {
        String cols = buildSelectCols(configs);
        CommonRequest request = new CommonRequest();
        request.setFiltrate(conditions);
        String conds = request.buildConditions();

        String sql = sqlFormatter.formate(cols, code, conds);

//        Page<Map<String, Object>> page = new Page<>();
//        page.setCurrent(1);
//        page.setSize(10);
//        List<Map<String, Object>> list = baseMapper.customPage(page, sql);
        List<Map<String, Object>> list = baseMapper.customListAll(sql);
        return transformMapToOrderdListBatch(list, configs);
    }

    /*
     * 将每一行数据从map结构转化成list&lt;Pair&gt;结构
     *
     * @param list
     * @return
     */
    private List<List<PartsAttribute>> transformMapToOrderdListBatch(List<Map<String, Object>> list, List<Config> configs) {
        List<List<PartsAttribute>> resultList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            resultList.add(transformMapToOrderdList(map, configs));
        }
        return resultList;
    }

    /*
     * 将行数据从map结构转化成list&lt;Pair&gt;结构
     *
     * @param list
     * @return
     */
    private List<PartsAttribute> transformMapToOrderdList(Map<String, Object> map, List<Config> configs) {

        if (map == null) {
            return null;
        }

        List<PartsAttribute> resultList = new ArrayList<>();
        for (Config config : configs) {
            PartsAttribute attr = new PartsAttribute(config.getFd(), config.getFdnm(), map.get(config.getFd()));
            resultList.add(attr);
        }
        return resultList;
    }

    private String buildSelectCols(List<Config> configs) {
        return buildSelectCols(configs, null);
    }

    private String buildSelectCols(List<Config> configs, String tbName) {
        StringBuilder builder = new StringBuilder();
        for (Config config : configs) {
            if (tbName != null && !config.getFd().contains(".")) {
                builder.append(tbName).append(".");
            }
            builder.append(config.getFd()).append(",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    @Override
    public List<PartsAttribute> selectOneGisData(String code, long objectId, List<Config> configs) {
        String cols = buildSelectCols(configs);
        String sql = String.format("select %s from %s where objectid=%s", cols, code, objectId);
        Map<String, Object> resultMap = baseMapper.customSelectOne(sql);
        return transformMapToOrderdList(resultMap, configs);
    }

    @Override
    public List<PartsAttribute> selectOneAfforestResourceGisData(String code, Long objectId, List<Config> configs) {

        configs.removeIf(config -> config.getFd().equalsIgnoreCase("LD_OBJECTID"));
        configs.add(new Config("GYLD_LD.name as LD_NAME", "所属绿地", "text"));

        String cols = buildSelectCols(configs, code);
        String sql = String.format("select %s from %s left join gyld_ld on %s.LD_OBJECTID=gyld_ld.OBJECTID where %s.objectid=%s", cols, code, code, code, objectId);
        Map<String, Object> resultMap = baseMapper.customSelectOne(sql);

        configs.removeIf(config -> config.getFd().equalsIgnoreCase("GYLD_LD.name as LD_NAME"));
        configs.add(new Config("LD_NAME", "所属绿地"));
        return transformMapToOrderdList(resultMap, configs);
    }

    @Override
    public List<PartsAttribute> selectOnePartsGisData(String code, long baseId, List<Config> configs) {
        String cols = buildSelectCols(configs);
        String sql = String.format("select %s from parts_base base join %s parts on base.parts_cate_code='%s' and base.refer_id=parts.objectid where baseid=%s", cols, code, code, baseId);
        Map<String, Object> resultMap = baseMapper.customSelectOne(sql);
        return resultMap == null ? null : transformMapToOrderdList(resultMap, configs);
    }

    @Override
    public List<VideoParts> listVideoDatas(List<PartsCategory> videoCates) throws SQLException, GeometryExceptionWithContext {

        if (videoCates == null || videoCates.size() == 0) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> resultSet = new ArrayList<>();
        for (PartsCategory cate : videoCates) {
            String sql = "select '" + cate.getCode() + "' TYPE, objectid, id, wz, wkt, srid from " + cate.getCode();
            resultSet.addAll(baseMapper.customListAll(sql));
        }

        List<VideoParts> retList = new ArrayList<>();
        for (Map<String, Object> m : resultSet) {
            VideoParts videoParts = new VideoParts();
            videoParts.setId((Serializable) m.get("ID"));
            videoParts.setObjectid((Serializable) m.get("OBJECTID"));
            videoParts.setType((String) m.get("TYPE"));

            videoParts.setShape((String) m.get("WKT"));
            videoParts.setSrid(((BigDecimal) m.get("SRID")).intValue());
            videoParts.setLocation((String) m.get("WZ"));

            retList.add(videoParts);
        }

        return retList;
    }

    @Override
    public List<Map<String, Object>> searchVideos(List<PartsCategory> videoCates, String keyword) {
        if (videoCates == null || videoCates.size() == 0) {
            return new ArrayList<>();
        }

        StringBuilder builder = new StringBuilder();
        for (PartsCategory cate : videoCates) {
            builder.append("select '" + cate.getCode() + "' TYPE, objectid, wz from " + cate.getCode() + " where wz like '%" + keyword + "%' union ");
        }
        builder.delete(builder.length() - " union ".length(), builder.length());

        // 只查询前15个
        Page<Map<String, Object>> page = new Page<>();
        page.setSize(15);
        page.setCurrent(1);

        return baseMapper.customPage(page, builder.toString());
    }

    @Override
    public void translateShapeToWkt(String code) {
        List<Map<String, Object>> lists = baseMapper.customListAll("select objectid, shape from " + code + " where shape is not null");
        lists.forEach(record -> {
            Long objectId = ((BigDecimal) record.get("OBJECTID")).longValue();
            System.out.print("OBJECTI[" + objectId + "] > ");
            Struct struct = (Struct) record.get("SHAPE");
            if (struct != null) {
                WKT wkt = new WKT();
                try {
                    String wktStr = new String(wkt.fromStruct(struct));
                    System.out.print(wktStr + " > ");
                    baseMapper.costomUpdate(String.format("update %s set wkt='%s' where objectid=%s", code, wktStr, objectId));
                    System.out.print("end update wkt > ");
                } catch (Exception e) {
                    System.err.print(e.getMessage() + " > ");
                }
            }

            System.out.println(" < end;");
        });
    }

    @Override
    public void translateWktToShape(String code) throws SQLException {
        List<Map<String, Object>> lists = baseMapper.customListAll("select objectid, wkt, srid from " + code + " where wkt is not null");

        lists.forEach(record -> {
            Long objectId = ((BigDecimal) record.get("OBJECTID")).longValue();
            System.out.print("OBJECTI[" + objectId + "] > ");
            String wktStr = (String) record.get("WKT");
            BigDecimal bdsrid = (BigDecimal) record.get("SRID");
            int srid = bdsrid == null ? SRID.Default : bdsrid.intValue();

            try {
                WKT wkt = new WKT();
                JGeometry geometry = wkt.toJGeometry(wktStr.getBytes());
                geometry.setSRID(srid);

                try (Connection conn = gisDataSource.getConnection()) {
                    Struct struct = JGeometry.store(conn.unwrap(OracleConnection.class), geometry);
                    baseMapper.clearShape(code, objectId);
                    baseMapper.updateShape(code, objectId, wktStr, srid, struct);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (GeometryExceptionWithContext geometryExceptionWithContext) {
                geometryExceptionWithContext.printStackTrace();
            }
        });

    }

    @Override
    @Transactional
    public void deleteObject(String code, Long objectId) {
        baseMapper.customDelete(String.format("delete from %s where OBJECTID=%s", code, objectId));
        baseMapper.customDelete(String.format("delete from parts_base where PARTS_CATE_CODE='%s' and REFER_ID=%s", code, objectId));
    }
}
