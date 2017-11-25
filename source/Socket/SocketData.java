package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by PC81 on 2017-11-24.
 */
public class SocketData {
    protected String input;
    protected String output;
    protected Socket socket;
    protected DataInputStream socketinputStream;
    protected DataOutputStream socketOutputStream;
}
