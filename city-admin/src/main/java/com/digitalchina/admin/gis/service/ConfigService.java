package com.digitalchina.admin.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.constant.enums.GisType;
import com.digitalchina.admin.gis.dto.PartsAttribute;
import com.digitalchina.admin.gis.dto.VideoParts;
import com.digitalchina.admin.gis.entity.Config;
import com.digitalchina.admin.mid.dto.layer.LayerCondition;
import com.digitalchina.admin.mid.dto.layer.LayerConfig;
import com.digitalchina.admin.mid.dto.layer.LayerListRequest;
import com.digitalchina.admin.mid.dto.layer.LayerProp;
import com.digitalchina.admin.mid.dto.request.CommonRequest;
import com.digitalchina.admin.mid.dto.request.RequestCondition;
import com.digitalchina.admin.mid.entity.PartsCategory;
import oracle.spatial.util.GeometryExceptionWithContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ryan
 * @since 2020-03-11
 */
public interface ConfigService extends IService<Config> {

    /**
     * 获取所有图层
     *
     * @return
     */
    List<Config> getAllLayer();

    /**
     * 图层数据列表
     *
     * @param request
     * @return
     */
    Page<Map<String, Object>> customPage(LayerListRequest request);

    /**
     * 编辑图层数据
     *
     * @param config
     */
    void customEdit(LayerConfig config);


    /**
     * 图层数据列表
     *
     * @param request
     * @return
     */
    Page<Map<String, Object>> customPage(CommonRequest request, String module);


    /**
     * 额外构建智慧城市市政设施部件通用的conditions
     *
     * @param conditions
     */
    void buildPartsBaseCondtions(List<LayerCondition> conditions);

    /**
     * 额外构建智慧城市市政设施部件通用的props
     *
     * @param props
     */
    void buildPartsBaseProps(List<LayerProp> props);

    /**
     * 新增GIS资源
     *
     * @param config
     * @param module
     */
    void customInsert(LayerConfig config, String module);

    /**
     * 获取当前资源可在这详情页显示的字段信息
     *
     * @param code
     * @return
     */
    List<Config> getVisibleCols(String code);

    /**
     * 获取当前资源可在这详情页显示的字段信息
     *
     * @param code
     * @param appBit 应用的标识，取自AppBit常量类
     * @return
     */
    List<Config> getVisibleCols(String code, int appBit);

    /**
     * 获取市政设施部件资源可在这详情页显示的字段信息<br>
     * 实际为parts_base信息 + config配置信息
     *
     * @param code
     * @return
     */
    List<Config> getPartsVisibleCols(String code);

    /**
     * 获取市政设施部件资源可在这详情页显示的字段信息<br>
     * 实际为parts_base信息 + config配置信息
     *
     * @param code
     * @param appBit 应用的标识，取自AppBit常量类
     * @return
     */
    List<Config> getPartsVisibleCols(String code, int appBit);

    /**
     * 按条件查询特定GIS资源，并将每一条数据按字段名/值顺序返回，便于前端按字段顺序展示
     *
     * @param code
     * @param conditions
     * @param configs    设定字段顺序与名字的依据
     * @return
     */
    List<List<PartsAttribute>> listGisDatas(String code, Map<String, RequestCondition> conditions, List<Config> configs);

    /**
     * 按条件查询市政设施部件GIS资源，并将每一条数据按字段名/值顺序返回，便于前端按字段顺序展示
     *
     * @param code
     * @param conditions
     * @param configs    设定字段顺序与名字的依据
     * @return
     */
    List<List<PartsAttribute>> listPartsGisDatas(String code, Map<String, RequestCondition> conditions, List<Config> configs);

    /**
     * 从gis资源表查询一个资源详情用于展示
     *
     * @param code
     * @param objectId
     * @param configs
     * @return
     */
    List<PartsAttribute> selectOneGisData(String code, long objectId, List<Config> configs);

    /**
     * 从gis资源表查询一个资源详情用于展示
     *
     * @param code
     * @param baseId
     * @param configs
     * @return
     */
    List<PartsAttribute> selectOnePartsGisData(String code, long baseId, List<Config> configs);

       /**
     * 按条件查询市政设施部件GIS资源，并将每一条数据按字段名/值顺序返回，便于前端按字段顺序展示<br>
     * 同时查询部件当前预警状态
     *
     * @param code
     * @param conditions
     * @param configs    设定字段顺序与名字的依据
     * @return
     */
    List<List<PartsAttribute>> listPartsGisDatasWithWarnInfo(String code, Map<String, RequestCondition> conditions, List<Config> configs);


    /**
     * 按条件查询市政设施部件GIS资源，并将每一条数据按字段名/值顺序返回，便于前端按字段顺序展示<br>
     * 同时查询积水点相关信息
     *
     * @param code
     * @param conditions
     * @param configs    设定字段顺序与名字的依据
     * @return
     */
    List<List<PartsAttribute>> listPartsGisDatasJSD(String code, Map<String, RequestCondition> conditions, List<Config> configs);

    /**
     * 按条件查询市政设施部件GIS资源，并将每一条数据按字段名/值顺序返回，便于前端按字段顺序展示<br>
     * 同时查询排水点相关信息
     *
     * @param code
     * @param conditions
     * @param configs    设定字段顺序与名字的依据
     * @return
     */
    List<List<PartsAttribute>> listPartsGisDatasPSD(String code, Map<String, RequestCondition> conditions, List<Config> configs);


    /**
     * 新增绿地
     *
     * @param config
     */
    void creatGreenbelt(LayerConfig config);

    /**
     * 加载所有摄像头点位
     *
     * @param videoCates
     * @return
     */
    List<VideoParts> listVideoDatas(List<PartsCategory> videoCates) throws SQLException, GeometryExceptionWithContext;

    /**
     * 更新部件地理位置信息
     *
     * @param code
     * @param objectId
     * @param wkt
     */
    void updateShape(String code, Long objectId, String wkt, int srid) throws Exception;

    /**
     * 获取部件地理位置相关信息
     *
     * @param code
     * @param objectId
     * @return
     */
    Map<String, Object> getGisInfo(String code, long objectId) throws SQLException, GeometryExceptionWithContext;

    /**
     * 单表按传入参数新增，除ObjectId都是用传入参数
     *
     * @param config
     */
    void customInsert(LayerConfig config);

    /**
     * 展示 gis定位必须的列
     *
     * @return
     */
    List<Config> getGisCols(String module, String code, GisType gisType);


    void translateShapeToWkt(String code);

    /**
     * 新增行道树和古树名木
     *
     * @param config
     */
    void creatShu(LayerConfig config);

    /**
     * 按位置关键词查找视频部件
     *
     * @param videoCates
     * @param keyword
     * @return
     */
    List<Map<String, Object>> searchVideos(List<PartsCategory> videoCates, String keyword);

    /**
     * 按分类加载绿地资源gis数据
     *
     * @param cateId
     * @return
     */
    List<Map<String, Object>> listGreenbelts(Long cateId);

    /**
     * 按特定字段分类统计资源数量
     *
     * @param code
     */
    List<Map<String, Object>> cntByDiv(String code, String field);

    void translateWktToShape(String code) throws SQLException;

    void deleteObject(String code, Long objectId);

    List<PartsAttribute> selectOneAfforestResourceGisData(String code, Long objectId, List<Config> configs);
}
