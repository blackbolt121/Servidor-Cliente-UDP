/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.tecnm.jdbc.servidorudp;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author rgo19
 */
public class ClienteUDP {
    private DatagramSocket dgSocket;
    private DatagramPacket datagram;
    private InetAddress destination;
    private byte msg[];
    private final static int PORT = 8050;
    public ClienteUDP() throws IOException {
        this.dgSocket = new DatagramSocket();
        destination = InetAddress.getByName("127.0.0.1");
        msg = new byte[1024];
    }
    public ClienteUDP(String ip, int port) throws IOException{
        dgSocket = new DatagramSocket();
        destination = InetAddress.getByName(ip);
        msg = new byte[1024];
    }
    public void send(String mensaje) throws IOException {
        datagram = new DatagramPacket(mensaje.getBytes(),mensaje.getBytes().length,destination,PORT);
        dgSocket.send(datagram);
        datagram = new DatagramPacket(msg,msg.length);
        dgSocket.receive(datagram);
        String recieved = new String(datagram.getData());
        System.out.println("Datos recividos del datagrama \n" + recieved);
        
    }
    public void closeSocket(){
        dgSocket.close();
    }
    public void writeCliente(){
        synchronized(this){
            boolean band = true;
            while(band){
                Scanner sc = new Scanner(System.in);
                System.out.println("Enviar mensaje: ");
                try {
                    this.send(sc.nextLine());
                    DatagramPacket recieve = new DatagramPacket(new byte[1024],1024); 
                } catch (IOException ex) {
                    Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("Desea enviar otro mensaje (Si/No): ");
                String value = sc.nextLine();
                if(value.equalsIgnoreCase("si")){
                    band = false;
                }else{
                    band = true;
                }
            }
            this.closeSocket();
        }
    }
    
    public static void main(String[] args) {
        try {
            ClienteUDP cu = new ClienteUDP();
            cu.writeCliente();
            
        } catch (IOException ex) {
            //Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

   
}
