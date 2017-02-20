package engine.multiplayer.server;

import java.net.*;
import java.util.ArrayList;

/**
 * Created by 18iwahlqvist on 2/19/2017.
 */
public class Server {

    DatagramSocket serverSocket;
    boolean listening = true;

    public int ID = 0;

    public static boolean running;
    public static Server instance;
    public void newConnected(ClientHandler c){
        try {
            for (ClientHandler client : clients) {
                String s = "CONNECTED:" + c.ID;
                DatagramPacket sendData = new DatagramPacket(s.getBytes(), s.getBytes().length, client.inetAddress, client.port);
                serverSocket.send(sendData);
            }
        }catch (Exception e){

        }
    }
    ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server(){
        instance = this;
        try{
            serverSocket = new DatagramSocket(4567);
            running = true;
            byte[] buffer = new byte[65536];
            while(listening){
                DatagramPacket incomingData = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(incomingData);
                boolean included = false;
                if(clients.size() != 0) {
                    for (ClientHandler client : clients) {
                        if (client.socketAddress.equals(incomingData.getSocketAddress())) {
                            included = true;
                        }
                    }
                }else{
                    clients.add(new ClientHandler(incomingData.getAddress(), incomingData.getSocketAddress(), incomingData.getPort()));
                }
                if(included = false){
                    clients.add(new ClientHandler(incomingData.getAddress(), incomingData.getSocketAddress(), incomingData.getPort()));
                }
                byte[] data = incomingData.getData();
                String s = new String(data, 0, incomingData.getLength());
                for(ClientHandler client : clients) {
                    if(!client.socketAddress.equals(incomingData.getSocketAddress())) {
                        DatagramPacket sendData = new DatagramPacket(s.getBytes(), s.getBytes().length, client.inetAddress, incomingData.getPort());
                        serverSocket.send(sendData);
                    }
                }
            }
            serverSocket.close();
        }catch(Exception e){
            running = false;
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        Server s = new Server();
    }
}

class ClientHandler{
    public SocketAddress socketAddress;
    public InetAddress inetAddress;
    public int port;
    public int ID;

    public ClientHandler(InetAddress inetAddress, SocketAddress socketAddress, int port){
        this.socketAddress = socketAddress;
        this.inetAddress = inetAddress;
        this.port = port;
        System.out.println(socketAddress + "\t" + inetAddress + "\t" + port);
        Server.instance.newConnected(this);
        ID = Server.instance.ID;
        Server.instance.ID++;
    }
}