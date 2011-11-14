package servicios;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import conexion.ControlFile;


public class ServiciosArchivo {

	public int enviarArchivo(String filePath, int cedula) {
		int resultado = 0;
		ControlFile control = new ControlFile();
		File file = control.compressFiles(filePath);
		Socket socket = null;
		try {
			socket = new Socket("10.0.2.5", 9095);
			DataOutputStream output = new DataOutputStream(
					socket.getOutputStream());
			output.writeInt(cedula);
		 	output.writeInt((int) file.length());
		 	
		 	byte[] mybytearray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, mybytearray.length);
			OutputStream os = socket.getOutputStream();
			os.write(mybytearray, 0, mybytearray.length);
			os.flush();		
			resultado=receiveDiagnose(socket);
			socket.close();
		} catch (UnknownHostException e) {
			resultado = 0;
			//System.out.println(e);
		} catch (IOException e) {
			resultado = 0;
			// TODO Auto-generated catch block
			//System.out.println(e);
		}
	
		return resultado;
	}
	
	public int receiveDiagnose(Socket socket) throws IOException {
		DataInputStream	input = new DataInputStream(socket.getInputStream());
			int size = input.readInt();
			
		return size;
	}
}
