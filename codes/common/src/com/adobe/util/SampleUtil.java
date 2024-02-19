package com.adobe.util;

import java.time.LocalDateTime;

public class SampleUtil {
	public static int getDayOfMonth() {
		return LocalDateTime.now().getDayOfMonth();
	}
}
