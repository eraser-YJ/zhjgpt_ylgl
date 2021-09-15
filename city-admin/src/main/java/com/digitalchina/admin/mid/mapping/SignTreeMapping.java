package com.digitalchina.admin.mid.mapping;

import com.digitalchina.admin.mid.entity.SignQuota;
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
public interface SignTreeMapping {

    @Mappings({@Mapping(target = "ncode", source = "ncode"), @Mapping(target = "nname", source = "nname"),
            @Mapping(target = "nmeno", source = "nmeno"), @Mapping(target = "isarea", source = "isarea"),
            @Mapping(target = "iskey", source = "iskey"), @Mapping(target = "qid", source = "qid"),
            @Mapping(target = "qparm", source = "qparm"), @Mapping(target = "gid", source = "gid"),
            @Mapping(target = "gparm", source = "gparm"), @Mapping(target = "crdt", source = "crdt"),
            @Mapping(target = "modt", source = "modt")})
    SignTree from(SignQuota signQuota);

    List<SignTree> from(List<SignQuota> signQuotaList);

}
