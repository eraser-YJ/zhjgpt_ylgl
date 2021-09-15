package com.digitalchina.admin.gis.controller;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.admin.constant.SRID;
import com.digitalchina.admin.constant.enums.GisType;
import com.digitalchina.admin.gis.dto.GisData;
import com.digitalchina.admin.gis.dto.PartsAttribute;
import com.digitalchina.admin.gis.dto.VideoParts;
import com.digitalchina.admin.gis.entity.Config;
import com.digitalchina.admin.gis.entity.UpdateShapeApp;
import com.digitalchina.admin.gis.service.ConfigService;
import com.digitalchina.admin.mid.dto.request.RequestCondition;
import com.digitalchina.admin.mid.entity.PartsCategory;
import com.digitalchina.admin.mid.service.PartsCategoryService;
import com.digitalchina.admin.utils.Gcj2WsgUtil;
import com.digitalchina.admin.utils.Wsg2GcjUtil;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import oracle.spatial.util.GeometryExceptionWithContext;
import oracle.spatial.util.WKT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("gisDatas")
@Api(tags = "GIS资源数据加载")
public class GisServerDataController {

    @Autowired
    private PartsCategoryService partsCategoryService;

    @Autowired
    private ConfigService configService;

    /**
     * 批量将gis组件表shape数据转一份wkt保存到原表中
     *
     * @param code
     * @return
     */
    @GetMapping("translate_wkt")
    public RespMsg<Void> translateShapeToWkt(String code) {
        configService.translateShapeToWkt(code);
        return RespMsg.ok();
    }

    @GetMapping("translate_shape")
    public RespMsg<Void> translateWktToShape(String code) throws SQLException {
        configService.translateWktToShape(code);
        return RespMsg.ok();
    }

    @PostMapping("/translate_wkt_batch")
    public RespMsg<Void> tranlateShapeToWktBatch(@RequestBody String[] tables) {
        for (String tb : tables) {
            configService.translateShapeToWkt(tb);
        }
        return RespMsg.ok();
    }

    @ApiOperation("城市部件GIS数据加载")
    @ApiImplicitParam(name = "code", value = "部件类别代码")
    @PostMapping("/{module}/{code}")
    public RespMsg<List<GisData>> loadPartsGisData(
            @PathVariable String module,
            @PathVariable String code,
            @RequestBody(required = false) Map<String, RequestCondition> conditions
    ) throws Exception {

        if (conditions == null) {
            conditions = new HashMap<>();
        }

        // 排除wkt is null部件
        RequestCondition cond = new RequestCondition();
        cond.setName("WKT");
        cond.setOp("is not null");
        cond.setValue(new String[]{"not null"});
        conditions.put("WKT", cond);

        // 查出当前图层部件gis类型 点/线/面
        PartsCategory partsCategory = partsCategoryService.getOne(Condition.<PartsCategory>create().eq("code", code));
        GisType gisType = partsCategory.getGisType();

        List<Config> configs = configService.getGisCols(module, code, gisType);

        // 查询数据
        List<List<PartsAttribute>> partsDatas = null;
        if ("parts".equalsIgnoreCase(module)) {
            partsDatas = configService.listPartsGisDatasWithWarnInfo(code, conditions, configs);
        } else if ("flood".equalsIgnoreCase(module) && "SZSS_JSD".equalsIgnoreCase(code)) {
            partsDatas = configService.listPartsGisDatasJSD(code, conditions, configs);
        } else if ("flood".equalsIgnoreCase(module) && "SZSS_PSD".equalsIgnoreCase(code)) {
            partsDatas = configService.listPartsGisDatasPSD(code, conditions, configs);
        } else {
            partsDatas = configService.listGisDatas(code, conditions, configs);
        }
        List<GisData> resultList = new ArrayList<>();
        for (List<PartsAttribute> p : partsDatas) {
            GisData gisData = new GisData();

            setShape(gisData, p);
            gisData.put("gis_type", gisType);

            // id
            Object id = getAttr(p, "parts".equalsIgnoreCase(module) ? "BASEID" : "OBJECTID");
            gisData.put("id", id);

            // 城市设施gis需要报警状态属性
            if ("parts".equalsIgnoreCase(module)) {
                gisData.put("warn_type", getWarnType(p));
            }
            if ("flood".equalsIgnoreCase(module) && "SZSS_JSD".equalsIgnoreCase(code)) {
                gisData.put("objectId", getAttr(p, "objectId"));
                gisData.put("zdmc", getAttr(p, "ZDMC"));
                gisData.put("zdbh", getAttr(p, "ZDBH"));
                gisData.put("jssd", getAttr(p, "JSSD"));
                gisData.put("jyl", getAttr(p, "JYL"));
                gisData.put("yjdj", getAttr(p, "YJDJ"));
                gisData.put("type", "JSD");
                //转换经纬度
                String[] shape = gisData.get("shape").toString().split(" ");
                String longitude = shape[1].substring(1); //原经度
                String latitudeAll = shape[2];
                String latitude = latitudeAll.substring(0,latitudeAll.length()-1); //原纬度

                double[] wsg = new double[]{Double.valueOf(latitude),Double.valueOf(longitude)};
                double[] gcj = Wsg2GcjUtil.transform(wsg);
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append("GCJ (");
//                stringBuilder.append(gcj[0]);
//                stringBuilder.append(" ");
//                stringBuilder.append(gcj[1]+")");
                gisData.put("gcjLatitude",gcj[0]);
                gisData.put("gcjLongitude",gcj[1]);


            }
            if ("flood".equalsIgnoreCase(module) && "SZSS_PSD".equalsIgnoreCase(code)) {
                gisData.put("objectId", getAttr(p, "objectId"));
                gisData.put("zdmc", getAttr(p, "ZDMC"));
                gisData.put("zdbh", getAttr(p, "ZDBH"));
                gisData.put("ls", getAttr(p, "LS"));
                gisData.put("sw", getAttr(p, "SW"));
                gisData.put("yjdj", getAttr(p, "YJDJ"));
                gisData.put("psType", getAttr(p, "PSTYPE"));
                gisData.put("type", "PSD");
                //转换经纬度
                String[] shape = gisData.get("shape").toString().split(" ");
                String longitude = shape[1].substring(1); //原经度
                String latitudeAll = shape[2];
                String latitude = latitudeAll.substring(0,latitudeAll.length()-1); //原纬度

                double[] wsg = new double[]{Double.valueOf(latitude),Double.valueOf(longitude)};
                double[] gcj = Wsg2GcjUtil.transform(wsg);
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append("GCJ (");
//                stringBuilder.append(gcj[0]);
//                stringBuilder.append(" ");
//                stringBuilder.append(gcj[1]+")");
                gisData.put("gcjLatitude",gcj[0]);
                gisData.put("gcjLongitude",gcj[1]);

            }
            resultList.add(gisData);

            // 锅炉房加运行情况
            // 情况可取值：正常/停炉/检修/中断，对应颜色 绿/灰/橙/红
            if ("SZSS_GLF".equalsIgnoreCase(code)) {
                gisData.put("YXQK", getAttr(p, "YXQK"));
            }

            // 热力范围加供热企业和区域温度
            if ("SZSS_GRFW".equalsIgnoreCase(code)) {
                gisData.put("GRQY", getAttr(p, "GRQY"));
                gisData.put("DWQY", getAttr(p, "DWQY"));
            }

            // 公园、绿地、绿地块
            if ("gyld".equalsIgnoreCase(module)) {
                gisData.put("name", getAttr(p, "NAME"));
            }
        }

        return RespMsg.ok(resultList);
    }


    private Object getAttr(List<PartsAttribute> p, String key) {
        for (PartsAttribute a : p) {
            if (key.equalsIgnoreCase(a.getKey())) {
                return a.getValue();
            }
        }
        return null;
    }


    @ApiOperation("城市部件GIS数据加载")
    @RequestMapping("/videos")
    public RespMsg<List<VideoParts>> videos() throws Exception {
        List<PartsCategory> videoCates = partsCategoryService.list(Condition.<PartsCategory>create()
                .eq("module", "video").eq("level", 2));
        List<VideoParts> videoDatas = configService.listVideoDatas(videoCates);
        return RespMsg.ok(videoDatas);
    }

    @ApiOperation("市政设施，事件信息GIS")
    @RequestMapping("/events")
    public RespMsg<List<GisData>> events() {
        // 待实现
        return RespMsg.ok();
    }

    @ApiOperation("获取各类绿地gis资源信息")
    @ApiImplicitParam(name = "cateId", value = "绿地类别编号")
    @PostMapping("/gyld/GYLD_LD")
    public RespMsg<List<GisData>> greenbeltGis(Long cateId) throws Exception {

        if (cateId == null) { // 不区分类别时，
            return loadPartsGisData("gyld", "GYLD_LD", null);
        }

        List<Map<String, Object>> gisDatas = configService.listGreenbelts(cateId);
        List<GisData> resultList = new ArrayList<>();
        gisDatas.forEach(data -> {
            GisData gisData = new GisData();
            gisData.setSrid(((BigDecimal) data.get("SRID")).intValue());
            gisData.setShape((String) data.get("WKT"));
            gisData.put("id", data.get("OBJECTID"));
            gisData.put("gis_type", GisType.polygon);
            gisData.put("name", data.get("NAME"));
            resultList.add(gisData);
        });
        return RespMsg.ok(resultList);
    }

    @ApiOperation("城市部件GIS数据加载")
    @ApiImplicitParam(name = "keyword", value = "检索关键词")
    @GetMapping("/videos/search")
    public RespMsg<List<Map<String, Object>>> videosSearch(String keyword) throws Exception {
        List<PartsCategory> videoCates = partsCategoryService.list(Condition.<PartsCategory>create()
                .eq("module", "video").eq("level", 2));
        return RespMsg.ok(configService.searchVideos(videoCates, keyword));
    }

    @ApiOperation("更新部件shape信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "部件类别", value = "code"),
            @ApiImplicitParam(name = "部件编号", value = "objectId"),
            @ApiImplicitParam(name = "wkt格式位置信息", value = "wkt"),
            @ApiImplicitParam(name = "坐标系编码，不传则默认为4326", value = "srid")
    })
    @PostMapping("update_shape/{code}")
    public RespMsg<Void> updateShapeInfo(@PathVariable("code") String code, Long objectId, String wkt, Integer srid) throws Exception {
        if (srid == null) {
            srid = SRID.Default;
        }
        configService.updateShape(code, objectId, wkt, srid);
        return RespMsg.ok();
    }

    @ApiOperation("移动端更新部件shape信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "部件类别", value = "code"),
            @ApiImplicitParam(name = "部件编号", value = "objectId"),
            @ApiImplicitParam(name = "经度", value = "longitude"),
            @ApiImplicitParam(name = "纬度", value = "latitude"),
            @ApiImplicitParam(name = "坐标系编码，不传则默认为4326", value = "srid")
    })
    @PostMapping("update_shapeApp/{code}")
    public RespMsg<Void> updateShapeAppInfo(@PathVariable("code") String code,@RequestBody UpdateShapeApp updateShapeApp ) throws Exception {
        Integer srid = 0;
        if (updateShapeApp.getSrid() == null) {
            srid = SRID.Default;
        }else{
            srid= updateShapeApp.getSrid();
        }
        if(updateShapeApp.getLatitude()!=null && updateShapeApp.getLongitude()!=null){
            double[] gcj = new double[]{updateShapeApp.getLatitude(),updateShapeApp.getLongitude()};
            double[] wsg = Gcj2WsgUtil.transform(gcj);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("POINT (");
            stringBuilder.append(wsg[1]);
            stringBuilder.append(" ");
            stringBuilder.append(wsg[0]+")");
            configService.updateShape(code, updateShapeApp.getObjectId(), stringBuilder.toString(), srid);
        }
        return RespMsg.ok();
    }

    @ApiOperation("获取部件对应的gis类型（type:point|line|polygon）及已有gis位置信息(shape, srid)，如果没有位置信息，返回对象只有type")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "部件类别", value = "code"),
            @ApiImplicitParam(name = "部件编号", value = "objectId"),
    })
    @GetMapping("gis_info/{code}")
    public RespMsg<Map<String, Object>> getGisInfo(@PathVariable("code") String code, long objectId) throws SQLException, GeometryExceptionWithContext {
        return RespMsg.ok(configService.getGisInfo(code, objectId));
    }

    /* ============== 下面三个方法都与获取部件属性集有关, 第一个是整合，后两个是老方法 =========================*/
    @ApiOperation("城市部件GIS点位属性")
    @ApiImplicitParam(name = "id", value = "id")
    @GetMapping("/feature/{module}/{code}")
    public RespMsg<List<PartsAttribute>> getFeatures(
            @PathVariable("module") String module,
            @PathVariable("code") String code,
            Long id) {
        if ("parts".equalsIgnoreCase(module)) {
            return getPartsAttributesById(code, id);
        } else {
            return getPartsAttributesByObjectId(code, id);
        }
    }

    @ApiOperation("通过objectId查询gis部件属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "组件类别", value = "code"),
            @ApiImplicitParam(name = "组件ObjectId", value = "objectId")
    })
    @GetMapping("/attributes/{code}")
    public RespMsg<List<PartsAttribute>> getPartsAttributesByObjectId(@PathVariable("code") String code, Long objectId) {
        List<Config> configs = configService.getVisibleCols(code);
        return RespMsg.ok(handleResult(configService.selectOneGisData(code, objectId, configs), code));
    }

    @ApiOperation("通过objectId查询绿化资源（从属于绿地的资源）属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "资源类别", value = "code"),
            @ApiImplicitParam(name = "资源ObjectId", value = "objectId")
    })
    @GetMapping("/afforest_resource/{code}/attributes")
    public RespMsg<List<PartsAttribute>> getAfforestResourceAttributesByObjectId(@PathVariable("code") String code, Long objectId) {
        List<Config> configs = configService.getVisibleCols(code);
        return RespMsg.ok(handleResult(configService.selectOneAfforestResourceGisData(code, objectId, configs), code));
    }


    @ApiOperation("通过baseId查询组件属性集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "组件类别", value = "code"),
            @ApiImplicitParam(name = "组件BaseId", value = "baseId")
    })
    @GetMapping("/parts/{code}/attributes")
    public RespMsg<List<PartsAttribute>> getPartsAttributesById(@PathVariable("code") String code, Long baseId) {
        List<Config> configs = configService.getPartsVisibleCols(code);
        return RespMsg.ok(configService.selectOnePartsGisData(code, baseId, configs));
    }
    /* =============================== 上面三个方法都与获取部件属性集有关 ========================================*/

    /*
     * 查询结果特殊处理，先用switch，后期用工厂模式拆分代码
     * @param selectOneGisData
     * @param code
     * @return
     */
    private List<PartsAttribute> handleResult(List<PartsAttribute> attributes, String code) {

        if (attributes == null) {
            return null;
        }

        switch (code) {
            case "GYLD_LD":
                Iterator<PartsAttribute> it = attributes.iterator();
                String[] cateNames = new String[3];
                Pattern pattern = Pattern.compile("CATE_LEVEL_(\\d)_NAME");
                while (it.hasNext()) {
                    PartsAttribute attribute = it.next();
                    if (attribute.getKey().matches("CATE_LEVEL_\\d_ID")) {
                        it.remove();
                    } else if (attribute.getKey().matches(pattern.pattern())) {
                        Matcher matcher = pattern.matcher(attribute.getKey());
                        if (matcher.matches()) {
                            int level = Integer.parseInt(matcher.group(1));
                            cateNames[level - 1] = (String) attribute.getValue();
                        }
                        it.remove();
                    }
                }
                StringBuilder builder = new StringBuilder();
                for (String cateName : cateNames) {
                    if (cateName != null) {
                        builder.append(cateName).append("/");
                    }
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                PartsAttribute cateAttr = new PartsAttribute("CATE_NAME", "绿地类别", builder.toString());
                attributes.add(0, cateAttr);
                return attributes;
            default:
                return attributes;
        }
    }

    private String getWarnType(List<PartsAttribute> data) {
        for (PartsAttribute attribute : data) {
            if ("WARNING_TYPE_VALUE".equalsIgnoreCase(attribute.getKey())) {
                if (attribute.getValue() == null) attribute.setValue("正常");
                return (String) attribute.getValue();
            }
        }
        return "正常";
    }

    private String getShape(List<PartsAttribute> data) throws Exception {
        Iterator<PartsAttribute> it = data.iterator();
        while (it.hasNext()) {
            PartsAttribute p = it.next();
            if ("SHAPE".equalsIgnoreCase(p.getKey())) {
                Struct shape = (Struct) p.getValue();
                it.remove();
                WKT wkt = new WKT();
                return new String(wkt.fromStruct(shape));
            }
        }
        throw new Exception("没有找到shape信息, no shape field was found");
    }


    // 获取点坐标
    private Object[] getCoords(List<PartsAttribute> data) {
        Object[] ret = new Object[2];
        data.forEach(d -> {
            if ("JD".equalsIgnoreCase(d.getKey())) {
                if (d.getValue() != null) {
                    ret[0] = d.getValue();
                }

            }

            if ("WD".equalsIgnoreCase(d.getKey())) {
                if (d.getValue() != null) {
                    ret[1] = d.getValue();
                }
            }
        });
        return ret;
    }

    private void setShape(GisData gisData, List<PartsAttribute> data) throws Exception {

        Iterator<PartsAttribute> it = data.iterator();
        while (it.hasNext()) {
            PartsAttribute p = it.next();
            if ("WKT".equalsIgnoreCase(p.getKey())) {
                gisData.setShape((String) p.getValue());
            }

            if ("SRID".equalsIgnoreCase(p.getKey())) {
                gisData.setSrid(((BigDecimal) p.getValue()).intValue());
            }
        }
    }
}
