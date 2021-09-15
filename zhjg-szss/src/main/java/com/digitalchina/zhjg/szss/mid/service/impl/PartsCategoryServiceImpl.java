package com.digitalchina.zhjg.szss.mid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.constant.enums.GisType;
import com.digitalchina.zhjg.szss.mid.entity.PartsCategory;
import com.digitalchina.zhjg.szss.mid.mapper.PartsCategoryMapper;
import com.digitalchina.zhjg.szss.mid.service.PartsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class PartsCategoryServiceImpl extends ServiceImpl<PartsCategoryMapper, PartsCategory> implements PartsCategoryService {

    @Autowired
    private PartsCategoryMapper partsCategoryMapper;

    @Override
    public List<PartsCategory> tree(List<String> modules, Integer rootId, Boolean status) {
        List<PartsCategory> allPartsCategories = partsCategoryMapper.selectPartsCate(modules, rootId, status);
        return buildTree(allPartsCategories, rootId);
    }

    private List<PartsCategory> buildTree(List<PartsCategory> allPartsCategories, Integer rootId) {
        // 初始化一个虚的根对象，作为查询到的顶级数据的parent对象
        PartsCategory root = new PartsCategory();
        root.setId(rootId);

        Map<Integer, List<PartsCategory>> listsMap = new HashMap<>();
        Map<Integer, PartsCategory> partsCategoriesMap = new HashMap<Integer, PartsCategory>() {{
            put(rootId, root);
            allPartsCategories.forEach(partsCategory -> put(partsCategory.getId(), partsCategory));
        }};

        for (PartsCategory partsCategory : allPartsCategories) {
            Integer parentId = partsCategory.getParentId();
            PartsCategory parentCategory = partsCategoriesMap.get(parentId);

            if (parentCategory == null) {
                continue;
            }

            List<PartsCategory> list = listsMap.get(parentId);

            if (list == null) {
                list = new ArrayList<>();
                listsMap.put(parentId, list);
                parentCategory.setChildren(list);
            }

            list.add(partsCategory);
        }


        return root.getChildren() == null ? new ArrayList<>() : root.getChildren();
    }

    @Override
    @Transactional
    public void create(PartsCategory partsCategory) {
        // level
        if (partsCategory.getParentId() != null) {
            partsCategory.setParent(getById(partsCategory.getParentId()));
            Assert.state(partsCategory.getParent() != null, "parent_id值不正确");
            partsCategory.setLevel(partsCategory.getParent().getLevel() + 1);
        } else {
            partsCategory.setLevel(1);
        }

        // sort
        if (partsCategory.getSort() == null) {
            partsCategory.setSort(100);
        }
        // gistype
        if (partsCategory.getGisType() == null) {
            partsCategory.setGisType(GisType.any);
        }

        // insert
        save(partsCategory);
        // idpath
        List<Integer> pathsList = new ArrayList<>();
        if (partsCategory.getParent() != null) {
            pathsList.addAll(Arrays.asList(partsCategory.getParent().getIdPath()));
        }
        pathsList.add(partsCategory.getId());
        Integer[] paths = new Integer[pathsList.size()];
        partsCategory.setIdPath(pathsList.toArray(paths));
        partsCategoryMapper.updateIdPaths(partsCategory);
    }

    @Override
    public List<String> selectSzData() {
        return partsCategoryMapper.selectSzData();
    }

    @Override
    public List<String> selectSzzkData() {
        return partsCategoryMapper.selectSzzkData();
    }

    @Override
    public List<String> selectYhdjData() {
        return partsCategoryMapper.selectYhdjData();
    }

    @Override
    public List<String> selectDldjData() {
        return partsCategoryMapper.selectDldjData();
    }

    @Override
    public List<String> selectLdlbData() {
        return partsCategoryMapper.selectLdlbData();
    }

    @Override
    public List<String> selectLdxzData() {
        return partsCategoryMapper.selectLdxzData();
    }

    @Override
    public List<String> selectLddjData() {
        return partsCategoryMapper.selectLddjData();
    }

    @Override
    public List<String> selectLdyhlxData() {
        return partsCategoryMapper.selectLdyhlxData();
    }

    @Override
    public List<String> selectLdyhdwData() {
        return partsCategoryMapper.selectLdyhdwData();
    }

    @Override
    public List<String> selectgydjData() {
        return partsCategoryMapper.selectgydjData();
    }

    @Override
    public List<String> selectgykfqkData() {
        return partsCategoryMapper.selectgykfqkData();
    }

    @Override
    public List<String> selectgyyhdwData() {
        return partsCategoryMapper.selectgyyhdwData();
    }
}
