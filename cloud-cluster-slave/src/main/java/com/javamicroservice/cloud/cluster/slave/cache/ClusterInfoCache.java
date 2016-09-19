package com.javamicroservice.cloud.cluster.slave.cache;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ClusterInfoCache {
	private static final long startTimestamp;
	static {
		startTimestamp = Calendar.getInstance().getTimeInMillis();
	}

	public static long getStartTimestamp() {
		return startTimestamp;
	}

	public static String getUpTime() {
		long timeDiff = Calendar.getInstance().getTimeInMillis()
				- startTimestamp;
		int day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
		long hours = TimeUnit.MILLISECONDS.toHours(timeDiff) - (day * 24);
		long minute = TimeUnit.MILLISECONDS.toMinutes(timeDiff)
				- (TimeUnit.MILLISECONDS.toHours(timeDiff) * 60);
		long second = TimeUnit.MILLISECONDS.toSeconds(timeDiff)
				- (TimeUnit.MILLISECONDS.toMinutes(timeDiff) * 60);
		return day + " day(s) " + hours + " hour(s) " + minute + " minute(s) "
				+ second + " second(s)";
	}
}
