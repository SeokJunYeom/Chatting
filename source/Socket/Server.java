package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;

/**
 * Created by PC81 on 2017-11-24.
 */
public class Server extends SocketData implements Network {
    private String ip;
    private int port;

    public Server() {
        this("127.0.0.1", 8000);
    }

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean init() {

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();

            socketinputStream = new DataInputStream(socket.getInputStream());
            socketOutputStream = new DataOutputStream(socket.getOutputStream());

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }

    public String getMessage() {
        return output;
    }

    public void send(String input) {

        try {
            socketOutputStream.writeUTF(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receive() {

        try {
            output = socketinputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {

        try {
            socketinputStream.close();
            socketOutputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
