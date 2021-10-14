/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.tecnm.jdbc.servidorudp;
import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author rgo19
 */
public class ServidorUDP {
    private int PORT;
    private byte msg[];
    private DatagramSocket s;
    
    public ServidorUDP() throws IOException{
        PORT = 8050;
        msg = new byte[1024];
        s = new DatagramSocket(PORT);
    }
    public ServidorUDP(int port) throws IOException{
        PORT = port;
        msg = new byte[1024];
        s = new DatagramSocket(PORT);
    }
    public void initServer() throws IOException{
        while(true){
            DatagramPacket paquete = new DatagramPacket(new byte[1024],1024);
            s.receive(paquete);
            System.out.println("Se ha recivido una petición: ");
            System.out.println("Procedente de la dirección: " + paquete.getAddress());
            System.out.println("En el puerto " + paquete.getPort());
            System.out.println("Mensaje del cliente: " +new String(paquete.getData()));
            System.out.println("Sirviendo la petición");
            String message = new String("Hola desde el servidor, mensaje recibido" + java.time.LocalDateTime.now());
            msg = message.getBytes();
            DatagramPacket packet = new DatagramPacket(msg,msg.length,paquete.getAddress(),paquete.getPort());
            s.send(packet);
        }
    }
    public static void main(String[] args) throws IOException {
        try{
            ServidorUDP server = new ServidorUDP();
            server.initServer();
        }catch(IOException e){
           e.printStackTrace();
        }
        
    }
}
