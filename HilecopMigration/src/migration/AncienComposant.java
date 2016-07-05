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
import hilecopComponent.BehaviourField;
import hilecopComponent.ComponentInstance;
import hilecopComponent.Connection;
import hilecopComponent.Constant;
import hilecopComponent.FusionArc;
import hilecopComponent.Generic;
import hilecopComponent.HilecopComponentDesignFile;
import hilecopComponent.InhibitorArc;
import hilecopComponent.PNAction;
import hilecopComponent.PNCondition;
import hilecopComponent.PNEntity;
import hilecopComponent.PNFunction;
import hilecopComponent.PNTime;
import hilecopComponent.PetriNetComponentBehaviour;
import hilecopComponent.Place;
import hilecopComponent.Port;
import hilecopComponent.RefPlace;
import hilecopComponent.RefTransition;
import hilecopComponent.Signal;
import hilecopComponent.TestArc;
import hilecopComponent.Transition;

public class AncienComposant {
	private HilecopComponentDesignFile designfile;
	private ArrayList<Place> listePlace;
	private ArrayList<Transition> listeTransition;
	private ArrayList<BasicArc> listeBasicArc;
	private ArrayList<TestArc> listeTestArc;
	private ArrayList<InhibitorArc> listeInhibitorArc;
	private ArrayList<FusionArc> listeFusionArc;
	private PetriNetComponentBehaviour pn;
	
	public AncienComposant(String path){
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

		pn = (PetriNetComponentBehaviour) designfile.getHilecopComponent().getComponentBehaviour();
		
		EList<PNEntity> listePNEntity = pn.getPNStructureObjects();
		listePlace = new ArrayList<Place>();
		listeTransition = new ArrayList<Transition>();
		listeBasicArc = new ArrayList<BasicArc>();
		listeTestArc =new ArrayList<TestArc>();
		listeInhibitorArc = new ArrayList<InhibitorArc>();
		listeFusionArc = new ArrayList<FusionArc>();
		
		for(int i=0;i<listePNEntity.size();i++){
			if(listePNEntity.get(i).getClass().toString().equals("class hilecopComponent.impl.PlaceImpl")){
				listePlace.add((Place)listePNEntity.get(i));
			}
			if(listePNEntity.get(i).getClass().toString().equals("class hilecopComponent.impl.TransitionImpl")){
				listeTransition.add((Transition) listePNEntity.get(i));
			}
			if(listePNEntity.get(i).getClass().toString().equals("class hilecopComponent.impl.BasicArcImpl")){
				listeBasicArc.add((BasicArc) listePNEntity.get(i));
			}
			if(listePNEntity.get(i).getClass().toString().equals("class hilecopComponent.impl.TestArcImpl")){
				listeTestArc.add((TestArc) listePNEntity.get(i));
			}
			if(listePNEntity.get(i).getClass().toString().equals("class hilecopComponent.impl.InhibitorArcImpl")){
				listeInhibitorArc.add((InhibitorArc) listePNEntity.get(i));
			}
			if(listePNEntity.get(i).getClass().toString().equals("class hilecopComponent.impl.FusionArcImpl")){
				listeFusionArc.add((FusionArc) listePNEntity.get(i));
			}
		}
	}

	public HilecopComponentDesignFile getRoot(){
		return designfile;
	}
	
	/*  Fields  */
	public EList<Port> getPorts(){
		return designfile.getHilecopComponent().getPorts();
	}
	public EList<Signal> getSignals(){
		return designfile.getHilecopComponent().getSignals();
	}
	public EList<Generic> getGenerics(){
		return designfile.getHilecopComponent().getGenerics();
	}
	public ArrayList<Constant> getConstants(){
		EList<BehaviourField> listeField = designfile.getHilecopComponent().getComponentBehaviour().getPrivateFields();
		ArrayList<Constant> listeConstant = new ArrayList<Constant>();
		for(int i=0;i<listeField.size();i++){
			if(listeField.get(i).getClass().toString().equals("class hilecopComponent.impl.ConstantImpl")){
				Constant constant = (Constant)designfile.getHilecopComponent().getComponentBehaviour().getPrivateFields().get(i);
				listeConstant.add(constant);
			}
		}
		return listeConstant;
	}

	public EList<ComponentInstance> getInstances(){
		return designfile.getHilecopComponent().getComponentBehaviour().getComponentsInstances(); 
	}
	
	public ArrayList<Place> getPlaces(){		
		return listePlace;
	}
	public ArrayList<Transition> getTransitions(){
		return listeTransition;
	}
	public EList<RefPlace> getRefPlaces(){
		return designfile.getHilecopComponent().getRefPlaces();
	}
	public EList<RefTransition> getRefTransitions(){
		return designfile.getHilecopComponent().getRefTransitions();
	}
	
	/*  PNElements  */
	public EList<PNAction> getPNActions(){
		return pn.getInterpretation().getActions();
	}
	public EList<PNFunction> getPNFunctions(){
		return pn.getInterpretation().getFunctions();
	}
	public EList<PNCondition> getPNConditions(){
		return pn.getInterpretation().getConditions();
	}	
	public EList<PNTime> getPNTimes(){
		return pn.getInterpretation().getTimes();
	}
	
	/*  Arcs  */
	public ArrayList<BasicArc> getBasicArcs(){
		return listeBasicArc;
	};
	public ArrayList<TestArc> getTestArcs(){
		return listeTestArc;
	};
	public ArrayList<InhibitorArc> getInhibitorArcs(){
		return listeInhibitorArc;
	};
	public ArrayList<FusionArc> getFusionArcs(){
		return listeFusionArc;
	}
	
	public EList<Connection> getConnections(){
		return designfile.getHilecopComponent().getComponentBehaviour().getConnections();
	}
}
