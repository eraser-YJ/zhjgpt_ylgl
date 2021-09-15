package com.digitalchina.event.midwarn.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.event.midwarn.entity.Rptgen;
import com.digitalchina.event.midwarn.mapper.RptgenMapper;
import com.digitalchina.event.midwarn.service.RptgenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * 生成的报告 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Service
public class RptgenServiceImpl extends ServiceImpl<RptgenMapper, Rptgen> implements RptgenService {

	@Value("${docx.drivePath}")
	private String drivePath;

	@Override
	public IPage<Rptgen> rptgenList(Integer size, Integer current, Integer rpitv, String crdt,String keyword) {
		IPage<Rptgen> page = new Page<>(current, size);
		String crdtStart = null;
		String crdtEnd = null;
		if (StrUtil.isNotBlank(crdt)) {
			crdtStart = (crdt.split("-")[0].trim() + " 00:00:00").replaceAll("/", "-");
			crdtEnd = (crdt.split("-")[1].trim() + " 23:59:59").replaceAll("/", "-");
		}
		List<Rptgen> rptgenList = baseMapper.rptgenList(page, rpitv, crdtStart,crdtEnd,keyword);
		return page.setRecords(rptgenList);
	}

	@Override
	public void browse(Integer rgid, HttpServletResponse response) {
		QueryWrapper<Rptgen> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(Rptgen.RGID, rgid);
		Rptgen rptgen = super.getOne(queryWrapper);

		/* 获取文件路径 */
		String filePath = getFilePath(rptgen.getRgpath());

		/* 获得文件名后缀 */
		response.setContentType(getContentType(filePath));
		response.setCharacterEncoding("gb2312");

		/* 将文件写入输出流,显示在界面上,实现预览效果 */
		browse(filePath, response);
	}

	// 文件流实现预览
	private void browse(String filePath, HttpServletResponse response) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			OutputStream os = response.getOutputStream();
			try {
				int count = 0;
				byte[] buffer = new byte[1024 * 1024];
				while ((count = fis.read(buffer)) != -1)
					os.write(buffer, 0, count);
				os.flush();
				/* responseData.setSuccess(true); */
			} catch (IOException e) {
				throw new ServiceException("将文件写入输出流失败!");
			} finally {
				if (os != null)
					os.close();
				if (fis != null)
					fis.close();
			}
		} catch (IOException e) {
			throw new ServiceException("读取预警报告失败!");
		}
	}

	// 获取文件头类型
	private String getContentType(String filePath) {
		String ext = "";
		if (!"".equals(filePath) && filePath.contains(".")) {
			ext = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()).toUpperCase();
		}
		/* 预览pdf */
		if ("pdf".equalsIgnoreCase(ext)) {
			return "application/pdf";
		}
		/* 预览pdf */
		if ("html".equalsIgnoreCase(ext)) {
			return "text/html";
		}
		throw new ServiceException("暂不支持的类型:" + ext);
	}

	// 根据项目所在的服务器环境,确定路径中的 / 和 \
	private String getFilePath(String rgpath) {
		String osName = System.getProperty("os.name");
		if (Pattern.matches("Linux.*", osName)) {
			rgpath = "/" + rgpath.replace("\\", "/");
		} else if (Pattern.matches("Windows.*", osName)) {
			rgpath.replace("/", "\\");
		}
		return drivePath + rgpath;
	}
}
