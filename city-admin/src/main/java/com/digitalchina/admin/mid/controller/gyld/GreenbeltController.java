package com.digitalchina.admin.mid.controller.gyld;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.dto.request.CommonRequest;
import com.digitalchina.admin.mid.entity.DataDict;
import com.digitalchina.admin.mid.service.DataDictService;
import com.digitalchina.admin.mid.service.gyld.GreenbeltService;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "公园绿地相关")
@RestController
@RequestMapping("/gyld")
public class GreenbeltController {

    @Autowired
    private GreenbeltService greenbeltService;
    @Autowired
    private DataDictService dataDictService;

    @ApiOperation("按行政区划和绿地组织的绿地树")
    @GetMapping("/tree")
    public RespMsg<Map<String, Object>> greenbeltTree() {
        List<DataDict> xzqhs = dataDictService.getDictNameList("zhjg_admin_division");
        return RespMsg.ok(greenbeltService.xzqhTree(xzqhs));
    }

    @ApiOperation("查询绿地下树木资源")
    @PostMapping("/ld/resources")
    public RespMsg<Page<Map<String, Object>>> listResourcesOfGreenbelt(@RequestBody CommonRequest request, Long id) {
        Page<Map<String, Object>> page = new Page<>();
        page.setSize(request.getSize());
        page.setCurrent(request.getCurrent());

        greenbeltService.listResourcesOfGreenbelt(page, id);

        return RespMsg.ok(page);
    }
}
