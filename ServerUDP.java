/*Paquete de almacenamiento de la clase ServerUDP*/
package ec.edu.epn.redes.mensaje.udp;
/*Clase importada para el manejo de errores*/
import java.io.IOException;


/* Al igual que se describe en el cliente.
DatagramPacket proporciona un constructor que permite crear instancias de un array de bytes
DatagramSocket da soporte a sockets para el envío y recepción de datagramas UDP.
Las dos clases están ligadas en lo que tiene que ver el protocolo UDP, mientras la una  ayuda a
contruir el paquetes de bytes separandolos en mensaje longitud, etc, DatagramSocket ayuda en el
envío recepción del paquete
*/
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
Definción de la clase ServerUDP
 */
public class ServerUDP {
	
	// Definición  del puerto 9091
	private static int PORT = 9091;
	
	/**
     * Clase apliación para ejecutar en el servidor
     */
    public static void main(String[] args) throws IOException {
    
	//Instanciación de la clase DatagramSocket para el envío del paquete    
    	DatagramSocket serverSocket = new DatagramSocket(PORT);
	//Mensaje de advertencia donde el puerto estra en uso
    	System.err.println("Server listening on port " + PORT + " using UDP connection\n");
    	//creamos la variable de tiempo inicial donde muestra el tiempo en milisegndos
	long initialTime = System.currentTimeMillis();
	//imprimimos el tiempo inicial en initialTime
	 System.out.println("Tiempo Inicial: "+initialTime+"\n");
    	 
	//empezamos a tratar contaatarnos con el cliente, en el lazo hasta que
	//logre conectarse
    	try {
            while (true) {
            	//Receive packet
		    /*Creamos un arrglo de 128 bytes de un buffer de llegada*/
            	byte bufferReceive[] = new byte[128];
	            
		    /*Instanciamos la clase DatagramPacket con parámetros, para que pueda leer los datos ingresados en 
			en un paquete, necesitando el arreglo de bytes bufferReceive y su longitud.
			*/
		    DatagramPacket receivePacket = new DatagramPacket(bufferReceive, bufferReceive.length);
	            /*dato el serversocket, por medio del método receive ingresa el objeto*/
		    serverSocket.receive(receivePacket);          
		    
		    //creamos la instacia del cliente para poder acceder y manipular la dirección IP.
	            InetAddress clientAdress = receivePacket.getAddress();
		    /*creamos la variablde puerto del cliente, por medio de extraer del paquete enviadoextraer el puerto*/
	            int clientPort = receivePacket.getPort();
		    //imprimos en el puerto del cliente
	            System.out.println("Client port: "+clientPort+"\n");
	            
	            //Send packet
		    //definimos el mensaje del cliente lo que vamos a enviar al cliente
		    String msg = "Message from Server";
		    //trasnformamos en bytes el mensaje
	            byte bufferSend[] = msg.getBytes();
		    //instanciamos  DatagramPacket y como parámetros los datos del mensaje ya covertido en bytes para ser enviado al lcliente
	            DatagramPacket sendPacket = new DatagramPacket(bufferSend,bufferSend.length,clientAdress,clientPort);
		    //enviamos el paquete 
		    serverSocket.send(sendPacket);
	            /*long endTime = System.currentTimeMillis();
	            System.out.println("Tiempo Final: "+endTime+"\n");
	            long sendTime = endTime-initialTime;
	            System.out.println("Envia el paquete en: "+sendTime+" ms\n");
		    serverSocket.send(sendPacket);*/	            
            }
        }
        finally {
        	//cerramos la aplicación
		serverSocket.close();
        }
    	
    }
    
}
