package com.digitalchina.admin.gis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.gis.entity.Config;
import com.digitalchina.admin.gis.mapper.ConfigMapper;
import com.digitalchina.admin.gis.service.ExportDataService;
import com.digitalchina.admin.mid.dto.request.CommonRequest;
import com.digitalchina.admin.mid.dto.request.RequestCondition;
import lombok.extern.slf4j.Slf4j;
import oracle.sql.TIMESTAMP;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Service
@Slf4j
public class ExportDataServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ExportDataService {
    @Override
    public List<Map<String, Object>> customAll(CommonRequest request, String module) {
        // 查询时这个要去掉
        request.getProps().removeIf(prop -> "CATE_ID".equalsIgnoreCase(prop.getField()));

        RequestCondition cateIdCondition = request.getFiltrate().get("CATE_ID");
        request.getFiltrate().remove("CATE_ID");

        String cols = request.buildCols();
        String conds = request.buildConditions();

        String sql;
        switch (module) {
            case "parts":
                sql = String.format("SELECT %s FROM parts_base base join %s parts on base.parts_cate_code = '%s' and parts.objectid = base.refer_id %s",
                        cols, request.getCode(), request.getCode(), conds);
                break;
            case "parts_search": // 市政设施综合查询
                sql = String.format("select %s from parts_base base left join parts_warn_info warn on base.baseid = warn.refer_baseid %s ", cols, conds);
                break;
            case "gyld_ld":

                if (cateIdCondition != null && cateIdCondition.getValue() != null) {
                    sql = "select %s from %s %s and (cate_level_1_id=%s or cate_level_2_id=%s or cate_level_3_id=%s)";
                    sql = String.format(sql, cols, request.getCode(), conds, cateIdCondition.getValue()[0], cateIdCondition.getValue()[0], cateIdCondition.getValue()[0]);
                } else {
                    sql = String.format("SELECT %s FROM %s %s", cols, request.getCode(), conds);
                }
                break;
            default:
                sql = String.format("SELECT %s FROM %s %s", cols, request.getCode(), conds);
        }

        log.debug("==== " + module + " ==> " + sql);

        List<Map<String, Object>> data = baseMapper.customListAll(sql);
        transTIMESTAMPToDate(data);
        return data;
    }

    private void transTIMESTAMPToDate(List<Map<String, Object>> data) {
        if (data != null) {
            data.forEach(d -> {
                d.entrySet().forEach(e -> {
                    if (e.getValue() != null && e.getValue() instanceof TIMESTAMP) {
                        try {
                            e.setValue(((TIMESTAMP) e.getValue()).dateValue());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
            });
        }
    }
}
