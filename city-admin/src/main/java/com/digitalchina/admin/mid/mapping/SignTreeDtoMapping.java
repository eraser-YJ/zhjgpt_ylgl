package com.digitalchina.admin.mid.mapping;

import com.digitalchina.admin.mid.dto.SignTreeDto;
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
public interface SignTreeDtoMapping {

    @Mappings({@Mapping(target = "id", source = "id"), @Mapping(target = "tid", source = "tid"),
            @Mapping(target = "tfid", source = "tfid"), @Mapping(target = "ncode", source = "ncode"),
            @Mapping(target = "nname", source = "nname"), @Mapping(target = "nmeno", source = "nmeno"),
            @Mapping(target = "gid", source = "gid"), @Mapping(target = "gparm", source = "gparm"),
            @Mapping(target = "crdt", source = "crdt"), @Mapping(target = "modt", source = "modt")}
            )
    SignTreeDto from(SignTree signTree);

    List<SignTreeDto> from(List<SignTree> signTreeList);


    @Mappings({@Mapping(target = "id", source = "id"), @Mapping(target = "tid", source = "tid"),
            @Mapping(target = "tfid", source = "tfid"), @Mapping(target = "ncode", source = "ncode"),
            @Mapping(target = "nname", source = "nname"), @Mapping(target = "nmeno", source = "nmeno"),
            @Mapping(target = "gid", source = "gid"), @Mapping(target = "gparm", source = "gparm")})
    SignTree to(SignTreeDto signTreeDto);

    List<SignTree> to(List<SignTreeDto> signTreeDtoList);

}
