import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class Client
{
    private static Socket socket;
    private static DataInputStream input;
    private static DataOutputStream out;
    private static String stdNumber = null;
    private static URL url;
    public void send(String methodName){
       	socket = Socket(url.getHost(), 80);
	out = new DataOutputStream(socket.getOutputStream()); 	
	String query = methodName + " " + url.getPath() + " HTTP/1.0/ \r\n" + "HOST: " + url.getHost() +"\r\n";
       	if(stdNumber != null){
		query += ("x-student-id: " + stdNumber +"\r\n");	
	} 
	query += "\r\n";
	out.writeUTF(query);
    }
     
    public void response() {
	in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
	String line;
	line = in.readUTF();
	String[] parts = line.split(" "):
	System.out.println(parts[1] + " " + parts[2]);			
	if (parts[1] == "200"){
					
		
	}	

	
	
	}
    }

    public static boolean isUrlValid(String url) {
        try {
            URL obj = new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static void main(String args[]) throws MalformedURLException {
        Scanner sc = new Scanner(System.in);

        String command_or_url = "";
        while( command_or_url != "exit" ) {
        	   
		if( isUrlValid(command_or_url) ) {
                // create a URL object with user input
                url = new URL(command_or_url);

                // retrieve the hostname of the url
                System.out.println("Hostname:- " + url.getHost());

                // retrive the path of URL
                System.out.println("Path:- " + url.getPath());

//              // retrive the file name
//              System.out.println("File:- " + url.getFile());

                // get METHOD from user input
                System.out.println("Enter METHOD");
                String METHOD = sc.next().trim().toUpperCase();
                while( METHOD != "GET" && METHOD != "POST" && METHOD != "PUT" && METHOD != "PATCH" && METHOD != "DELETE" ) {
                    System.out.println("Enter METHOD");
                    METHOD = sc.next().trim().toUpperCase();
                }

            }
            else if( command_or_url == "set-student-id-header" ) {

            }
            else if( command_or_url == "remove-student-id-header") {

            }
            else {
                System.out.println("invalid URL or command");
            }
            command_or_url = sc.next();
        }




    }



}
