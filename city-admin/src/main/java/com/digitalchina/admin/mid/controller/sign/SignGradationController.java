package com.digitalchina.admin.mid.controller.sign;

import com.digitalchina.admin.mid.entity.SignGradation;
import com.digitalchina.admin.mid.service.SignGradationService;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 分级配置 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@RestController
@RequestMapping("admin/signGradation")
//@Authorize
@Api(tags ="体征分级配置管理接口")
public class SignGradationController {
    @Autowired
    private SignGradationService signGradationService;

    @GetMapping("queryAll")
    @ApiOperation(value = "查询所有体征分级配置列表，用于字典")
    public RespMsg<List<SignGradation>> queryAll() {
        return RespMsg.ok(signGradationService.list(null));
    }
}
