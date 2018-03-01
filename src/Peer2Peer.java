import java.util.Scanner;

public class Peer2Peer{

    private static Server server;
    private static Client client;
    static int clientPortNumber;
    static int serverPortNumber;
    static Host host = new Host();
    static int availablePortNumbers[];
    static String programVersion;
    static String catalogName;
    static String PATH;

    public static void main (String args[]) throws Exception {


//        host.addNewHostToList("pupup", 9999);
//        host.addNewHostToList("sdfsdf", 324234);
//        System.out.println(host.getPortNumberList());
//        System.out.println(host.getHostList());
        //catalogName = args[0];

        System.out.println("Hi User, first of all, choose a path with files which want you to share with other hosts:  ");
        PATH = readFromUser();
        System.out.println("Hi User. First of all, choose program version - should it works in multi-host(MH) version (a lot of hosts connected to this network)" +
                " or in host2host (H2H) version (two hosts conntected to network)? \n"
                + "If you choose multi-host, type \"H2H\" \n" +
                "If you choose host-to-host, type \"MH\"");

//        host.addNewHostToList("test", 123);
//        host.addNewHostToList("test", 1234);
//        System.out.println(host.getPortNumberList());
//        System.out.println(host.getHostList());

        programVersion = readFromUser();
        if(programVersion.equals("H2H")){
            provideH2HInteraction();
        }else if(programVersion.equals("MH")){
            provideM2HInteraction();
        }else{
            System.out.println("Incorrect request!");
            System.exit(0);
        }
//
//        System.out.println("You want to connect to this peer-to-peer network. So lovely. There is a list of hosts connected to this network:  ");
//        for (int i = 0; i < host.getHostList().size(); i++) {
//            System.out.println(i + 1 + ". " + host.getHostList().get(i) + " listening on port: " + host.getPortNumberList().get(i));
//        }
//
//        System.out.println();
//        System.out.println("So, dear User, select proper number attached to selected host: ");
//        String hostName = readFromUser();
//        System.out.println("You have chosen: " + host.getHostList().get(new Integer(hostName) - 1) + " listening on port number: " + host.getPortNumberList().get(new Integer(hostName) - 1));
//        String properHostName = host.getHostList().get(new Integer(hostName) - 1);
//
//
////       System.out.println("Please select proper port when host listening: ");
////        String portNumber = readFromUser();
////        int PORT = Integer.parseInt(portNumber);
////        System.out.println("You have chosen: " + PORT);
////        int enablePortNumber = 60001;
////        for (int i = 0; i < availablePortNumbers.length; i++) {
////            if(host.getPortNumberList().contains(availablePortNumbers[i])){
////                enablePortNumber++;
////            }
////        }
//        clientPortNumber = 60002;
//        serverPortNumber = 60001;
//        host.addNewHostToList();
////przypisać na stałe numer portu
//
//        client = new Client(properHostName, clientPortNumber);
//        server = new Server(serverPortNumber);
//        server.startServerThread();
//        client.startClientThread();
        client.serverWWW.startServerWWW();
    }


    public static String readFromUser(){
        Scanner in = new Scanner(System.in);
        String name = in.nextLine().trim();
        return name;
    }

//    public static void attachPortNumberToHost(){
//        if(PORT)
//    }

    public static void provideH2HInteraction() throws InterruptedException {
        System.out.println("So you choose H2H version. Now select port number when you, as a host, want to listening: ");
        Integer sPortNumber = Integer.valueOf(readFromUser());
        if(sPortNumber > 10000){
            server = new Server(sPortNumber);
            server.startServerThread(programVersion);
        }else{
            System.out.println("I HIGHLY recommend you to choose number higher than 60000. Let's try again... \n");
            provideH2HInteraction();
        }
        Thread.sleep(500);
        System.out.println("Now you have to provide host name with which you want to connect:");
        String H2HHostname = readFromUser();
        System.out.println("Ok, so now provide port number when selected host listening: ");
        Integer H2HportNumber = Integer.valueOf(readFromUser());
        System.out.println("Selected host: " + H2HHostname + ", selected port number: " + H2HportNumber);
        client = new Client(H2HHostname, H2HportNumber, "H2H");
        client.startClientThread();

    }

    public static void provideM2HInteraction() throws Exception {
        System.out.println("You choose M2H version. Now select port number when you, as a host, want to listening: ");
        Integer sPortNumber = Integer.valueOf(readFromUser());
        if(sPortNumber > 10000){
            server = new Server(sPortNumber);
            server.startServerThread(programVersion);
        }else{
            System.out.println("I HIGHLY recommend you to choose number higher than 60000. Let's try again... \n");
            provideM2HInteraction();
        }
        Thread.sleep(500);
        System.out.println("You choose M2H version. You must know proper credentials of one of the host connected to this network.\n"  +
                "So now type host name when selected host listening: ");
        String M2HHostname = readFromUser();
        System.out.println("Ok, so now provide port number when selected host listening: ");
        Integer H2HportNumber = Integer.valueOf(readFromUser());
        System.out.println("Selected host: " + M2HHostname + ", selected port number: " + H2HportNumber);
        host.addNewHostToList(M2HHostname, H2HportNumber);
        client = new Client(M2HHostname, H2HportNumber, "MH");
        client.startClientThread();

    }

    public static void repeatM2HInteraction() throws Exception {

        System.out.println("Type host name when selected host listening: ");
        String M2HHostname = readFromUser();
        System.out.println("Ok, so now provide port number when selected host listening: ");
        Integer H2HportNumber = Integer.valueOf(readFromUser());
        System.out.println("Selected host: " + M2HHostname + ", selected port number: " + H2HportNumber);
        host.addNewHostToList(M2HHostname, H2HportNumber);
        client = new Client(M2HHostname, H2HportNumber, "MH");
        client.startClientThread();
    }
}
