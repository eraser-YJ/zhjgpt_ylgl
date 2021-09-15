package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.mid.entity.PartsCategory;
import com.digitalchina.zhjg.szss.mid.entity.ZhjgNotice;
import com.digitalchina.zhjg.szss.mid.service.ZhjgNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新闻通知
 * @author shkstart
 * @create 2020-08-07 10:24
 */
@RestController
@RequestMapping("/ZhjgNotice")
@Api(tags = "新闻通知")
public class ZhjgNoticeController {

    @Autowired
    private ZhjgNoticeService zhjgNoticeService;

    /**
     * 获取新闻内容--根据id
     * @param id
     * @return
     */
    @GetMapping("/noticeById")
    @ApiOperation("获取新闻内容")
    @ApiImplicitParam(name = "id", value = "待获取树顶级ID，若为null，取全部，非null")
    public RespMsg<List<ZhjgNotice>> selectNoticeById(Integer id) {
        return RespMsg.ok(zhjgNoticeService.selectZhjgNotice(id));
    }

    /**
     * 获取新闻内容--分页
     * @param page
     * @return
     */
    @GetMapping("/allNotice")
    @ApiOperation("获取新闻内容")
    @ApiImplicitParam(name = "id", value = "待获取树顶级ID，若为null，取全部，非null")
    public RespMsg<IPage<ZhjgNotice>>  selectAllNotice(Page<ZhjgNotice> page) {
        page.setRecords(zhjgNoticeService.selectZhjgAllNotice(page));
        return RespMsg.ok(page);
}

    /**
     * 新增新闻内容
     * @param zhjgNotice
     * @return
     */
    @PostMapping("/insertNotice")
    @ApiOperation("新增新闻内容")
    @ApiImplicitParam(name = "zhjgNotice", value = "id 序列号，title 标题  content 内容 createtime 创建时间")
    public RespMsg<Integer> insertNotice(ZhjgNotice zhjgNotice){
        return RespMsg.ok(zhjgNoticeService.insetNotice(zhjgNotice));
    }

    /**
     * 删除新闻内容--根据id
     * @param id
     * @return
     */
    @GetMapping("/deleteNotice")
    @ApiOperation("删除新闻内容")
    @ApiImplicitParam(name = "zhjgNotice", value = "id 序列号，根据id，删除对应的新闻内容")
    public RespMsg<Integer> deleteNotice(Integer id){
        return RespMsg.ok(zhjgNoticeService.deleteNotice(id));
    }

    /**
     * 修改新闻内容--根据id
     * @param zhjgNotice
     * @return
     */
    @GetMapping("/updateNotice")
    @ApiOperation("修改新闻内容")
    @ApiImplicitParam(name = "zhjgNotice", value = "id 序列号，根据id，修改对应的新闻内容")
    public RespMsg<Integer> updateNotice(ZhjgNotice zhjgNotice){
        return RespMsg.ok(zhjgNoticeService.updateNotice(zhjgNotice));
    }
}
