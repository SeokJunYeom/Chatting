package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by PC81 on 2017-11-24.
 */
public class Client extends SocketData implements Network {
    private String ip;
    private int port;

    public Client() {
        this("127.0.0.1", 8000);
    }

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean init() {

        try {
            socket = new Socket(ip, port);

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

    public void enterMessage(String data) {
        input = data;
    }

    public String getMessage() {

        try {
            return output;
        } finally {
            output = "";
        }
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