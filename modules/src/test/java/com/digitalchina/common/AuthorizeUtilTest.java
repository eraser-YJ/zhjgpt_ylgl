package com.digitalchina.common;

import java.text.DecimalFormat;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.utils.AuthorizeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * AuthorizeUtil 工具类单元测试
 * 
 * @author Warrior 2019年8月4日
 */
public class AuthorizeUtilTest {

	@RestController
	@RequestMapping("test")
	@Api(tags = "测试接口")
	@Authorize("admin")
	public class TestController {

		@ApiOperation(value = "测试A+B", tags = "test")
		@GetMapping("add")
		@ApiImplicitParams({ @ApiImplicitParam(name = "a", value = "加数a", dataType = "Integer", required = true),
				@ApiImplicitParam(name = "b", value = "加数b", dataType = "Integer", required = true) })
		@Authorize
		public RespMsg<Integer> add(Integer a, Integer b) {
			return RespMsg.ok(a + b);
		}

		@ApiOperation(value = "测试A+B", tags = "test")
		@PostMapping("add1")
		@ApiImplicitParams({ @ApiImplicitParam(name = "a", value = "加数a", dataType = "Integer", required = true),
				@ApiImplicitParam(name = "b", value = "加数b", dataType = "Integer", required = true) })
		@Authorize("admin,normal")
		public RespMsg<Integer> add1(Integer a, Integer b) {
			return RespMsg.ok(a + b);
		}

		@ApiOperation(value = "测试A+B", tags = "test")
		@RequestMapping("add2")
		@ApiImplicitParams({ @ApiImplicitParam(name = "a", value = "加数a", dataType = "Integer", required = true),
				@ApiImplicitParam(name = "b", value = "加数b", dataType = "Integer", required = true) })
		public RespMsg<Integer> add2(Integer a, Integer b) {
			return RespMsg.ok(a + b);
		}
	}

	@Test
	public void findAuthTest() {
		TestController test = new TestController();
		Map<String, String> auths = AuthorizeUtil.findAuth(test);
		Assert.assertEquals("", auths.get("test/add"));
		Assert.assertEquals("admin,normal", auths.get("test/add1"));
		Assert.assertEquals("admin", auths.get("test/add2"));
		Assert.assertEquals(3, auths.size());
	}

	public static void main(String[] args) {
		Double[] data = new Double[] { 105.54, 97.42, 25.13, 31.46, 53.38, 51.63, 25.13, 44.63, 70.17, 39.29, 43.13,
				31.54, 63.75, 45.79, 54.21, 35.38, 56.46, 65.00, 86.13, 37.42, 57.17, 52.67, 25.54, 35.17, 40.75, 54.87,
				58.92, 38.17, 51.63, 47.67 };
		
		for (int i = 1; i < data.length; i++) {
			Double d = (data[i] - data[i - 1]) / data[i - 1] * 100;
			System.out.println("{\"val\": " + String.format("%.2f", d) + "}");
		}
	}
}
