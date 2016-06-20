/**
 * @author shui
 */
package migration;

import java.io.IOException;

import hilecopComponent.HilecopComponentDesignFile;

public class MigrationDuProjet {
	/**
	 * faire la migration de l'ancien projet vers le nouveau
	 * @param path1 : chemin de l'ancien projet
	 * @param path2 : chemin du nouveau projet
	 * @throws IOException
	 */
	public void migrationHILECOP(String path1, String path2) throws IOException{
		ProjetAncien ancien = new ProjetAncien();
		HilecopComponentDesignFile ancienroot = ancien.read(path1);
		
		String name = ancienroot.getDesignFileName();

		ProjetNouveau nouveau = new ProjetNouveau(path2, name);
		nouveau.createRoot(name);
		nouveau.save();
	}
}
