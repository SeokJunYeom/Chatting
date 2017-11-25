package Socket;

/**
 * Created by PC81 on 2017-11-23.
 */
public interface Network {
    boolean init();
    void send(String input);
    void receive();
    void close();
    String getMessage();
}
