package com.digitalchina.event.mid.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.event.dto.CustomAssessmentDto;
import com.digitalchina.event.dto.HistogramDto;
import com.digitalchina.event.dto.PieChartDto;
import com.digitalchina.event.mid.entity.HiProcinst;
import com.digitalchina.event.mid.mapper.HiProcinstMapper;
import com.digitalchina.event.mid.service.HiProcinstService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-06
 */
@Service
public class HiProcinstServiceImpl extends ServiceImpl<HiProcinstMapper, HiProcinst> implements HiProcinstService {

    @Override
    public List<Map<String, Object>> trendAnalysis(String type) {
        return baseMapper.trendAnalysis(type);
    }

    @Override
    public BigDecimal avgProceTime() {
        BigDecimal avg = baseMapper.avgProceTime();
        if(null != avg && avg.compareTo(BigDecimal.ZERO) ==1){
            avg = avg.setScale(1,BigDecimal.ROUND_HALF_UP);
        }else{
            avg = BigDecimal.ZERO;
        }
        return avg;
    }

    @Override
    public List<PieChartDto> completionAnalysis() {
        return baseMapper.completionAnalysis();
    }

    @Override
    public List<HistogramDto> typeAnalysis(String type) {
        return baseMapper.typeAnalysis(type);
    }

    @Override
    public List<PieChartDto> sourceAnalysis() {
        return baseMapper.sourceAnalysis();
    }

    @Override
    public List<HistogramDto> deptAnalysisForFirstSource() {
        return baseMapper.deptAnalysisForFirstSource();
    }

    @Override
    public List<HistogramDto> topByType(String type, Integer rows, Integer bedid,Integer bdtype) {
        return baseMapper.topByType(type,rows,bedid,bdtype);
    }

    @Override
    public List<CustomAssessmentDto> customAssessmentByType(String type, String startTime, String endTime) {
        return baseMapper.customAssessmentByType(type, startTime, endTime);
    }

    @Override
    public IPage<CustomAssessmentDto> customAssessmentByType(IPage page, String type, String startTime, String endTime) {
        return page.setRecords(baseMapper.customAssessmentByType(page, type, startTime, endTime));
    }

    @Override
    public CustomAssessmentDto customAssessmentTotal() {
        return baseMapper.customAssessmentTotal();
    }

}
