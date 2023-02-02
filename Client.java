//importing useful classes
import java.net.*;
import java.io.*;
import java.util.Scanner;

//client class
public class Client
{
	/*main method takes TCP/UDP arguments via command line
	 * host number is args[0]
	 * port number is args[1]
	 * protocol is args[2]
	 */
	public static void main(String[] args)
	{
		//get hostname/ip address, port number, and protocol from args
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);
		String protocol = args[2];
		protocol = protocol.toUpperCase();

	    //instantiate scanner
	    Scanner readKey = new Scanner(System.in);

	    //using TCP
		if(protocol.equals("TCP"))
		{
			//try-catch for exceptions
			try
			{
				//create client socket at hostname/ip address and port number
				Socket clientSocket = new Socket(hostName, portNumber);

				//instantiate printwriter and bufferedreader for output, input respectively
			    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
			    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			    //instantiate input line and output character
			    String inputLine = null;
			    char outputChar;

			    //receive and display connected status from server
			    inputLine = input.readLine();
			    System.out.println(inputLine);
			    
			    //infinite while loop
			    while(true)
			    {
			    	//prompt for key entry
			    	System.out.print("Enter a key (a, b, or c) or type 'exit' to exit: ");
			    	
			    	//read the input from the keyboard
			    	inputLine = readKey.nextLine();
			    	
			    	//if user typed exit
			    	if(inputLine.equals("exit"))
			    	{
			    		//signal the server
			    		output.println("exit");
			    		
			    		//close all resources
			    		readKey.close();
			    		output.close();
			    		input.close();
			    		clientSocket.close();
			    		
			    		//exit the program
			    		System.exit(0);
			    	}
			    	//user didn't type exit
			    	else
			    	{
			    		//if user entered only a character
			    		if(inputLine.length() == 1)
			    		{
			    			//output the character
					    	outputChar = inputLine.charAt(0);
					    	output.println(outputChar);
			    		}
			    	}
			    }
			}
			//catch ioexceptions and print a statement so we know there's a problem
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
		}
		//using UDP
		else if(protocol.equals("UDP"))
		{
			//instantiate input line
			String inputLine = null;

			//try-catch for exceptions
			try
			{
				//instantiate the datagramsocket for client
				DatagramSocket clientSocket = new DatagramSocket();

				//instantiate output character buffer
				byte[] outputChar = new byte[1024];

				//infinite while loop
				while(true)
				{
			    	//prompt for key entry
			    	System.out.print("Enter a key (a, b, or c) or type 'exit' to exit: ");
			    	
					//get the input line
					inputLine = readKey.nextLine();

		    		//if user entered only a character
					if(inputLine.length() == 1)
					{
						//convert input line to bytes
						outputChar = inputLine.getBytes();
						
						//get the inetaddress
						InetAddress address = InetAddress.getByName(hostName);
						
						//create a datagram packet with input bytes, inetaddress, and port number
						DatagramPacket packet = new DatagramPacket(outputChar, outputChar.length, address, portNumber);

						//send the packet
						clientSocket.send(packet);
					}
					
					//if user typed exit, close the program
			    	if(inputLine.equals("exit"))
			    		System.exit(0);
				}
			}
			//catch socketexceptions and print a statement so we know there's a problem
			catch(SocketException se)
			{
				System.out.println("Socket exception");
			}
			//catch unknownhostexceptions and print a statement so we know there's a problem
			catch(UnknownHostException uhe)
			{
				System.out.println("Unknown host exception");
			}
			//catch ioexceptions and print a statement so we know there's a problem
			catch(IOException ioe)
			{
				System.out.println("I/O exception");
			}
		}
	}

}
