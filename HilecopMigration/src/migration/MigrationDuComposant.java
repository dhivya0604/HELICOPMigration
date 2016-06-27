package migration;

import field.*;
import hilecopComponent.*;
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
	
}
