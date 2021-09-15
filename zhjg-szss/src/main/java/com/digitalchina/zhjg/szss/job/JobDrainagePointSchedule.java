package com.digitalchina.zhjg.szss.job;

import com.digitalchina.zhjg.szss.gis.entity.*;
import com.digitalchina.zhjg.szss.gis.service.*;
import com.digitalchina.zhjg.szss.mid.dto.JpsdSmsMessage;
import com.digitalchina.zhjg.szss.mid.service.SmsSender;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 查询实时数据,水位,水势,流量,流速等
 */
@Component
public class JobDrainagePointSchedule {

    private static final Logger LOG = LoggerFactory.getLogger(JobDrainagePointSchedule.class);

    @Autowired
    private DrainageReallyService drainageReallyService;

    @Autowired
    private DeviceWarningService deviceWarningService;

    @Autowired
    private DrainagePipeWarnService drainagePipeWarnService;

    @Autowired
    private WarnHandleStatusService warnHandleStatusService;

    @Autowired
    private WarnHandleService warnHandleService;

    @Autowired
    private SmsSender smsSender;

    @Scheduled(cron = "0 0/05 * * * ?")//5分钟一次
    public void updateDrainagePointInfo() {

        LOG.info("============================程序开始-排水点信息同步========================================");

        try{
            //1.查询所有排水点信息
            List<DrainagePoint> drainagePointList = drainageReallyService.drainagePointList();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(DrainagePoint drainagePoint : drainagePointList){
                Date zTM = null;
                Date llTM = null;
                String zdbh = drainagePoint.getZdbh();
                //2.根据站点编号查询此站点上一次更新的时间
                PsdMaxTm psdMaxTm = drainageReallyService.selectPSDMAXTM(zdbh);
                if(psdMaxTm==null){
                    //3如果SZSS_PSDMAXTM表中,不存在上次更新的水位最大时间,则插入当前时间
                    PsdMaxTm psdMaxTms = new PsdMaxTm();
                    psdMaxTms.setPsdzdbh(zdbh);
                    psdMaxTms.setPsdzmaxtm(new Date());
                    psdMaxTms.setPsdllmaxtm(new Date());
                    drainageReallyService.insertPSDMAXTM(psdMaxTms);
                    continue;
                }else{
                    //4.根据上次同步的时间,查询需要同步的排水点水位数据
                    List<DrainageReallyData> listZdata = drainageReallyService.selectDrainageZReallyNew(psdMaxTm);
                    if (listZdata.size()>0){
                        DrainageReallyData drainageReallyData = listZdata.get(0); //取第一条,最新的
                        //String zdbh = drainageReallyData.getStcd(); //测站编号(站点编号)
                        if(StringUtils.isEmpty(zdbh) || StringUtils.isEmpty(drainageReallyData.getZdmc())){
                            continue;
                        }
                        //查询排水设备基本信息
                        Map<String,String> bjfzMap = drainageReallyService.selectPSDDevice(zdbh);
                        //设备报警阀值
                        String bjfz = bjfzMap.get("BJFZ");
                        if(drainageReallyData.getZ()>=Double.parseDouble(bjfz)){ //判断报警值，插入报警信息

                            try{
                                //发送预警短息
                                JpsdSmsMessage message = new JpsdSmsMessage();
                                message.setStationType(JpsdSmsMessage.StationType.PSD);
                                message.setStationCode(drainageReallyData.getStcd());
                                message.setDepth(String.valueOf(drainageReallyData.getZ()));
                                smsSender.sendJpsdWarning(message);
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                            drainageReallyData.setYjdj("1");
                            DrainagePipeWarn drainagePipeWarn = new DrainagePipeWarn();
                            drainagePipeWarn.setSwsd(String.valueOf(drainageReallyData.getZ()));
                            drainagePipeWarn.setZdbh(drainageReallyData.getStcd());
                            drainagePipeWarn.setZdmc(drainageReallyData.getZdmc());

                            drainagePipeWarn.setZrdw(drainageReallyData.getZrdw());
                            drainagePipeWarn.setLxdh(drainageReallyData.getLxdh());
                            drainagePipeWarn.setFzr(drainageReallyData.getFzr());
                            drainagePipeWarn.setStatus("0");
                            String TM = sdf.format(drainageReallyData.getTm());
                            drainagePipeWarn.setSbsj(TM);
                            drainagePipeWarn.setSzwz(drainageReallyData.getSzwz());
                            drainagePipeWarn.setSzqy(drainageReallyData.getSzqy());
                            drainagePipeWarn.setYjdj("1");
                            //查询此时的流量实时数据
                            DrainageReallyData ListLLData1 = drainageReallyService.listLLData1(drainageReallyData.getTm(),drainageReallyData.getStcd());
                            if(ListLLData1!=null){
                                drainagePipeWarn.setGwls(String.valueOf(ListLLData1.getV()==null?"0":ListLLData1.getV()));
                            }
                            //List<Map<String,String>> statusList = deviceWarningService.selectWarnStatusBySbbh(zdbh);
                            drainagePipeWarnService.insertDrainagePipeWarn(drainagePipeWarn);
                            System.out.println(drainagePipeWarn.getObjectId());
                            //插入SZSS_WARN_HANDLE表
                            WarnHandle warnHandle = new WarnHandle();
                            warnHandle.setZdbh(zdbh);
                            warnHandle.setZdmc(drainageReallyData.getZdmc());
                            warnHandle.setJcdlx("1");
                            warnHandle.setStatus("0");
                            warnHandle.setSbsj(TM);
                            warnHandle.setYjdj("1");
                            warnHandle.setSjbh(String.valueOf(drainagePipeWarn.getObjectId()));
                            warnHandleService.insertWarnHandle(warnHandle);
                            //插入SZSS_WARN_HANDLE_STATUS表
                            WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
                            warnHandleStatus.setSjbh(String.valueOf(drainagePipeWarn.getObjectId()));
                            warnHandleStatus.setJcdlx("1");//排水点
                            warnHandleStatus.setUpdateTime(TM);
                            warnHandleStatus.setCznr("排水点预警，深度为："+drainagePipeWarn.getSwsd());
                            warnHandleStatus.setStatus("0");
                            warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
                        }else{
                            drainageReallyData.setYjdj("0");
                        }
                        //更新szss_psd表中的积水深度
                        drainageReallyService.updateSzssPsd(zdbh,drainageReallyData.getZ(),drainageReallyData.getYjdj());
                        zTM = drainageReallyData.getTm();
                        PsdMaxTm psdMaxTms = new PsdMaxTm();
                        psdMaxTms.setPsdzdbh(zdbh);
                        psdMaxTms.setPsdzmaxtm(drainageReallyData.getTm());
                        drainageReallyService.updatePsdZMaxTm(psdMaxTms);
                    }else{
                        LOG.info("根据中间表最大时间值查询排水点"+zdbh+"实时积水深度无数据，无需更新数据。。。。。。");
                    }
                    List<DrainageReallyData> listllData = drainageReallyService.selectDrainageLLReallyNew(psdMaxTm);
                    if(listllData.size()>0){
                        DrainageReallyData drainageReallyllData = listllData.get(0);
                        llTM = drainageReallyllData.getTm();
                        Date updateTM;
                        if(zTM.getTime()>llTM.getTime()){
                            updateTM=zTM;
                        }else{
                            updateTM = llTM;
                        }
                        //更新szss_psd表中的ll字段和jcsj字段
                        drainageReallyService.updateLLSzssPsd(zdbh,drainageReallyllData.getQ(),drainageReallyllData.getV(),updateTM);
                        PsdMaxTm psdMaxTms = new PsdMaxTm();
                        psdMaxTms.setPsdzdbh(zdbh);
                        psdMaxTms.setPsdllmaxtm(drainageReallyllData.getTm());
                        drainageReallyService.updatePsdLLMaxTm(psdMaxTms);
                    }else{
                        LOG.info("根据中间表最大时间值查询排水点"+zdbh+"实时流量无数据，无需更新数据。。。。。。");
                    }


                }
            }










//            //Integer id = 1;
//            //查询上一次psdId的值
//            //Integer id = drainageReallyService.selectPSDMAXID();
//            //查询上一次记录的时间
//            Date PSDMAXDATE = drainageReallyService.selectPSDMAXDate(); //排水点水位时间
//            Date PSDLLMAXDATE = drainageReallyService.selectPSDLLMAXDATE(); //排水点流量时间
//
//            //查询水位的最大时间
//            Date maxPsdZDate = drainageReallyService.reallyMaxPSDZDate();
//            //查询流量最大时间
//            Date maxPsdLLDate = drainageReallyService.reallyMaxPSDLLDate();
//
//            //查询实时新增的排水点水位数据
//            List<DrainageReallyData> ListZData = drainageReallyService.selectDrainageZReally(PSDMAXDATE);
//            //查询实时新增的排水点流量数据
//            List<DrainageReallyData> ListLLData = drainageReallyService.selectDrainageLLReally(PSDLLMAXDATE);
//            if(ListZData.size()>0){
//                //通过增量实时数据进行预警规则判断
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                for(DrainageReallyData drainageReallyData : ListZData){
//                    String zdbh = drainageReallyData.getStcd(); //测站编号(站点编号)
//                    if(StringUtils.isEmpty(zdbh) || StringUtils.isEmpty(drainageReallyData.getZdmc())){
//                        continue;
//                    }
//                    //查询排水设备基本信息
//                    Map<String,String> bjfzMap = drainageReallyService.selectPSDDevice(zdbh);
//                    //设备报警阀值
//                    String bjfz = bjfzMap.get("BJFZ");
//                    if(drainageReallyData.getZ()>=Double.parseDouble(bjfz)){ //判断报警值，插入报警信息
//
//                        try{
//                            //发送预警短息
//                            JpsdSmsMessage message = new JpsdSmsMessage();
//                            message.setStationType(JpsdSmsMessage.StationType.PSD);
//                            message.setStationCode(drainageReallyData.getStcd());
//                            message.setDepth(String.valueOf(drainageReallyData.getZ()));
//                            smsSender.sendJpsdWarning(message);
//                        }catch(Exception e){
//                            e.printStackTrace();
//                        }
//
//                        drainageReallyData.setYjdj("1");
//                        DrainagePipeWarn drainagePipeWarn = new DrainagePipeWarn();
//                        drainagePipeWarn.setSwsd(String.valueOf(drainageReallyData.getZ()));
//                        drainagePipeWarn.setZdbh(drainageReallyData.getStcd());
//                        drainagePipeWarn.setZdmc(drainageReallyData.getZdmc());
//
//                        drainagePipeWarn.setZrdw(drainageReallyData.getZrdw());
//                        drainagePipeWarn.setLxdh(drainageReallyData.getLxdh());
//                        drainagePipeWarn.setFzr(drainageReallyData.getFzr());
//                        drainagePipeWarn.setStatus("0");
//                        String TM = formatter.format(drainageReallyData.getTm());
//                        drainagePipeWarn.setSbsj(TM);
//                        drainagePipeWarn.setSzwz(drainageReallyData.getSzwz());
//                        drainagePipeWarn.setSzqy(drainageReallyData.getSzqy());
//                        drainagePipeWarn.setYjdj("1");
//                        //查询此时的流量实时数据
//                        DrainageReallyData ListLLData1 = drainageReallyService.listLLData1(drainageReallyData.getTm(),drainageReallyData.getStcd());
//                        if(ListLLData1!=null){
//                            drainagePipeWarn.setGwls(String.valueOf(ListLLData1.getV()==null?"0":ListLLData1.getV()));
//                        }
//                        //List<Map<String,String>> statusList = deviceWarningService.selectWarnStatusBySbbh(zdbh);
//                        drainagePipeWarnService.insertDrainagePipeWarn(drainagePipeWarn);
//                        System.out.println(drainagePipeWarn.getObjectId());
//                        //插入SZSS_WARN_HANDLE表
//                        WarnHandle warnHandle = new WarnHandle();
//                        warnHandle.setZdbh(zdbh);
//                        warnHandle.setZdmc(drainageReallyData.getZdmc());
//                        warnHandle.setJcdlx("1");
//                        warnHandle.setStatus("0");
//                        warnHandle.setSbsj(TM);
//                        warnHandle.setYjdj("1");
//                        warnHandle.setSjbh(String.valueOf(drainagePipeWarn.getObjectId()));
//                        warnHandleService.insertWarnHandle(warnHandle);
//                        //插入SZSS_WARN_HANDLE_STATUS表
//                        WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
//                        warnHandleStatus.setSjbh(String.valueOf(drainagePipeWarn.getObjectId()));
//                        warnHandleStatus.setJcdlx("1");//排水点
//                        warnHandleStatus.setUpdateTime(TM);
//                        warnHandleStatus.setCznr("排水点预警，深度为："+drainagePipeWarn.getSwsd());
//                        warnHandleStatus.setStatus("0");
//                        warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
//                        //规则：根据设备编号查询，如果没有数据或者status状态是3，则插入数据，如果关闭状态则插入到状态表中
//                        //查询此站点编号在SZSS_PSD_WARN中status不等于3的,是否存在
////                        Integer num = deviceWarningService.selectWarnStatusNot3BySbbh(zdbh);
////                        if(statusList.size()>0){
////                            for(Map statusMap: statusList){
////                                if("3".equals(statusMap.get("STATUS"))){
////                                    if(!listStatus.contains(1)){
////                                        if(num<1){
////                                            drainagePipeWarnService.insertDrainagePipeWarn(drainagePipeWarn);
////                                            WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
////                                            warnHandleStatus.setSjbh(String.valueOf(statusMap.get("OBJECTID")));
////                                            warnHandleStatus.setJcdlx("1");//排水点
////                                            warnHandleStatus.setUpdateTime(formatter.format(drainageReallyData.getTm()));
////                                            warnHandleStatus.setCznr("积水点预警，深度为："+drainagePipeWarn.getSwsd());
////                                            warnHandleStatus.setStatus("0");
////                                            warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
////                                            listStatus.add(1);
////                                        }
////
////                                    }
////                                }else{//相同设备已经产生预警信息，则插入预警处置状态表
////                                    WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
////                                    warnHandleStatus.setSjbh(String.valueOf(statusMap.get("OBJECTID")));
////                                    warnHandleStatus.setJcdlx("1");//排水点
////                                    warnHandleStatus.setUpdateTime(TM);
////                                    warnHandleStatus.setCznr("排水点预警，深度为："+drainagePipeWarn.getSwsd());
////                                    warnHandleStatus.setStatus("0");
////                                    warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
////                                }
////                            }
////                        }else{
////                            drainagePipeWarnService.insertDrainagePipeWarn(drainagePipeWarn);//产生报警信息，排水点报警信息表
////                            WarnHandleStatus warnHandleStatus = new WarnHandleStatus();
////
////                            warnHandleStatus.setSjbh(String.valueOf(drainagePipeWarnService.selectDrainagePipeWarnByZDBH(drainagePipeWarn.getZdbh())));
////                            warnHandleStatus.setJcdlx("1");//排水点
////                            warnHandleStatus.setUpdateTime(TM);
////                            warnHandleStatus.setCznr("排水点预警，深度为："+drainagePipeWarn.getSwsd());
////                            warnHandleStatus.setStatus("0");
////                            warnHandleStatusService.insertWarnHandleStatus(warnHandleStatus);
////                        }
//                    }else{
//                        drainageReallyData.setYjdj("0");
//                    }
//                    //更新szss_psd表中的积水深度
//                    drainageReallyService.updateSzssPsd(zdbh,drainageReallyData.getZ(),drainageReallyData.getYjdj());
//                }
////                //实时增量数据插入SZSS_PSLL表中
////                Integer psllNum = drainageReallyService.insertPSLL(ListReallyData);
////                //实时数据插入到SZSS_PSSW表中
////                Integer psswNum = drainageReallyService.insertPSSW(ListReallyData);
////                if(ListReallyData.size()>0) {
////                    DrainageReallyData lastData = ListReallyData.get(ListReallyData.size() - 1);
////                    //更新id
////                    drainageReallyService.updatePSDID(lastData.getTm());
////                }
//
//            }else{
//                LOG.info("根据中间表最大时间值查询实时积水深度无数据，无需更新数据。。。。。。");
//            }
//            if(ListLLData.size()>0){
//                for(DrainageReallyData drainageReallyData : ListLLData){
//                    String zdbh = drainageReallyData.getStcd();
//                    //更新szss_psd表中的ll字段和jcsj字段
//                    drainageReallyService.updateLLSzssPsd(zdbh,drainageReallyData.getQ(),drainageReallyData.getTm());
//                }
//            }else{
//                LOG.info("根据中间表最大时间值查询实时流量无数据，无需更新数据。。。。。。");
//            }
//            //更新时间--更新SZSS_MAXID表
//            drainageReallyService.updatePSDID(maxPsdZDate);
//            drainageReallyService.updatePSDLLMAXDATE(maxPsdLLDate);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error("定时任务失败"+ e.getCause());
        }

        LOG.info("============================程序结束-排水点信息同步========================================");

    }
}
