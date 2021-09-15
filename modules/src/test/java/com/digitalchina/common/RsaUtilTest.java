package com.digitalchina.common;

import com.digitalchina.modules.utils.Base64Utils;
import com.digitalchina.modules.utils.RSAUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RsaUtilTest {

	private static List<String> TEST_LIST = new ArrayList<>();

	static {
		TEST_LIST.add("1qaz2wsx");
		TEST_LIST.add("123456");
		TEST_LIST.add("12343212");
		TEST_LIST.add("DSAWQEWQEW");
		TEST_LIST.add("！#@！￥！%￥%");
		TEST_LIST.add("zhong中文&&* 21@");
		TEST_LIST.add("@#FF565 rew23212@@#+-*/.");
	}

	@Test
	public void test1() throws Exception {
		for (String str : TEST_LIST) {
			// 加密
			byte[] rsabytes = RSAUtils.encryptByPublicKey(str.getBytes());
			// 解密
			rsabytes = RSAUtils.decryptByPrivateKey(rsabytes);
			String result = new String(rsabytes);
			Assert.assertTrue(str.equals(result));
		}
	}

	@Test
	public void test2() throws Exception {
		test("test1");
		test("123456");
		test("654321");
	}

	private void test(String s) throws Exception {
		//加密
		byte[] rsabytes = RSAUtils.encryptByPublicKey(s.getBytes());
		String base64 = Base64Utils.encode(rsabytes);
		System.out.println(base64);
		System.out.println("================================");
		//解密
		rsabytes = RSAUtils.decryptByPrivateKey(Base64Utils.decode(base64));
		System.out.println(new String(rsabytes));
		System.out.println("================================");
	}
}
