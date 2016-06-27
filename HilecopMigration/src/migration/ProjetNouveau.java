/**
 * @author shui
 */
package migration;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import field.*;
import hilecopComponent.HilecopComponentDesignFile;
import root.*;

public class ProjetNouveau {
	private ResourceSet resourceSet;
	private Resource newres;
	private HilecopRoot newroot;

	/**
	 * constructor; create un nouveau fichier root
	 * @param path
	 * @param name
	 * @throws IOException
	 */
	public ProjetNouveau(String path, String name) throws IOException{
		resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(root.RootPackage.eNS_URI,root.RootPackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("root",new XMIResourceFactoryImpl());
		String filename = path + "\\" + name + ".root";
		File file = new File(filename);
		file.createNewFile();
		URI newURI = URI.createFileURI(filename);
		newres = resourceSet.createResource(newURI);
	}

	/**
	 * cr¨¦er un nouveau composant avec le m¨ºme nom de l'ancien
	 * @param name
	 */
	public void createRoot(String name){
		newroot = RootFactory.eINSTANCE.createHilecopRoot(name);
		
		//cr¨¦er ResetPort, ClockPort et ClockEnablePort et les ajouter dans le composant
		
		ResetPort resetport = FieldFactory.eINSTANCE.createResetPort();
		ClockPort clockport = FieldFactory.eINSTANCE.createClockPort();
		ClockEnablePort clockenable = FieldFactory.eINSTANCE.createClockEnablePort();

		newroot.getComponent().setResetPort(resetport);
		newroot.getComponent().setClockPort(clockport);
		newroot.getComponent().setClockEnablePort(clockenable);
	}

	/**
	 * la migration
	 * @param designfile : hilecopcomponent de l'ancien projet
	 */
	public void migration(HilecopComponentDesignFile designfile){
		System.out.println(newroot.getComponent().eContents().size());

		System.out.println(designfile.getHilecopComponent().getComponentBehaviour().getPorts());
		System.out.println(designfile.getHilecopComponent().getPorts());
		System.out.println(designfile.getHilecopComponent().getGenerics());

		System.out.println("All:"+ designfile.getHilecopComponent().eContents().size());
		System.out.println("All:"+ designfile.getHilecopComponent().eContents());
		//System.out.println("Port:"+ designfile.getHilecopComponent().getPorts().size());
		//System.out.println("Generic:"+ designfile.getHilecopComponent().getGenerics().size());
		//System.out.println("Signal:"+ designfile.getHilecopComponent().getSignals().size());
		System.out.println("Field:"+ designfile.getHilecopComponent().getPublicFields().size());
		System.out.println("Field:"+ designfile.getHilecopComponent().getPublicFields());
		System.out.println(designfile.getHilecopComponent().getComponentBehaviour());

		System.out.println(designfile.getHilecopComponent().getDescriptorName());
		System.out.println(designfile.getHilecopComponent().getName());
		System.out.println(designfile.getDesignFileName());
	}


	/**
	 *  sauvegarder le fichier
	 * @throws IOException 
	 */
	public void save() throws IOException{
		newres.getContents().add(newroot);
		newres.save(null);
	}
}


