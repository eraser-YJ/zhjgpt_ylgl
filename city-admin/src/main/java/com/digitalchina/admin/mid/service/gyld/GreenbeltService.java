package com.digitalchina.admin.mid.service.gyld;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.DataDict;

import java.util.List;
import java.util.Map;

public interface GreenbeltService {
    Map<String, Object> xzqhTree(List<DataDict> xzqhs);

    /**
     * 查询绿地下资源
     * @param page
     * @param ldObjectid
     */
    void listResourcesOfGreenbelt(Page<Map<String, Object>> page, Long ldObjectid);
}
