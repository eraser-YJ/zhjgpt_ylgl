package com.digitalchina.admin.mid.mapping;

import com.digitalchina.admin.mid.dto.RelatedItemDto;
import com.digitalchina.admin.mid.entity.SignQuota;
import com.digitalchina.admin.mid.entity.SignTree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * <p>
 *RelatedItemDto转换
 * </p>
 *
 * @author liuping
 * @since 2019-10-16
 */
@Mapper(componentModel = "spring")
public interface RelatedItemDtoMapping {

    @Mappings({@Mapping(target = "ncode", source = "ncode"),
            @Mapping(target = "nname", source = "nname")})
    RelatedItemDto fromSignQuota(SignQuota signQuota);

    List<RelatedItemDto> fromSignQuotaList(List<SignQuota> signQuotaList);


    @Mappings({@Mapping(target = "ncode", source = "ncode"),
            @Mapping(target = "nname", source = "nname")})
    RelatedItemDto fromSignTree(SignTree signTree);

    List<RelatedItemDto> fromSignTreeList(List<SignTree> signTreeList);
}
