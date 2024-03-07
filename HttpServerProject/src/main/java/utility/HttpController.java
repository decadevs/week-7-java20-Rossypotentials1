package utility;

import configuration.HttpServer;

import java.io.*;
import java.net.Socket;

import static configuration.HttpServer.HTML_PATH;
import static configuration.HttpServer.JSON_PATH;

public class HttpController implements Runnable{
    private Socket clientSocket;

    public HttpController(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)){
             String request = input.readLine();

             if(request != null){
                 String [] ipAddressParts = request.split("\\s+");
                 if (ipAddressParts.length >= 2 && ipAddressParts[0].equals("GET")){
                     String filePath = ipAddressParts[1];
                     if (filePath.equals("/")  || filePath.equals("file.html")){
                         htmlResponse(output);
                     } else if (filePath.equals("/json")) {
                         jsonResponse(output);
                     }else {
                         pageNotFoundResponse(output);
                     }

                 }
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void htmlResponse(PrintWriter output) {
        File file = new File(HTML_PATH);
            if(file.exists()){
                sendMessage(output,file, "text/html");
            }else {
                pageNotFoundResponse(output);
            }
    }

    private void jsonResponse(PrintWriter output) {

            File file = new File(JSON_PATH);
            if(file.exists()){
                sendMessage(output,file, "application/json");
            }else {
                pageNotFoundResponse(output);
            }
        }

    private void sendMessage(PrintWriter output, File file, String contentType) {
        output.println("HTTP/1.1 200 OK");
        output.println("Type of Content " + contentType);
        output.println();


        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                output.println(line);
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pageNotFoundResponse(PrintWriter output) {
        output.println("HTTP/1.1 404 NOT FOUND");
        output.println("Content type: text/plain" );
        output.println();
        output.println("404 Not Found - The Requested resource was not found on this server");

    }


}

