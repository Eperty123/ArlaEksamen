package BLL;

import BE.Message;

import java.util.Comparator;

public class MessageSorter implements Comparator<Message> {

    @Override
    public int compare(Message o1, Message o2) {

        return o1.getMessageStartTime().compareTo(o2.getMessageStartTime());
    }
}
