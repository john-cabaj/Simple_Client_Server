//importing useful classes
import java.net.*;
import java.io.*;

//server class
public class Server
{

	/*main method takes TCP/UDP arguments via command line
	 * port number is args[0]
	 * protocol is args[1]
	 */
	public static void main(String[] args)
	{
		//get port number from args
		int portNumber = Integer.parseInt(args[0]);

		//get name of protocol and convert to lowercase
		String protocol = args[1];
		protocol = protocol.toUpperCase();

		//using TCP
		if(protocol.equals("TCP"))
		{
			//display which protocol is being used
			System.out.println("Using TCP protocol.");

			//try-catch for exceptions
			try
			{
				//instantiate server socket at port number
				ServerSocket serverSocket = new ServerSocket(portNumber);

				//print out waiting for client connection status and wait to accept client connection
				System.out.println("Server socket created. Waiting for client connection....");
				Socket clientSocket = serverSocket.accept();

				//print client connected status
				System.out.println("Client connected!");

				//instantiate printwriter and bufferedreader for output, input respectively
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
			    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			    //output connection status to client
			    output.println("You're connected to the server!\n");

			    //instantiate input line
			    String inputLine = null;
			    
			    //instantiate input character and character counts
				char inputChar;
				int aCount = 0, bCount = 0, cCount = 0;

				//infinite while loop
				while (true)
				{
					//receive input line and convert to lowercase
					inputLine = input.readLine();
					inputLine = inputLine.toLowerCase();
					
					//client exit, wait for new client
					if(inputLine.equals("exit"))
					{
						//print out waiting for client connection status and wait to accept client connection
						System.out.println("Client closed. Waiting for new client connection....");
						clientSocket = serverSocket.accept();
						
						//print client connected status
						System.out.println("New client connected!");

						//re-instantiate printwriter and bufferedreader for output, input respectively
						output = new PrintWriter(clientSocket.getOutputStream(), true);
					    input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					    
					    //output connection status to client
					    output.println("You're connected to the server!\n");
					}
					//client didn't exit
					else
					{
						//get character and convert to lowercase
						inputChar = inputLine.charAt(0);
						inputChar = Character.toLowerCase(inputChar);

						/*switch statement on the input character
						 * if a, print number of a's,
						 * if b, print number of b's,
						 * if c, print number of c's
						 */
						switch(inputChar)
						{
							case 'a': aCount++;
									  System.out.println("a: " + aCount);
									  break;
							case 'b': bCount++;
							          System.out.println("b: " + bCount);
							  		  break;
							case 'c': cCount++;
							          System.out.println("c: " + cCount);
									  break;
						}
					}
				}
			}
			//catch ioexceptions and print a statement so we know there's a problem
			catch(IOException ioe)
			{
				System.out.println("I/O exception");
			}
			//catch nullpointerexception if client closes and display message
			catch(NullPointerException npe)
			{
				System.out.println("Client must have closed.");
			}
		}

		//using UDP
		else if(protocol.equals("UDP"))
		{
			//display which protocol is being used
			System.out.println("Using UDP protocol.");

			//instantiate receive buffer
			byte[] receiveData = new byte[1024];

			//instantiate input character and character counts
			char inputChar;
			int aCount = 0, bCount = 0, cCount = 0;
			
		    //instantiate input line
		    String inputLine = null;

			//try-catch for exceptions
			try
			{
				//instantiate datagramsocket for server and datagram packet
				DatagramSocket serverSocket = new DatagramSocket(portNumber);
				DatagramPacket packetReceived = new DatagramPacket(receiveData, receiveData.length);

				//infinite while loop
				while(true)
				{
					//receive packet from client
					serverSocket.receive(packetReceived);

					//get input line from client
					inputLine = new String(packetReceived.getData());
					
					//get character and convert to lowercase
					inputChar = inputLine.charAt(0);
					inputChar = Character.toLowerCase(inputChar);
	
					/*switch statement on the input character
					 * if a, print number of a's,
					 * if b, print number of b's,
					 * if c, print number of c's
					 */
					switch(inputChar)
					{
						case 'a': aCount++;
								  System.out.println("a: " + aCount);
								  break;
						case 'b': bCount++;
						          System.out.println("b: " + bCount);
						  		  break;
						case 'c': cCount++;
						          System.out.println("c: " + cCount);
								  break;
					}
				}
			}
			//catch socketexceptions and print a statement so we know there's a problem
			catch(SocketException se)
			{
				System.out.println("Socket exception");
			}
			//catch ioexceptions and print a statement so we know there's a problem
			catch(IOException ioe)
			{
				System.out.println("I/O exception");
			}
		}

	}

}
