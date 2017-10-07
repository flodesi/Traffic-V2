/*
 * 
 */
package server.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import static server.FrontEnd.lightSet;


public class Utils {
        
    public static int adjustments() throws Exception{
        String clientSentence;
	ServerSocket welcomeSocket = new ServerSocket(140);
        int i = 0;
        int client;

	while (true) {
            while(i<1000){
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
             new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            clientSentence = inFromClient.readLine();
            client = Integer.parseInt(clientSentence);
            lightSet = Integer.parseInt(clientSentence);
            welcomeSocket.close();
            System.out.println(lightSet);
            i++;
            return client;
            }
	}
    }
}
