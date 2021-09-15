package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.PartsWarnInfo;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgGLF;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgRltj;
import com.digitalchina.zhjg.szss.gis.service.ZhjgRltjService;
import com.digitalchina.zhjg.szss.gis.service.ZhjgRwcsService;
import com.digitalchina.zhjg.szss.utils.ExportUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-12-17 11:41
 */
@RestController
@RequestMapping("/ZhjgZhrl")
@Api(tags = "智慧热力")
public class ZhjgZhrlController {

    @Autowired
    private ZhjgRltjService zhjgRltjService;

    @Autowired
    private ZhjgRwcsService zhjgRwcsService;



    /**
     * 智慧供热-统计查询-供热公司统计
     * @return
     */
    @GetMapping("/selectZhjgRlTj")
    @ApiOperation("供热公司统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "行政区划代码",dataType = "String")
    })
    public RespMsg<List<Map<String, String>>> selectZhjgRlTj(String admdivCode) {
        return RespMsg.ok(zhjgRltjService.selectZhjgRlgsTj(admdivCode));
    }


    /**
     * 智慧供热-统计查询-热网参数分析
     * @param page
     * @param code
     * @param glqy
     * @param glfmc
     * @param hrzmc
     * @return
     */
    @GetMapping("/selectZhjgRwcsTj")
    @ApiOperation("热网参数分析--分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数",dataType = "Integer"),
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String"),
            @ApiImplicitParam(name = "glfmc", value = "锅炉房名称",dataType = "String"),
            @ApiImplicitParam(name = "hrzmc", value = "换热站名称",dataType = "String"),
    })
    public RespMsg<Page> selectZhjgRwcsTj(Page<ZhjgRltj> page, String code, String glqy, String glfmc, String hrzmc){
        page.setRecords(zhjgRwcsService.selectZhjgRwcsTj(page,code, glqy, glfmc, hrzmc));
        return RespMsg.ok(page);
    }


    /**
     * 智慧供热-统计查询-查询供热公司列表
     * @return
     */
    @GetMapping("/selectGsmcList")
    @ApiOperation("查询供热公司列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "行政区划代码",dataType = "String")
    })
    public RespMsg<List<Map<String, String>>> selectGsmcList( ) {
        return RespMsg.ok(zhjgRltjService.selectGsmcList());
    }

    /**
     * 智慧供热-统计查询-查询供热公司列表
     * @return
     */
    @GetMapping("/selectGlfmcList")
    @ApiOperation("查询锅炉房名称列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "行政区划代码",dataType = "String")
    })
    public RespMsg<List<Map<String, String>>> selectGlfmcList( ) {
        return RespMsg.ok(zhjgRltjService.selectGlfmcList());
    }

    /**
     * 智慧供热-统计查询-查询供热公司列表
     * @return
     */
    @GetMapping("/selectHrzmcList")
    @ApiOperation("查询换热站列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "行政区划代码",dataType = "String")
    })
    public RespMsg<List<Map<String, String>>> selectHrzmcList( ) {
        return RespMsg.ok(zhjgRltjService.selectHrzmcList());
    }

    /**
     * 固定测温点小区查询
     * @return
     */
    @GetMapping("/selectXqmcList")
    @ApiOperation("固定测温点小区列表")
    public RespMsg<List<Map<String,String>>> selectXQMCList(){
        return RespMsg.ok(zhjgRltjService.selectXQMCList());
    }


    /**
     * 智慧供热-锅炉房档案
     * @param page
     * @param code
     * @param glqy
     * @param glfmc
     * @return
     */
    @GetMapping("/selectZhjgGLFDA")
    @ApiOperation("锅炉房档案--分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数",dataType = "Integer"),
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String"),
            @ApiImplicitParam(name = "glfmc", value = "锅炉房名称",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public RespMsg<Page> selectZhjgGLFDA(Page<ZhjgGLF> page, String code, String glqy, String glfmc, String startTime, String endTime){
        page.setRecords(zhjgRwcsService.selectZhjgGlfda(page,code, glqy, glfmc, startTime,endTime));
        return RespMsg.ok(page);
    }

    @GetMapping("/GLFDA_export")
    @ApiOperation("锅炉房档案导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String"),
            @ApiImplicitParam(name = "glfmc", value = "锅炉房名称",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public  void GLFDAistExport(String code, String glqy, String glfmc, String startTime, String endTime,HttpServletResponse response) {
        List<ZhjgGLF> listData = zhjgRwcsService.selectZhjgGlfda(code, glqy, glfmc, startTime, endTime);
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // excel标题
        String[] title = {"序号", "地区", "供热企业", "锅炉房名称", "供热能力（MV）", "锅炉房性质", "在网面积（万㎡）", "居民用户数", "是否监测"};
        // excel文件名
        String fileName = "锅炉房档案" + sdf.format(dt) + ".xlsx";
        //sheet
        String sheetName = "锅炉房档案数据列表";

        List<Object[]> dataList = new ArrayList<>();
        Object content[][] = new Object[listData.size() + 2][title.length];
        for (int i = 0; i < listData.size(); i++) {
            ZhjgGLF zhjgGLF = listData.get(i);
            content[i][0] = (i + 1) + ""; //序号
            content[i][1] = zhjgGLF.getXzqh(); //行政区划
            content[i][2] = zhjgGLF.getGlqy(); //供热企业
            content[i][3] = zhjgGLF.getGlfmc(); //锅炉房名称
            content[i][4] = zhjgGLF.getGrnl(); //供热能力（MV）
            content[i][5] = zhjgGLF.getGlfxz(); //锅炉房性质
            content[i][6] = zhjgGLF.getZwmj(); //在网面积
            content[i][7] = zhjgGLF.getJmyhs(); // 居民用户数
            content[i][8] = zhjgGLF.getSfjc(); //是否监测
            dataList.add(content[i]);
        }
        try {
            ExportUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            ExportUtil.export(sheetName, title, dataList, fileName, os);
            ExportUtil.close(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 智慧供热-换热站档案
     * @param page
     * @param code
     * @param glqy
     * @param ssry
     * @param hrzmc
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/selectZhjgHRZDA")
    @ApiOperation("换热站档案--分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数",dataType = "Integer"),
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String"),
            @ApiImplicitParam(name = "ssry", value = "所属热源",dataType = "String"),
            @ApiImplicitParam(name = "hrzmc", value = "换热站名称",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public RespMsg<Page> selectZhjgHRZDA(Page page,String code,String glqy,String ssry,String hrzmc,String startTime,String endTime){
        page.setRecords(zhjgRwcsService.selectZhjgHRZ(page,code, glqy, ssry, hrzmc,startTime,endTime));
        return RespMsg.ok(page);
    }

    @GetMapping("/HRZDA_export")
    @ApiOperation("换热站档案导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String"),
            @ApiImplicitParam(name = "hrzmc", value = "换热站名称",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public  void HRZDAistExport(String code, String glqy, String ssry, String  hrzmc,String startTime, String endTime,HttpServletResponse response) {
        List<Map<String,Object>> listData = zhjgRwcsService.selectZhjgHRZ(code, glqy, ssry, hrzmc,startTime, endTime);
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // excel标题
        String[] title = {"序号", "地区", "换热站名称", "所属供热企业", "所属街道", "所属社区", "在网面积（万㎡）", "居民用户数"};
        // excel文件名
        String fileName = "换热站档案" + sdf.format(dt) + ".xlsx";
        //sheet
        String sheetName = "换热站档案数据列表";

        List<Object[]> dataList = new ArrayList<>();
        Object content[][] = new Object[listData.size() + 2][title.length];
        for (int i = 0; i < listData.size(); i++) {
            Map<String,Object> dataMap = listData.get(i);
            content[i][0] = (i + 1) + ""; //序号
            content[i][1] = dataMap.get("XZQH"); //行政区划
            content[i][2] = dataMap.get("HRZMC"); //换热站名称
            content[i][3] = dataMap.get("SSGS"); //所属供热企业
            content[i][4] = dataMap.get("SSJD"); //所属街道
            Object SSSQ = dataMap.get("SSSQ");
            if(SSSQ==null){
                SSSQ="";
            }
            content[i][5] = SSSQ; //所属社区
            content[i][6] = dataMap.get("ZWMJ"); //在网面积（万㎡）
            content[i][7] = dataMap.get("JMYHS"); // 居民用户数
            dataList.add(content[i]);
        }
        try {
            ExportUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            ExportUtil.export(sheetName, title, dataList, fileName, os);
            ExportUtil.close(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 智慧供热-供热企业档案
     * @param page
     * @param code
     * @param glqy
     * @return
     */
    @GetMapping("/selectZhjgGRQYDA")
    @ApiOperation("供热企业档案--分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数",dataType = "Integer"),
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String")
    })
    public RespMsg<Page> selectZhjgGRQYDA(Page page,String code,String glqy){
        page.setRecords(zhjgRwcsService.selectZhjgGrqyDA(page,code, glqy));
        return RespMsg.ok(page);
    }

    @GetMapping("/GRQYDA_export")
    @ApiOperation("供热企业档案导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String")
    })
    public  void GRQYDAistExport(String code, String glqy,HttpServletResponse response) {
        List<Map<String,Object>> listData = zhjgRwcsService.selectZhjgGrqyDA(code, glqy);
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // excel标题
        String[] title = {"序号", "地区", "供热企业名称", "供热能力（MW）", "供热覆盖范围", "在网面积（万㎡）", "开栓面积（万㎡）"};
        // excel文件名
        String fileName = "供热企业档案" + sdf.format(dt) + ".xlsx";
        //sheet
        String sheetName = "供热企业数据列表";

        List<Object[]> dataList = new ArrayList<>();
        Object content[][] = new Object[listData.size() + 2][title.length];
        for (int i = 0; i < listData.size(); i++) {
            Map<String,Object> dataMap = listData.get(i);
            content[i][0] = (i + 1) + ""; //序号
            content[i][1] = dataMap.get("XZXQ"); //行政区划
            content[i][2] = dataMap.get("GLQY"); //供热企业名称
            content[i][3] = dataMap.get("GRNL"); //供热能力
            content[i][4] = dataMap.get("GRFGFW"); //供热覆盖范围
            content[i][5] = dataMap.get("ZWMJ"); //在网面积（万㎡）
            content[i][6] = dataMap.get("KSMJ"); //在网面积（万㎡）
            dataList.add(content[i]);
        }
        try {
            ExportUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            ExportUtil.export(sheetName, title, dataList, fileName, os);
            ExportUtil.close(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 智慧供热-固定测温点信息
     * @param page
     * @param code
     * @param glqy
     * @return
     */
    @GetMapping("/selectZhjgGDCWD")
    @ApiOperation("固定测温点--分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数",dataType = "Integer"),
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String"),
            @ApiImplicitParam(name = "ssxq", value = "所属小区",dataType = "String"),
            @ApiImplicitParam(name = "yhmc", value = "用户名称",dataType = "String"),
            @ApiImplicitParam(name = "sbzt", value = "设备状态",dataType = "String")
    })
    public RespMsg<Page> selectZhjgGDCWD(Page page,String code,String glqy,String ssxq,String yhmc,String sbzt){
        page.setRecords(zhjgRwcsService.selectZhjgGdcwd(page,code,glqy,ssxq,yhmc,sbzt));
        return RespMsg.ok(page);
    }


    @GetMapping("/GDCWD_export")
    @ApiOperation("固定测温点导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String"),
            @ApiImplicitParam(name = "ssxq", value = "所属小区",dataType = "String"),
            @ApiImplicitParam(name = "yhmc", value = "用户名称",dataType = "String"),
            @ApiImplicitParam(name = "sbzt", value = "设备状态",dataType = "String")
    })
    public  void GDCWDistExport(String code, String glqy,String ssxq,String yhmc,String sbzt,HttpServletResponse response) {
        List<Map<String,Object>> listData = zhjgRwcsService.selectZhjgGdcwd(code, glqy,ssxq,yhmc,sbzt);
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // excel标题
        String[] title = {"序号", "地区", "供热企业名称", "用户名称", "所属小区", "散热器类型", "设备状态"};
        // excel文件名
        String fileName = "固定测温点" + sdf.format(dt) + ".xlsx";
        //sheet
        String sheetName = "固定测温点数据列表";

        List<Object[]> dataList = new ArrayList<>();
        Object content[][] = new Object[listData.size() + 2][title.length];
        for (int i = 0; i < listData.size(); i++) {
            Map<String,Object> dataMap = listData.get(i);
            content[i][0] = (i + 1) + ""; //序号
            content[i][1] = dataMap.get("XZXQ"); //行政区划
            content[i][2] = dataMap.get("GRQY"); //供热企业名称
            content[i][3] = dataMap.get("YHMC"); //用户名称
            content[i][4] = dataMap.get("SSXQ"); //所属小区
            content[i][5] = dataMap.get("SRQLX"); //散热器类型
            content[i][6] = dataMap.get("SBZT"); //设备状态
            dataList.add(content[i]);
        }
        try {
            ExportUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            ExportUtil.export(sheetName, title, dataList, fileName, os);
            ExportUtil.close(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/selectZhjgRYHXX")
    @ApiOperation("热用户信息--分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数",dataType = "Integer"),
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String"),
            @ApiImplicitParam(name = "ssxq", value = "所属小区",dataType = "String"),
            @ApiImplicitParam(name = "ssry", value = "所属热源",dataType = "String"),
            @ApiImplicitParam(name = "yhmc", value = "用户名称",dataType = "String"),
            @ApiImplicitParam(name = "sfgdcw", value = "是否固定测温点",dataType = "String")
    })
    public RespMsg<Page> selectZhjgRYHXX(Page page,String code,String glqy,String ssxq,String yhmc,String ssry,String sfgdcw){
        page.setRecords(zhjgRwcsService.selectZhjgRyhxx(page,code,glqy,ssxq,yhmc,ssry,sfgdcw));
        return RespMsg.ok(page);
    }

    @GetMapping("/RYHXX_export")
    @ApiOperation("热用户导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "行政区域代码",dataType = "String"),
            @ApiImplicitParam(name = "glqy", value = "供热公司名称",dataType = "String"),
            @ApiImplicitParam(name = "ssxq", value = "所属小区",dataType = "String"),
            @ApiImplicitParam(name = "ssry", value = "所属热源",dataType = "String"),
            @ApiImplicitParam(name = "yhmc", value = "用户名称",dataType = "String"),
            @ApiImplicitParam(name = "sfgdcw", value = "是否固定测温点",dataType = "String")
    })
    public  void RYHXXistExport(String code,String glqy,String ssxq,String yhmc,String ssry,String sfgdcw,HttpServletResponse response) {
        List<Map<String,Object>> listData = zhjgRwcsService.selectZhjgRyhxx(code, glqy,ssxq,yhmc,ssry,sfgdcw);
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // excel标题
        String[] title = {"序号", "地区", "供热企业名称", "用户名称", "所属小区", "所属热源", "是否固定测温点"};
        // excel文件名
        String fileName = "热用户信息" + sdf.format(dt) + ".xlsx";
        //sheet
        String sheetName = "热用户信息数据列表";

        List<Object[]> dataList = new ArrayList<>();
        Object content[][] = new Object[listData.size() + 2][title.length];
        for (int i = 0; i < listData.size(); i++) {
            Map<String,Object> dataMap = listData.get(i);
            content[i][0] = (i + 1) + ""; //序号
            content[i][1] = dataMap.get("XZQH"); //行政区划
            content[i][2] = dataMap.get("SSZGS"); //供热企业名称
            content[i][3] = dataMap.get("ZHXM"); //用户名称
            content[i][4] = dataMap.get("SSXQ"); //所属小区
            content[i][5] = dataMap.get("SSRY"); //散热器类型
            content[i][6] = dataMap.get("SFGDCW"); //设备状态
            dataList.add(content[i]);
        }
        try {
            ExportUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            ExportUtil.export(sheetName, title, dataList, fileName, os);
            ExportUtil.close(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
