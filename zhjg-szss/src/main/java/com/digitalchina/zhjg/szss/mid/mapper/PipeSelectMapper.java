package com.digitalchina.zhjg.szss.mid.mapper;

import java.util.List;
import java.util.Map;

public interface PipeSelectMapper {

    /**
     * 查询地下管网的code
     * @return
     */
    List<Map<String,String>> pipeSelect();
}
