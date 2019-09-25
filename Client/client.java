import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) throws IOException {

        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        Socket socket = null;
        
        System.out.print("\nEnter the server IP Address: ");
        String host = br.readLine();
        socket = new Socket(host, 4444);
        BufferedReader sbr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pwr=new PrintWriter(socket.getOutputStream(),true);
        System.out.print("\nEnter the File name to be sent: ");
        String fileName = br.readLine();
        File file = new File(fileName);
        // Get the size of the file
       // long length = file.length();
       // byte[] bytes = new byte[16 * 1024];
        FileInputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();
        int i=in.available();
        pwr.println(String.valueOf(i));
        pwr.flush();
        System.out.print("\nProcessing File... \n\n");
        byte[][] bytes = new byte[100][];
        byte[] byte101= new byte[i-(i/100)*100];
        System.out.println();
        for(int j=0;j<100;j++){
            bytes[j]=new byte[i/100];
            in.read(bytes[j],0,i/100);
            out.write(bytes[j]);
            bytes[j]=null;
            System.gc();
            System.out.print("\rTransfer Progress: "+(j+1)+"% done     |     "+((i/100)*(j+1)/1048576)+"MB out of "+(i/1048576)+"MB");
        }
        in.read(byte101);
        out.write(byte101);
        System.out.println("\n\n\n\n"+i);
       
       

        out.close();
        in.close();
        socket.close();
    }
}