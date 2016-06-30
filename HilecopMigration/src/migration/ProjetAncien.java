/**
 * @author shui
 */
package migration;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import hilecopComponent.BasicArc;
import hilecopComponent.FusionArc;
import hilecopComponent.HilecopComponentDesignFile;
import hilecopComponent.InhibitorArc;
import hilecopComponent.PNEntity;
import hilecopComponent.PetriNetComponentBehaviour;
import hilecopComponent.Place;
import hilecopComponent.TestArc;
import hilecopComponent.Transition;

public class ProjetAncien {
	private HilecopComponentDesignFile designfile;
	private ArrayList<Place> listePlace;
	private ArrayList<Transition> listeTransition;
	private ArrayList<BasicArc> listeBasicArc;
	private ArrayList<TestArc> listeTestArc;
	private ArrayList<InhibitorArc> listeInhibitorArc;
	private ArrayList<FusionArc> listeFusionArc;

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

			if(listePN.get(i).getClass().toString().equals("class hilecopComponent.impl.BasicArcImpl")){
				listeBasicArc.add((BasicArc) listePN.get(i));
			}
			if(listePN.get(i).getClass().toString().equals("class hilecopComponent.impl.TestArcImpl")){
				listeTestArc.add((TestArc) listePN.get(i));
			}
			if(listePN.get(i).getClass().toString().equals("class hilecopComponent.impl.InhibitorArcImpl")){
				listeInhibitorArc.add((InhibitorArc) listePN.get(i));
			}
			if(listePN.get(i).getClass().toString().equals("class hilecopComponent.impl.FusionArcImpl")){
				listeFusionArc.add((FusionArc) listePN.get(i));
			}
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

	public ArrayList<BasicArc> getBasicArc(){
		return listeBasicArc;
	};
	public ArrayList<TestArc> getTestArc(){
		return listeTestArc;
	};
	public ArrayList<InhibitorArc> getInhibitorArc(){
		return listeInhibitorArc;
	};
	public ArrayList<FusionArc> getFusionArc(){
		return listeFusionArc;
	}
}
