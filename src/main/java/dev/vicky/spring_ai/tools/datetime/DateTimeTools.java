package dev.vicky.spring_ai.tools.datetime;

import java.time.LocalDateTime;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Collection of tools for date and time operations that can be called by the
 * LLM.
 */
public class DateTimeTools {

	/**
	 * Retrieves the current date and time in the user's timezone.
	 *
	 * @return A string representation of the current date-time and zone.
	 */
	@Tool(description = "Provides the current date, time, and timezone information, which can be used to calculate future or past dates.")
	public String getCurrentDateTime() {
		return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
	}

}
