package com.digitalchina.admin.mid.service.warn2;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.dto.warn2.WarnMesuDetailDto;
import com.digitalchina.admin.mid.dto.warn2.WarnMesuDto;
import com.digitalchina.admin.mid.entity.Iotdvc;
import com.digitalchina.admin.mid.entity.warn.Nfelement;

/**
 * <预警指标接口>
 * 
 * @author lihui
 * @since 2019年12月27日
 */
public interface WarnMesuService {

	/**
	 * 物联网指标列表
	 * 
	 * @param page
	 * @param special
	 * @param topic
	 * @param cmnid
	 */
	Page<WarnMesuDto> iotlist(Page<Iotdvc> page, String special, String topic, Integer cmnid, String keyword);

	/**
	 * 业务组件指标列表
	 * 
	 * @param page
	 * @param special
	 * @param topic
	 * @param cmnid
	 */
	IPage<WarnMesuDto> nflist(Page<Nfelement> page, String special, String topic, Integer cmnid, String keyword);

	/**
	 * 物联网指标明细
	 * 
	 * @param id
	 * @return
	 */
	WarnMesuDetailDto iotDetail(Integer id);

	/**
	 * 业务组件指标明细
	 * 
	 * @param id
	 * @return
	 */
	WarnMesuDetailDto nfDetail(Integer id);

}
