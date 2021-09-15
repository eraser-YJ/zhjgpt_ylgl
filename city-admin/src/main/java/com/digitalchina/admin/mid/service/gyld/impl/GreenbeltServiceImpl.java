package com.digitalchina.admin.mid.service.gyld.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.gis.mapper.ConfigMapper;
import com.digitalchina.admin.mid.entity.DataDict;
import com.digitalchina.admin.mid.service.gyld.GreenbeltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GreenbeltServiceImpl implements GreenbeltService {

    @Autowired
    private ConfigMapper mapper;

    @Override
    public Map<String, Object> xzqhTree(List<DataDict> xzqhs) {

        Map<String, Object> treeRoot = new HashMap<String, Object>() {{
            put("ID", null);
            put("NAME", "长春新区");
            put("TYPE", "XZQH_CODE");
            put("CHILDREN", new ArrayList<>());
        }};

        for (DataDict xzqh : xzqhs) {
            Map<String, Object> xzqhMap = new HashMap<String, Object>() {{
                put("ID", xzqh.getId());
                put("NAME", xzqh.getItemName());
                put("TYPE", "XZQH_CODE");
                put("CHILDREN", mapper.customListAll(
                        "select objectid as ID, NAME, 'LD_OBJECTID' as type from GYLD_LD where ADMDIV_CODE=" + xzqh.getId()
                ));
            }};

            ((ArrayList) treeRoot.get("CHILDREN")).add(xzqhMap);
        }

        return treeRoot;
    }

    @Override
    public void listResourcesOfGreenbelt(Page<Map<String, Object>> page, Long ldObjectid) {
        String sql = "select '行道树' as type, zwpz,code  from GYLD_XDS where LD_OBJECTID=%s " +
                " union " +
                " select '古树名木' as type, zwpz,code  from GYLD_GSMM  where LD_OBJECTID=%s";
        sql = String.format(sql, ldObjectid, ldObjectid);
        List<Map<String, Object>> records = mapper.customPage(page, sql);
        page.setRecords(records);
    }
}
