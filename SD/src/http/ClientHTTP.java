/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ClientHTTP {
    public static void main(String[] args) throws IOException {
        String host = "www.ece.rice.edu";
        String file = "/~wakin/images/lena512.bmp";
        
        Socket socket = new Socket(host,80);
        
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        
        String crlf = "\r\n";
        String msg = "Get "+file+" HTTP/1.1"+crlf+
                     "Host: "+host+crlf+crlf;
        byte[] buffer = new byte[256];
        out.write(msg.getBytes());
        
        
        FileOutputStream image = new FileOutputStream("lena.bmp");
        
        
        while(in.read(buffer) == buffer.length){
            image.write(buffer);
            System.out.print(new String(buffer));
        }
        image.close();
        socket.close();
        
    }
    
}
