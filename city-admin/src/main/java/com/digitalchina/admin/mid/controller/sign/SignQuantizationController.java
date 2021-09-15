package com.digitalchina.admin.mid.controller.sign;

import com.digitalchina.admin.mid.entity.SignQuantization;
import com.digitalchina.admin.mid.service.SignQuantizationService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  量化配置 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@RestController
@RequestMapping("admin/signQuantization")
//@Authorize
@Api(tags ="体征量化配置管理接口")
public class SignQuantizationController {
    @Autowired
    private SignQuantizationService signQuantizationService;

    @GetMapping("queryAll")
    @ApiOperation(value = "查询所有体征量化配置列表，用于字典")
    public RespMsg<List<SignQuantization>> queryAll() {
        return RespMsg.ok(signQuantizationService.list(null));
    }
}
