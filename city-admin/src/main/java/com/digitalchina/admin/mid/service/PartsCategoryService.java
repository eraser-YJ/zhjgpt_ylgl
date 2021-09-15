package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.PartsCategory;

import java.util.List;

public interface PartsCategoryService extends IService<PartsCategory> {

    /**
     * 按部件code查询本部件及其逐级祖先对象，按后代到祖先向上顺序依次排序
     *
     * @param code
     * @return
     */
    List<PartsCategory> partsAncestors(String code);
}
