package mom.net;

import org.zeromq.ZMQ;

public class Socket {
    private final ZMQ.Socket socket;

    Socket(ZMQ.Socket socket) {
        this.socket = socket;
    }

    public void bind(String address) {
        socket.bind(address);
        setSendTimeOut();
    }

    public void send(String notification) {
        socket.send(notification);
    }

    public void close() {
        socket.close();
    }

    public byte[] recv() {
        return socket.recv();
    }

    public void connect(String address) {
        socket.connect(address);
        setReceiveTimeOut();
    }

    public String recvStr() {
        return socket.recvStr();
    }

    public void subscribe(byte[] bytes) {
        socket.subscribe(bytes);
    }

    public void setReceiveTimeOut() {
        socket.setReceiveTimeOut(1000);
    }

    public void setSendTimeOut() {
        socket.setSendTimeOut(1000);
    }
}
