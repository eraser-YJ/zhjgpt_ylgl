package com.digitalchina.modules.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 极光推送dto fegin不支持List传输
 * </p>
 *
 * @author liuping
 * @since 2019-12-23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JpushDto implements Serializable {

    private static final long serialVersionUID = 5185220569468668567L;
    private List<String> aliass;

    private List<String> tags;

    private List<String> registrationIds;

    private String content;

    private String newsid;

    private String url;

    private String title;


}
