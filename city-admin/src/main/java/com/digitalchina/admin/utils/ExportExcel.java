package com.digitalchina.admin.utils;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ExportExcel {
    // 发送响应流方法
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public static void export(String title, String[] rowName, List<Object[]> dataList, String fileName, OutputStream out) throws Exception {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook (); // 创建工作簿对象
            XSSFSheet sheet = workbook.createSheet(title); // 创建工作表
            XSSFRow rowm = sheet.createRow(0);  // 产生表格标题行
            XSSFCell cellTiltle = rowm.createCell(0);   //创建表格标题列
            // sheet样式定义;    getColumnTopStyle();    getStyle()均为自定义方法 --在下面,可扩展

            //合并表格标题行，合并列数为列名的长度,第一个0为起始行号，第二个1为终止行号，第三个0为起始列好，第四个参数为终止列号
            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, (rowName.length - 1));
            sheet.addMergedRegion(cellRangeAddress);

            XSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);// 获取列头样式对象
            XSSFCellStyle style = getStyle(workbook); // 获取单元格样式对象
            style.setWrapText(true);


            cellTiltle.setCellStyle(columnTopStyle);    //设置标题行样式
            cellTiltle.setCellValue(title);     //设置标题行值

            // 使用RegionUtil类为合并后的单元格添加边框
            RegionUtil.setBorderBottom(0, cellRangeAddress, sheet,workbook); // 下边框
            RegionUtil.setBorderLeft(0, cellRangeAddress, sheet,workbook); // 左边框
            RegionUtil.setBorderRight(0, cellRangeAddress, sheet,workbook); // 右边框
            RegionUtil.setBorderTop(0, cellRangeAddress, sheet,workbook); // 上边框

            int columnNum = rowName.length;     // 定义所需列数
            XSSFRow rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)
            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                XSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
                XSSFRichTextString text = new XSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text); // 设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
            }

            // 将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);   // 遍历每个对象
                XSSFRow row = sheet.createRow(i + 3);   // 创建所需的行数
                for (int j = 0; j < obj.length; j++) {
                    XSSFCell cell = null;   // 设置单元格的数据类型
//                    if (j == 0) {
//                        cell = row.createCell(j, XSSFCell.CELL_TYPE_NUMERIC);
//                        cell.setCellValue(i + 1);
//                    } else {
                        cell = row.createCell(j, XSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue(""); // 设置单元格的值
                        }
//                    }
                    cell.setCellStyle(style); // 设置单元格样式
                }
            }

            // 让列宽随着导出的列长自动适应




            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    XSSFRow currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                   /* if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue()
                                    .getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }*/
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else if(colNum == columnNum-1 || colNum == columnNum-3) {

                    sheet.setColumnWidth(colNum, 20 * 256);


                }else {
                    sheet.autoSizeColumn(colNum,true);
                    sheet.setColumnWidth(colNum, sheet.getColumnWidth(colNum) * 16 / 10);
//                	sheet.setColumnWidth(colNum, 40 * 256);
                }
            }

            XSSFPrintSetup printSetup = sheet.getPrintSetup();
            //设置页边距
            // printSetup.setHeaderMargin((double) 0.44); //页眉
            // printSetup.setFooterMargin((double) 0.2);//页脚

            //设置页宽
            //printSetup.setFitWidth((short)1);
            //printSetup.setFitHeight((short)1000);

            //设置打印方向，横向就是true
            printSetup.setLandscape(true);
            //设置A4纸
            printSetup.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);

            //FileOutputStream output=new FileOutputStream("d:\\workbook1.xlsx");

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 列头单元格样式
     */
    private static XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {

        // 设置字体
        XSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 10);
        // 字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        // 设置左边框;
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 设置右边框;
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        // 设置顶边框;
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);

        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    /*
     * 列数据信息单元格样式
     */
    private static XSSFCellStyle getStyle(XSSFWorkbook workbook) {
        // 设置字体
        XSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short)10);
        // 字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        // 设置左边框;
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 设置右边框;
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        // 设置顶边框;
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        return style;
    }


    /**
     * 关闭输出流
     * @param os
     */
    public static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    public static boolean isCHinese(char c){
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if(ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                ||ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                ||ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                ||ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  // GENERAL_PUNCTUATION 判断中文的“号
                ||ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION     // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
                ||ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS    // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
        )
            return true;
        return false;
    }

    public static boolean isCHinese(String str){
        char[] ch =  str.toCharArray();
        for (char c : ch) {
            if(isCHinese(c))
                return true;
        }
        return false;
    }
}
