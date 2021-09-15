package com.digitalchina.admin.mid.controller.warn;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.admin.constant.AppBitCode;
import com.digitalchina.admin.gis.entity.Config;
import com.digitalchina.admin.gis.service.ConfigService;
import com.digitalchina.admin.gis.service.ExportDataService;
import com.digitalchina.admin.mid.dto.layer.LayerCondition;
import com.digitalchina.admin.mid.dto.layer.LayerConfig;
import com.digitalchina.admin.mid.dto.layer.LayerProp;
import com.digitalchina.admin.mid.dto.request.CommonRequest;
import com.digitalchina.admin.utils.ExportExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = "导出excel通用Controller")
@RestController
@RequestMapping("/admin/layer/data")
public class ExportDataController {
    @Autowired
    private ConfigService configService;

    @Autowired
    private ExportDataService exportDataService;

    /**
     * 其中app为1,2,3
     * 1:市政设施-设备信息管理
     * 2:绿化资源管理,地下管网-管网信息管理,监控一张图-绿化工程
     * 3:市政设施-综合查询
     * @param request
     * @param module
     * @param app
     * @param response
     */
    @PostMapping("export/{app}/{module}")
    @ApiOperation("1:市政设施-设备信息管理 2:绿化资源管理,地下管网-管网信息管理,监控一张图-绿化工程 3:市政设施-综合查询 导出功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "请求参数对象", value = "request"),
            @ApiImplicitParam(name = "请求模块module", value = "parts市政城市部件，flood防汛排水, park公园绿地, tubenet管网, hot供热")
    })
    public void eparts_info_listxport(@RequestBody CommonRequest request, @PathVariable String module, @PathVariable("app") int app, HttpServletResponse response){
        String code = request.getCode();
        if(StringUtils.isEmpty(code)){
            code="市政设施统计查询综合查询";
        }
        // excel文件名
        String fileName=null;
        //excelsheet名
        String sheetName = null;

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Config> configs;
        List<LayerProp> listP = null;
        //excel标题
        List<String> excelTitle = new ArrayList<>();
        List<Map<String,String>> ListMap = new ArrayList<>();
        if(app==1){ //市政设施

            //原返回的结构不动
            configs = configService.list(Condition.<Config>create().eq(Config.TB, code).orderByAsc("fdsort"));
            System.out.println(configs);
            LayerConfig result = new LayerConfig(code);
            result.setConditions(getConditions2(configs));
            // 除config中构建的condtion，额外构建通用conditions
            configService.buildPartsBaseCondtions(result.getConditions());

            result.setProps(getProps(configs, AppBitCode.ZHJG));
            // 除config中获取到的props，额外构建通用props
            configService.buildPartsBaseProps(result.getProps());
            System.out.println(result);
            //循环LayerConfig中的props
            listP = result.getProps();

            fileName = code+"数据" + sdf.format(dt) + ".xlsx";
            sheetName = "数据列表";

        }else if(app==2){ //绿化资源
            configs = configService.list(Condition.<Config>create().eq(Config.TB, code).orderByAsc("FDSORT"));
            LayerConfig result = new LayerConfig(code);
            result.setConditions(getConditions2(configs));
            result.setProps(getProps(configs, app));
            handleConfigResult(result, code);
            //循环LayerConfig中的props
            listP = result.getProps();
            if(code.equals("GYLD_LD")){ //绿地信息管理
                fileName = "绿地信息管理"+ sdf.format(dt) + ".xlsx";
                sheetName= "绿地信息管理数据";
            }else if(code.equals("GYLD_GY")){ //公园信息管理
                fileName = "公园信息管理"+ sdf.format(dt) + ".xlsx";
                sheetName= "公园信息管理数据";
            }else if(code.equals("GYLD_GSMM")){ //古树名木管理
                fileName = "古树名木管理"+ sdf.format(dt) + ".xlsx";
                sheetName= "古树名木管理数据";
            }else if(code.equals("GYLD_XDS")){ //行道树管理
                fileName = "行道树管理"+ sdf.format(dt) + ".xlsx";
                sheetName= "行道树管理数据";
            }else if(code.equals("GYLD_LAW_FILE")){ // 法律规制管理
                fileName = "法律规制管理"+ sdf.format(dt) + ".xlsx";
                sheetName= "法律规制管理数据";
            }
        }else if(app==3){ //综合查询 module 传parts_search

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
            //循环LayerConfig中的props
            listP = result.getProps();
            fileName = "综合查询"+ sdf.format(dt) + ".xlsx";
            sheetName= "综合查询数据";
        }

        //查询出需要展示的字段中文和字段名
        for(LayerProp layerProp : listP){
            if(layerProp.getTable()){ //table为true则为显示字段
                Map<String,String> map = new HashMap<>();
                map.put("text",layerProp.getText());
                map.put("field",layerProp.getField());
                ListMap.add(map);
                //将需要显示的字段中文放入要导出的excel标题
                excelTitle.add(layerProp.getText());
            }
        }
        String[] title = new String[excelTitle.size()];
        excelTitle.toArray(title);
        //查询数据
        List<Map<String, Object>> listData = exportDataService.customAll(request,module);
        List<Object []> dataList = new ArrayList<>();
        Object content[][] = new Object[listData.size()+2][excelTitle.size()];
        //循环要获取的字段值
            for(int i=0;i<listData.size();i++){
                Map<String, Object> mapData = listData.get(i);
                for(int j = 0; j<ListMap.size(); j++){
                    Map<String,String> mapField = ListMap.get(j);
                    Object data = mapData.get(mapField.get("field"));
                    if(data==null){
                    data="";
                }
                content[i][j] = data;
            }
                dataList.add(content[i]);

        }
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            ExportExcel.export(sheetName, title, dataList, fileName, os);
            ExportExcel.close(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 其中app为2
     * @param request
     * @param module
     * @param app
     * @param response
     */
    @PostMapping("exportYLLife/{app}/{module}")
    @ApiOperation("园林绿地生命周期导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "请求参数对象", value = "request"),
            @ApiImplicitParam(name = "请求模块module", value = "parts市政城市部件，flood防汛排水, park公园绿地, tubenet管网, hot供热")
    })
    public void epartsYLLifw_info_listxport(@RequestBody CommonRequest request, @PathVariable String module, @PathVariable("app") int app, HttpServletResponse response){
        String code = request.getCode();
        //excel标题
        List<String> excelTitle = new ArrayList<>();
        List<Map<String,String>> ListMap = new ArrayList<>();
        List<Config> configs = configService.list(Condition.<Config>create().eq(Config.TB, code).orderByAsc("FDSORT"));
        LayerConfig result = new LayerConfig(code);
        result.setConditions(getConditions2(configs));
        result.setProps(getProps(configs, app));
        handleConfigResult(result, code);
        result.getProps().add(new LayerProp("年份","TO_CHAR(SYSDATE,'YYYY')-TO_CHAR(DATE_BUILD,'YYYY')",LayerProp.TYPE_NUMBER,1.0,1.0,1.0,null));
        List<LayerProp> listP = result.getProps();
        //查询出需要展示的字段中文和字段名
        for(LayerProp layerProp : listP){
            if(layerProp.getTable()){ //table为true则为显示字段
                Map<String,String> map = new HashMap<>();
                map.put("text",layerProp.getText());
                map.put("field",layerProp.getField());
                ListMap.add(map);
                //将需要显示的字段中文放入要导出的excel标题
                excelTitle.add(layerProp.getText());
            }
        }
        String[] title = new String[excelTitle.size()];
        excelTitle.toArray(title);
        //查询数据
        List<Map<String, Object>> listData = exportDataService.customAll(request,module);
        List<Object []> dataList = new ArrayList<>();
        Object content[][] = new Object[listData.size()+2][excelTitle.size()];
        //循环要获取的字段值
        for(int i=0;i<listData.size();i++){
            Map<String, Object> mapData = listData.get(i);
            for(int j = 0; j<ListMap.size(); j++){
                Map<String,String> mapField = ListMap.get(j);
                Object data = mapData.get(mapField.get("field"));
                if(data==null){
                    data="";
                }
                content[i][j] = data;
            }
            dataList.add(content[i]);

        }
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // excel文件名
        String fileName = "园林绿地生命周期" + sdf.format(dt) + ".xlsx";
        //excelsheet名
        String sheetName = "园林绿地生命周期数据";

        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            ExportExcel.export(sheetName, title, dataList, fileName, os);
            ExportExcel.close(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    // 发送响应流方法
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                //fileName = new String(fileName.getBytes(), "ISO8859-1");
                fileName = new String(fileName.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            //response.setContentType("application/octet-stream;charset=ISO8859-1");
            //response.setContentType("application/binary;charset=UTF-8");
            //response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            //response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName+".xls", "UTF-8"));
            //response.addHeader("Pargam", "no-cache");
            //response.addHeader("Cache-Control", "no-cache");
            response.setHeader("Content-Disposition", "inline;filename="+ new String(fileName.getBytes("utf-8"),"iso8859-1"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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

}
