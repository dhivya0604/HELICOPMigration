/**
 * @author shui
 */
package migration;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import hilecopComponent.HilecopComponentDesignFile;
import hilecopComponent.PNEntity;
import hilecopComponent.PetriNetComponentBehaviour;
import hilecopComponent.Place;
import hilecopComponent.Transition;

public class ProjetAncien {
	private HilecopComponentDesignFile designfile;
	private ArrayList<Place> listePlace;
	private ArrayList<Transition> listeTransition;
	
	public ProjetAncien(String path){
		ResourceSet ancienResourceSet = new ResourceSetImpl();
		ancienResourceSet.getPackageRegistry().put(hilecopComponent.HilecopComponentPackage.eNS_URI,hilecopComponent.HilecopComponentPackage.eINSTANCE);
		URI ancienfileURI = URI.createFileURI(path);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("hilecopcomponent",new XMIResourceFactoryImpl());
		Resource res2 = ancienResourceSet.getResource(ancienfileURI, true);
		try{
			designfile = (HilecopComponentDesignFile) res2.getContents().get(0);
		}catch(IndexOutOfBoundsException e) {
			System.out.println("You catch a error with File "+ path);
			System.exit(0);
		}
		
		PetriNetComponentBehaviour pn = (PetriNetComponentBehaviour) designfile.getHilecopComponent().getComponentBehaviour();
		EList<PNEntity> listePN = pn.getPNStructureObjects();
		listePlace = new ArrayList<Place>();
		listeTransition = new ArrayList<Transition>();
		for(int i=0;i<listePN.size();i++){
			if(listePN.get(i).getClass().toString().equals("class hilecopComponent.impl.PlaceImpl")){
				listePlace.add((Place)listePN.get(i));
			}
			if(listePN.get(i).getClass().toString().equals("class hilecopComponent.impl.TransitionImpl")){
				listeTransition.add((Transition) listePN.get(i));
			}
			/*
			if(listePN.get(i).getClass().toString().equals("class hilecopComponent.impl.BasicArcImpl")){
				
			}
			*/
		}
	}
	
	public HilecopComponentDesignFile getRoot(){
		return designfile;
	}
	
	public ArrayList<Place> getPlace(){		
		return listePlace;
	}
	
	public ArrayList<Transition> getTransition(){
		return listeTransition;
	}
	
	/**
	 * @TODO les autres get?
	 */
}
