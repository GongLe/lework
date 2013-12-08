package org.lework.runner.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IPv4Test {

	@Test
	public void IPv4() {
		assertEquals( true,  IPv4Utils.isIPv4Valid("192.168.1.1"));
		assertEquals( true,  IPv4Utils.isIPv4Private("192.168.1.1"));
		assertEquals( false,  IPv4Utils.isIPv4Private("0.0.0.0"));
		assertEquals( false,  IPv4Utils.isIPv4Private("8.8.8.8"));
	}
}
