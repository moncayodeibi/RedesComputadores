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
     Creamos una variable donde definimos el puerto 9091 para ocuparlo en 
     nuestra transmisión como puerto de escucha
     */
	
	/*incio de la clase main*/
	public static void main(String[] args) throws IOException {
        
	/*declaramos al variable serverAddres tipo string qu epor medio de un JOPtionPane de ingreso 
	nos va a permitir ingresar una dirección ip y del servidor, que en este momento ya debe estar corriendo
	y debe estar escuchando en el puerto 9091
	*/
		
	String serverAddress = JOptionPane.showInputDialog("Enter IP Address of a machine that is\n" +
            "running the date service on port "+SERVER_PORT+":");
       
        //Send packet (Request)
	/*Inatanciamos la clase DatagramSocket que como dijimos antes servia para manipular las IP, 
	el objeto es clientSocket
	*/	
        DatagramSocket clientSocket = new DatagramSocket();
		
	/*creamos una array byfferSend con los digitos que ingresamos en el JOPtioPane anterior y los convertimos en bytes*/	
        byte bufferSend[] = serverAddress.getBytes();
	
	/*Instanciamos la clase DatagramPacket con parámetros, para que pueda transformar los datos ingresados en 
	en un paquete de envío, necesitando el arreglo de bytes bufferSend, su longitud, el nombre por la clase InetAddress de
	la dirección ip que mandamos, y el puerto de escucha
	*/
        DatagramPacket sendPacket = new DatagramPacket(bufferSend,bufferSend.length,InetAddress.getByName(serverAddress),SERVER_PORT);
        /*Utilizamos el metodo send del objeto instanciado de la clase clientSocket y mandamos sendPacket, que tiene los datos 
	de la ip del servidor que digitamos
	*/
	clientSocket.send(sendPacket);
        
        //Receive packet
	//creamos un buffer de llegada de bytes con 128 posiciones para almacenar lo que ingrese	
        byte bufferReceive[] = new byte[128];
	/*Instanciamos de igual manera otro objeto de Datagram Paquet 
	que no sirva de buffer de llegada, con parámetros el arrar creaado y su longitud*/	
        DatagramPacket receivePacket = new DatagramPacket(bufferReceive, bufferReceive.length);
        /*para luego con el objeto clienteSocket de DatagramSocket utiliza el método de recibir */
	clientSocket.receive(receivePacket);
        
        //Transforma bytes a String
	
	/*Por medio de la instanciaciión de la clase InputStream vamos a crear un objeto que nos va a 
	ayudar a leer los bytes de llegada o enviados por el servidor como parametros se extrae
	por medio del método getData de los bytes del mensaje enviado
	*/
        InputStream myInputStream = new ByteArrayInputStream(receivePacket.getData()); 
	// por medio del siguiente objeto de la clase BufferReader vamos a leer las líneas 
	//de mensaje que hayan sido enviadas por el server	
        BufferedReader input = new BufferedReader(new InputStreamReader(myInputStream));
        //se crea un String que nos va a ayudar a colorar en esta variable los que el objeto input obtenga al leer la línea
	String answer = input.readLine();
    
        //En esta parte solo Desplegamos el mensaje por medio de la clase JOptionPane
        JOptionPane.showMessageDialog(null, answer);
	/*Cerramos el socket del liente*/	
        clientSocket.close();
	/*Cerramos el sistema*/	
        System.exit(0);
    }
	
}
