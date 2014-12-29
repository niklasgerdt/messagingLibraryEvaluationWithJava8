package moma.net;

import org.zeromq.ZMQ;

public class Socket {
    private final ZMQ.Socket socket;

    Socket(ZMQ.Socket socket) {
        this.socket = socket;
    }

    public void bind(String address) {
        socket.bind(address);
    }

    public void send(String notification) {
        socket.send(notification);
    }

    public void close() {
        socket.close();
    }
}
