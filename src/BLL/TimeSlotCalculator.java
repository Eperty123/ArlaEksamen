package BLL;

import BE.Message;

import java.time.LocalDateTime;

public class TimeSlotCalculator {


    /**
     * This method returns the amount of 30 minute time slots,
     * that a message's duration (or view time on the screen) is comprised of. This is used to book time
     * slots on a ScreenBit's timetable.
     * @param message message contains start and end times (LocalDateTime objects).
     * @return
     */
    public static int calculateTimeSlots(Message message){
        return calculate(message.getMessageStartTime(), message.getMessageEndTime());
    }

    /**
     * This method returns the amount of 30 minute time slots,
     * that a message's duration (or view time on the screen) is comprised of. This is used to book time
     * slots on a ScreenBit's timetable.
     * @param startTime
     * @param endTime
     * @return
     */
    public static int calculateTimeSlots(LocalDateTime startTime, LocalDateTime endTime){

        // Determine how many 30 minute time slots the LocalDateTime's represent.
        // 14:30 would represent 29 slots for instance.
        return calculate(startTime, endTime);
    }

    /**
     * Calculates the actual amount of 30 minute timeslots that the interval between start-
     * and end-time is comprised of.
     * @param startTime
     * @param endTime
     * @return
     */
    private static int calculate(LocalDateTime startTime, LocalDateTime endTime){
        int endb = (endTime.getHour() * 2) + (endTime.getMinute()== 0 ? 0 : 1);
        int startb = (startTime.getHour() * 2) + (startTime.getMinute()== 0 ? 0 : 1);

        // If the message's end time is on a different day than the start time, the time slots are adjusted accordingly.
        if(endTime.getDayOfMonth() > startTime.getDayOfMonth()){
            endb += 48 * (endTime.getDayOfMonth() - startTime.getDayOfMonth());
        }
        return endb - startb;
    }

}
