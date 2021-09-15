package com.digitalchina.zhjg.szss.job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.modules.log.AbstractLogAspect;
import com.digitalchina.zhjg.szss.gis.entity.*;
import com.digitalchina.zhjg.szss.gis.service.*;
import com.digitalchina.zhjg.szss.mid.dto.JpsdSmsMessage;
import com.digitalchina.zhjg.szss.mid.service.SmsSender;
import com.netflix.discovery.converters.Auto;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2021-01-20 17:25
 */
@Component
public class JobPondingPointSchedule {

    private static final Logger LOG = LoggerFactory.getLogger(JobPondingPointSchedule.class);

    @Autowired
    private RainFallService rainFallService;

    @Autowired
    private PondingPointRealDataService pondingPointRealDataService;

    @Autowired
    private PondingPointWarnService pondingPointWarnService;

    @Autowired
    private  WarnHandleStatusService warnHandleStatusService;

    @Autowired
    private WarnHandleService warnHandleService;

    @Autowired
    private SmsSender smsSender;

    @Scheduled(cron = "0 0/01 * * * ?")//01分钟一次
    public void updatePondingPointInfo()  {
       LOG.info("============================程序开始：积水点信息同步========================================");
        try{
            //1.查询所有积水点信息
            List<PondingPointPage> pondingPointList = rainFallService.pondingPointList();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(PondingPointPage pondingPointPage : pondingPointList){
                String zdbh = pondingPointPage.getZdbh();
                //2.根据站点编号查询此编号上次同步的时间
                JsdMaxTm jsdMaxTm = rainFallService.selectJsdMaxTm(zdbh);
                if(jsdMaxTm==null){
                    //3.如果SZSS_JSDMAXTM表中不存在,则插入SZSS_JSDMAXTM,时间为当前时间
                    JsdMaxTm jsdMaxTms = new JsdMaxTm();
                    jsdMaxTms.setJsdzdbh(zdbh);
                    jsdMaxTms.setJsdmaxtm(new Date());
                    rainFallService.insertJsdMaxTm(jsdMaxTms);
                    continue;
                }else{
                    //4.根据上次同步的时间查询需要同步积水点数据
                    List<Map<String,String>> mapList = rainFallService.selectNewReallyData(jsdMaxTm);
                    if(mapList.size()>0){
                        Map<String,String> mapInfo = mapList.get(0);//取第一条(最新的数据)
                        PondingPointRealData realData =  new PondingPointRealData();
                        if(mapInfo.get("ZDMC")==null||mapInfo.get("ZDMC")==""){
                            continue;
                        }
                        realData.setZdbh(String.valueOf(mapInfo.get("ZDBH")));
                        realData.setJyl( Double.parseDouble(String.valueOf(mapInfo.get("DRP")==null?0.0:mapInfo.get("DRP"))));
                        realData.setJssd(Double.parseDouble(String.valueOf(mapInfo.get("Z")==null?0.0:mapInfo.get("Z"))));
                        realData.setJsmj(0.0);
                        realData.setLy("智能感知");
                        realData.setJcsj(sdf.parse(String.valueOf(mapInfo.get("TM"))));
                        realData.setJyjb("无数据");

                        Map<String,String> bjfzValue = rainFallService.selectByZdbh(realData.getZdbh());//获取设备报警阀值---根据设备编号查询
                        if(realData.getJssd()>=Double.parseDouble(String.valueOf(bjfzValue.get("BJFZ")))){//判断报警值，插入报警信息
                            try{
                                //发送预警短息
                                JpsdSmsMessage message = new JpsdSmsMessage();
                                message.setStationType(JpsdSmsMessage.StationType.JSD);
                                message.setStationCode(String.valueOf(mapInfo.get("ZDBH")));
                                message.setDepth(String.valueOf(String.valueOf(mapInfo.get("Z")==null?0.0:mapInfo.get("Z"))));
                                smsSender.sendJpsdWarning(message);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            PondingPointWarn pondingPointWarn = new PondingPointWarn();
                            pondingPointWarn.setJssd(String.valueOf(realData.getJssd()));
                            pondingPointWarn.setZdbh(realData.getZdbh());
                            pondingPointWarn.setZdmc(String.valueOf(mapInfo.get("ZDMC")));
                            pondingPointWarn.setJyl(String.valueOf(realData.getJyl()));
                            pondingPointWarn.setStatus("0");
                            pondingPointWarn.setSzwz(String.valueOf(mapInfo.get("SZWZ")));
                            pondingPointWarn.setSzqy(String.valueOf(mapInfo.get("SZQY")));
                            pondingPointWarn.setZrdw(String.valueOf(mapInfo.get("ZRDW")));
                            pondingPointWarn.setSbsj(String.valueOf(mapInfo.get("TM")).substring(0,19));
                            pondingPointWarn.setYjdj("1");
                            realData.setYjdj("1");//产生预警默认为1
                            List<Map<String,String>> statusList = pondingPointWarnService.selectWarnStatusBySbbh(realData.getZdbh());
                            pondingPointWarnService.insertPondingPointWarn(pondingPointWarn);
                            System.out.println(pondingPointWarn.getObjectId());
                            //插入SZSS_WARN_HANDLE表
                            WarnHandle warnHandle = new WarnHandle();
                            warnHandle.setZdbh(realData.getZdbh());
                            warnHandle.setZdmc(String.valueOf(mapInfo.get("ZDMC")));
                            warnHandle.setJcdlx("0");
                            warnHandle.setStatus("0");
                            warnHandle.setSbsj((String.valueOf(mapInfo.get("TM")).substring(0,19)));
                            warnHandle.setYjdj("1");
                            warnHandle.setSjbh(String.valueOf(pondingPointWarn.getObjectId()));
                            warnHandleService.insertWarnHandle(warnHandle);
                            //插入SZSS_WARN_HANDLE_STATUS表
                            WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
                            warnHandleStatus.setSjbh(String.valueOf(pondingPointWarn.getObjectId()));
                            warnHandleStatus.setJcdlx("0");//积水点
                            warnHandleStatus.setUpdateTime(String.valueOf(mapInfo.get("TM")).substring(0,19));
                            warnHandleStatus.setCznr("积水点预警，深度为："+pondingPointWarn.getJssd());
                            warnHandleStatus.setStatus("0");
                            warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
                        }else{
                            realData.setYjdj("0");
                        }
                        //原此实时数据更新到SZSS_JSD_REALDATA,现修改为直接更新SZSS_JSD中几个实时字段
                        LOG.info("更新的积水站点编号为:"+realData.getZdbh());
                        pondingPointRealDataService.updatePondingPointRealData(realData.getJssd(),realData.getJyl(),realData.getYjdj(),realData.getJcsj(),realData.getZdbh());
                        //5.更新表SZSS_JSDMAXTM
                        JsdMaxTm jsdMaxTmNow = new JsdMaxTm();
                        jsdMaxTmNow.setJsdzdbh(realData.getZdbh());
                        jsdMaxTmNow.setJsdmaxtm(realData.getJcsj());
                        rainFallService.updateJsdMaxTm(jsdMaxTmNow);
                    }else{
                        LOG.info("根据中间表最大时间值查询"+zdbh+"无数据，无需更新数据。。。。。。");
                    }

                }
            }


//            Map<String,Date> jsdMaxIdMap= rainFallService.selectJsdMaxId();//查询积水点最大时间中间表  SZSS_MAXID表
//            Date jsdMaxdate= jsdMaxIdMap.get("JSDMAXDATE");
//            Map<String,Date> maxIdMap= rainFallService.selectMaxidThird();// 查询雨量表最大时间
//            List<Integer> listStatus =  new ArrayList<>();//存储插入状态，如果有值代表本次跑批已经插入一次预警状态表,避免多次插入
//            List<Map<String,String>> mapList = rainFallService.selectRainFallInfo(jsdMaxdate);
//            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//           if(mapList.size()>0){//大于0表示有数据需要更新，反之则无需数据更新
//               for (Map mapInfo : mapList) {//对所有设备的回传信息进行处理
//                   PondingPointRealData realData =  new PondingPointRealData();
//                   if(mapInfo.get("ZDMC")==null||mapInfo.get("ZDMC")==""){
//                       continue;
//                   }
//                   realData.setZdbh(String.valueOf(mapInfo.get("ZDBH")));
//                   realData.setJyl( Double.parseDouble(String.valueOf(mapInfo.get("DRP")==null?0.0:mapInfo.get("DRP"))));
//                   realData.setJssd(Double.parseDouble(String.valueOf(mapInfo.get("Z")==null?0.0:mapInfo.get("Z"))));
//                   realData.setJsmj(0.0);
//                   realData.setLy("智能感知");
//                   realData.setJcsj(sdf.parse(String.valueOf(mapInfo.get("TM"))));
//                   realData.setJyjb("无数据");
//
//                   Map<String,String> bjfzValue = rainFallService.selectByZdbh(realData.getZdbh());//获取设备报警阀值---根据设备编号查询
//                   if(realData.getJssd()>=Double.parseDouble(String.valueOf(bjfzValue.get("BJFZ")))){//判断报警值，插入报警信息
//                        try{
//                            //发送预警短息
//                            JpsdSmsMessage message = new JpsdSmsMessage();
//                            message.setStationType(JpsdSmsMessage.StationType.JSD);
//                            message.setStationCode(String.valueOf(mapInfo.get("ZDBH")));
//                            message.setDepth(String.valueOf(String.valueOf(mapInfo.get("Z")==null?0.0:mapInfo.get("Z"))));
//                            smsSender.sendJpsdWarning(message);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//
//                       PondingPointWarn pondingPointWarn = new PondingPointWarn();
//                       pondingPointWarn.setJssd(String.valueOf(realData.getJssd()));
//                       pondingPointWarn.setZdbh(realData.getZdbh());
//                       pondingPointWarn.setZdmc(String.valueOf(mapInfo.get("ZDMC")));
//                       pondingPointWarn.setJyl(String.valueOf(realData.getJyl()));
//                       pondingPointWarn.setStatus("0");
//                       pondingPointWarn.setSzwz(String.valueOf(mapInfo.get("SZWZ")));
//                       pondingPointWarn.setSzqy(String.valueOf(mapInfo.get("SZQY")));
//                       pondingPointWarn.setZrdw(String.valueOf(mapInfo.get("ZRDW")));
//                       pondingPointWarn.setSbsj(String.valueOf(mapInfo.get("TM")).substring(0,19));
//                       pondingPointWarn.setYjdj("1");
//                       realData.setYjdj("1");//产生预警默认为1
//                       List<Map<String,String>> statusList = pondingPointWarnService.selectWarnStatusBySbbh(realData.getZdbh());
//                       pondingPointWarnService.insertPondingPointWarn(pondingPointWarn);
//                       System.out.println(pondingPointWarn.getObjectId());
//                       //插入SZSS_WARN_HANDLE表
//                       WarnHandle warnHandle = new WarnHandle();
//                       warnHandle.setZdbh(realData.getZdbh());
//                       warnHandle.setZdmc(String.valueOf(mapInfo.get("ZDMC")));
//                       warnHandle.setJcdlx("0");
//                       warnHandle.setStatus("0");
//                       warnHandle.setSbsj((String.valueOf(mapInfo.get("TM")).substring(0,19)));
//                       warnHandle.setYjdj("1");
//                       warnHandle.setSjbh(String.valueOf(pondingPointWarn.getObjectId()));
//                       warnHandleService.insertWarnHandle(warnHandle);
//                       //插入SZSS_WARN_HANDLE_STATUS表
//                       WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
//                       warnHandleStatus.setSjbh(String.valueOf(pondingPointWarn.getObjectId()));
//                       warnHandleStatus.setJcdlx("0");//积水点
//                       warnHandleStatus.setUpdateTime(String.valueOf(mapInfo.get("TM")).substring(0,19));
//                       warnHandleStatus.setCznr("积水点预警，深度为："+pondingPointWarn.getJssd());
//                       warnHandleStatus.setStatus("0");
//                       warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
//                       //规则：根据设备编号查询，如果没有数据或者status状态是3，则插入数据，如果关闭状态则插入到状态表中
//                       //查询此站点编号在SZSS_JSD_WARN中status不等于3的,是否存在
////                       Integer num = pondingPointWarnService.selectWarnStatusNot3BySbbh(realData.getZdbh());
////                       if(statusList.size()>0){
////                           for (Map statusMap: statusList) {
////                               if("3".equals(statusMap.get("STATUS"))){
////                                   if(!listStatus.contains(1)){
////                                       if(num<1){
////                                           pondingPointWarnService.insertPondingPointWarn(pondingPointWarn);//产生报警信息，积水点报警信息表
////                                           WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
////                                           warnHandleStatus.setSjbh(String.valueOf(statusMap.get("OBJECTID")));
////                                           warnHandleStatus.setJcdlx("0");//积水点
////                                           warnHandleStatus.setUpdateTime(String.valueOf(mapInfo.get("TM")).substring(0,19));
////                                           warnHandleStatus.setCznr("积水点预警，深度为："+pondingPointWarn.getJssd());
////                                           warnHandleStatus.setStatus("0");
////                                           warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
////                                           listStatus.add(1);
////                                       }
////
////                                   }
////                               }else {//相同设备已经产生预警信息，则插入预警处置状态表
////                                   WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
////                                   warnHandleStatus.setSjbh(String.valueOf(statusMap.get("OBJECTID")));
////                                   warnHandleStatus.setJcdlx("0");//积水点
////                                   warnHandleStatus.setUpdateTime(String.valueOf(mapInfo.get("TM")).substring(0,19));
////                                   warnHandleStatus.setCznr("积水点预警，深度为："+pondingPointWarn.getJssd());
////                                   warnHandleStatus.setStatus("0");
////                                   warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
////                               }
////                           }
////                       }else {//预警信息表没有对应数据的预警信息，则插入预警信息
////                           pondingPointWarnService.insertPondingPointWarn(pondingPointWarn);//产生报警信息，积水点报警信息表
////                           WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
////                           warnHandleStatus.setSjbh(String.valueOf(pondingPointWarnService.selectPondingPointWarnByTime(pondingPointWarn.getSbsj())));
////                           warnHandleStatus.setJcdlx("0");//积水点
////                           warnHandleStatus.setUpdateTime(String.valueOf(mapInfo.get("TM")).substring(0,19));
////                           warnHandleStatus.setCznr("积水点预警，深度为："+pondingPointWarn.getJssd());
////                           warnHandleStatus.setStatus("0");
////                           warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
////                       }
//                   }else{
//                       realData.setYjdj("0");
//                   }
//               //    realData.setYjz(Double.parseDouble(String.valueOf(bjfzValue.get("BJFZ"))));//预警值取值设备的报警阀值
//               //    pondingPointRealDataService.insertPondingPointRealData(realData);//插入积水点实时数据
//                   //原此实时数据更新到SZSS_JSD_REALDATA,现修改为直接更新SZSS_JSD中几个实时字段
//                   LOG.info("更新的积水站点编号为:"+realData.getZdbh());
//                   pondingPointRealDataService.updatePondingPointRealData(realData.getJssd(),realData.getJyl(),realData.getYjdj(),realData.getJcsj(),realData.getZdbh());
//               }
//               rainFallService.updateJsdMaxId(maxIdMap.get("TM"));//保存最大时间值
//           }else{
//               LOG.info("根据中间表最大时间值查询无数据，无需更新数据。。。。。。");
//           }
//            listStatus.clear();
        }catch (Exception e){
            LOG.error("定时任务失败"+ e.getCause().getMessage());
        }

       LOG.info("============================程序结束：积水点信息同步========================================");
    }
}
