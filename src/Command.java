import java.io.IOException;
import java.net.Socket;

public interface Command {

    void executeFileCommand (Socket s) throws Exception;

    void executeAddCommand (Socket s) throws Exception;

    void executeShowCommand (Socket s) throws Exception;

    void executeGetCommand (Socket s) throws Exception;

    void executeChangeCommand (Socket s) throws Exception;


}
