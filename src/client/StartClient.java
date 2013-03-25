package client;

/**
 * Title:        StartClient class
 * Description:  Tests the Client class

 * @author Sam Beed B0632953
 */

import clientGUI.*;

public class StartClient
{
   public static void main(String[] args)
   {
      ClientGUI gui = new ClientGUI("CAVE");
      Client client1 = new Client();
      client1.run();
   }
}
