import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        InetAddress IP=InetAddress.getLocalHost();



        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
        }

        Socket socket = null;
        InputStream in = null;
        FileOutputStream out = null;

        try {
            System.out.println("\n\nEnter this address on client := "+IP.getHostAddress()+"\n Waiting for connection...");
            socket = serverSocket.accept();
            System.out.println("\n\nConnection Established\n\n");
        } catch (IOException ex) {
            System.out.println("Can't accept client connection. ");
        }

        BufferedReader sbr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            in = socket.getInputStream();
        } catch (IOException ex) {
            System.out.println("Can't get socket input stream. ");
        }

        try {
            out = new FileOutputStream("abcd.zip");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. ");
        }
        int size=Integer.parseInt(sbr.readLine());
        System.out.println(size);
        // byte[] bytes = new byte[size];

        int count;

        // while ((count = in.read(bytes))!=-1) {
        //     out.write(bytes, 0, count);
        // }

        byte[][] bytes = new byte[100][];
        byte[] byte101= new byte[size-(size/100)*100];
        for(int j=0;j<100;j++){
            bytes[j]=new byte[size/100];

            while ((count = in.read(bytes[j]))!=-1) {
                out.write(bytes[j], 0, count);
            }

            bytes[j]=null;
            System.gc();
            System.out.print("\rTransfer Progress: "+(j+1)+"% done     |     "+((size/100)*(j+1)/1048576)+"MB out of "+(size/1048576)+"MB");
       
        }
        while ((count = in.read(byte101))!=-1) {
                out.write(byte101, 0, count);
        }





        System.out.println("\n\nFile Received.\n\n\n");


        out.close();
        in.close();
        socket.close();
        serverSocket.close();
    }
}