package conexion;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;






public class ControlFile {

	private static final int BUFFER = 4096;
	private String _zipFile;


	public File compressFiles(String fileDirectory) {
		File file = null;
		String[] _files = { fileDirectory+"/Master.db",
				fileDirectory+"/ArcData.dbs", fileDirectory+"/ArcParam.dbs" };
		_zipFile = fileDirectory+".zip";
		byte[] buffer = new byte[BUFFER];

		try {
			FileOutputStream fout = new FileOutputStream(_zipFile);
			ZipOutputStream zout = new ZipOutputStream(fout);

			for (int i = 0; i < _files.length; i++) {
				FileInputStream fin = new FileInputStream(_files[i]);
				zout.putNextEntry(new ZipEntry(_files[i]));
				int length;
				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}

				zout.closeEntry();
				fin.close();
			}
			zout.close();
			file = new File(_zipFile);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return file;
	}

	public File createPatient(String nombre){
		File resultado = null;
		String file =nombre;
		File patientDirectory = new File("/sdcard/"+file);
		patientDirectory.mkdir();
		File externalDirectory = new File("/sdcard/external_sd/BlackBerry/jeanmarierivera");
		try {
			copyDirectory(externalDirectory,patientDirectory);
			resultado =compressFiles(file);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		return resultado;
	}
	
	public void copyDirectory(File sourceLocation , File targetLocation) throws IOException {
		                
	       if (sourceLocation.isDirectory()) {	            
	            String[] children = sourceLocation.list();
	            for (int i=0; i<children.length; i++) {
	                copyDirectory(new File(sourceLocation, children[i]),
	                        new File(targetLocation, children[i]));
	            }
	       }else {
		            
		            InputStream in = new FileInputStream(sourceLocation);
		            OutputStream out = new FileOutputStream(targetLocation);
		            
		            // Copy the bits from instream to outstream
		            byte[] buf = new byte[1024];
		            int len;
		            while ((len = in.read(buf)) > 0) {
		                out.write(buf, 0, len);
		            }
		            in.close();
		            out.close();
		        }
	}
}
