package org.springframework.samples.petclinic.service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateFormater {

	public static LocalDate getLocalDate() {
		LocalDate currentDate = LocalDate.now(); // Get system date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(formatter);
		return LocalDate.parse(formattedDate, formatter);
	}
}
