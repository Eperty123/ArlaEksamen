package BLL;

import BE.Message;

import java.util.Comparator;

public class MessageSorter implements Comparator<Message> {

    @Override
    /**
     * Compares two messages, based on start time to decide which one is the most current message.
     */
    public int compare(Message o1, Message o2) {
        return o1.getMessageStartTime().compareTo(o2.getMessageStartTime());
    }
}
