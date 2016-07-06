/**
 * @author shui
 */
package migration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MigrationDuProjet {
	
	private ArrayList<File> listeFichier;
	private String locate;
	
	public MigrationDuProjet(ArrayList<File> lf, String lo){
		listeFichier = lf;
		locate = lo;
	}
	
	/**
	 * la migration du projet(s)
	 * @throws IOException
	 */
	public void migrationHILECOP() throws IOException{
		for(int i=0;i<listeFichier.size();i++){
			if(listeFichier.get(i).getName().endsWith(".hilecopcomponent")){
				String path = locate + "\\"+listeFichier.get(i).getParentFile().getName();
				File f1=new File(path);
				f1.mkdir();
				String fichierpath = listeFichier.get(i).getAbsolutePath();
				migrationComposant(fichierpath, path);
			}
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
}
