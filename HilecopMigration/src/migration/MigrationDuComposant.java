package migration;

import java.util.ArrayList;

import field.*;
import hilecopComponent.*;
import hilecopComponent.Field;
import petriNet.PetriNetFactory;
import root.HilecopRoot;

public class MigrationDuComposant {

	private ProjetNouveau newprojet;
	private HilecopRoot newroot;

	public MigrationDuComposant(ProjetNouveau nouveau){
		newprojet = nouveau;
		newroot = nouveau.getRoot();
	}

	public void migeration(ProjetAncien ancien){
		HilecopComponentDesignFile designfile = ancien.getRoot();
		/*
		 * Field
		 */
		int nbPort = designfile.getHilecopComponent().getPorts().size();
		System.out.println("Number of Port :" + nbPort);
		for(int i=0;i<nbPort;i++){
			Port port = designfile.getHilecopComponent().getPorts().get(i);
			newroot.getComponent().getFields().add(convertPort(port));
			System.out.println("Port " + (i+1) +" is migrated");
		}

		int nbSignal = designfile.getHilecopComponent().getSignals().size();
		System.out.println("Number of Signal :" + nbSignal);
		for(int i=0;i<nbSignal;i++){
			Signal signal = designfile.getHilecopComponent().getSignals().get(i);
			newroot.getComponent().getFields().add(convertSignal(signal));
			System.out.println("Signal " + (i+1) +" is migrated");
		}

		int nbGeneric = designfile.getHilecopComponent().getGenerics().size();
		System.out.println("Number of Generic :" + nbGeneric);
		for(int i=0;i<nbGeneric;i++){
			Generic generic = designfile.getHilecopComponent().getGenerics().get(i);
			newroot.getComponent().getFields().add(convertGeneric(generic));
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
				newroot.getComponent().getFields().add(convertConstant(constant));
			}
		}
		System.out.println("Number of Constant :"+nbConstant);


		/*
		 * PN
		 */

		int nbPlace = ancien.getPlace().size();
		System.out.println("Number of Place : " + nbPlace);
		for(int i=0;i<nbPlace;i++){
			Place place = ancien.getPlace().get(i);
			newroot.getComponent().getPNStructureObjects().add(convertPlace(place));
			System.out.println("Place " + (i+1) +" is migrated");
		}

		int nbRefPlace = designfile.getHilecopComponent().getRefPlaces().size();
		System.out.println("Number of Generic :" + nbRefPlace);
		for(int i=0;i<nbRefPlace;i++){
			RefPlace refplace = designfile.getHilecopComponent().getRefPlaces().get(i);
			newroot.getComponent().getPNStructureObjects().add(convertRefPlace(refplace));
			System.out.println("RefPlace " + (i+1) +" is migrated");
		}
		
		int nbTransition = ancien.getTransition().size();
		System.out.println("Number of Transition : " + nbTransition);
		for(int i=0;i<nbTransition;i++){
			Transition Transition = ancien.getTransition().get(i);
			newroot.getComponent().getPNStructureObjects().add(convertTransition(Transition));
			System.out.println("Transition " + (i+1) +" is migrated");
		}
		
		int nbRefTransition = designfile.getHilecopComponent().getRefTransitions().size();
		System.out.println("Number of Generic :" + nbRefTransition);
		for(int i=0;i<nbRefTransition;i++){
			RefTransition refTransition = designfile.getHilecopComponent().getRefTransitions().get(i);
			newroot.getComponent().getPNStructureObjects().add(convertRefTransition(refTransition));
			System.out.println("RefTransition " + (i+1) +" is migrated");
		}
	}


	/*
	public void migrateConnection(Connection connection){
		field.Connection newconnection = FieldFactory.eINSTANCE.createConnection();

		//@TODO connection only has getID 

		//newconnection.setName(connection.get);
		//newroot.getComponent().getFields().add(newconnection);

	}
	 */

	private VHDLPort convertPort(Port port){
		VHDLPort newport = FieldFactory.eINSTANCE.createVHDLPort(); 
		newport.setName(port.getName());
		convertPortMode(newport, port);
		newport.setDefaultValue(port.getDefaultValue());
		newport.setType(port.getType());
		return newport;
	}

	private VHDLSignal convertSignal(Signal signal){
		VHDLSignal newsignal = FieldFactory.eINSTANCE.createVHDLSignal();
		newsignal.setName(signal.getName());
		newsignal.setType(signal.getType());
		newsignal.setDefaultValue(signal.getDefaultValue());
		return newsignal;
	}

	private VHDLGeneric convertGeneric(Generic generic){
		VHDLGeneric newgeneric = FieldFactory.eINSTANCE.createVHDLGeneric();
		newgeneric.setName(generic.getName());
		newgeneric.setType(generic.getType());
		newgeneric.setDefaultValue(generic.getDefaultValue());
		return newgeneric;
	}

	private VHDLConstant convertConstant(Constant constant){
		VHDLConstant newconstant = FieldFactory.eINSTANCE.createVHDLConstant();
		newconstant.setName(constant.getName());
		newconstant.setType(constant.getType());
		newconstant.setDefaultValue(constant.getDefaultValue());
		return newconstant;
	}

	private petriNet.Place convertPlace(Place place){
		petriNet.Place newplace = PetriNetFactory.eINSTANCE.createPlace();
		newplace.setName(place.getName());
		newplace.setMarking(Integer.parseInt(place.getMarkupExpression()));
		return newplace;
	}

	private petriNet.RefPlace convertRefPlace(RefPlace refplace){
		petriNet.RefPlace newrefplace = PetriNetFactory.eINSTANCE.createRefPlace();
		newrefplace.setName(refplace.getName());
		convertRefPlaceMode(newrefplace, refplace);
		//check refplace.place exist ou pas
		ArrayList<petriNet.Place> listeplace = newprojet.getPlace();
		String placename = refplace.getPlace().getName();
		Boolean notfind = true;
		for(int i=0;i<listeplace.size();i++){
			if(listeplace.get(i).getName().equals(placename)){
				newrefplace.setPlace(listeplace.get(i));
				notfind = false;
			}
		}
		if(notfind){
			System.out.println("Can't find Place "+ placename);
		}
		return newrefplace;
	}

	private petriNet.Transition convertTransition(Transition transition){
		petriNet.Transition newtransition = PetriNetFactory.eINSTANCE.createTransition();
		newtransition.setName(transition.getName());
		//Time time = 
		//newtransition.setTime(transition.getTemporalBehaviour());
		return newtransition;
	}

	private petriNet.RefTransition convertRefTransition(RefTransition reftransition){
		petriNet.RefTransition newreftransition = PetriNetFactory.eINSTANCE.createRefTransition();
		newreftransition.setName(reftransition.getName());
		convertRefTransitionMode(newreftransition, reftransition);		
		//check reftransition.transition exist ou pas
		ArrayList<petriNet.Transition> listetransition = newprojet.getTransition();
		String transitionname = reftransition.getTransition().getName();
		Boolean notfind = true;
		for(int i=0;i<listetransition.size();i++){
			if(listetransition.get(i).getName().equals(transitionname)){
				newreftransition.setTransition(listetransition.get(i));
				notfind = false;
			}
		}
		if(notfind){
			System.out.println("Can't find transition "+ transitionname);
		}
		return newreftransition;
	}
	/**
	 * Donner mode du port selon mode de l'ancien port
	 * @param newport
	 * @param port
	 */
	private void convertPortMode(VHDLPort newport, Port port){
		/**
		 * @TODO comparer les autres champs?
		 */
		if(port.getMode().getValue()==0){
			newport.setMode(PortMode.IN);
		}
		if(port.getMode().getValue()==1){
			newport.setMode(PortMode.IN);
		}
		if(port.getMode().getValue()==2){
			newport.setMode(PortMode.IN);
		}
		/**
		 * TODO gerer exception
		 */
	}

	private void convertRefPlaceMode(petriNet.RefPlace newrefplace, RefPlace refplace){
		if(refplace.getMode().getValue()==0){
			newrefplace.setMode(PortMode.IN);
		}
		if(refplace.getMode().getValue()==1){
			newrefplace.setMode(PortMode.OUT);
		}
		if(refplace.getMode().getValue()==2){
			newrefplace.setMode(PortMode.INOUT);
		}
		/**
		 * TODO peut = ConvertPortMode?
		 */
	}
	/**
	 * @TODO comment utiliser un method pour port/place/transition
	 * @param newrefTransition
	 * @param refTransition
	 */
	private void convertRefTransitionMode(petriNet.RefTransition newrefTransition, RefTransition refTransition){
		if(refTransition.getMode().getValue()==0){
			newrefTransition.setMode(PortMode.IN);
		}
		if(refTransition.getMode().getValue()==1){
			newrefTransition.setMode(PortMode.OUT);
		}
		if(refTransition.getMode().getValue()==2){
			newrefTransition.setMode(PortMode.INOUT);
		}
	}

}
