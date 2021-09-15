package com.digitalchina.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.digitalchina.modules.utils.PwdUtil;

public class PwdUtilTest {

	private static List<String> TEST_LIST = new ArrayList<>();

	static {
		TEST_LIST.add(null);
		TEST_LIST.add("");
		TEST_LIST.add("123456");
		TEST_LIST.add("12343212");
		TEST_LIST.add("DSAWQEWQEW");
		TEST_LIST.add("！#@！￥！%￥%");
		TEST_LIST.add("zhong中文&&* 21@");
		TEST_LIST.add("@#FF565 rew23212@@#");
	}

	@Test
	public void testMd5() {
		for (String str : TEST_LIST) {
			String md5Str = PwdUtil.generate(str);
			System.out.println(md5Str);
			Assert.assertTrue(PwdUtil.verify(str, md5Str));
			Assert.assertFalse(PwdUtil.verify(str, str));
			Assert.assertFalse(PwdUtil.verify(str, "d4997108256a48fb81940d31683213"));
		}
	}
}
