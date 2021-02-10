package day11;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import util.ConsignmentCalculator;

public class ConsignmentTest {
	public static void main(String[] args){
		LocalDate[] holidays = {LocalDate.of(2021, 01, 26),LocalDate.of(2021, 01, 22),LocalDate.of(2021, 8, 15), LocalDate.of(2021, 1, 1), LocalDate.of(2021,5, 1)};
		try {
		LocalDateTime deliveryDateTime = ConsignmentCalculator.Assistant.getAssistant()
				.setDetails(LocalDateTime.of(LocalDate.of(2021, 01, 21), LocalTime.of(23, 00, 00)), "900:00:00", "5:00:01", holidays)
				.includeSundays(false)
				.getDeliveryDetails();
		
		System.out.printf("  Delivery Details\n\nDate   :   %s\nDay    :   %s\nTime   :   %.9s",
				deliveryDateTime.toLocalDate(), 
				deliveryDateTime.toLocalDate().getDayOfWeek(), 
				deliveryDateTime.toLocalTime()
			);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
