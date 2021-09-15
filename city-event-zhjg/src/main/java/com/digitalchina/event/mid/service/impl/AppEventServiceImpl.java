package com.digitalchina.event.mid.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.mid.entity.Befrom;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.service.AppEventService;
import com.digitalchina.event.mid.service.BefromService;
import com.digitalchina.event.mid.service.BusieventService;
import com.digitalchina.event.utils.MapUtil;
import com.digitalchina.modules.constant.enums.EventEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class AppEventServiceImpl implements AppEventService {

    @Autowired
    private BusieventService eventService;
    @Autowired
    private BefromService befromService;

    @Override
    public RespMsg<Map<String, Object>> eventsOfToday() {
        String nowDate = DateUtil.formatDate(new Date());
        DateTime start = DateUtil.parse(nowDate + " 00:00:00");
        DateTime end = DateUtil.parse(nowDate + " 23:59:59");
        //当日事件
        List<Busievent> busieventList = eventService.list(Condition.<Busievent>create().
                between(Busievent.CRDT, start, end)
                .select(Busievent.BEID, Busievent.BESTAT, Busievent.EFBH));
        //总事件数
        int total = busieventList.size();
        //已完成事件数
        int finish = getFinishEventCount(busieventList);
        //未完成事件数
        int unfinish = total - finish;
        //事件类型前三
        List<Map<String, Object>> eventSource = getEventSource(busieventList);
        //返回前端结果
        Map<String, Object> resultMap = MapUtil.getHashMap(
                "total", MapUtil.getHashMap("value", total, "unit", "件"),
                "finish", MapUtil.getHashMap("title", "办结", "value", finish, "unit", "件"),
                "unfinish", MapUtil.getHashMap("title", "未办结", "value",unfinish, "unit", "件"),
                "source", eventSource
        );
        return RespMsg.ok(resultMap);
    }

    private int getFinishEventCount(List<Busievent> busieventList) {
        int finish = 0;
        for (Busievent event : busieventList) {
            if (event.getBestat().toString().equals(EventEnum.FINISH_STAT.getValue().toString())) {
                finish++;
            }
        }
        return finish;
    }

    private List<Map<String, Object>> getEventSource(List<Busievent> busieventList) {
        if (busieventList == null || busieventList.size() == 0) {
            return null;
        }
        //获取事件来源，及对应值
        Map<Integer, Integer> countMap = getCountMap(busieventList);
        //按照事件来源，从大到小进行排序：
        ArrayList<Map.Entry<Integer, Integer>> entries = getSortMap(countMap);
        //前三大的事件组合成list
        List<Map<String, Object>> sourceList = getSourceList(entries);
        return sourceList;
    }

    private List<Map<String,Object>> getSourceList(ArrayList<Map.Entry<Integer,Integer>> entries) {
        List<Map<String, Object>> sourceList = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            sourceList.add(MapUtil.getHashMap(
                    "title", getEfbhName(entries.get(i).getKey()),
                    "value", entries.get(i).getValue(),
                    "unit", "件",
                    "rate", getRate(entries.get(i).getKey(), entries.get(i).getValue()),
                    "rank", (i+1)
            ));
            if(i == 2) {
                break;
            }
        }
        return sourceList;
    }

    private Object getRate(Integer efbh, Integer value) {
        String lastDate = DateUtil.formatDate(DateUtil.yesterday());
        DateTime start = DateUtil.parse(lastDate + " 00:00:00");
        DateTime end = DateUtil.parse(lastDate + " 23:59:59");
        //昨日此来源事件数
        int lastCount = eventService.count(Condition.<Busievent>create()
                .between(Busievent.CRDT, start, end)
                .eq(Busievent.EFBH, efbh));
        //计算环比
        if(lastCount == 0) {
            //如果昨天没有，今天有，则环比为100%
            return "100";
        } else {
            float num = (float)(value - lastCount) / lastCount;
            num = num * 100;
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            String rate = df.format(num);
            return rate;
        }
    }

    private String getEfbhName(Integer efbh) {
        Befrom befrom = befromService.getOne(Condition.<Befrom>create().eq(Befrom.EFBH, efbh).select(Befrom.EFNM));
        String beformName = (befrom == null) ? "" : befrom.getEfnm();
        return beformName;
    }

    private Map<Integer, Integer> getCountMap(List<Busievent> busieventList) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (Busievent busievent : busieventList) {
            countMap.put(busievent.getEfbh(), countMap.containsKey(busievent.getEfbh()) ? (countMap.get(busievent.getEfbh()) + 1) : 1);
        }
        return countMap;
    }

    private ArrayList<Map.Entry<Integer, Integer>> getSortMap(Map map) {
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> obj1, Map.Entry<Integer, Integer> obj2) {
                return obj2.getValue() - obj1.getValue();
            }
        });
        return (ArrayList<Map.Entry<Integer, Integer>>) entries;
    }
}
