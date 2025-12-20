package dev.vicky.spring_ai.tools.datetime;

import java.time.LocalDateTime;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateTimeTools {

	@Tool(description = "Provides the current date, time, and timezone information, which can be used to calculate future or past dates.")
	public String getCurrentDateTime() {
		return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
	}

}
