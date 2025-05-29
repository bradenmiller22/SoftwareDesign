// Fig. 8.5: Time2.java
// Time2 class declaration with overloaded constructors.  


import java.sql.Time;

/**
* This class maintains the time in 24-hour format.
* @see Object
* @author Deitel & Associates, Inc.
*/
public class Time2 {
   private int hour; // 0 - 23
   private int minute; // 0 - 59
   private int second; // 0 - 59

	/**
	* Time2 no-argument constructor initializes each instance variable
	* to zero. This ensures that Time objects start in a consistent state
	* @throws IllegalArgumentException In the case of an invalid time
	*/
   public Time2() {                                             
      this(0, 0, 0); // invoke constructor with three arguments
   } 

	/**
	* Time2 constructor: hour supplied, minute and second defaulted to 0
	* @param hour the hour
	* @throws Exception In the case of an invalid time
	*/
   public Time2(int hour) {                                               
      this(hour, 0, 0); // invoke constructor with three arguments 
   } 

	/**
	* Time2 constructor: hour and minute supplied, second defaulted to 0
	* @param hour the hour
	* @param minute the minute
	* @throws IllegalArgumentException In the case of an invalid time
	*/
   public Time2(int hour, int minute) {
      this(hour, minute, 0); // invoke constructor with three arguments 
   } 

   /**
	* Time2 constructor
	* @param hour the hour
	* @param minute the minute
	* @param second the second
	* @throws IllegalArgumentException In the case of an invalid time
	*/
   public Time2(int hour, int minute, int second) {                    
      if (hour < 0 || hour >= 24) {
         throw new IllegalArgumentException("hour must be 0-23");
      } 

      if (minute < 0 || minute >= 60) {
         throw new IllegalArgumentException("minute must be 0-59");
      } 

      if (second < 0 || second >= 60) {
         throw new IllegalArgumentException("second must be 0-59");
      } 

      this.hour = hour;
      this.minute = minute; 
      this.second = second; 
   } 

    /**
	* Time constructor
	* @param time A Time object with which to initialize
	* @throws IllegalArgumentException In the case of an invalid time
	*/    
   public Time2(Time2 time) {                                   
      // invoke constructor with three arguments
      this(time.hour, time.minute, time.second);
   } 

	/**
	* Set a new time value using universal time. Perform
	* validity checks on the data. Set invalid values to zero.
	* @param hour the hour
	* @param minute the minute
	* @param second the second
	* @see com.deitel
	* @see Time
	* @see #setSecond
	* @throws Exception In the case of an invalid time
	*/
   public void setTime(int hour, int minute, int second) {
      if (hour < 0 || hour >= 24) {
         throw new IllegalArgumentException("hour must be 0-23");
      } 

      if (minute < 0 || minute >= 60) {
         throw new IllegalArgumentException("minute must be 0-59");
      } 

      if (second < 0 || second >= 60) {
         throw new IllegalArgumentException("second must be 0-59");
      } 

      this.hour = hour;
      this.minute = minute; 
      this.second = second; 
   } 

	/**
	* Sets the hour.
	* @param hour the hour
	* @throws IllegalArgumentException In the case of an invalid hour
	*/   
	public void setHour(int hour) {
      if (hour < 0 || hour >= 24) {
         throw new IllegalArgumentException("hour must be 0-23");
      }

      this.hour = hour;
   } 

	/**
	* Sets the minute.
	* @param minute the minute
	* @throws IllegalArgumentException In the case of an invalid minute
	*/
   public void setMinute(int minute) {
      if (minute < 0 || minute >= 60) {
         throw new IllegalArgumentException("minute must be 0-59");
      }

      this.minute = minute; 
   } 

    /**
	* Sets the second.
	* @param second the second.
	* @throws Exception In the case of an invalid second
	*/
   public void setSecond(int second) {
      if (second < 0 || second >= 60) {
         throw new IllegalArgumentException("second must be 0-59");
      }

      this.second = second;
   } 

   // Get Methods         
	/**
	* Gets the hour.
	* @return an <code>integer</code> specifying the hour.
	*/
   public int getHour() {return hour;}

   /**
	* Gets the minute.
	* @return an <code>integer</code> specifying the minute.
	*/   
   public int getMinute() {return minute;} 

   /**
	* Gets the second.
	* @return an <code>integer</code> specifying the second.
	*/     
   public int getSecond() {return second;}   

   /**
	* Convert to String in universal-time format (HH:MM:SS)
	* @return a <code>String</code> representation
	* of the time in universal-time format
	*/
   public String toUniversalString() {
      return String.format(
         "%02d:%02d:%02d", getHour(), getMinute(), getSecond());
   } 

   /**
	* Convert to String in standard-time format (H:MM:SS AM or PM)
	* @return a <code>String</code> representation
	* of the time in standard-time format
	*/
   public String toString() {
      return String.format("%d:%02d:%02d %s", 
         ((getHour() == 0 || getHour() == 12) ? 12 : getHour() % 12),
         getMinute(), getSecond(), (getHour() < 12 ? "AM" : "PM"));
   } 
} //end of class


/**************************************************************************
 * (C) Copyright 1992-2018 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
