package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.dto.SignTreeDto2;
import com.digitalchina.admin.mid.entity.SignTree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
public interface SignTreeMapper extends BaseMapper<SignTree> {


    /**
     * 查询子树，都不传查整个树
     * @param tid 版本id 必传
     * @param root 子树根id 可选
     * @param rootPath 子树根路径 可选
     * @return
     */
    List<SignTreeDto2> queryTreeByroot(@Param("tid")Integer tid, @Param("root")Integer root,@Param("rootPath") String rootPath);

    /**
     * 根据版本id查一棵树
     * @param tid 版本id
     * @return
     */
    @Select("select * from sign.sign_tree where tid =#{tid} order by nlevel")
    List<SignTreeDto2> queryTreeByTid(@Param("tid")Integer tid);

}
