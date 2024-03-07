package services.impl;

import services.HttpService;
import utility.HttpController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static configuration.HttpServer.PORT;

public class HttpServiceImpl implements HttpService {

    @Override
    public void connect(int port) {
        try (ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Server Connected at Port " + port);
            while (true){
                Socket clientSocket = server.accept();
                System.out.println("Connection Established");
                new  Thread(new HttpController(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

