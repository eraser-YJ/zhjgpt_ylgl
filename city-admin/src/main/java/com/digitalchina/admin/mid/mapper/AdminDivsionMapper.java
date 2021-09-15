package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.AdminDivsion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminDivsionMapper extends BaseMapper<AdminDivsion> {
    List<AdminDivsion> selectWarnAdmList(@Param("aid")Integer aid);
}
