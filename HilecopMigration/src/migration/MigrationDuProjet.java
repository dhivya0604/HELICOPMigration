/**
 * @author shui
 */
package migration;

import java.io.IOException;
import java.util.ArrayList;

import hilecopComponent.HilecopComponentDesignFile;

public class MigrationDuProjet {
	
	/*
	 * necessaire?
	 */
	/**
	 * faire la migration de l'ancien projet vers le nouveau
	 * @param ancienfichiers : list de fichiers .hilecopcomponent de l'ancien projet
	 * @param locate : chemin du nouveau projet
	 * @throws IOException
	 */
	public void migrationHILECOP(ArrayList<String> ancienfichiers, String locate) throws IOException{
		int nb = ancienfichiers.size();
		for(int i=0;i<nb;i++){
			String path = ancienfichiers.get(i);
			migrationComposant(path,locate);
		}
	}
	
	/**
	 * la migration pour chaque composant
	 * @param path1
	 * @param path2
	 * @throws IOException
	 */
	public void migrationComposant(String path1, String path2) throws IOException{
		ProjetAncien ancien = new ProjetAncien();
		HilecopComponentDesignFile ancienroot = ancien.read(path1);
		
		String name = ancienroot.getDesignFileName();

		ProjetNouveau nouveau = new ProjetNouveau(path2, name);
		nouveau.createRoot(name);
		nouveau.save();
	}
}
