package com.digitalchina.modules.mapper;

import com.digitalchina.modules.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
@Component
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<SysRoleMenu> selectMidMenu(Integer uid);

    List<SysRoleMenu> selectNoticeRole(Integer uid);

}
