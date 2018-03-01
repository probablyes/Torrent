import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.*;

public class Client extends Node{

    protected int PORT;
    protected Thread clientThread;
    protected Socket socket;
    protected BufferedWriter out = null;
    protected BufferedReader in = null;
    protected String hostName;
    protected Host host = new Host();
    protected String command;
    protected Map<Integer, String> map;
    protected String connectionType;

    //protected ServerWWW serverWWW;


    protected Client(String hostName, int portNumber, String connectionType){
        this.connectionType = connectionType;
        this.PORT = portNumber;
        this.hostName = hostName;
    }


    protected void configureClient(String conn) throws Exception {
        System.out.println("Your client-node port number is: " + PORT + ". peer-2-peer client node is working...");
        System.out.println("Trying to connect with server... ");
        Thread.sleep(3000);
        System.out.println("You are connected with selected host.");

        if(conn.equals("H2H")){
            makeH2HRequest();
        }else if(conn.equals("MH")){
            //sciagam liste hostów
            makeMHRequest();
        }

    }

    protected void makeMHRequest() throws Exception {
        try {
            Scanner sc = new Scanner(System.in);
            Socket s = new Socket(hostName, PORT);
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
//            System.out.println("Tutaj wpisz 'add'");
//            command = sc.nextLine();
//            out.write(command + "\n");
//            out.flush();
//            System.out.println("jebana komenda wysłana: " + command);

            while (true) {
                System.out.println("\nWhat you want to do now? You've got 7 options: If you want to see files for download in selected host, type \"show\" \n" +
                        "If you want to push file to this host, type \"add\"\n"+
                        "If you want to pull file from host, type \"file\"\n" +
                        "If you want to close program, type \"exit\"\n" +
                        "If you want to get list of connected hosts, type \"get\"\n" +
                        "If you want to connect with other host, type \"change\"\n" +
                        //"If you want to connect with other host in the same time, type \"connect\"\n" +
                        "In you want to see the summary, type \"summary\"");

                command = sc.nextLine();
                if (command.equals("add") || command.equals("show") || command.startsWith("file") || command.equalsIgnoreCase("get") || command.equals("change") ||
                        command.equalsIgnoreCase("summary")) {
                    //sendCommandToServer(s, command);
                    if (command.startsWith("file")) {
                        executeFileCommand(s);
                        makeMHRequest();
//                        InputStream in = s.getInputStream();
//                        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        int byteToBeRead = -1;
//
//
//                        final File folder = new File("/home/probablyes/peer-2-peer-host2 (kopia)/src/assets");
//                        File newFile = new File(folder.getAbsolutePath() + File.separator + "zjebany plik kurak");
//                        FileOutputStream fs = new FileOutputStream(newFile);
//                        while ((byteToBeRead = in.read()) != -1) {
//                            //System.out.println(byteToBeRead);
//                            fs.write(byteToBeRead);
//                        }
//
//                        fs.flush();
//                        fs.close();                   p
                    }
                    if (command.startsWith("show")){
                        executeShowCommand(s);
                        makeMHRequest();
                    }
                    if (command.equals("add")){
                        executeAddCommand(s);
                        makeMHRequest();
                    }
                    if (command.equalsIgnoreCase("get")){
                        executeGetCommand(s);
                        makeMHRequest();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    }
                    if (command.equalsIgnoreCase("change")){
                        executeChangeCommand(s);
                    }
                    if (command.equalsIgnoreCase("summary")){
//                        if (Desktop.isDesktopSupported()) {
//                            Desktop.getDesktop().browse(new URI("localhost:8080/summary"));
//                        }
                        System.out.println("If you want to see summary, please open internet browser and type: localhost:8080/summary");
                        makeMHRequest();
                    }
                    if (command.equalsIgnoreCase("exit")){
                        sc.close();
                        s.close();
                        Peer2Peer.main(new String[1]);
                    }
                    //s.close();
                } else if (command.equals("exi")) {
                    break;
                }
                Thread.sleep(200);
            }
            sc.close();

        } catch (ConnectException ce) {
            System.out.println("No server listening on this port. Waiting for server. Trying to reconnect...");
            Thread.sleep(2000);
            configureClient(connectionType);
        }
    }
//        try {
//            Thread.sleep(5000);
//
//            socket = new Socket(hostName, PORT);
//            System.out.println("Connected with server!");
//
//            System.out.println("What you want to do now? You've got 3 options: If you want to see files for download in selected host, type \"SHOW\" \n" +
//                    "If you want to push file to this host, type \"PUSH\"\n"+
//                    "If you want to pull file from host, type \"PULL\"");
//            String command = Peer2Peer.readFromUser().toUpperCase();
//            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            out.write(command);
//            out.flush();
//
//            if(command.equals("SHOW")){
////                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
////                out.write(command);
////                out.flush();
////                socket.close();
//            }else if(command.equals("PULL")){
//
//                InputStream in = socket.getInputStream();
//                //ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                int byteToBeRead = -1;
//
//
//                final File folder = new File("/home/probablyes/peer-2-peer-host2 (kopia)");
//                File newFile=new File(folder.getAbsolutePath()+File.separator+"pipi");
//                FileOutputStream fs = new FileOutputStream(newFile);
//                while((byteToBeRead = in.read())!=-1){
//                    //System.out.println(byteToBeRead);
//                    fs.write(byteToBeRead);
//                }
//
//                fs.flush();
//                fs.close();
//            }else if(command.equals("PUSH")){
//
//                File file = new File("/home/probablyes/peer-2-peer-host2 (kopia)/src/assets/123.txt");
//                long length = file.length();
//                byte[] bytes = new byte[16 * 1024];
//                InputStream in = new FileInputStream(file);
//                OutputStream out = socket.getOutputStream();
//
//                int count;
//                while ((count = in.read(bytes)) > 0) {
//                    out.write(bytes, 0, count);
//                }
//
//                out.close();
//                in.close();
//                socket.close();
//
//            }
//
//
////            while(true){
////                socket = new Socket(hostName, PORT);
////                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////                String line = null;
////                while ((line = in.readLine()) != null) {
////                    System.out.println(line);h
////                }
////            }
//
//
////            while(true){
//
////                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
////                out.write("its working!!!");
////                out.flush();
////                socket.close();
////            }
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (ConnectException ce){
//            System.out.println("No server listening on this port. Waiting for server. Trying to reconnect...");
//            Thread.sleep(2000);
//            configureClient();
//        }


    protected void startClientThread(){
        Runnable r = () -> {
            try {
                configureClient(connectionType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        clientThread = new Thread(r);
        clientThread.start();
    }

//    int setClientPortNumber(int portNumber) throws Exception {
//        host.getPortNumberList()
//    }


    protected void saveHostList(Socket s) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line = null;

        PrintWriter writer = new PrintWriter(new FileWriter(new File("/home/probablyes/peer-2-peer-host2/src/config.txt"), true));

        while ((line = in.readLine()) != null) {
            writer.write(line + "\n");
        }
        writer.close();
        in.close();
    }

    protected void getConfigList() throws IOException {
        System.out.println("List of host recently connected to this network (host name - port number): ");
        System.out.println(this.host.getHostList().toString());
    }

    public void receiveShowRequest(Socket s) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = in.readLine()) != null) {
            System.out.println(line);
            sb.append(line + "</br>");
        }
        serverWWW.addReceivedInformationToPost(sb.toString(), hostName, PORT);

    }

    protected void sendCommandToServer(Socket s, String command) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        out.write(command + "\n");
        out.flush();
    }

    protected void makeH2HRequest() throws Exception {
        try {
            Scanner sc = new Scanner(System.in);
            Socket s = new Socket(hostName, PORT);

            while (true) {
                System.out.println("\nWhat you want to do now? You've got 5 options: If you want to see files for download in selected host, type \"show\" \n" +
                        "If you want to push file to this host, type \"add\"\n"+
                        "If you want to pull file from host, type \"file\"\n" +
                        "If you want to close program, type \"exit\"\n");

                command = sc.nextLine();

                if (command.equals("add") || command.equals("show") || command.startsWith("file") || command.equalsIgnoreCase("get") || command.equalsIgnoreCase("summary")) {
                    //sendCommandToServer(s, command);

                    if (command.startsWith("file")) {
                        executeFileCommand(s);
                        makeH2HRequest();
//                        InputStream in = s.getInputStream();
//                        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        int byteToBeRead = -1;
//
//
//                        final File folder = new File("/home/probablyes/peer-2-peer-host2 (kopia)/src/assets");
//                        File newFile = new File(folder.getAbsolutePath() + File.separator + "zjebany plik kurak");
//                        FileOutputStream fs = new FileOutputStream(newFile);
//                        while ((byteToBeRead = in.read()) != -1) {
//                            //System.out.println(byteToBeRead);
//                            fs.write(byteToBeRead);
//                        }
//
//                        fs.flush();
//                        fs.close();                   p
                    }
                    if (command.startsWith("show")){
                        executeShowCommand(s);
                        makeH2HRequest();
                    }
                    if (command.equals("add")){
//                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
//                        out.write(command + "\n");
//                        out.flush();
                        executeAddCommand(s);
                        makeH2HRequest();
                        //sc.close();
                    }
                    if (command.equalsIgnoreCase("summary")){
                        System.out.println("If you want to see summary, please open internet browser and type: localhost:8080/summary");
                        makeMHRequest();
                    }
                    //s.close();
                } else if (command.equals("exit")) {
                    break;
                }
                Thread.sleep(200);
            }
            sc.close();

        } catch (ConnectException ce) {
            System.out.println("No server listening on this port. Waiting for server. Trying to reconnect...");
            Thread.sleep(2000);
            configureClient(connectionType);
        }
    }

    public void receiveFile(Socket s, String name) throws IOException {
//        InputStream in = s.getInputStream();
//        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int byteToBeRead = -1;
//
//        final File folder = new File("/home/probablyes/peer-2-peer-host2/src/assets");
//        File newFile = new File(folder.getAbsolutePath() + File.separator + " kutasek.txt");
//        FileOutputStream fs = new FileOutputStream(newFile);
//        while ((byteToBeRead = in.read()) != -1) {
//            //System.out.println(byteToBeRead);
//            fs.write(byteToBeRead);
//        }
//
//        fs.flush();
//        fs.close();
        InputStream in = null;
        OutputStream out = null;

        in = s.getInputStream();
        out = new FileOutputStream("/home/probablyes/peer-2-peer-host2/src/assets" + "/testotesto.txt");

        byte[] bytes = new byte[16*1024];

        int count;
        while ((count = in.read(bytes)) != -1) {
            out.write(bytes, 0, count);
            System.out.println("RECEIVED: " + count + " bytes.");
        }

        out.close();
        in.close();
        //s.close();
    }


    @Override
    public void executeFileCommand(Socket s) throws Exception {
        sendCommandToServer(s, command);
        serverWWW.addSentInformationToPost(command + "\n", hostName, PORT);
//        System.out.println("Select file: ");
//        receiveShowRequest(s);
//        //serverWWW.addReceivedInformationToPost()
//        Scanner scanner = new Scanner(System.in);
//        String fileName = scanner.nextLine();
//        sendCommandToServer(s, fileName);                                   //tutaj
//        serverWWW.addSentInformationToPost(command, hostName, PORT);
        String parts[] = command.split(" ");
        receiveFile(s, parts[1]);
        //serverWWW.addReceivedInformationToPost(s, nazwaPliku);

    }

    @Override
    public void executeAddCommand(Socket s) throws Exception {
        sendCommandToServer(s, command);
        serverWWW.addSentInformationToPost(command, hostName, PORT);
        System.out.println("Choose file name:");
        super.printMd5List(new File("/home/probablyes/peer-2-peer-host2/src/assets"));
        System.out.println(files);
        //wysylanie nazwy pliku??
        Scanner sc = new Scanner(System.in);
        command = sc.nextLine();//
        sendCommandToServer(s, command);
        serverWWW.addSentInformationToPost(command, hostName, PORT);
        sendFileThroughSocket(s,command);
        serverWWW.addSentInformationToPost("Sent file: " + command, hostName, PORT);

    }

    @Override
    public void executeShowCommand(Socket s) throws Exception {
        sendCommandToServer(s, command);
        serverWWW.addSentInformationToPost(command, hostName, PORT);
        receiveShowRequest(s);
    }

    @Override
    public void executeGetCommand(Socket s) throws Exception{
        sendCommandToServer(s, command);
        serverWWW.addSentInformationToPost(command, hostName, PORT);
        saveHostList(s);
        getConfigList();
        serverWWW.addReceivedInformationToPost("Received file: config.txt", hostName, PORT);
    }

    @Override
    public void executeChangeCommand(Socket s) throws Exception{
        sendCommandToServer(s, "change");
        serverWWW.addSentInformationToPost(command, hostName, PORT);
        s.close();
        Peer2Peer.repeatM2HInteraction();
    }
}
