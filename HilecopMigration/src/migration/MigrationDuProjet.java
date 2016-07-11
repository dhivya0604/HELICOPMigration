/**
 * @author shui
 */
package migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MigrationDuProjet {

	private String locate;

	public MigrationDuProjet(String lo){
		locate = lo;
	}

	/**
	 * la migration du projet(s)
	 * @param liste_hilecopcomponent 
	 * @param liste_vhd 
	 * @throws IOException
	 */
	public void migrationHILECOP(ArrayList<File> liste_vhd, ArrayList<File> liste_hilecopcomponent) throws IOException{
		for(int i=0;i<liste_hilecopcomponent.size();i++){
			String path = locate + "\\"+liste_hilecopcomponent.get(i).getParentFile().getName();
			File f1=new File(path);
			f1.mkdir();
			String fichierpath = liste_hilecopcomponent.get(i).getAbsolutePath();
			migrationComposant(fichierpath, path);
		}
		for(int i=0;i<liste_vhd.size();i++){
			String path = locate + "\\"+liste_vhd.get(i).getParentFile().getName();
			String name = liste_vhd.get(i).getName();
			File f1=new File(path);
			f1.mkdir();
			String fichierpath = liste_vhd.get(i).getAbsolutePath();
			migrationVHD(fichierpath, path,name);
		}
	}

	private void migrationComposant(String path1, String path2) throws IOException{
		AncienComposant ancien = new AncienComposant(path1);
		String name = ancien.getRoot().getDesignFileName();

		NouveauComposant nouveau = new NouveauComposant(path2, name);
		MigrationDuComposant migtool = new MigrationDuComposant(nouveau,ancien);
		migtool.migeration();
		nouveau.save();
	}

	private void migrationVHD(String path1, String path2, String name) throws IOException{
		@SuppressWarnings("resource")
		InputStream is = new FileInputStream (path1);
		OutputStream os=null;
		if(name.startsWith(".")){
			name = name.substring(1);
		}
		String filename = path2 + "\\" + name.replaceAll(".vhd", ".script_vhd");
		File file = new File(filename);
		file.createNewFile();
		try{
			os=new FileOutputStream(file);
			byte buffer[]=new byte[4*1024];


			int len = 0;
			while((len = is.read(buffer)) != -1){ 
				os.write(buffer,0,len);
			}

			os.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				os.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
/*
	private void migrationInterface(){
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(root.RootPackage.eNS_URI,root.RootPackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("root",new XMIResourceFactoryImpl());
		String name = "0";
		String filename = path2 + "\\" + name + ".script_vhd";
		File file = new File(filename);
		file.createNewFile();
		URI newURI = URI.createFileURI(filename);
		Resource newres = resourceSet.createResource(newURI);

	//	Diagram 
	}
 */
}
