package com.digitalchina.admin.mid.mapping;

import com.digitalchina.admin.mid.dto.NodeWeightDto;
import com.digitalchina.admin.mid.entity.SignTree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * <p>
 *SignTreeDto转换
 * </p>
 *
 * @author liuping
 * @since 2019-10-16
 */
@Mapper(componentModel = "spring")
public interface NodeWeightDtoMapping {

    @Mappings({@Mapping(target = "id", source = "id"),
            @Mapping(target = "nname", source = "nname"),
            @Mapping(target = "weight", source = "weight")})
    NodeWeightDto from(SignTree signTree);

    List<NodeWeightDto> from(List<SignTree> signTreeList);

}
