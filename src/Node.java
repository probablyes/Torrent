import ServerWWW.ServerWWW;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

abstract class Node {

    protected ServerWWW serverWWW = new ServerWWW();

    protected List<String> files = new ArrayList<>();

    abstract void executeFileCommand(Socket s) throws Exception;

    abstract void executeAddCommand(Socket s) throws Exception;

    abstract void executeShowCommand(Socket s) throws Exception;

    abstract void executeGetCommand(Socket s) throws Exception;

    abstract void executeChangeCommand(Socket s) throws Exception;

    public String printMd5List(final File folder) throws Exception {

        files.clear();
        StringBuffer sb = new StringBuffer();
        StringBuffer sum = new StringBuffer();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                printMd5List(fileEntry);
            } else {
                files.add(fileEntry.getName());
                sb = new StringBuffer();
                MessageDigest md = MessageDigest.getInstance("MD5");

                InputStream is = Files.newInputStream(fileEntry.toPath());
                DigestInputStream dis = new DigestInputStream(is, md);
                while (dis.read() != -1) ; //!!!

                byte[] digest = md.digest();
                sb.append(fileEntry.getName() + " ");
                for (byte b : digest) {
                    sb.append(String.format("%02x", b));
                }
                sb.append("\n");
                sum.append(sb);
                // System.out.println(" "+sb.toString());
            }
        }
        return "\nThere is a list of file:\n" + sum.toString() + "\n";
    }

    public void receiveShowRequest(Socket s) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line = null;

        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

    }

    public void sendFileThroughSocket(Socket s, String fileNumber) throws Exception {
        try {
            File file = new File("/home/probablyes/peer-2-peer-host2/src/assets" +"/"+fileNumber);
            // Get the size of the file
            long length = file.length();
            InputStream in = new FileInputStream(file);
            OutputStream out = s.getOutputStream();

            int count;
            byte[] buffer = new byte[10000];
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                out.flush();
                System.out.println("Sent: " + count + " bytes.");
            }

            out.close();
            in.close();
            // s.close();
        } catch (FileNotFoundException exc) {
            System.out.println(exc);
            //makeH2HRequest();
        }
    }

}
