package com.digitalchina.admin.mid.controller.warn;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.constant.AppBitCode;
import com.digitalchina.admin.gis.entity.Config;
import com.digitalchina.admin.gis.service.ConfigService;
import com.digitalchina.admin.mid.dto.layer.LayerCondition;
import com.digitalchina.admin.mid.dto.layer.LayerConfig;
import com.digitalchina.admin.mid.dto.layer.LayerListRequest;
import com.digitalchina.admin.mid.dto.layer.LayerProp;
import com.digitalchina.admin.mid.dto.request.CommonRequest;
import com.digitalchina.common.utils.MapUtil;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.TransConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = "图层数据管理（城市部件）")
@RestController
@RequestMapping("/admin/layer/data")
public class LayerDataController {

    @Autowired
    private ConfigService configService;

    @GetMapping("config")
    @ApiOperation("图层配置")
    @ApiImplicitParam(name = "code", value = "图层编码")
    public RespMsg<LayerConfig> config(String code, Integer app) {

        if (app == null) {
            app = AppBitCode.CSYX;
        } // 1为城市运行，2为智慧建管

        List<Config> configs = configService.list(Condition.<Config>create().eq(Config.TB, code).orderByAsc("FDSORT"));
        LayerConfig result = new LayerConfig(code);
        if (app == AppBitCode.CSYX) {
            result.setConditions(getConditions(configs));
        } else {
            result.setConditions(getConditions2(configs));
        }
        result.setProps(getProps(configs, app));

        handleConfigResult(result, code);

        return RespMsg.ok(result);
    }


    /**
     * 分析评价\园林绿地生命周期查询
     * @param code
     * @param app
     * @return
     */
    @GetMapping("configLD")
    @ApiOperation("析评价\\园林绿地生命周期查询")
    @ApiImplicitParam(name = "code", value = "图层编码")
    public RespMsg<LayerConfig> configLD(String code, Integer app) {

        if (app == null) {
            app = AppBitCode.CSYX;
        } // 1为城市运行，2为智慧建管

        List<Config> configs = configService.list(Condition.<Config>create().eq(Config.TB, code).orderByAsc("FDSORT"));
        LayerConfig result = new LayerConfig(code);
        if (app == AppBitCode.CSYX) {
            result.setConditions(getConditions(configs));
        } else {
            result.setConditions(getConditions2(configs));
        }
        result.setProps(getProps(configs, app));

        handleConfigResult(result, code);
        result.getProps().add(new LayerProp("年份","TO_CHAR(SYSDATE,'YYYY')-TO_CHAR(DATE_BUILD,'YYYY')",LayerProp.TYPE_NUMBER,1.0,1.0,1.0,null));
        return RespMsg.ok(result);
    }

    // 临时用一用
    private void handleConfigResult(LayerConfig result, String code) {
        switch (code) {
            case "GYLD_LD":
                result.getProps().add(0, new LayerProp("绿地类型", "CATE_ID", "tree", 0.,
                        0., 1., null, "/zhjgszss/parts_category/tree?module=greenbelt", "id",
                        "name", null));
                break;
        }
    }

    @GetMapping("parts_config")
    @ApiOperation("仅适用于智慧建管城市设施管理的图层配置接口")
    @ApiImplicitParam(name = "code", value = "图层编码")
    public RespMsg<LayerConfig> partsConfig(String code) {
        List<Config> configs = configService.list(Condition.<Config>create().eq(Config.TB, code).orderByAsc("fdsort"));
        LayerConfig result = new LayerConfig(code);

        result.setConditions(getConditions2(configs));
        // 除config中构建的condtion，额外构建通用conditions
        configService.buildPartsBaseCondtions(result.getConditions());

        result.setProps(getProps(configs, AppBitCode.ZHJG));
        // 除config中获取到的props，额外构建通用props
        configService.buildPartsBaseProps(result.getProps());

        return RespMsg.ok(result);
    }

    @GetMapping("parts_search_config")
    @ApiOperation("市政设施统计查询综合查询模块")
    public RespMsg<LayerConfig> partsSearchConfig() {

        LayerConfig result = new LayerConfig(null);
        result.setConditions(new ArrayList<>());
        result.setProps(new ArrayList<>());


        LayerCondition cateCondition = new LayerCondition("部件分类", "PARTS_CATE_ID", LayerProp.TYPE_TREE, "eq",
                "/zhjgszss/parts_category/tree?module=parts", "id", "name");
        result.getConditions().add(cateCondition);
        // 其它base查询条件
        configService.buildPartsBaseCondtions(result.getConditions());
        LayerCondition dateCondition = new LayerCondition("统计时间", "DATE_CREATED", LayerProp.TYPE_DATE_RANGE);
        result.getConditions().add(dateCondition);

        // base属性
        configService.buildPartsBaseProps(result.getProps());

        result.getProps().add(0,
                new LayerProp("预警类型", "WARNING_TYPE_ID", LayerProp.TYPE_NUMBER, 1., 0., 0., "parts_base")
        );

        // 取出base属性中养护单位，设置其可在表格中显示
        for (LayerProp prop : result.getProps()) {
            if ("MAINTAINER_NAME".equalsIgnoreCase(prop.getField())) {
                prop.setTable(true);
                break;
            }
        }

        return RespMsg.ok(result);
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public RespMsg<Page<Map<String, Object>>> list(@RequestBody LayerListRequest request) {
        return RespMsg.ok(configService.customPage(request));
    }

    @PostMapping("list2/{module}")
    @ApiOperation("智慧建管平台使用的调用参数变更版数据列表接口，简化请求数据，同时增加条件支持")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "请求参数对象", value = "request"),
            @ApiImplicitParam(name = "请求模块", value = "parts市政城市部件，flood防汛排水, park公园绿地, tubenet管网, hot供热")
    })
    public RespMsg<Page<Map<String, Object>>> list2(@RequestBody CommonRequest request, @PathVariable String module) {
        return RespMsg.ok(configService.customPage(request, module));
    }

    @PostMapping("/parts_search")
    @ApiOperation("市政设施》统计查询》综合查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "请求参数对象", value = "request"),
    })
    public RespMsg<Page<Map<String, Object>>> partsSearch(@RequestBody CommonRequest request) {
        return RespMsg.ok(configService.customPage(request, "parts_search"));
    }

    @PostMapping("edit")
    @ApiOperation("编辑")
    @Transactional(value = TransConstant.GIS_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public RespMsg<Void> edit(@RequestBody LayerConfig config) {
        configService.customEdit(config);
        return RespMsg.ok();
    }

    @PostMapping("create/{module}")
    @ApiOperation("新增")
    @Transactional(value = TransConstant.GIS_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public RespMsg<Void> create(@RequestBody LayerConfig config, @PathVariable("module") String module) {

        switch (module) {
            case "parts": // 市政设施-部件
            case "tubenet": // 地下管网
                configService.customInsert(config, module);
                break;
            case "gyld_ld": // 绿地
                configService.creatGreenbelt(config);
                break;
            case "gyld_xds":
            case "gyld_gsmm":
                configService.creatShu(config);
                break;
            default:
                configService.customInsert(config);
        }
        return RespMsg.ok();
    }

    @GetMapping("delete")
    @ApiOperation("删除")
    public RespMsg<Void> delete(String code, Long objectId) {
        configService.deleteObject(code, objectId);
        return RespMsg.ok();
    }

    private List<LayerProp> getProps(List<Config> configs, int appBit) {
        List<LayerProp> pconfigs = configs
                .stream()
                .filter(c -> (c.getVisibleMask() & appBit) == appBit)
                .map(c -> {
                    return new LayerProp(c.getFdnm(), c.getFd(), c.getType(), c.getList(), c.getEdit(),
                            c.getAddable(), null, c.getSource(), c.getSourceDataIdField(),
                            c.getSourceDataLabelField(), c.getRelateNameField());
                }).collect(Collectors.toList());
        return pconfigs;
    }

    @GetMapping("all")
    @ApiOperation("所有图层")
    public RespMsg<List<Map<String, Object>>> all() {
        List<Config> configs = configService.getAllLayer();
        List<Map<String, Object>> result = new ArrayList<>();
        configs.forEach(c -> {
            result.add(MapUtil.getHashMap("code", c.getTb(), "name", c.getTbnm()));
        });
        return RespMsg.ok(result);
    }

    private List<LayerCondition> getConditions(List<Config> configs) {
        Stream<Config> fconds = configs.stream().filter(c -> {
            return Double.valueOf(1).equals(c.getCondition());
        });
        List<LayerCondition> conds = new ArrayList<>();
        fconds.forEach(c -> {
            conds.add(new LayerCondition(
                    c.getFdnm(), c.getFd(), c.getType(), c.getOp(), c.getSource(),
                    c.getSourceDataIdField(), c.getSourceDataLabelField()
            ));
        });
        return conds;
    }

    // 市政设施用的，避免影响城市运行，复制一个方法
    private List<LayerCondition> getConditions2(List<Config> configs) {
        Stream<Config> fconds = configs.stream().filter(c -> {
            return Double.valueOf(1).equals(c.getCondition());
        });
        List<LayerCondition> conds = new ArrayList<>();
        fconds.forEach(c -> {
            // 日期型转为日期range
            String type = LayerProp.TYPE_DATE.equalsIgnoreCase(c.getType()) ? LayerProp.TYPE_DATE_RANGE : c.getType();
            conds.add(new LayerCondition(
                    c.getFdnm(), c.getFd(), type, c.getOp(), c.getSource(),
                    c.getSourceDataIdField(), c.getSourceDataLabelField()
            ));
        });
        return conds;
    }
}
