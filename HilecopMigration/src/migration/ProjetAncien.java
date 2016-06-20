/**
 * @author shui
 */
package migration;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import hilecopComponent.HilecopComponentDesignFile;

public class ProjetAncien {
	
	/**
	 * ouvrire le fichier hilecopcomponent de l'ancien projet
	 * @param path
	 * @return
	 */
	public HilecopComponentDesignFile read(String path){
		ResourceSet ancienResourceSet = new ResourceSetImpl();
		ancienResourceSet.getPackageRegistry().put(hilecopComponent.HilecopComponentPackage.eNS_URI,hilecopComponent.HilecopComponentPackage.eINSTANCE);
		URI ancienfileURI = URI.createFileURI(path);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("hilecopcomponent",new XMIResourceFactoryImpl());
		Resource res2 = ancienResourceSet.getResource(ancienfileURI, true);
		HilecopComponentDesignFile designfile = (HilecopComponentDesignFile) res2.getContents().get(0);
		return designfile;
	}
}
