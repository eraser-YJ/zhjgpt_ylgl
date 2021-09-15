package com.digitalchina.zhjg.szss.mid.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.mid.entity.ZhjgNotice;
import com.digitalchina.zhjg.szss.mid.mapper.ZhjgNoticeMapper;
import com.digitalchina.zhjg.szss.mid.service.ZhjgNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-08-07 10:21
 */
@Service
public class ZhjgNoticeServiceImpl  extends ServiceImpl<ZhjgNoticeMapper, ZhjgNotice> implements ZhjgNoticeService {

    @Autowired
    private ZhjgNoticeMapper zhjgNoticeMapper;

    @Override
    public List<ZhjgNotice> selectZhjgNotice(Integer id) {
        return zhjgNoticeMapper.selectZhjgNotice(id);
    }

    @Override
    public List<ZhjgNotice> selectZhjgAllNotice(Page<ZhjgNotice> page) {
        return zhjgNoticeMapper.selectZhjgAllNotice(page);
    }

    @Override
    public Integer insetNotice(ZhjgNotice zhjgNotice) {
        return zhjgNoticeMapper.insetNotice(zhjgNotice);
    }

    @Override
    public Integer deleteNotice(Integer id) {
        return zhjgNoticeMapper.deleteNotice(id);
    }

    @Override
    public Integer updateNotice(ZhjgNotice zhjgNotice) {
        return zhjgNoticeMapper.updateNotice(zhjgNotice);
    }

}
