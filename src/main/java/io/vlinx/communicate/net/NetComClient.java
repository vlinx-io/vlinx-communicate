package io.vlinx.communicate.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import io.vlinx.encryption.AES;
import io.vlinx.logging.Logger;

public abstract class NetComClient {

    private String serverAddress = "";
    private int serverPort = 0;

    private Gson gson = new Gson();

    private String pwd = "";
    private NetMessage msg;

    public NetComClient(String serverAddress, int serverPort, String pwd) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.pwd = pwd;
    }

    public NetMessage sendRequest(NetMessage request) throws Exception {
        try {
            String json = gson.toJson(request);
            String responseStr = sendContent(json);
            NetMessage response = processResponse(responseStr);
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    private String sendContent(String content) throws Exception {

        content = AES.encrypt(content, pwd);

        Socket socket = null;
        try {
            socket = new Socket(serverAddress, serverPort);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(
                    socket.getOutputStream());
            out.writeUTF(content);
            String ret = input.readUTF();
            out.close();
            input.close();
            return ret;
        } catch (Exception e) {
            throw e;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    throw e;
                }
            }
        }
    }

    public NetMessage processResponse(String response) throws Exception {
        try {
            if (response == null || response.isEmpty()) {
                Logger.ERROR("The response is empty");
                return null;
            }

            response = AES.decrypt(response, pwd);

            Object object = gson.fromJson(response, NetMessage.type);
            if (object instanceof NetMessage) {
                return transformMessage((NetMessage) object);
            } else {
                throw new Exception("Could not parse the message object");
            }
        } catch (Exception e) {
            throw e;
        }

    }

    protected NetMessage transformMessage(NetMessage msg) {
        return msg;
    }

}