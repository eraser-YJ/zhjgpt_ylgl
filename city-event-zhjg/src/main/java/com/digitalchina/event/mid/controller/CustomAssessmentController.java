package com.digitalchina.event.mid.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.utils.ExportExcelUtils;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.dto.CustomAssessmentDto;
import com.digitalchina.event.mid.service.HiProcinstService;
import com.digitalchina.modules.constant.enums.EventTypeEnum;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自定义考核 前端控制器
 * </p>
 *
 * @author liuping
 * @since 2019-09-11
 */
@Api(tags = "自定义考核接口")
@RestController
@RequestMapping("/customassessment")
public class CustomAssessmentController {

    private final static String EXPORTED_FLAG_PRE = "exportedFlag:";

    @Autowired
    private HiProcinstService hiProcinstService;

    private static final Logger LOG = LoggerFactory.getLogger(CustomAssessmentController.class);

    @Authorize
    @PostMapping(value = "page", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("自定义考核分页列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "类型:默认AREA,可选值： AREA  DEPT", dataType = "String", required = false),
            @ApiImplicitParam(name = "startTime", value = "开始时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
            @ApiImplicitParam(name = "endTime", value = "结束时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false)})
    public RespMsg<IPage<CustomAssessmentDto>> page(@RequestParam(defaultValue = "AREA") String type,
                                                    @RequestParam(required = false)String startTime, @RequestParam(required = false)String endTime,
                                                    @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
        if(!type.equals(EventTypeEnum.AREA.getCode())
                &&!type.equals(EventTypeEnum.DEPT.getCode())){
            throw new ServiceException("参数错误！");
        }
        IPage<CustomAssessmentDto> page = new Page<>(current, size);
        return RespMsg.ok(hiProcinstService.customAssessmentByType(page, type, startTime, endTime));
    }

    @Authorize
    @PostMapping(value = "list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("自定义考核列表,不分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "类型:默认AREA,可选值： AREA  DEPT", dataType = "String", required = false),
            @ApiImplicitParam(name = "startTime", value = "开始时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
            @ApiImplicitParam(name = "endTime", value = "结束时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false)})
    public RespMsg<List<CustomAssessmentDto>> list(@RequestParam(defaultValue = "AREA") String type,
                                                     @RequestParam(required = false)String startTime, @RequestParam(required = false)String endTime) {
        if(!type.equals(EventTypeEnum.AREA.getCode())
                &&!type.equals(EventTypeEnum.DEPT.getCode())){
                throw new ServiceException("参数错误！");
        }
        return RespMsg.ok(hiProcinstService.customAssessmentByType( type, startTime, endTime));
    }

    @Authorize
    @GetMapping("overview")
    @ApiOperation("评价概览,统计全部")
    public RespMsg<CustomAssessmentDto> overview() {
        return RespMsg.ok(hiProcinstService.customAssessmentTotal());
    }


    @GetMapping("export")
    @ApiOperation("导出")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "类型:默认AREA,可选值： AREA  DEPT", dataType = "String", required = false),
            @ApiImplicitParam(name = "startTime", value = "开始时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
            @ApiImplicitParam(name = "endTime", value = "结束时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
            @ApiImplicitParam(name = "exportKey", value = "请求唯一序列号，可用于检查是否导出完成", dataType = "String", required = false)})
    public void export(@RequestParam(defaultValue = "AREA") String type, @RequestParam(required = false)String startTime,
                       @RequestParam(required = false)String endTime, @RequestParam(required = false)String exportKey,
                          HttpServletRequest request, HttpServletResponse response) {
        String name = EventTypeEnum.AREA.getDesc();
        if(!type.equals(EventTypeEnum.AREA.getCode()) && (null != (name = EventTypeEnum.DEPT.getDesc())
                &&!type.equals(EventTypeEnum.DEPT.getCode()))){
            throw new ServiceException("参数错误！");
        }
        LOG.info(name);
        setExportFlag(request,exportKey,false);
        String srcPath = "excel" + File.separator + "CustomAssessment.xls";
        List<CustomAssessmentDto> datalist = hiProcinstService.customAssessmentByType(type, startTime, endTime);
        //数据填充
        Map<String, Object> beans = new HashMap<>();
        beans.put("name",name);
        beans.put("datalist",datalist);
        try {
            //获得导出模板
            File file = ResourceUtils.getFile("classpath:" + srcPath);
            if (!file.exists()) {
                LOG.error("导出模板找不到！");
            }
            String destFileName = "考核结果.xls";
            // 准备输出流
            OutputStream os = null;
            // 设置响应
            response.setContentType("application/x-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(destFileName.getBytes(), "iso8859-1"));
            os = response.getOutputStream();
            ExportExcelUtils.excprtStudentExcel(beans, file.getPath(), os);
            setExportFlag(request,exportKey,true);
        } catch (Exception e) {
           if(LOG.isDebugEnabled()){
               LOG.debug(e.getMessage());
           }
        }
    }


    /**
     * 获取导出是否结束
     *
     * @param request
     * @return
     */
    @GetMapping("isExport")
    @ApiOperation(value = "获取导出是否结束", notes="返回值：-1 不存在 ,0 未结束,1 已结束")
    @ResponseBody
    @ApiImplicitParam(name = "exportKey", value = "请求唯一序列号", dataType = "String", required = true)
    public int isExport(String exportKey, HttpServletRequest request) {
         Object exportedFlag = request.getSession().getAttribute(EXPORTED_FLAG_PRE + exportKey);
         if(ObjectUtil.isEmpty(exportedFlag)){
            return -1;
         }
         if((Boolean) exportedFlag){
             request.getSession().removeAttribute(EXPORTED_FLAG_PRE + exportKey);
            return 1;
         }
        return 0;
    }

    /**
     * 设置导出标志
     * @param request
     * @param exportKey
     * @param exportedFlag
     */
    private void setExportFlag(HttpServletRequest request,String exportKey,boolean exportedFlag){
        if(StringUtils.isBlank(exportKey)){
            return;
        }
        request.getSession().setAttribute(EXPORTED_FLAG_PRE + exportKey, exportedFlag);
    }

}
