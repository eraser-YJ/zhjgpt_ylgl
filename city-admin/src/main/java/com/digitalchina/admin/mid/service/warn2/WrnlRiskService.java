package com.digitalchina.admin.mid.service.warn2;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.dto.warn2.WarnRiskDetailDto;
import com.digitalchina.admin.mid.entity.WrnlRisk;
import com.digitalchina.modules.entity.SysDept;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2020-01-02
 */
public interface WrnlRiskService extends IService<WrnlRisk> {

	/**
	 * 列表
	 * 
	 * @param page
	 * @param special
	 * @param topic
	 * @param status
	 * @param keyword
	 */
	Page<WrnlRisk> iotlist(Page<WrnlRisk> page, String special, String topic, Integer status, String keyword);

	/**
	 * 业务组件列表
	 * 
	 * @param page
	 * @param special
	 * @param topic
	 * @param status
	 * @param keyword
	 */
	Page<WrnlRisk> nflist(Page<WrnlRisk> page, String special, String topic, Integer status, String keyword);

	/**
	 * 明细
	 * 
	 * @param id
	 * @return
	 */
	WarnRiskDetailDto detail(Integer id);

	/**
	 * 保存
	 * 
	 * @param data
	 * @return
	 */
	void save(WarnRiskDetailDto data);

	/**
	 * 部门
	 * 
	 * @return
	 */
	List<SysDept> depts();

}
