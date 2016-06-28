package migration;

import field.*;
import hilecopComponent.*;
import petriNet.PetriNetFactory;
import root.HilecopRoot;

public class MigrationDuComposant {

	private HilecopRoot nouveau;
	
	public MigrationDuComposant(HilecopRoot root){
		nouveau = root;
	}
	
	public void migratePort(Port port){
		VHDLPort newport = FieldFactory.eINSTANCE.createVHDLPort(); 
		/**
		 * @TODO comparer avec script
		 */
		newport.setName(port.getName());
		convertPortMode(newport, port);
		newport.setDefaultValue(port.getDefaultValue());
		newport.setType(port.getType());
		nouveau.getComponent().getFields().add(newport);
	}
	
	/**
	 * migrer Field-Signal
	 * @param signal - ancien signal
	 */
	public void migrateSignal(Signal signal){
		VHDLSignal newsignal = FieldFactory.eINSTANCE.createVHDLSignal();
		newsignal.setName(signal.getName());
		newsignal.setType(signal.getType());
		newsignal.setDefaultValue(signal.getDefaultValue());		
		nouveau.getComponent().getFields().add(newsignal);
	}
	
	/**
	 * migrer Field-Generic
	 * @param generic
	 */
	public void migrateGeneric(Generic generic){
		VHDLGeneric newgeneric = FieldFactory.eINSTANCE.createVHDLGeneric();
		newgeneric.setName(generic.getName());
		newgeneric.setType(generic.getType());
		newgeneric.setDefaultValue(generic.getDefaultValue());
		nouveau.getComponent().getFields().add(newgeneric);
	}
	
	/**
	 * migrer Field-Constant
	 * @param constant
	 */
	public void migrateConstant(Constant constant){
		VHDLConstant newconstant = FieldFactory.eINSTANCE.createVHDLConstant();
		newconstant.setName(constant.getName());
		newconstant.setType(constant.getType());
		newconstant.setDefaultValue(constant.getDefaultValue());
		nouveau.getComponent().getFields().add(newconstant);
		
	}
	/*
	public void migrateConnection(Connection connection){
		field.Connection newconnection = FieldFactory.eINSTANCE.createConnection();
		
		//@TODO connection only has getID 
		
		//newconnection.setName(connection.get);
		//nouveau.getComponent().getFields().add(newconnection);
		
	}
	*/
	/**
	 * 
	 * @param place
	 */
	public void migratePlace(Place place){
		petriNet.Place newplace = PetriNetFactory.eINSTANCE.createPlace();
		newplace.setName(place.getName());
		newplace.setMarking(Integer.parseInt(place.getMarkupExpression()));
		nouveau.getComponent().getPNStructureObjects().add(newplace);
	}
	
	public void migrateRefPlace(RefPlace refplace){
		petriNet.RefPlace newrefplace = PetriNetFactory.eINSTANCE.createRefPlace();
		newrefplace.setName(refplace.getName());
		convertRefPlaceMode(newrefplace, refplace);
		nouveau.getComponent().getPNStructureObjects().add(newrefplace);
	}
	
	public void migrateTransition(Transition transition){
		petriNet.Transition newtransition = PetriNetFactory.eINSTANCE.createTransition();
		newtransition.setName(transition.getName());
		//Time time = 
		//newtransition.setTime(transition.getTemporalBehaviour());
		nouveau.getComponent().getPNStructureObjects().add(newtransition);
	}
		
	/**
	 * Donner mode du port selon mode de l'ancien port
	 * @param newport
	 * @param port
	 */
	public void convertPortMode(VHDLPort newport, Port port){
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
	
	public void convertRefPlaceMode(petriNet.RefPlace newrefplace, RefPlace refplace){
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
	
}
