/*
 * 
 */
package com.mycompany.wrmclient;
import java.io.*;
import java.net.*;
import java.util.Scanner;
 
/**
 * 
 *
 *
 * 
 */
public class WRMClient {
 
    public static void main(String[] args) {
        
 
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        Scanner scan = new Scanner(System.in);
        try {
            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
 
            
            
            while (true) {
                System.out.println("Please input <REQUESTQUOTE> for a random programming quote or");
                System.out.println("<EXIT> in order to exit this client");
                System.out.println("Enter a Command: ");
                String line = scan.nextLine();
                if("<REQUESTQUOTE>".equals(line)){
                DatagramPacket request = new DatagramPacket(new byte[1], 1, address, port);
                socket.send(request);
 
                byte[] buffer = new byte[512];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);
 
                String quote = new String(buffer, 0, response.getLength(), "US-ASCII");
 
                System.out.println(quote);
                System.out.println();
 
                Thread.sleep(1000);
                }if("<EXIT>".equals(line)){
                System.exit(0);
                }else {
                System.out.println("Invalid input");
                }
            }
            
 
        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

