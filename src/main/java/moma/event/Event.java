package moma.event;

import lombok.*;

@ToString
public class Event {
    @Getter
    private final int id;
    @Getter
    private final int senderId;
    @Getter
    private final int receiverId;
    @Getter
    private final long nanoCreated;
    @Getter
    private final long nanoReceived;
    @Getter
    private final String content;

    public Event(int sender, int id, String msg) {
        senderId = sender;
        this.id = id;
        receiverId = 0;
        nanoCreated = System.nanoTime();
        nanoReceived = 0;
        content = msg;
    }
}
