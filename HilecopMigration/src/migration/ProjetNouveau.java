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
import hilecopComponent.Constant;
import hilecopComponent.Field;
import hilecopComponent.Generic;
import hilecopComponent.HilecopComponentDesignFile;
import hilecopComponent.Port;
import hilecopComponent.Signal;
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
		MigrationDuComposant migtool = new MigrationDuComposant(newroot);
		
		/*
		 * Field
		 */
		int nbPort = designfile.getHilecopComponent().getPorts().size();
		System.out.println("Number of Port :" + nbPort);
		for(int i=0;i<nbPort;i++){
			Port port = designfile.getHilecopComponent().getPorts().get(i);
			migtool.migratePort(port);
			System.out.println("Port " + (i+1) +" is migrated");
		}
		
		int nbSignal = designfile.getHilecopComponent().getSignals().size();
		System.out.println("Number of Signal :" + nbSignal);
		for(int i=0;i<nbSignal;i++){
			Signal signal = designfile.getHilecopComponent().getSignals().get(i);
			migtool.migrateSignal(signal);
			System.out.println("Signal " + (i+1) +" is migrated");
		}
		
		int nbGeneric = designfile.getHilecopComponent().getGenerics().size();
		System.out.println("Number of Generic :" + nbGeneric);
		for(int i=0;i<nbGeneric;i++){
			Generic generic = designfile.getHilecopComponent().getGenerics().get(i);
			migtool.migrateGeneric(generic);
			System.out.println("Generic " + (i+1) +" is migrated");
		}
		
		int nbpf = designfile.getHilecopComponent().getComponentBehaviour().getPrivateFields().size();
		int nbConstant = 0;
		for(int i=0;i<nbpf;i++){
			Field field = designfile.getHilecopComponent().getComponentBehaviour().getPrivateFields().get(i);
			//System.out.println(field.getClass());
			if(field.getClass().toString()=="Constant"){
				nbConstant = nbConstant+1;
				Constant constant = (Constant)designfile.getHilecopComponent().getComponentBehaviour().getPrivateFields().get(i);
				migtool.migrateConstant(constant);
			}
		}
		System.out.println("Number of Constant :"+nbConstant);
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


