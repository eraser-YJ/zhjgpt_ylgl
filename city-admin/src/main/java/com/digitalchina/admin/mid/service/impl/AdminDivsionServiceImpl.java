package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.AdminDivsion;
import com.digitalchina.admin.mid.mapper.AdminDivsionMapper;
import com.digitalchina.admin.mid.service.AdminDivsionService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminDivsionServiceImpl extends ServiceImpl<AdminDivsionMapper, AdminDivsion> implements AdminDivsionService {

    @Autowired
    private AdminDivsionMapper adminDivsionMapper;

    @Override
    public List<AdminDivsion> tree(Integer aid) {
        // aid为null查询全部,否则查询adupnms包含aid但id不是aid的,既查出aid对应的所有后代
        List<AdminDivsion> Alllist = adminDivsionMapper.selectWarnAdmList(aid);
        AdminDivsion adminD = new AdminDivsion();
        adminD.setAdid(aid);

        Map<Integer,List<AdminDivsion>> listMap = new HashMap<>();
        Map<Integer,AdminDivsion> adminDivsionMap = new HashMap<Integer,AdminDivsion>(){{
            put(aid,adminD);
            Alllist.forEach(adminDivsion -> put(adminDivsion.getAdid(),adminDivsion));
        }};

        for(AdminDivsion adminDivsion : Alllist){
            Integer adpid = adminDivsion.getAdpid();
            List<AdminDivsion> list = listMap.get(adpid);
            if(list == null){
                list = new ArrayList<>();
                listMap.put(adpid,list);
                adminDivsionMap.get(adpid).setChildren(list);
            }
            list.add(adminDivsion);
        }
        return adminD.getChildren() == null ? new ArrayList<>() : adminD.getChildren();
    }
}
