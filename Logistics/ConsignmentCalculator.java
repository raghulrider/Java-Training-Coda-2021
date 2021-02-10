package util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class ConsignmentCalculator {

  private LocalDateTime startDateTime;
  private String totTrvlTime;
  private String trvlTimePerDay;
  private boolean includeSundays;
  private LocalDate[] holidays;
  private boolean ignoreHoliday;

  private ConsignmentCalculator(Assistant assistant) {
	  
    this.startDateTime = assistant.startDateTime;
    this.totTrvlTime = assistant.totTrvlTime;
    this.trvlTimePerDay = assistant.trvlTimePerDay;
    this.holidays = assistant.holidays;
    this.includeSundays = assistant.includeSundays;
    this.ignoreHoliday = assistant.ignoreHoliday;
  }

  private final LocalDateTime getDeliveryTimeAndDate() throws Exception {

    long totTrvlHrsInSeconds = durationToSeconds(totTrvlTime);
    //System.out.println(totTrvlHrsInSeconds);
    long trvlHrsPerDayInSeconds = durationToSeconds(trvlTimePerDay); //Total travel hours per day
    
    //86400 seconds is 24 hours
    if (trvlHrsPerDayInSeconds > 86400) {
      throw new InvalidTravelTimeException("You entered : " + trvlTimePerDay);
    }
    
    final LocalTime day = LocalTime.of(23, 59, 59);
    final long availableSeconds = Duration.between(startDateTime.toLocalTime(), day).toSeconds(); //Seconds Ramaining at the start of day

    if (totTrvlHrsInSeconds < availableSeconds) {
      if (trvlHrsPerDayInSeconds > totTrvlHrsInSeconds) {
       
    	//System.out.println("Deliverable within a day");
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime t2 = calculateDateTime(totTrvlHrsInSeconds);
        LocalTime total = startTime.plusHours(t2.getHour()).plusMinutes(t2.getMinute());
        LocalDateTime deliveryDate = LocalDateTime.of(startDateTime.toLocalDate(), total);
//        System.out.printf("  Delivery Details\nDate   :   %s\nDay    :   %s\nTime   :   %.9s\n\n",
//        		deliveryDate.toLocalDate(), 
//        		deliveryDate.toLocalDate().getDayOfWeek(), 
//        		deliveryDate.toLocalTime()
//			);
        return deliveryDate;
      
      }
      else {
    	  
        //System.out.println("One day delivery possible but per day travel is limited");
        totTrvlHrsInSeconds -= trvlHrsPerDayInSeconds;
        
        
        return travelDays(totTrvlHrsInSeconds, trvlHrsPerDayInSeconds);
        
      }
    } else {
    	
      //System.out.println("one day delivery not possible");
      
      if (trvlHrsPerDayInSeconds > availableSeconds) {
    	  
        //System.out.println("i drive for available time");
        totTrvlHrsInSeconds -= availableSeconds;
        
      } else {
    	  
        //System.out.println("i can only drive this much");
        totTrvlHrsInSeconds -= trvlHrsPerDayInSeconds;
        
      }
      
      //System.out.println(totTrvlHrsInSeconds + " " + trvlHrsPerDayInSeconds);
      return travelDays(totTrvlHrsInSeconds, trvlHrsPerDayInSeconds);

    }
  }

  private LocalDateTime travelDays(long totTrvlHrsInSeconds, long trvlHrsPerDayInSeconds) {

    startDateTime = startDateTime.plus(1, ChronoUnit.DAYS);
    while (totTrvlHrsInSeconds >= trvlHrsPerDayInSeconds) {

      if (startDateTime.toLocalDate().getDayOfWeek() != DayOfWeek.SUNDAY || includeSundays) { //skipping sundays
        
    	  boolean isHoliday = false;
    	  for (LocalDate holiday: holidays) { //checking if current day falls under holiday
    		  if (holiday.equals(startDateTime.toLocalDate())) {
    			  isHoliday = true;
    		  }
    	  }
    	  if (!isHoliday || ignoreHoliday) { //if not holiday
    		  totTrvlHrsInSeconds -= trvlHrsPerDayInSeconds;
    	  }
      	}
      	//System.out.println(totTrvlHrsInSeconds);
      	startDateTime = startDateTime.plus(1, ChronoUnit.DAYS);
    }

    while (true) {

      boolean isHoliday = false;
      for (LocalDate holiday: holidays) {
        //System.out.println(holiday + " " + startDateTime.toLocalDate());
        if (holiday.equals(startDateTime.toLocalDate())) {
          isHoliday = true;
        }
      }
      if (isHoliday && !ignoreHoliday) {
        startDateTime = startDateTime.plus(1, ChronoUnit.DAYS);
      } else {
        if (startDateTime.toLocalDate().getDayOfWeek() != DayOfWeek.SUNDAY || includeSundays) {
          break;
        } else {
          startDateTime = startDateTime.plus(1, ChronoUnit.DAYS);
        }
      }
    }

    LocalDateTime deliveryDate = LocalDateTime.of(startDateTime.toLocalDate(), calculateDateTime(totTrvlHrsInSeconds));
 
//    System.out.printf("  Delivery Details\nDate   :   %s\nDay    :   %s\nTime   :   %.9s\n\n",
//    		deliveryDate.toLocalDate(), 
//    		deliveryDate.toLocalDate().getDayOfWeek(), 
//    		deliveryDate.toLocalTime()
//		);
    return deliveryDate;
  }

  private final LocalTime calculateDateTime(long seconds) {
    long hours = TimeUnit.SECONDS.toHours(seconds);
    seconds -= (hours * 60 * 60);
    long minute = TimeUnit.SECONDS.toMinutes(seconds);
    seconds -= (minute * 60);
    long second = TimeUnit.SECONDS.toSeconds(seconds);
    return LocalTime.of((int) hours, (int) minute, (int) second);
  }

  private final long durationToSeconds(String duration) {
    StringTokenizer st = new StringTokenizer(duration, ":");
    long seconds = 0;
    while (st.hasMoreTokens()) { //Total travel hours in seconds
      seconds += TimeUnit.HOURS.toSeconds(Long.parseLong(st.nextToken()));
      seconds += TimeUnit.MINUTES.toSeconds(Long.parseLong(st.nextToken()));
      seconds += Long.parseLong(st.nextToken());
    }
    return seconds;
  }

  public static class Assistant {

    private LocalDateTime startDateTime;
    private String totTrvlTime;
    private String trvlTimePerDay;
    private boolean includeSundays;
    private LocalDate[] holidays;
    private boolean ignoreHoliday;

    private Assistant() {}

    public static final Assistant getAssistant() {
      return new Assistant();
    }

    public final Assistant setDetails(LocalDateTime startDateTime, String totTrvlTime, String trvlTimePerDay, LocalDate[] holidays) {
      this.startDateTime = startDateTime;
      this.totTrvlTime = totTrvlTime;
      this.trvlTimePerDay = trvlTimePerDay;
      this.holidays = holidays;
      return this;
    }

    public final Assistant includeSundays(boolean includeSundays) {
      this.includeSundays = includeSundays;
      return this;
    }
    
    public final Assistant ignoreHolidays(boolean ignoreHoliday) {
    	this.ignoreHoliday = ignoreHoliday;
    	return this;
    }

    public final LocalDateTime getDeliveryDetails() throws Exception {
      return new ConsignmentCalculator(this).getDeliveryTimeAndDate();
    }
  }
}

class InvalidTravelTimeException extends Exception {

  private String errMsg = "Travel time per day cannot be greater than 24 hrs (00:00:00-23:59:59) ";
  public InvalidTravelTimeException(String errMsg) {
    this.errMsg += errMsg;
  }@Override
  public String toString() {
    return super.toString() + " " + this.errMsg;
  }
}