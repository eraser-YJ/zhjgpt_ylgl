package com.digitalchina.zhjg.szss.gis.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.GyldgsmmDto;
import com.digitalchina.zhjg.szss.gis.entity.GyldgyDto;
import com.digitalchina.zhjg.szss.gis.entity.GyldldDto;
import com.digitalchina.zhjg.szss.gis.entity.GyldxdsDto;
import com.digitalchina.zhjg.szss.gis.service.GyldInfoService;
import com.digitalchina.zhjg.szss.gis.service.GyldgsmmInfoService;
import com.digitalchina.zhjg.szss.gis.service.GyldgyInfoService;
import com.digitalchina.zhjg.szss.gis.service.GyldxdsInfoService;
import com.digitalchina.zhjg.szss.mid.service.PartsCategoryService;
import com.digitalchina.zhjg.szss.utils.ExportUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-11-02 14:26
 */
@RestController
@RequestMapping("/exceldata")
@Api(tags = "excel数据导入")
public class GyldInfoController {

    private static final Logger log = LoggerFactory.getLogger(GyldInfoController.class);

    @Autowired
    private GyldInfoService gyldInfoService;

    @Autowired
    private GyldxdsInfoService gyldxdsInfoService;

    @Autowired
    private GyldgyInfoService gyldgyInfoService;

    @Autowired
    private GyldgsmmInfoService gyldgsmmInfoService;

    @Autowired
    private PartsCategoryService partsCategoryService;

    //生成excel模版,上传到服务器的路径
    @Value("${upfilepath.url}")
    private String path;


    @PostMapping("/importdata")
    @ApiOperation("EXCEL导入")
    @ApiImplicitParams({@ApiImplicitParam(name = "gyldxxgl", value = "绿地信息管理EXCEL导入"),
            @ApiImplicitParam(name = "gyldxdsgl", value = "行道树管理EXCEL导入"),
            @ApiImplicitParam(name = "gyldgygl", value = "公园信息管理EXCEL导入"),
            @ApiImplicitParam(name = "gyldgsmmgl", value = "行道树管理EXCEL导入")
    })
    public RespMsg<T> importLdxxData(@RequestParam("file") MultipartFile file, String name) {
        try {
            //文件上传到的保存位置
//            String realPath="/opt/midapps/zhjg-szss/upfiles";
//            //文件名称
//            String fileName = file.getOriginalFilename();
//            //文件后缀
//            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//            //上传到服务器得文件名
//            String newFileName = fileName+new SimpleDateFormat("yyyyMMddHHssSSS").format(new Date())+"."+extension;
//            //处理文件上传
//            file.transferTo(new File(realPath,newFileName));
            //XSSFWorkbook workbook = new  XSSFWorkbook(new FileInputStream(filePath));
            Workbook workbook = getWorkBook(file);
            Sheet sheetAt = workbook.getSheetAt(0);
            DecimalFormat ds = new DecimalFormat("0");
            switch (name) {
                case "gyldxxgl": //绿地信息管理EXCEL导入
                    List<GyldldDto> gyldList = new ArrayList<>();//绿地信息管理集合
                    for (Row row : sheetAt) {
                        if (row.getRowNum() == 0) { continue; }
                        GyldldDto gyldDto = new GyldldDto();
                        gyldDto.setCode(row.getCell(0)==null?"":row.getCell(0).getStringCellValue());//绿地识别码
                        gyldDto.setName(row.getCell(1)==null?"":row.getCell(1).getStringCellValue());//绿地名称
                        if(row.getCell(2)!=null){
                            gyldDto.setCateLevel1Name(getStringValue(row,2)[0]);//绿地一级类别名称
                            gyldDto.setCateLevel1Id(Integer.valueOf(getStringValue(row,2)[1]));//绿地一级类别编号
                        }else{
                            gyldDto.setCateLevel1Name("");
                            gyldDto.setCateLevel1Id(null);
                        }

                        if(row.getCell(3)!=null){
                            gyldDto.setPropertyName(getStringValue(row,3)[0]);//绿地性质名称
                            gyldDto.setPropertyCode(Integer.valueOf(getStringValue(row,3)[1]));//绿地性质编号
                        }else{
                            gyldDto.setPropertyName("");
                            gyldDto.setPropertyCode(null);
                        }

                        if(row.getCell(4)!=null){
                            gyldDto.setAdmDivName(getStringValue(row,4)[0]);//所属区域
                            gyldDto.setAdmDivCode(Integer.valueOf(getStringValue(row,4)[1]));//区域代码
                        }else{
                            gyldDto.setAdmDivName("");
                            gyldDto.setAdmDivCode(null);
                        }

                        if(row.getCell(5)!=null){
                            gyldDto.setDegreeName(getStringValue(row,5)[0]);//绿地等级
                            gyldDto.setDegreeCode(Integer.valueOf(getStringValue(row,5)[1]));//绿地等级代码
                        }else{
                            gyldDto.setDegreeName("");
                            gyldDto.setDegreeCode(null);
                        }

                        gyldDto.setArea(String.valueOf(doubleToInt(row.getCell(6)==null?0:row.getCell(6).getNumericCellValue())));//绿化面积
                        if(row.getCell(7)!=null){
                            gyldDto.setMainTypeName(getStringValue(row,7)[0]);//养护类别
                            gyldDto.setMainTypeCode(Integer.valueOf(getStringValue(row,7)[1]));//养护类别 编码
                        }else{
                            gyldDto.setMainTypeName("");
                            gyldDto.setMainTypeCode(null);
                        }

                        if(row.getCell(8)!=null){
                            gyldDto.setMainTainName(getStringValue(row,8)[0]);
                            gyldDto.setMainTainCode(Integer.valueOf(getStringValue(row,8)[1]));
                        }else{
                            gyldDto.setMainTainName("");
                            gyldDto.setMainTainCode(null);
                        }

                        gyldDto.setPrincipal(row.getCell(9)==null?"":row.getCell(9).getStringCellValue());//责任人
                        String sPhone="";
                        if(row.getCell(10)!=null){
                            String numStr = String.valueOf(row.getCell(10).getNumericCellValue());
                            sPhone = ds.format(Double.parseDouble(numStr)).trim();
                        }
                        gyldDto.setPrincipalTel(sPhone);//责任电话
                        gyldDto.setDateBuild(row.getCell(11)==null?null:row.getCell(11).getDateCellValue());//建设时间
                        gyldDto.setDatecreated(new Date());//导入时间

                        //excel表格不包含字段，安置赋值为null
                        gyldDto.setCateLevel2Id(null);
                        gyldDto.setCateLevel2Name("");
                        gyldDto.setCateLevel3Id(null);
                        gyldDto.setCateLevel3Name("");

                        gyldList.add(gyldDto);
                        if (gyldList.size() % 100 == 0) {
                            gyldInfoService.insertGyldInfoList(gyldList);
                            gyldList.clear();
                        }
                    }
                    gyldList.forEach(s-> System.out.println(s));
                    if (gyldList.size() > 0) {
                        gyldInfoService.insertGyldInfoList(gyldList);
                        gyldList.clear();
                    }
                    break;
                case "gyldxdsgl": //行道树管理EXCEL导入
                    List<GyldxdsDto> xdsList = new ArrayList<>();//行道数管理集合
                    for (Row row : sheetAt) {
                        if (row.getRowNum() == 0) {continue;}
                        GyldxdsDto gyldxdsDto = new GyldxdsDto();
                        gyldxdsDto.setCode(String.valueOf(doubleToInt(row.getCell(0)==null?0:row.getCell(0).getNumericCellValue())));//树木唯一编码
                        if(row.getCell(1)!=null){
                            gyldxdsDto.setLdObjectId(Integer.valueOf(getStringValue(row,1)[0]));//所属绿地编号
                        }else{
                            gyldxdsDto.setLdObjectId(null);
                        }
                        if(row.getCell(2)!=null){
                            gyldxdsDto.setZwpz(getStringValue(row,2)[0]);//树种
                            gyldxdsDto.setZwpzCode(Integer.valueOf(getStringValue(row,2)[1]));//树种编号
                        }else{
                            gyldxdsDto.setZwpz("");
                            gyldxdsDto.setZwpzCode(null);
                        }

                        gyldxdsDto.setSg(doubleToInt(row.getCell(3)==null?0:row.getCell(3).getNumericCellValue()));//树高
                        gyldxdsDto.setGf(doubleToInt(row.getCell(4)==null?0:row.getCell(4).getNumericCellValue()));//冠幅
                        gyldxdsDto.setXj(doubleToInt(row.getCell(5)==null?0:row.getCell(5).getNumericCellValue()));//胸径
                        gyldxdsDto.setFzd(doubleToInt(row.getCell(6)==null?0:row.getCell(6).getNumericCellValue()));//分支点
                        gyldxdsDto.setSl(doubleToInt(row.getCell(7)==null?0:row.getCell(7).getNumericCellValue()));//树龄

                        if(row.getCell(8)!=null){
                            gyldxdsDto.setSzzk(getStringValue(row,8)[0]);//生长状况
                            gyldxdsDto.setSzzkCode(Integer.valueOf(getStringValue(row,8)[1]));//生长状况编号
                        }else{
                            gyldxdsDto.setSzzk("");
                            gyldxdsDto.setSzzkCode(null);
                        }

                        if(row.getCell(9)!=null){
                            gyldxdsDto.setYhdj(getStringValue(row,9)[0]);//养护等级
                            gyldxdsDto.setYhdjCode(Integer.valueOf(getStringValue(row,9)[1]));//养护等级编号
                        }else{
                            gyldxdsDto.setYhdj("");
                            gyldxdsDto.setYhdjCode(null);
                        }

                        if(row.getCell(10)!=null){
                            gyldxdsDto.setXzqh(getStringValue(row,10)[0]);//行政区域
                            gyldxdsDto.setXzqhCode(Integer.valueOf(getStringValue(row,10)[1]));//行政区域编号
                        }else{
                            gyldxdsDto.setXzqh("");
                            gyldxdsDto.setXzqhCode(null);
                        }


                        gyldxdsDto.setRemark(row.getCell(11)==null?"":row.getCell(11).getStringCellValue());//备注
                        gyldxdsDto.setDateCreated(row.getCell(12)==null?null:row.getCell(12).getDateCellValue());//创建日期
                        if(row.getCell(13)!=null){
                            gyldxdsDto.setDldj(getStringValue(row,13)[0]);//道路等级
                        }else{
                            gyldxdsDto.setDldj("");
                        }

                        xdsList.add(gyldxdsDto);
                        if (xdsList.size() % 100 == 0) {
                            gyldxdsInfoService.insertGyldxdsInfoList(xdsList);
                            xdsList.clear();
                        }
                    }
                    if (xdsList.size() > 0) {
                        gyldxdsInfoService.insertGyldxdsInfoList(xdsList);
                        xdsList.clear();
                    }
                    break;
                case "gyldgygl": //公园信息管理EXCEL导入
                    List<GyldgyDto> gyList = new ArrayList<>();//公园信息管理集合
                    for (Row row : sheetAt) {
                        if (row.getRowNum() == 0) { continue; }
                        GyldgyDto gyldgyDto = new GyldgyDto();
                        gyldgyDto.setCode(String.valueOf(doubleToInt(row.getCell(0)==null ? 0 : row.getCell(0).getNumericCellValue()))); //公园唯一编号
                        gyldgyDto.setName(row.getCell(1)==null?"":row.getCell(1).getStringCellValue()); //公园名称
                        if(row.getCell(2) != null){
                            gyldgyDto.setDegreeName(getStringValue(row,2)[0]); //公园等级
                            gyldgyDto.setDegreeCode(Integer.valueOf(getStringValue(row,2)[1])); //公园等级编号
                        }else{
                            gyldgyDto.setDegreeName("");
                            gyldgyDto.setDegreeCode(null);
                        }

                        gyldgyDto.setAddress(row.getCell(3)==null?"":row.getCell(3).getStringCellValue());  //地址
                        if(row.getCell(4) != null ){
                            gyldgyDto.setOpenlevelName(getStringValue(row,4)[0]);  //开放情况
                            gyldgyDto.setOpenlevelCode(Integer.valueOf(getStringValue(row,4)[1]));  //开放情况编号
                        }else{
                            gyldgyDto.setOpenlevelName("");
                            gyldgyDto.setOpenlevelCode(null);
                        }

                        gyldgyDto.setTotalArea(doubleToInt(row.getCell(5)==null ? 0 : row.getCell(5).getNumericCellValue()));  //公园总面积
                        gyldgyDto.setGreenArea(doubleToInt(row.getCell(6)==null ? 0 : row.getCell(6).getNumericCellValue())); //公园绿地面积
                        gyldgyDto.setWaterArea(doubleToInt(row.getCell(7)==null ? 0 : row.getCell(7).getNumericCellValue()));  //公园水域面积
                        if(row.getCell(8) != null){
                            gyldgyDto.setMaintainName(getStringValue(row,8)[0]); //养护单位
                            gyldgyDto.setMaintainCode(Integer.valueOf(getStringValue(row,8)[1])); //养护单位编号
                        }else{
                            gyldgyDto.setMaintainName("");
                            gyldgyDto.setMaintainCode(null);
                        }

                        gyldgyDto.setPrincipal(row.getCell(9)==null?"":row.getCell(9).getStringCellValue()); //负责人
                        String sPhone = "";
                        if(row.getCell(10)!=null){
                            String numStr = String.valueOf(row.getCell(10).getNumericCellValue());
                            sPhone = ds.format(Double.parseDouble(numStr)).trim();
                        }
                        gyldgyDto.setPrincipalTel(sPhone); //负责人电话

                        if(row.getCell(11)!=null){
                            gyldgyDto.setXzqh(getStringValue(row,11)[0]); //行政区划
                            gyldgyDto.setXzqhCode(Integer.valueOf(getStringValue(row,11)[1])); //行政区划编码
                        }else{
                            gyldgyDto.setXzqh(""); //行政区划
                            gyldgyDto.setXzqhCode(null);
                        }

                        gyldgyDto.setDateBuild(row.getCell(12)==null?new Date():row.getCell(12).getDateCellValue()); //创建时间
                        gyldgyDto.setRemark( row.getCell(13)==null?"":row.getCell(13).getStringCellValue()); //备注

                        gyldgyDto.setDatecreated(new Date()); //入库时间
                        gyList.add(gyldgyDto);
                        if (gyList.size() % 100 == 0) {
                            gyldgyInfoService.insertGyldgyInfoList(gyList);
                            gyList.clear();
                        }
                    }
                    if (gyList.size() > 0) {
                        gyldgyInfoService.insertGyldgyInfoList(gyList);
                        gyList.clear();
                    }
                    break;
                case "gyldgsmmgl": //古树名木管理EXCEL导入

                    List<GyldgsmmDto> gsmmList = new ArrayList<>();//古树名木管理集合
                    for (Row row : sheetAt) {
                        if (row.getRowNum() == 0) {continue;}
                        GyldgsmmDto gyldgsmmDto = new GyldgsmmDto();
                        gyldgsmmDto.setCode(row.getCell(0)==null?"":row.getCell(0).getStringCellValue());//树木唯一编码
                        if(row.getCell(1)!=null){
                            gyldgsmmDto.setLdObjectId(Integer.valueOf(getStringValue(row,1)[0]));//所属绿地编号
                        }else{
                            gyldgsmmDto.setLdObjectId(null);
                        }

                        if(row.getCell(2)!=null){
                            gyldgsmmDto.setZwpz(getStringValue(row,2)[0]);//树种
                            gyldgsmmDto.setZwpzCode(Integer.valueOf(getStringValue(row,2)[1]));//树种编号
                        }else{
                            gyldgsmmDto.setZwpz("");
                            gyldgsmmDto.setZwpzCode(null);
                        }


                        gyldgsmmDto.setSg(doubleToInt(row.getCell(3)==null?0:row.getCell(3).getNumericCellValue()));//树高
                        gyldgsmmDto.setGf(doubleToInt(row.getCell(4)==null?0:row.getCell(4).getNumericCellValue()));//冠幅
                        gyldgsmmDto.setXj(doubleToInt(row.getCell(5)==null?0:row.getCell(5).getNumericCellValue()));//胸径
                        gyldgsmmDto.setFzd(doubleToInt(row.getCell(6)==null?0:row.getCell(6).getNumericCellValue()));//分支点
                        gyldgsmmDto.setSl(doubleToInt(row.getCell(7)==null?0:row.getCell(7).getNumericCellValue()));//树龄
                        if(row.getCell(8) != null){
                            gyldgsmmDto.setSzzk(getStringValue(row,8)[0]);//生长状况
                            gyldgsmmDto.setSzzkCode(Integer.valueOf(getStringValue(row,8)[1]));//生长状况编号
                        }else{
                            gyldgsmmDto.setSzzk("");
                            gyldgsmmDto.setSzzkCode(null);
                        }
                        if(row.getCell(9) != null){
                            gyldgsmmDto.setYhdj(getStringValue(row,9)[0]);//养护等级
                            gyldgsmmDto.setYhdjCode(Integer.valueOf(getStringValue(row,9)[1]));//养护等级编号
                        }else{
                            gyldgsmmDto.setYhdj("");
                            gyldgsmmDto.setYhdjCode(null);
                        }

                        if(row.getCell(10)!=null){
                            gyldgsmmDto.setXzqh(getStringValue(row,10)[0]);//行政区域
                            gyldgsmmDto.setXzqhCode(Integer.valueOf(getStringValue(row,10)[1]));//行政区域编号
                        }else{
                            gyldgsmmDto.setXzqh("");
                            gyldgsmmDto.setXzqhCode(null);
                        }


                        gyldgsmmDto.setPrincipal(row.getCell(11)==null ? "" : row.getCell(11).getStringCellValue()); //负责人
                        String sPhone = "";
                        if(row.getCell(12)!=null){
                            String numStr = String.valueOf(row.getCell(12).getNumericCellValue());
                            sPhone = ds.format(Double.parseDouble(numStr)).trim();
                        }
                        gyldgsmmDto.setPrincipalTel(sPhone); //负责人电话
                        gyldgsmmDto.setRemark(row.getCell(13)==null?"":row.getCell(13).getStringCellValue());//备注
                        gyldgsmmDto.setDateCreated(new Date());//创建日期
                        gsmmList.add(gyldgsmmDto);
                        if (gsmmList.size() % 100 == 0) {
                            gyldgsmmInfoService.insertGyldgsmmInfoList(gsmmList);
                            gsmmList.clear();
                        }
                    }
                    if (gsmmList.size() > 0) {
                        gyldgsmmInfoService.insertGyldgsmmInfoList(gsmmList);
                        gsmmList.clear();
                    }
                    break;
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("EXCEL入库失败："+e.getMessage());
            return RespMsg.error("上传失败,请检查模版内容");

        }

        return RespMsg.ok();
    }

    /**
     * 获取字符串并分割
     * @param row
     * @param num
     * @return
     */
    private String [] getStringValue(Row row, Integer num){
        return row.getCell(num).getStringCellValue().split("_");
    }

    /**
     * double 转 int
     *
     * @param doubleValue
     * @return
     */
    private int doubleToInt(Double doubleValue) {
        return doubleValue.intValue();
    }

    /**
     * EXCEL生成动态下拉菜单
     * @param fileName
     * @param type
     * @throws Exception
     */
    @GetMapping("/createExcel")
    @ApiOperation("EXCEL模板生成下拉菜单")
    @ApiImplicitParams({@ApiImplicitParam(name = "gyldxxgl", value = "绿地信息管理EXCEL导入"),
            @ApiImplicitParam(name = "gyldxdsgl", value = "行道树管理EXCEL导入"),
            @ApiImplicitParam(name = "gyldgygl", value = "公园信息管理EXCEL导入"),
            @ApiImplicitParam(name = "fileName", value = "文件名"),
            @ApiImplicitParam(name = "type", value = "分类")
    })
    public void createExcel(String fileName, String type, HttpServletResponse response)throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path+fileName+".xlsx"));//读取
        switch (type) {
            case "gyldxxgl":
                XSSFSheet ldxxglSheetAt1 = workbook.getSheetAt(1);
                XSSFSheet ldxxglSheetAt2 = workbook.getSheetAt(2);
                XSSFSheet ldxxglSheetAt3 = workbook.getSheetAt(3);
                XSSFSheet ldxxglSheetAt4 = workbook.getSheetAt(4);
                XSSFSheet ldxxglSheetAt5 = workbook.getSheetAt(5);
                XSSFSheet ldxxglSheetAt6 = workbook.getSheetAt(6);
                List<String> ldlbList = partsCategoryService.selectLdlbData();
                List<String> ldxzList = partsCategoryService.selectLdxzData();
                List<String> ldqyList = gyldInfoService.selectQyData();
                List<String> lddjList = partsCategoryService.selectLddjData();
                List<String> ldyhlbList = partsCategoryService.selectLdyhlxData();
                List<String> ldyhdwList = partsCategoryService.selectLdyhdwData();
                for(int i = 0;i<ldlbList.size();i++){//绿地信息管理--绿地类别
                    XSSFRow row = ldxxglSheetAt1.createRow(i);
                    row.createCell(0).setCellValue(ldlbList.get(i));
                }
                for(int i = 0;i<ldxzList.size();i++){//绿地信息管理--绿地性质
                    XSSFRow row = ldxxglSheetAt2.createRow(i);
                    row.createCell(0).setCellValue(ldxzList.get(i));
                }
                for(int i = 0;i<ldqyList.size();i++){//绿地信息管理--绿地区域
                    XSSFRow row = ldxxglSheetAt3.createRow(i);
                    row.createCell(0).setCellValue(ldqyList.get(i));
                }
                for(int i = 0;i<lddjList.size();i++){//绿地信息管理--绿地等级
                    XSSFRow row = ldxxglSheetAt4.createRow(i);
                    row.createCell(0).setCellValue(lddjList.get(i));
                }
                for(int i = 0;i<ldyhlbList.size();i++){//绿地信息管理--绿地养护类别
                    XSSFRow row = ldxxglSheetAt5.createRow(i);
                    row.createCell(0).setCellValue(ldyhlbList.get(i));
                }
                for(int i = 0;i<ldyhdwList.size();i++){//绿地信息管理--绿地养护单位
                    XSSFRow row = ldxxglSheetAt6.createRow(i);
                    row.createCell(0).setCellValue(ldyhdwList.get(i));
                }
                writeExcelData(path,fileName,workbook,response);
                break;
            case "gyldxdsgl":
                XSSFSheet xdsglSheetAt1 = workbook.getSheetAt(1);
                XSSFSheet xdsglSheetAt2 = workbook.getSheetAt(2);
                XSSFSheet xdsglSheetAt3 = workbook.getSheetAt(3);
                XSSFSheet xdsglSheetAt4 = workbook.getSheetAt(4);
                XSSFSheet xdsglSheetAt5 = workbook.getSheetAt(5);
                XSSFSheet xdsglSheetAt6 = workbook.getSheetAt(6);
                List<String> ldList = gyldInfoService.selectLdData();
                List<String> qyList = gyldInfoService.selectQyData();
                List<String> zwpzList = partsCategoryService.selectSzData();
                List<String> szzkList = partsCategoryService.selectSzzkData();
                List<String> yhdjList = partsCategoryService.selectYhdjData();
                List<String> dldjList = partsCategoryService.selectDldjData();
                for(int i = 0;i<ldList.size();i++){//行道数--绿地编号
                    XSSFRow row = xdsglSheetAt1.createRow(i);
                    row.createCell(0).setCellValue(ldList.get(i));
                }
                for(int i = 0;i<zwpzList.size();i++){//行道数--植物品种
                    XSSFRow row = xdsglSheetAt2.createRow(i);
                    row.createCell(0).setCellValue(zwpzList.get(i));
                }

                for(int i = 0;i<szzkList.size();i++){//行道数--生长状况
                    XSSFRow row = xdsglSheetAt3.createRow(i);
                    row.createCell(0).setCellValue(szzkList.get(i));
                }

                for(int i = 0;i<yhdjList.size();i++){//行道数--养护等级
                    XSSFRow row = xdsglSheetAt4.createRow(i);
                    row.createCell(0).setCellValue(yhdjList.get(i));
                }

                for(int i = 0;i<qyList.size();i++){//行道数--区域编号
                    XSSFRow row = xdsglSheetAt5.createRow(i);
                    row.createCell(0).setCellValue(qyList.get(i));
                }
                for(int i = 0;i<dldjList.size();i++){//行道数--道路等级
                    XSSFRow row = xdsglSheetAt6.createRow(i);
                    row.createCell(0).setCellValue(dldjList.get(i));
                }
                writeExcelData(path,fileName,workbook,response);
                break;
            case "gyldgygl":
                XSSFSheet gyglSheetAt1 = workbook.getSheetAt(1);
                XSSFSheet gyglSheetAt2 = workbook.getSheetAt(2);
                XSSFSheet gyglSheetAt3 = workbook.getSheetAt(3);
                XSSFSheet gyglSheetAt4 = workbook.getSheetAt(4);
                List<String> gydjList = partsCategoryService.selectgydjData(); //公园等级集合
                List<String> gykfqkList = partsCategoryService.selectgykfqkData(); //公园开放情况
                List<String> gyyhdwList = partsCategoryService.selectgyyhdwData(); //公园养护单位
                List<String> gyqyList = gyldInfoService.selectQyData(); //公园行政区域
                for(int i = 0;i<gydjList.size();i++){
                    XSSFRow row = gyglSheetAt1.createRow(i);
                    row.createCell(0).setCellValue(gydjList.get(i));
                }
                for(int i = 0;i<gykfqkList.size();i++){
                    XSSFRow row = gyglSheetAt2.createRow(i);
                    row.createCell(0).setCellValue(gykfqkList.get(i));
                }
                for(int i = 0;i<gyyhdwList.size();i++){
                    XSSFRow row = gyglSheetAt3.createRow(i);
                    row.createCell(0).setCellValue(gyyhdwList.get(i));
                }
                for(int i = 0;i<gyqyList.size();i++){
                    XSSFRow row = gyglSheetAt4.createRow(i);
                    row.createCell(0).setCellValue(gyqyList.get(i));
                }
                writeExcelData(path,fileName,workbook,response);
                break;
            case "gyldgsmmgl":
                XSSFSheet gsmmglSheetAt1 = workbook.getSheetAt(1);
                XSSFSheet gsmmglSheetAt2 = workbook.getSheetAt(2);
                XSSFSheet gsmmglSheetAt3 = workbook.getSheetAt(3);
                XSSFSheet gsmmglSheetAt4 = workbook.getSheetAt(4);
                XSSFSheet gsmmglSheetAt5 = workbook.getSheetAt(5);
                List<String> gsmmldlbList = gyldInfoService.selectLdData();
                List<String> gsmmszList = partsCategoryService.selectSzData();
                List<String> gsmmszzkList = partsCategoryService.selectSzzkData();
                List<String> gsmmyhdjList = partsCategoryService.selectYhdjData();
                List<String> gsmmqyList =  gyldInfoService.selectQyData();
                for(int i = 0;i<gsmmldlbList.size();i++){
                    XSSFRow row = gsmmglSheetAt1.createRow(i);
                    row.createCell(0).setCellValue(gsmmldlbList.get(i));
                }
                for(int i = 0;i<gsmmszList.size();i++){
                    XSSFRow row = gsmmglSheetAt2.createRow(i);
                    row.createCell(0).setCellValue(gsmmszList.get(i));
                }
                for(int i = 0;i<gsmmszzkList.size();i++){
                    XSSFRow row = gsmmglSheetAt3.createRow(i);
                    row.createCell(0).setCellValue(gsmmszzkList.get(i));
                }
                for(int i = 0;i<gsmmyhdjList.size();i++){
                    XSSFRow row = gsmmglSheetAt4.createRow(i);
                    row.createCell(0).setCellValue(gsmmyhdjList.get(i));
                }
                for(int i = 0;i<gsmmqyList.size();i++){
                    XSSFRow row = gsmmglSheetAt5.createRow(i);
                    row.createCell(0).setCellValue(gsmmqyList.get(i));
                }
                writeExcelData(path,fileName,workbook,response);
                break;

        }
    }

    /**
     * 生成excel通用方法
     * @param path
     * @param fileName
     * @param workbook
     */
    private void writeExcelData(String path,String fileName,XSSFWorkbook workbook,HttpServletResponse response){
        try {
            FileOutputStream out = new FileOutputStream(path+fileName+"_zj"+".xlsx");//追加
            out.flush();
            workbook.write(out);
            out.close();
            ExportUtil.setResponseHeader(response, fileName+"_zj"+".xlsx");
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith("xls")) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                //2007 及2007以上
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return workbook;
    }

}
