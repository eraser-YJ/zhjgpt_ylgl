package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.AdminDivsion;

import java.util.List;

public interface AdminDivsionService extends IService<AdminDivsion> {

    List<AdminDivsion> tree(Integer aid);
}
