//paquetes raiz del la Clase ClientUDP.java
package ec.edu.epn.redes.mensaje.udp;

//A continuación se ven los paquetes extras para la realización del proyecto
/*BufferedReader es una clase que permite hacer lecturas sencillas de 
texto desde un flujo de caracteres*/
import java.io.BufferedReader;

/*ByteArrayInputStream permite un buffer en la memoria
para ser utilizado como un InputStream.*/
import java.io.ByteArrayInputStream;

/*clase para control de errores*/
import java.io.IOException;

/*La clase InputStream permite la lectura de bytes*/
import java.io.InputStream;

/*InputStreamReader es una clase que tiene métodos para leer caracteres*/
import java.io.InputStreamReader;

/*DatagramPacket proporciona un constructor que permite crear instancias de un array de bytes
DatagramSocket da soporte a sockets para el envío y recepción de datagramas UDP.

Las dos clases están ligadas en lo que tiene que ver el protocolo UDP, mientras la una  ayuda a
contruir el paquetes de bytes separandolos en mensaje longitud, etc, DatagramSocket ayuda en el
envío recepción del paquete
*/
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/*InetAddress permite crear objetos que permiten manipular direcciones IP y nombres de dominio*/
import java.net.InetAddress;

/*Clase para mostrar mensajes en pantalla, interfaz */
import javax.swing.JOptionPane;

/**
 * Inicio Clase ClientUDP
 */
public class ClientUDP {

	private static int SERVER_PORT = 9091;
	
	/**
     * Runs the client as an application.  First it displays a dialog
     * box asking for the IP address or hostname of a host running
     * the date server, then connects to it and displays the data that
     * the server send.
     */
	
	public static void main(String[] args) throws IOException {
        
		String serverAddress = JOptionPane.showInputDialog("Enter IP Address of a machine that is\n" +
            "running the date service on port "+SERVER_PORT+":");
       
        //Send packet (Request)
        DatagramSocket clientSocket = new DatagramSocket();
        byte bufferSend[] = serverAddress.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(bufferSend,bufferSend.length,InetAddress.getByName(serverAddress),SERVER_PORT);
        clientSocket.send(sendPacket);
        
        //Receive packet
        byte bufferReceive[] = new byte[128];
        DatagramPacket receivePacket = new DatagramPacket(bufferReceive, bufferReceive.length);
        clientSocket.receive(receivePacket);
        
        //Transforma bytes a String
        InputStream myInputStream = new ByteArrayInputStream(receivePacket.getData()); 
        BufferedReader input = new BufferedReader(new InputStreamReader(myInputStream));
        String answer = input.readLine();
    
        //Despliega mensaje
        JOptionPane.showMessageDialog(null, answer);
        clientSocket.close();
        System.exit(0);
    }
	
}
