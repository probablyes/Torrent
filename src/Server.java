
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class Server extends Node{

    protected int PORT;
    protected Thread serverThread;
    protected BufferedWriter out = null;
    protected BufferedReader in = null;
    protected String fileName;
    protected Host hosts = new Host();

    public Server(int portNumber){
        PORT = portNumber;
    }

    public void configureServer(String name) throws Exception {
        ServerSocket ss;
        try {
            ss = new ServerSocket(PORT);
            System.out.println("Your server node port number is: " + PORT + ". peer-2-peer server node starts working...");
            hosts.addNewHostToList(InetAddress.getLocalHost().getHostName(), PORT);

            while(true) {
                Socket s = ss.accept();
                this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String line = null;

                while (!s.isClosed() && (line = in.readLine()) != null) {
                    if(line.equals("add")) {
                        executeAddCommand(s);
                    }else if(line.equals("show")) {
                        //sendInfoThroughSocket(s, printMd5List(new File("/home/probablyes/peer-2-peer-host2/src/assets")));
                        executeShowCommand(s);
                    }else if(line.startsWith("file")) {
                        //setFileName(s);
                        String part[] = line.split(" ");
                        sendFileThroughSocket(s, part[1]);
                    }else if(line.startsWith("get")){
                        executeGetCommand(s);
                    }else if(line.equalsIgnoreCase("change")){
                        receiveShowRequest(s);
                        ss.close();
                        s.close();
                        configureServer(name);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        ServerSocket serverSocket;
//        try {
//            serverSocket = new ServerSocket(PORT);
//            System.out.println("Your server node port number is: " + PORT + ". peer-2-peer server node starts working...");
//
//            Socket socket = serverSocket.accept();
//            System.out.println("Connected with client!");
//            String command = null;
//
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                command = line;
//            }
//            ///-------------------------////////
//            if(command.equals("SHOW")){
//                System.out.println("komenda show");
//            }else if(command.equals("PULL")){
//                System.out.println("komenda pull");
//                int fileNumber=0;
//                //in.close();
//                sendFileThroughSocket(socket, fileNumber);
//            }else if(command.equals("PUSH")){
//
//                System.out.println("komenda push");
//                InputStream in = null;
//                OutputStream out = null;
//                in = socket.getInputStream();
//                out = new FileOutputStream("/home/probablyes/peer-2-peer-host2/src/assets/kupka.txt");
//
//                byte[] bytes = new byte[16*1024];
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
////          while(true) {
//
////                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////                String line = null;
////                while ((line = in.readLine()) != null) {
////                    System.out.println("Read: " + line);
////                }
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("UNEXPECTED SERVER ERROR");
//
//        }

    public void startServerThread(String name){
        Runnable r = () -> {
            try {
                configureServer(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        serverThread = new Thread(r);
        serverThread.start();
    }

    public String showFileList() throws IOException {
        //strumienie java 8 - spoko opcja
//        StringBuilder stringBuilder = new StringBuilder();
//        Files.list(Paths.get("/home/probablyes/peer-2-peer-host2/src/assets"))
//                .forEach(System.out::println);

        File folder = new File("/home/probablyes/peer-2-peer-host2/src/assets");
        File[] listOfFiles = folder.listFiles();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                stringBuilder.append(i + ". " + listOfFiles[i].getName() + "\n");
            }
        }
        return stringBuilder.toString();

    }
//////////////////////
    public static void sendFileThroughSocket(Socket s, int fileNumber) {
        try {

            final File folder = new File("/home/probablyes/peer-2-peer-host2/src/assets");
            File fileToSend=folder.listFiles()[fileNumber];

            InputStream in = Files.newInputStream(fileToSend.toPath());
            OutputStream out = s.getOutputStream();

            int count;
            byte[] buffer = new byte[8192];
            while ((count = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, count);
            }

            out.close();
        }catch(Exception exc) {
            System.out.println(exc);
        }
    }

    public void sendInfoThroughSocket(Socket s, String message) throws Exception {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

        out.write(message);
        out.newLine();
        out.flush();
        out.close();
    }

    public void setFileName(Socket s) throws IOException {
        String line;

        fileName = in.readLine();
        System.out.println("Receiving...: " + fileName);

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
        out = new FileOutputStream("/home/probablyes/peer-2-peer-host2/src/assets" + "/" + fileName);

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
        //sendInfoThroughSocket(s, "Select number of file: ");
        //setFileName(s);
//        serverWWW.addReceivedInformationToPost("command: file");
//        //printMd5List(new File("/home/probablyes/peer-2-peer-host2/src/assets"));
//        // setFileName(s);
//        sendInfoThroughSocket(s, files.toString());
//        serverWWW.addSentInformationToPost(files.toString());
//        System.out.println(s.isClosed());
//        receiveShowRequest(s);
//        serverWWW.addReceivedInformationToPost("file");
        //System.out.println(fileToPush);
        //sendFileThroughSocket(s, fileNumber);
    }

    @Override
    public void executeAddCommand(Socket s) throws Exception {
        serverWWW.addReceivedInformationToPost("command: add");
        setFileName(s);
        receiveFile(s, fileName);
        serverWWW.addReceivedInformationToPost("received file: " + fileName);
    }

    @Override
    public void executeShowCommand(Socket s) throws Exception {
        serverWWW.addReceivedInformationToPost("command: show");
        sendInfoThroughSocket(s, printMd5List(new File("/home/probablyes/peer-2-peer-host2/src/assets")));
        serverWWW.addSentInformationToPost(printMd5List(new File("/home/probablyes/peer-2-peer-host2/src/assets")));
    }

    @Override
    public void executeGetCommand(Socket s) throws Exception {
        sendInfoThroughSocket(s, hosts.getHostList());
        //serverWWW.addSentInformationToPost(hosts.getHostList());
    }

    @Override
    public void executeChangeCommand(Socket s) throws Exception {

    }
}