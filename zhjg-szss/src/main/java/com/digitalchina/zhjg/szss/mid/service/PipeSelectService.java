package com.digitalchina.zhjg.szss.mid.service;

import java.util.List;
import java.util.Map;

public interface PipeSelectService {
    /**
     * 查询地下管网的code
     * @return
     */
    List<Map<String,String>> pipeSelect();
}
