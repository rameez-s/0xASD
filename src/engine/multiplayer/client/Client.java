package engine.multiplayer.client;

import engine.Engine;
import engine.objects.Sprite;
import objects.Player;
import org.lwjgl.opengl.GL;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.glfw.GLFW.*;

public class Client
{

    int ID = 0;

    DatagramSocket sock = null;
    InetAddress host;
    int port;
    public Client()
    {
        System.out.println("Started Client");
        port = 4567;
        try
        {
            ID = 0;
            sock = new DatagramSocket();
            System.out.println("Socket: " + sock.getLocalSocketAddress());
            host = InetAddress.getByName("localhost");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        recieveData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        catch(IOException e)
        {
            System.err.println("IOException " + e);
        }
    }

    public void sendData() throws Exception{
        long time = System.nanoTime() + 20000000L;
        System.out.println(ID);
        while(Engine.instance.scenes.get(Engine.instance.currentScene).players.size() - 1 < ID){
            Player player = new Player();
            player.position.x = (float) (-250 + Math.random() * 500);
            System.out.println(player.position.x);
            Engine.instance.scenes.get(Engine.instance.currentScene).add(player, 3);
            System.out.println(Engine.instance.scenes.get(Engine.instance.currentScene).players.size());
        }
        Player player = (Player) Engine.instance.scenes.get(Engine.instance.currentScene).players.get(ID);
        player.controllable = true;
        while(true) {
            if(System.nanoTime() > time) {


                String data = ID + "," + player.position.x + "," + player.position.y + "," + player.position.z + "," + player.velocity.x + "," + player.velocity.y + "," + player.velocity.z;
                byte[] b = data.getBytes();
                DatagramPacket dp = new DatagramPacket(b, b.length, host, port);
                sock.send(dp);
                time += 200000000L;
            }
        }
    }


    public void recieveData() throws Exception{
        byte[] buffer = new byte[65536];
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
        while(true){
            sock.receive(reply);

            byte[] data = reply.getData();
            String message = new String(data, 0, reply.getLength());
            if(message.startsWith("Connected:")){
                handleConnection(message);
            }
            handleMessage(message);
        }
    }
    private void handleConnection(String message){
        System.out.println(message.trim());

    }

    private void handleMessage(String message){
        System.out.println(message.trim());
        String[] data = message.split(",");
        while(Engine.instance.scenes.get(Engine.instance.currentScene).players.size() - 1 < Integer.parseInt(data[0])){
            glfwMakeContextCurrent(Engine.instance.getWindow());
            GL.createCapabilities();
            Player player = new Player();
            player.position.x = (float) (-100 + Math.random() * 200);
            System.out.println(player.position.x);
            Engine.instance.scenes.get(Engine.instance.currentScene).add(player, 3);
            System.out.println(Engine.instance.scenes.get(Engine.instance.currentScene).players.size());
        }
        Sprite player = Engine.instance.scenes.get(Engine.instance.currentScene).players.get(Integer.parseInt(data[0]));
        player.position.set(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
        player.velocity.set(Float.parseFloat(data[4]), Float.parseFloat(data[5]), Float.parseFloat(data[6]));
    }
    AtomicInteger numberPlayers = new AtomicInteger();
    public static void main(String args[]){
        new Engine().start();

    }
}