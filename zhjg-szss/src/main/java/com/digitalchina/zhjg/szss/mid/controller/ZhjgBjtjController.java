package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.gis.service.ZhjgBjtjService;
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

/**
 * 部件统计
 * @author shkstart
 * @create 2020-08-11 16:05
 */
@RestController
@RequestMapping("/ZhjgBjtj")
@Api(tags = "部件统计")
public class ZhjgBjtjController {

    @Autowired
    private ZhjgBjtjService zhjgBjtjService;

    /**
     * 部件统计--分页
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    @GetMapping("/partTotal")
    @ApiOperation("部件统计--分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数",dataType = "Integer"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "Date"),
    })
    public RespMsg<Page> selectPartTotal(String startTime, String endTime, Integer partsCateId, Page<ZhjgBjtj> page){
        page.setRecords(zhjgBjtjService.selectZhjgBjtj(page,startTime, endTime, partsCateId));
        return RespMsg.ok(page);
    }

    @GetMapping("/partTotal/export")
    @ApiOperation("部件统计--导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "Date"),
    })
    public void selectPartTotalExport(String startTime, String endTime, Integer partsCateId, HttpServletResponse response){
        List<ZhjgBjtj> listData = zhjgBjtjService.selectZhjgBjtjExport(startTime, endTime, partsCateId);
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // excel标题
        String[] title = { "序号","部件分类","部件名称","北湖区","高新区","长德区","空港区","合计"};
        // excel文件名
        String fileName = "设施类型统计" + sdf.format(dt)+ ".xlsx";
        //sheet
        String sheetName = "设施类型统计数据列表";
        List<Object []> dataList = new ArrayList<>();
        Object content[][] = new Object[listData.size()+2][title.length];
        for(int i = 0; i < listData.size(); i++){
            ZhjgBjtj zhjgBjtj = listData.get(i);
            content[i][0] = (i + 1) + ""; //序号
            content[i][1] = zhjgBjtj.getBjfl(); //部件分类
            content[i][2] = zhjgBjtj.getBjmc(); //部件名称
            content[i][3] = zhjgBjtj.getBhNum(); //北湖区数量
            content[i][4] = zhjgBjtj.getGxNum(); //高新区数量
            content[i][5] = zhjgBjtj.getCdNum(); //长德区数量
            content[i][6] = zhjgBjtj.getKgNum(); //空港区数量
            content[i][7] = zhjgBjtj.getTotal(); //合计
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