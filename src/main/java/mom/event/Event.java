package mom.event;

import lombok.*;

@ToString
public class Event {
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

    public Event(int sender, String msg) {
        senderId = sender;
        receiverId = 0;
        nanoCreated = System.nanoTime();
        nanoReceived = 0;
        content = msg;
    }

    private Event(int sender, int receiver, String msg, long sendTime) {
        senderId = sender;
        receiverId = receiver;
        nanoCreated = sendTime;
        nanoReceived = System.nanoTime();
        content = msg;
    }

    public static class Builder {
        private Event message;
        private int sender;
        private int receiver;
        private String msg;

        public Builder(final String message, final int senderId) {
            this.msg = message;
            this.sender = senderId;
        }

        public Builder(final Event message, final int receiver) {
            this.message = message;
            this.receiver = receiver;
        }

        public Event build() {
            if (this.message == null)
                return new Event(sender, msg);
            else
                return new Event(message.senderId, receiver, message.content, message.nanoCreated);
        }
    }
}
