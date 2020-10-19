import java.io.*;
import java.net.*;
import java.util.*;

public class Client
{
    private static Socket socket;
    //private static DataInputStream in;
    private static BufferedReader in;
    //private static DataOutputStream out;
    private static PrintWriter out;
    private static String stdNumber = null;
    private static URL url;
    public static void send(String METHOD) throws  ConnectException,IOException,UnknownHostException {
        socket = new Socket(url.getHost(), 80);

        boolean autoflush = true;
        out = new PrintWriter(socket.getOutputStream(), autoflush);
        String path = "/";
        if( url.getPath() != "") {
            path = url.getPath();
        }
        out.println(METHOD + " " + path + " HTTP/1.0");
        System.out.println(METHOD + " " + path + " HTTP/1.0");
       	 
	out.println("Host: " + url.getHost());
        out.println("Connection: Close");
        if( stdNumber != null ) {
            out.println("x-student-id: " + stdNumber);
        }
        out.println();

    }

    public static void response() throws IOException {
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        boolean loop = true;
        StringBuilder sb = new StringBuilder(8096);
        while (loop) {
            
	if (in.ready()) {
                int i = 0;
                while (i != -1) {
                    i = in.read();
                    sb.append((char) i);
                }
                loop = false;
            }
        }
	String s = sb.toString();
	System.out.println("__________________________________________");	
	System.out.println(s);	
	System.out.println("__________________________________________");	
       	String errorCode = s.substring(s.indexOf(" ") + 1 ,s.indexOf(" ") + 4);
		
        if(errorCode.equals("200") && s.contains("Content-Type: text/plain")){
		//System.out.println(s.substring(s.indexOf("\r\n" ,s.indexOf("Current-Length"))+1));		
		System.out.println(s.substring(s.indexOf("\r\n\r\n") + 4));	
	}

        else if(errorCode.equals("200") && s.contains("Content-Type: text/html")){
		File file = new File("index.html");
     		PrintWriter printWriter = new PrintWriter(file);
      		printWriter.println(s.substring(s.indexOf("\r\n\r\n") + 4));
      		printWriter.close();			
	}
	else if(errorCode.equals("200") && s.contains("Content-Type: application/json")){
		File file = new File("index.json");
      		PrintWriter printWriter = new PrintWriter(file);
      		printWriter.println(s.substring(s.indexOf("\r\n\r\n") + 4));
      		printWriter.close();			
	}
	else{
		System.out.println(s.substring(s.indexOf(" ") + 1 , s.indexOf("\r\n")));					
	} 
  	 
	socket.close();
    }

    public static boolean isUrlValid(String input_url) {
       		 
	  try {
            url = new URL(input_url);
	    return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a command or a URL: ");
        String command_or_url = sc.next();
        while( !command_or_url.equals("exit") ) {
            if( command_or_url.equals("set-student-id-header") ) {
                System.out.print("Enter student id: ");
                stdNumber = sc.next();
            }
            else if( command_or_url.equals("remove-student-id-header") ) {
                stdNumber = null;
		System.out.println("student id removed");
            }
            else if( isUrlValid(command_or_url) ) {
                // retrieve the hostname of the url

                System.out.print("Enter METHOD: ");
                String METHOD = sc.next().trim().toUpperCase();

                while( !METHOD.equals("GET") && !METHOD.equals("POST") && !METHOD.equals("PUT") && !METHOD.equals("PATCH") && !METHOD.equals("DELETE") ) {
                    System.out.print("Enter METHOD: ");
                    METHOD = sc.next().trim().toUpperCase();
                }

                send(METHOD);
		System.out.println("http request Sended");
                response();
            }
            else {
                System.out.println("invalid URL or command");
            }
            System.out.print("Enter a command or a URL: ");
            command_or_url = sc.next();
        }




    }



}
