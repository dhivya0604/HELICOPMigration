/**
 * @author shui
 */
package migration;

import java.io.IOException;

import hilecopComponent.HilecopComponentDesignFile;

public class Migration {

	public void migrationHILECOP(String path1, String path2) throws IOException{
		ProjetAncien ancien = new ProjetAncien();
		HilecopComponentDesignFile ancienroot = ancien.read(path1);
		
		String name = ancienroot.getDesignFileName();

		ProjetNouveau nouveau = new ProjetNouveau(path2, name);
		nouveau.createRoot(name);
		nouveau.save();
	}
}
