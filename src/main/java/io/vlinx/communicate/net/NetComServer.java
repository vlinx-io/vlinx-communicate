package io.vlinx.communicate.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import io.vlinx.encryption.AES;
import io.vlinx.logging.Logger;

public abstract class NetComServer {

    private Gson gson = new Gson();
    private int port = 0;
    private String pwd = "";

    public NetComServer(int port, String pwd) {
        this.port = port;
        this.pwd = pwd;
    }

    public void start() throws Exception {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket client = serverSocket.accept();
                new HandlerThread(client);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private class HandlerThread implements Runnable {
        private Socket socket;

        public HandlerThread(Socket client) {
            socket = client;

            Thread thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }

        public void run() {

            DataInputStream input = null;

            try {
                input = new DataInputStream(socket.getInputStream());
                String messageStr = input.readUTF();

                messageStr = AES.decrypt(messageStr, pwd);

                Object object = gson.fromJson(messageStr, NetMessage.type);
                if (object instanceof NetMessage) {
                    Logger.INFO("Request: " + gson.toJson(object) + " From: " + socket.getRemoteSocketAddress());
                    NetMessage response = handleMessage((NetMessage) object, socket);
                    String json = response.getJson();
                    Logger.INFO("Response: " + json + " To: " + socket.getRemoteSocketAddress());
                    json = AES.encrypt(json, pwd);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(json);

                } else {
                    Logger.ERROR("Could not parse the message object");
                }

            } catch (Exception e) {
                Logger.ERROR(e);
            } finally {
                if (socket != null) {
                    try {
                        input.close();
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        Logger.ERROR(e);
                    }
                }
            }
        }
    }

    protected abstract NetMessage handleMessage(NetMessage request, Socket socket);

}