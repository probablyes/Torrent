package ServerWWW;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;

public class ServerWWW {

    static StringBuilder htmlReceivedMessage = new StringBuilder();
    static StringBuilder htmlSentMessage = new StringBuilder();

    public void startServerWWW() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/summary", new MyHandler());
        server.setExecutor(null);
        server.start();
        htmlSentMessage.append("<h3>Sending History </h3>");
    }

    static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "<b><h2>Summarize</h2></b>" + "<h3>Sending History: </h3>" + htmlSentMessage + "<h3> Receiving History </h3>" + htmlReceivedMessage;
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    public String addReceivedInformationToPost(String message, String hostName, int portNumber){
        htmlReceivedMessage.append("<p><b>" + message + "</b> , <i>host: " + hostName + ", port: " + portNumber + "</i>, when? -> " + new Date().toString() + "</p>");
        return htmlReceivedMessage.toString();
    }

    public String addReceivedInformationToPost(String message){
        htmlReceivedMessage.append("<p><b>" + message + "</b> , <i> as a server, </i>, when? -> " + new Date().toString() + "</p>");
        return htmlReceivedMessage.toString();
    }

    public String addSentInformationToPost(String message, String hostName, int portNumber){
        htmlSentMessage.append("<p><b>" + message + "</b> , <i>host: " + hostName + ", port: " + portNumber + "</i>, when? -> " + new Date().toString() + "</p>");
        return htmlSentMessage.toString();
    }

    public String addSentInformationToPost(String message){
        htmlSentMessage.append("<p><b>" + message + "</b> , <i> as a server, </i>, when? -> " + new Date().toString() + "</p>");
        return htmlSentMessage.toString();
    }




}
