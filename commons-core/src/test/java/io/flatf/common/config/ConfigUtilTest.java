package io.flatf.common.config;

import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

public class ConfigUtilTest {

	@Test
	public void test() {
		
		URL resource = ConfigUtilTest.class.getClassLoader().getResource("test.properties");

        Assert.assertNotNull(resource);
        System.out.println(resource.getFile());

	}

}
