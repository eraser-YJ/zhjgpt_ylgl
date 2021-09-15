package com.digitalchina.admin.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.gis.entity.Config;
import com.digitalchina.admin.mid.dto.request.CommonRequest;

import java.util.List;
import java.util.Map;

public interface ExportDataService  extends IService<Config> {

    /**
     * 查询所有数据
     * @param request
     * @param module
     * @return
     */
    List<Map<String, Object>> customAll(CommonRequest request, String module);
}
