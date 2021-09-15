package com.digitalchina.admin.mid.service;

import com.digitalchina.admin.mid.entity.DataDict;
import com.digitalchina.admin.mid.entity.DataDictGroup;

import java.util.List;

public interface DataDictService {
    List<DataDict> getDictNameList(String dataDictCode);

    List<DataDictGroup> getDictGroups(String module);

    List<DataDict> getItemsByCode(String dictCode);

    void createItem(DataDict dictItem);

    void updatItem(DataDict param);
}
