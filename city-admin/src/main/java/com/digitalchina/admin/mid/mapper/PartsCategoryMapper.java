package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.PartsCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PartsCategoryMapper extends BaseMapper<PartsCategory> {

    @Select("WITH RECURSIVE T AS " +
            "(SELECT ID, NAME, code, parent_id FROM szss.parts_category WHERE code = #{code} " +
            "UNION ALL SELECT K.ID, K.NAME, K.code, K.parent_id FROM szss.parts_category K,T WHERE K.ID = T.parent_id ) " +
            "SELECT id,name FROM T")
    List<PartsCategory> selectAncestorsByCode(String code);
}
