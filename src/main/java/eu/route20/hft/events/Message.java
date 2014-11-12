package eu.route20.hft.events;

import lombok.*;

@ToString public class Message {
	@Getter private final int senderId;
	@Getter private final int receiverId;
	@Getter private final long nanoSend;
	@Getter private final long nanoRouted;
	@Getter private final String message;

	private Message(int sender, String msg) {
		senderId = sender;
		receiverId = 0;
		nanoSend = System.nanoTime();
		nanoRouted = 0;
		message = msg;
	}

	private Message(int sender, int receiver, String msg, long sendTime) {
		senderId = sender;
		receiverId = receiver;
		nanoSend = sendTime;
		nanoRouted = System.nanoTime();
		message = msg;
	}

	public static class Builder {
		private Message message;
		private int sender;
		private int receiver;
		private String msg;

		public Builder(final String message, final int senderId) {
			this.msg = message;
			this.sender = senderId;
		}

		public Builder(final Message message, final int receiver) {
			this.message = message;
			this.receiver = receiver;
		}
		
		public Message build() {
			if (this.message == null)
				return new Message(sender, msg);
			else
				return new Message(message.senderId, receiver, message.message, message.nanoSend);
		}
	}
}
