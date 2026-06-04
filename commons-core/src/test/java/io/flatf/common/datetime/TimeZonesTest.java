package io.flatf.common.datetime;

import io.flatf.common.constant.TimeZoneConst;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimeZonesTest {

	@Test
	public void test() {

		ZoneOffset standardOffset = ZoneId.systemDefault().getRules().getStandardOffset(Instant.EPOCH);

		System.out.println(standardOffset);

		System.out.println(TimeZoneConst.SYS_DEFAULT);
		System.out.println(TimeZoneConst.CST);
		System.out.println(TimeZoneConst.JST);

		ZoneOffset ofHours = ZoneOffset.ofHours(8);

		System.out.println(TimeZoneConst.SYS_DEFAULT.equals(ofHours));

		System.out.println(TimeZoneConst.SYS_DEFAULT.equals(standardOffset));

		ZoneId.getAvailableZoneIds().forEach(System.out::println);

	}

}
