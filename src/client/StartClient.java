package client;

/**
 * Title:        StartClient class
 * Description:  Tests the Client class
 *
 * @author Sam Beed B0632953
 */

import clientGUI.*;
import java.io.*;
import java.util.*;

public class StartClient
{
   public static void main(String[] args) throws IOException
   {
      
      Client client1 = new Client();
      ClientGUI gui = new ClientGUI("CAVE", client1);
      client1.start();
       
       

   }
}
