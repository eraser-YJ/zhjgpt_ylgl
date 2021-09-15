package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.DataDict;
import com.digitalchina.admin.mid.entity.DataDictGroup;
import com.digitalchina.admin.mid.mapper.DataDictMapper;
import com.digitalchina.admin.mid.mapper.xml.DataDictGroupMapper;
import com.digitalchina.admin.mid.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataDictServiceImpl extends ServiceImpl<DataDictMapper, DataDict> implements DataDictService {

    @Autowired
    private DataDictMapper dataDictMapper;

    @Autowired
    private DataDictGroupMapper groupMapper;

    @Override
    public List<DataDict> getDictNameList(String dataDictCode) {


//        DataDict dataDict = new DataDict();
//        dataDict.setDictCode(dataDictCode);
//        Map<String,String> map = new HashMap<>();

        List<DataDict> allDataDictList = baseMapper.selectList(
                new QueryWrapper<DataDict>().eq("dict_code", dataDictCode).eq("status", "1").orderByAsc("sort")
        );
        return allDataDictList;
    }

    @Override
    public List<DataDictGroup> getDictGroups(String module) {
        return groupMapper.selectList(new QueryWrapper<DataDictGroup>().eq("remark", module));
    }

    @Override
    public List<DataDict> getItemsByCode(String dictCode) {
        return dataDictMapper.selectList(
                new QueryWrapper<DataDict>().eq("dict_code", dictCode).orderByAsc("sort")
        );
    }

    @Override
    public void createItem(DataDict dictItem) {
        dictItem.setStatus("1");
        dictItem.setItemValue(dictItem.getItemName());

        List<Map<String, Object>> result = dataDictMapper.selectMaps(new QueryWrapper<DataDict>().select("max(sort)").eq("dict_code", dictItem.getDictCode()));
        int maxSort = 0;
        if (result.size() > 0 && result.get(0) != null) {
            maxSort = (int) result.get(0).get("max");
        }
        dictItem.setSort(maxSort + 10);

        save(dictItem);
    }

    @Override
    public void updatItem(DataDict dictItem) {
        updateById(dictItem);
    }
}
