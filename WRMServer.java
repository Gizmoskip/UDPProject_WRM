/*
 * 
 */
package com.mycompany.wrmserver;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
 
/**
 *
 */
public class WRMServer {
    private DatagramSocket socket;
    private List<String> listQuotes = new ArrayList<String>();
    private Random rand;
 
    public WRMServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
        rand = new Random();
    }
 
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input the port number: ");
        String line = scan.nextLine();
        String quoteFile = "quotes.csv";
        int port = Integer.parseUnsignedInt(line);
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date();  
        System.out.println("Server Started at "+formatter.format(date)); 
        
        try {
            WRMServer server = new WRMServer(port);
            server.loadQuotesFromFile(quoteFile);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
 
    private void service() throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        while (true) {
            
            DatagramPacket request = new DatagramPacket(new byte[1], 1);
            socket.receive(request);
            
            
            
            String quote = getRandomQuote();
            byte[] buffer = quote.getBytes();
 
            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();
            
            Date date = new Date(); 
            System.out.println("Request Recieved from "+clientAddress+": "+formatter.format(date));
 
            DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            socket.send(response);
        }
    }
 
    private void loadQuotesFromFile(String quoteFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(quoteFile));
        String aQuote;
 
        while ((aQuote = reader.readLine()) != null) {
            listQuotes.add(aQuote);
        }
 
        reader.close();
    }
 
    private String getRandomQuote() {
        int randomIndex = rand.nextInt(listQuotes.size());
        String randomQuote = listQuotes.get(randomIndex);
        return randomQuote;
    }
}
