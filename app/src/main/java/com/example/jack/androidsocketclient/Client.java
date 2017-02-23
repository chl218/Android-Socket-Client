package com.example.jack.androidsocketclient;

import android.os.AsyncTask;
import android.widget.TextView;

import java.net.*;
import java.io.*;

public class Client extends AsyncTask<Void, Void, Void> {

    private String dstAddress;
    private int dstPort;
    private String response = "";
    private TextView textResponse;

    Client(String addr, int port, TextView textResponse) {
        dstAddress = addr;
        dstPort    = port;
        this.textResponse = textResponse;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            System.out.println("Connecting to " + dstAddress + " on port " + dstPort);
            Socket client = new Socket(dstAddress, dstPort);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());

            //response = "Just connected to " + client.getRemoteSocketAddress();

            PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

            outToServer.println("Hello from " + client.getLocalSocketAddress());
            outToServer.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //response = "Server Says : " + in.readLine();
            System.out.println("Server Says : " + in.readLine());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        textResponse.setText(response);
        super.onPostExecute(result);
    }

}