package client;

/**
 * Title:        Client class
 * Description:  Client that connects to a server, receives and displays a
 *               message and then terminates
 * 
 * This class does not have any output streams set up yet, because
 * at the moment it only reads information from the server.
 *
 * @author Sam Beed B0632953
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends Thread
{
    //Streams used for communicating with server
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream objIS;
    private PrintWriter toServer;
    private BufferedReader fromServer;
            
    private Socket socket;    // Socket to server
    private static final int SERVER_PORT_NUMBER = 3000;
    private boolean connected;
    private boolean keepRunning;
    
    private HashMap<String, HashMap<String, Integer>> questions;

    /**
     * Constructor
     */
    public Client() {
        connected = false;
        keepRunning = true;
    }
    
    /**
     * This is the client's main method - it performs a single
     * interaction with the server using the processHello method
     */
    @Override
    public void run()
    {        
        try {
            while (keepRunning) {
                
            }
        
            quit();
        }
        catch (IOException e) {
            System.out.println("Exception in client run " + e);
        }
    }

    /*
     * This method creates a socket on the local host to
     * the specified port number, for communications with the server
     */
    public boolean connectToServer() {
        try {
            //this is a portable way of getting the local host address
            final InetAddress SERVER_ADDRESS = InetAddress.getLocalHost();
            System.out.println("Attempting to contact " + SERVER_ADDRESS);

            socket = new Socket(SERVER_ADDRESS, SERVER_PORT_NUMBER);
            openStreams();
            connected = true;
            return true;
        }
        catch (IOException e) {
            String ls = System.getProperty("line.separator");
            System.out.println(ls + "Trouble contacting the server: " + e);
            System.out.println("Perhaps you need to start the server?");
            System.out.println("Make sure they're talking on the same port?" + ls);
            return false;
        }
    }
    

    /**
     * Open streams for communicating with the server.
     * @throws IOException 
     */
    private void openStreams() throws IOException
    {
        final boolean AUTO_FLUSH = true;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        objIS = new ObjectInputStream(is);
        toServer = new PrintWriter(os, AUTO_FLUSH);
        fromServer = new BufferedReader(new InputStreamReader(is));
        
        System.out.println("...Streams opened");
    }

    /**
     * Close streams to server.
     * @throws IOException 
     */
    private void closeStreams() throws IOException
    {
        toServer.close();
        fromServer.close();
        objIS.close();
        os.close();
        is.close();
        
        System.out.println("...Streams closed down");
    }

    /**
     * Sends a message to the server.
     * @param request 
     */
    public void sendToServer(String request) throws IOException {
        toServer.println(request);
        if (request.equalsIgnoreCase("GET_VOTES")) {
            getVotes();
        }
        else {
            processServerMessage();
        }
    }
    
    /**
     * Processes messages received from the server.
     * @throws IOException 
     */
    private void processServerMessage() throws IOException
    {
        String messageFromServer = fromServer.readLine();
        System.out.println("Server said: " + messageFromServer); //display message
    }
    
    /**
     * 
     */
    private void getVotes() {
        try {
            questions = (HashMap<String, HashMap<String, Integer>>) objIS.readObject();
        }
        catch (IOException ex) {
            System.out.println("IO Exception : " + ex.getMessage());
        }
        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFound Exception : " + ex.getMessage());
        }
    }
    
    /**
     * 
     */
    public Set<String> getQuestions() {
        return questions.keySet();
    }
    
    /**
     * 
     * @param question
     * @return 
     */
    public Map<String, Integer> getAnswers(String question) {
       return questions.get(question); 
    }
    
    /**
     * Returns true if a connection is currently up.
     * @return Connection status
     */
    public boolean isConnected() {
        return connected;
    }
    
    /**
     * Requests the questions.
     */
    public void requestQuestions() {
        
    }

    /**
     * Ends the program.
     */
    public void quit() throws IOException {
        toServer.println("QUIT");
        closeStreams();
        socket.close();
        System.exit(0);
    }
}