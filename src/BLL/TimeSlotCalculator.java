package BLL;

import BE.Message;

import java.time.LocalDateTime;

public class TimeSlotCalculator {


    public static int calculateTimeSlots(Message message){
        return TimeSlotCalculator.calculate(message.getMessageStartTime(), message.getMessageEndTime());
    }

    public static int calculateTimeSlots(LocalDateTime startTime, LocalDateTime endTime){

        // Determine how many 30 minute time slots the LocalDateTime's represent.
        // 14:30 would represent 29 slots for instance.
        return TimeSlotCalculator.calculate(startTime, endTime);
    }

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
