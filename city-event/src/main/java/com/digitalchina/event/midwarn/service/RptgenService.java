package com.digitalchina.event.midwarn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.event.midwarn.entity.Rptgen;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 生成的报告 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
public interface RptgenService extends IService<Rptgen> {

	/**
	 * 预警报告列表
	 * 
	 * @param size
	 * @param current
	 * @param rpitv
	 * @param crdt
	 * @param keyword
	 * @return
	 */
	IPage<Rptgen> rptgenList(Integer size, Integer current, Integer rpitv, String crdt,String keyword);

	/**
	 * 预览
	 * 
	 * @param rgid
	 * @param response
	 */
	void browse(Integer rgid, HttpServletResponse response);

}
