package projet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFile {

	public static void zip(String basePath, File dir, ZipOutputStream ZipOs) throws IOException {
		// TODO Auto-generated method stub
		//config(dir+"/Configurations2");
		byte[] data = new byte[1024];
		File[] files = dir.listFiles();
		for (File file : files) {
			if(file.isDirectory()) {
				String path = basePath + file.getName() + "/";
				ZipOs.putNextEntry(new ZipEntry(path));
				zip(path, file, ZipOs);
				ZipOs.closeEntry();
			}else {
				FileInputStream fin = new FileInputStream(file);
				ZipOs.putNextEntry(new ZipEntry(basePath + file.getName()));
				int taille;
				while((taille= fin.read(data))>0) {
					ZipOs.write(data, 0, taille);
				}
				ZipOs.closeEntry();
				fin.close();
				//config(dir+"/Configurations2");
			}
		}
	}
	public static void config(String destDir) {
		File conf=new File(destDir);
		
        if (conf.exists()&& conf.isDirectory()) {
            File[] list = conf.listFiles();
            	
            if (list != null) {
                for (int i = 0; i <list.length; i++) {
                	if(!list[i].getName().equals("accelerator")) {
                		 if(!list[i].isDirectory()) {
                			list[i].delete();
                			list[i].mkdir();
                			
                		}
                	config(list[i].getAbsolutePath());
                	                                	 
                	}
                }
            }
        }
		
	}
}
