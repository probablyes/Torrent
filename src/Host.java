import java.io.*;

public class Host {

    public String getHostList() throws IOException {

        String config;
        BufferedReader br = new BufferedReader(new FileReader("src/config.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            config = sb.toString();
        } finally {
            br.close();
        }
        return config;

    }

    public void addNewHostToList(String host, int port) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(new File("src/config.txt"), true));
        writer.write(host + " " + port + "\n");
        writer.close();

    }

    public void removeHostFromList(){

    }



}
