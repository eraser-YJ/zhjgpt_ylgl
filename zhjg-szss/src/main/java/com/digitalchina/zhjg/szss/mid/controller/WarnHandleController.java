package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.*;
import com.digitalchina.zhjg.szss.gis.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 预警处置
 * @author shkstart
 * @create 2020-08-07 10:24
 */
@RestController
@RequestMapping("/WarnHandle")
@Api(tags = "预警处置")
public class WarnHandleController {

    @Autowired
    private WarnHandleService warnHandleService;

    @Autowired
    private PondingPointWarnService pondingPointWarnService;

    @Autowired
    private DrainagePipeWarnService drainagePipeWarnService;


    @Autowired
    private WarnHandleStatusService warnHandleStatusService;


    /**
     * 第一次处置预警信息---插入预警处置信息
     * 此接口是 通过积水点预警或排水官网预警 处置追踪插入的数据  状态status 默认为0  未处理/开始状态
     * 同时更新SZSS_WARN_HANDLE，SZSS_WARN_HANDLE_STATUS 两张表
     * 要去必须上传jcdlx（监测点类型），否则后期无法进行数据筛选
     * @param warnHandleParam
     * @return
     */

    @PostMapping("/insertFirstWarnHandleInfo")
    @ApiOperation("第一次插入预警处置信息--jcdlx（监测点类型）必输")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "jcdlx", value = "监测点类型",dataType = "String"),
            @ApiImplicitParam(name = "status", value = "处置状态：0 开始 1 处置中 2关闭",dataType = "String"),
            @ApiImplicitParam(name = "czdw", value = "处置单位",dataType = "String"),
            @ApiImplicitParam(name = "czr", value = "处置人",dataType = "String"),
            @ApiImplicitParam(name = "sbsj", value = "上报时间",dataType = "String"),
            @ApiImplicitParam(name = "yjdj", value = "预警等级",dataType = "String"),
            @ApiImplicitParam(name = "yjlx", value = "预警类型",dataType = "String"),
            @ApiImplicitParam(name = "jjcd", value = "紧急程度",dataType = "String"),
            @ApiImplicitParam(name = "cznr", value = "处置内容",dataType = "String"),
            @ApiImplicitParam(name = "dxnr", value = "短信内容",dataType = "String"),
            @ApiImplicitParam(name = "lednr", value = "LED内容",dataType = "String"),
            @ApiImplicitParam(name = "sjbh", value = "事件编号",dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "手机号",dataType = "String"),
            @ApiImplicitParam(name = "yjcz", value = "预警处置",dataType = "String")
    })
    public RespMsg<Integer> insertFirstWarnHandleInfo(WarnHandleParam warnHandleParam) {
        Integer intStr = 1;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStr = sdf.format(new Date());
            WarnHandle warnHandle = new WarnHandle();
            WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
            warnHandle.setCzdw(warnHandleParam.getCzdw());
            warnHandle.setCzr(warnHandleParam.getCzr());
            warnHandle.setJcdlx(warnHandleParam.getJcdlx());
            warnHandle.setJjcd(warnHandleParam.getJjcd());
            //warnHandle.setSbsj(timeStr);
            warnHandle.setStatus("3");
            warnHandle.setYjdj(warnHandleParam.getYjdj());
            warnHandle.setYjlx(warnHandleParam.getYjlx());
            warnHandle.setZdbh(warnHandleParam.getZdbh());
            warnHandle.setZdmc(warnHandleParam.getZdmc());
            warnHandle.setZxclsj(sdf.parse(timeStr));
            warnHandle.setSjbh(warnHandleParam.getSjbh());
            //修改预警处置表--SZSS_WARN_HANDLE
            //warnHandleService.insertWarnHandle(warnHandle);
            warnHandleService.updateWarnHandleNew(warnHandle);

            warnHandleStatus.setCznr(warnHandleParam.getCznr());
            warnHandleStatus.setDxnr(warnHandleParam.getDxnr());
            warnHandleStatus.setJcdlx(warnHandleParam.getJcdlx());
            warnHandleStatus.setLednr(warnHandleParam.getLednr());
            warnHandleStatus.setStatus(warnHandleParam.getStatus());
            warnHandleStatus.setSjbh(warnHandleParam.getSjbh());
            warnHandleStatus.setUpdateTime(timeStr);
            warnHandleStatus.setTel(warnHandleParam.getTel());
            warnHandleStatus.setYjcz(warnHandleParam.getYjcz());
            //插入预警处置状态表
            //warnHandleStatus.setStatus("3");
            /*
            //更新 积水预警信息表、排水官网预警信息表 --事件处理处理状态 值
            if("0".equals(warnHandleParam.getJcdlx())){//0 代表积水预警
                //Map<String,String> map =pondingPointWarnService.selectBaseInfo(warnHandleParam.getZdmc());
                warnHandleStatus.setTel(warnHandleParam.getTel());
                //String date = pondingPointWarnService.selectWarnDate(warnHandleParam.getZdmc());
                warnHandleStatus.setUpdateTime(timeStr);
                warnHandleStatus.setCznr(warnHandleParam.getCznr());//处置内容为客户填写的内容
                //warnHandleStatus.setUpdateTime(date.substring(0,19));
                //warnHandleStatus.setCznr("积水点预警");

            }else if("1".equals(warnHandleParam.getJcdlx())){//1 代表排水预警
                Map<String,String> map =drainagePipeWarnService.selectBaseInfo(warnHandleParam.getZdmc());
                warnHandleStatus.setTel(map.get("LXDH"));
                String date = pondingPointWarnService.selectWarnDate(warnHandleParam.getZdmc());
                warnHandleStatus.setUpdateTime(timeStr);
                warnHandleStatus.setCznr(warnHandleParam.getCznr());//处置内容为客户填写的内容
                //warnHandleStatus.setCznr("排水点预警");
                //warnHandleStatus.setUpdateTime(date.substring(0,19));
            }
            warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
             */


            //插入状态为关闭状态
            warnHandleStatus.setStatus(warnHandleParam.getStatus());
            warnHandleStatus.setCznr(warnHandleParam.getCznr());//第二次插入，处置内容为客户填写的内容
            warnHandleStatus.setUpdateTime(timeStr);
            warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);


            //更新 积水预警信息表、排水官网预警信息表 --事件处理处理状态 值
            if("0".equals(warnHandleParam.getJcdlx())){//0 代表积水预警
                pondingPointWarnService.updatePondingPointWarn(warnHandleParam.getSjbh(),warnHandleStatus.getStatus());
            }else if("1".equals(warnHandleParam.getJcdlx())){//1 代表排水预警
                drainagePipeWarnService.updateDrainagePipeWarn(warnHandleParam.getSjbh(),warnHandleStatus.getStatus());
            }

        }catch (Exception e){
            e.printStackTrace();
            intStr = 0;
        }
        return RespMsg.ok(intStr);

    }

    /**
     * 再次处置预警信息--插入预警处置信息
     * 更新SZSS_WARN_HANDLE_STATUS表
     * 要去必须上传jcdlx（监测点类型），否则后期无法进行数据筛选
     * @param warnHandleParam
     * @return
     */
    @PostMapping("/insertSecondWarnHandleInfo")
    @ApiOperation("再次插入预警处置信息--jcdlx（监测点类型）必输")
    @ApiImplicitParams({@ApiImplicitParam(name = "jcdlx", value = "监测点类型",dataType = "String"),
            @ApiImplicitParam(name = "status", value = "处置状态：0 开始 1 处置中 2关闭",dataType = "String"),
            @ApiImplicitParam(name = "cznr", value = "处置内容",dataType = "String"),
            @ApiImplicitParam(name = "dxnr", value = "短信内容",dataType = "String"),
            @ApiImplicitParam(name = "lednr", value = "LED内容",dataType = "String"),
            @ApiImplicitParam(name = "sjbh", value = "事件编号",dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "手机号",dataType = "String"),
            @ApiImplicitParam(name = "yjcz", value = "预警处置",dataType = "String")
    })
    public RespMsg<Integer> insertSecondWarnHandleInfo(WarnHandleParam warnHandleParam) {
        Integer rtNumber = 1;//代表成功
        try{
            //获取当前时间--指定时间格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStr = sdf.format(new Date());
            //预警处置状态表--赋值
            WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
            warnHandleStatus.setCznr(warnHandleParam.getCznr());
            warnHandleStatus.setDxnr(warnHandleParam.getDxnr());
            warnHandleStatus.setJcdlx(warnHandleParam.getJcdlx());
            warnHandleStatus.setLednr(warnHandleParam.getLednr());
            warnHandleStatus.setStatus(warnHandleParam.getStatus());
            warnHandleStatus.setSjbh(warnHandleParam.getSjbh());
            warnHandleStatus.setUpdateTime(timeStr);
            warnHandleStatus.setTel(warnHandleParam.getTel());
            warnHandleStatus.setYjcz(warnHandleParam.getYjcz());

            //插入预警处置状态表
            warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);

            //更新预警处置表--事件处理状态、最新处理时间
            warnHandleService.updateWarnHandle(warnHandleParam.getSjbh(),warnHandleParam.getStatus(),timeStr,warnHandleParam.getJcdlx());

            //更新 积水预警信息表、排水官网预警信息表 --事件处理处理状态 值
            if("0".equals(warnHandleParam.getJcdlx())){//0 代表积水预警
                pondingPointWarnService.updatePondingPointWarn(warnHandleParam.getSjbh(),warnHandleParam.getStatus());
            }else if("1".equals(warnHandleParam.getJcdlx())){//1 代表排水预警
                //drainagePipeWarnService.updateDrainagePipeWarn(warnHandleParam.getSjbh(),warnHandleParam.getStatus());
                //处置完成后排水点需要更新预警等级YJDJ和状态
                drainagePipeWarnService.updateDrainagePipeWarnYJDJ(warnHandleParam.getSjbh(),warnHandleParam.getStatus(),"0");

            }
        }catch (Exception e){
            e.printStackTrace();
            rtNumber = 0;//0 代表失败updatePondingPointWarn
        }
        return RespMsg.ok(rtNumber);
    }

    /**
     * 处置跟踪--查询预警处置状态信息
     * @param sjbh
     * @param jcdlx
     * @return
     */
    @GetMapping("/selectWarnHandleStatus")
    @ApiOperation("处置跟踪--查询预警处置状态信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "jcdlx", value = "监测点类型",dataType = "String"),
            @ApiImplicitParam(name = "sjbh", value = "事件编号",dataType = "String")
    })
    public RespMsg<List<Map<String,String>>>  selectWarnHandleStatus(String sjbh,String jcdlx){
        return RespMsg.ok( warnHandleStatusService.selectWarnHandleStatus(sjbh,jcdlx,null));
    }


    /**
     * 处置跟踪--分页查询
     * @param page
     * @param sjbh
     * @param zdbh
     * @param zdmc
     * @param yjdj
     * @param jcdlx
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/selectWarnHandlePage")
    @ApiOperation("处置跟踪--分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "jcdlx", value = "监测点类型",dataType = "String"),
            @ApiImplicitParam(name = "sjbh", value = "事件编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "yjdj", value = "预警等级",dataType = "String"),
            @ApiImplicitParam(name = "status", value = "事件状态",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public RespMsg<IPage<WarnHandle>> selectWarnHandlePage(Page<WarnHandle> page, String sjbh, String zdbh, String zdmc, String yjdj, String jcdlx, String status,String startTime, String endTime) {
        page.setRecords(warnHandleService.selectWarnHandle(page, sjbh, zdbh, zdmc, yjdj, jcdlx, status, startTime, endTime));
        return RespMsg.ok(page);
    }

    /**
     * 查询积水、排水预警回显信息
     * @param sjbh
     * @param jcdlx
     * @return
     */
    @GetMapping("/selectReturnInfoById")
    @ApiOperation("查询积水、排水预警回显信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "jcdlx", value = "监测点类型",dataType = "String"),
            @ApiImplicitParam(name = "sjbh", value = "事件编号",dataType = "String")
    })
    public RespMsg<Map<String,String>>  selectReturnInfoById(String sjbh,String jcdlx){
        Map<String,String> map = null;
        //更新 积水预警信息表、排水官网预警信息表 --事件处理处理状态 值
        if("0".equals(jcdlx)){//0 代表积水预警
            map = pondingPointWarnService.selectPondingPointWarnById(sjbh);
        }else if("1".equals(jcdlx)){//1 代表排水预警selectPondingPointWarnById
            map = drainagePipeWarnService.selectDrainagePipeWarnById(sjbh);
        }
        return RespMsg.ok(map);
    }
}
