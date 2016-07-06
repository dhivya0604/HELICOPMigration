package migration;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;

import field.*;
import field.FusionConnection;
import field.SimpleConnection;
import hilecopComponent.*;
import hilecopComponent.Connection;
import petriNet.PetriNetFactory;
import root.HilecopRoot;
import root.RootFactory;
import script.ScriptFactory;
import script.VHDLAction;
import script.VHDLCondition;
import script.VHDLElement;
import script.VHDLFunction;
import script.VHDLTime;

public class MigrationDuComposant {

	private NouveauComposant newprojet;
	private HilecopRoot newroot;
	private AncienComposant origiprojet;

	public MigrationDuComposant(NouveauComposant nouveau,AncienComposant ancien){
		newprojet = nouveau;
		newroot = nouveau.getRoot();
		origiprojet = ancien;
	}

	public void migeration(){
		System.out.println("Fiche "+newroot.getComponent().getName());
		this.migrationField();
		this.migrationInstance();
		this.migrationVHDL();
		this.migrationBasicNode();
		this.migrationRefNode();
		this.migrationArc();
		this.migrationConnection();
		System.out.println("");
	}


	public void migrationField(){
		EList<Port> listePort = origiprojet.getPorts();
		System.out.println("Number of Port :" + listePort.size());
		for(int i=0;i<listePort.size();i++){
			Port port = listePort.get(i);
			newroot.getComponent().getFields().add(convertPort(port));
			System.out.println("Port " + (i+1) +" is migrated");
		}

		EList<Signal> listeSignal = origiprojet.getSignals();
		System.out.println("Number of Signal :" + listeSignal.size());
		for(int i=0;i<listeSignal.size();i++){
			Signal signal = listeSignal.get(i);
			newroot.getComponent().getFields().add(convertSignal(signal));
			System.out.println("Signal " + (i+1) +" is migrated");
		}

		EList<Generic> listeGeneric = origiprojet.getGenerics();
		System.out.println("Number of Generic :" + listeGeneric.size());
		for(int i=0;i<listeGeneric.size();i++){
			Generic generic = listeGeneric.get(i);
			newroot.getComponent().getFields().add(convertGeneric(generic));
			System.out.println("Generic " + (i+1) +" is migrated");
		}

		ArrayList<Constant> listeConstant = origiprojet.getConstants();
		System.out.println("Number of Constant :" + listeConstant.size());
		for(int i=0;i<listeGeneric.size();i++){
			Constant constant = listeConstant.get(i);
			newroot.getComponent().getFields().add(convertConstant(constant));
			System.out.println("Generic " + (i+1) +" is migrated");
		}
	}
	
	public void migrationInstance(){
		EList<ComponentInstance> listeInstance = origiprojet.getInstances();
		System.out.println("Number of Instance : " + listeInstance.size());
		for(int i=0;i<listeInstance.size();i++){
			newroot.getComponent().getComponentInstances().add(convertInstance(listeInstance.get(i)));
			System.out.println("Instance " + (i+1) +" is migrated");
		}
	}
	
	public void migrationVHDL(){
		EList<PNAction> listeVHDLAction = origiprojet.getPNActions();
		System.out.println("Number of PNAction : "+listeVHDLAction.size());
		for(int i=0;i<listeVHDLAction.size();i++){
			newroot.getComponent().getVHDLElements().add(convertVHDLAction(listeVHDLAction.get(i)));
		}
		
		EList<PNFunction> listeVHDLFunction = origiprojet.getPNFunctions();
		System.out.println("Number of PNFunction : "+listeVHDLFunction.size());
		for(int i=0;i<listeVHDLFunction.size();i++){
			newroot.getComponent().getVHDLElements().add(convertVHDLFunction(listeVHDLFunction.get(i)));
		}
		
		EList<PNCondition> listeVHDLCondition = origiprojet.getPNConditions();
		System.out.println("Number of PNCondition : "+listeVHDLCondition.size());
		for(int i=0;i<listeVHDLCondition.size();i++){
			newroot.getComponent().getVHDLElements().add(convertVHDLCondition(listeVHDLCondition.get(i)));
		}
		
		EList<PNTime> listeVHDLTime = origiprojet.getPNTimes();
		System.out.println("Number of PNTime : "+listeVHDLTime.size());
		for(int i=0;i<listeVHDLTime.size();i++){
			newroot.getComponent().getVHDLElements().add(convertVHDLTime(listeVHDLTime.get(i)));
		}
	}
	
	public void migrationBasicNode(){
		ArrayList<Place> listePlace = origiprojet.getPlaces();
		System.out.println("Number of Place : " + listePlace.size());
		for(int i=0;i<listePlace.size();i++){
			Place place = listePlace.get(i);
			newroot.getComponent().getPNStructureObjects().add(convertPlace(place));
			System.out.println("Place " + (i+1) +" is migrated");
		}
		
		ArrayList<Transition> listeTransition = origiprojet.getTransitions();
		System.out.println("Number of Transition : " + listeTransition.size());
		for(int i=0;i<listeTransition.size();i++){
			Transition Transition = listeTransition.get(i);
			newroot.getComponent().getPNStructureObjects().add(convertTransition(Transition));
			System.out.println("Transition " + (i+1) +" is migrated");
		}
	}
	
	public void migrationRefNode(){
		EList<RefPlace> listeRefPlace = origiprojet.getRefPlaces();
		System.out.println("Number of RefPlace :" + listeRefPlace.size());
		for(int i=0;i<listeRefPlace.size();i++){
			RefPlace refplace = listeRefPlace.get(i);
			newroot.getComponent().getPNStructureObjects().add(convertRefPlace(refplace));
			System.out.println("RefPlace " + (i+1) +" is migrated");
		}
		
		EList<RefTransition> listeRefTransition = origiprojet.getRefTransitions();
		System.out.println("Number of RefTransition :" + listeRefTransition.size());
		for(int i=0;i<listeRefTransition.size();i++){
			RefTransition refTransition = listeRefTransition.get(i);
			newroot.getComponent().getPNStructureObjects().add(convertRefTransition(refTransition));
			System.out.println("RefTransition " + (i+1) +" is migrated");
		}
	}

	public void migrationArc(){
		ArrayList<BasicArc> listeBasicArc = origiprojet.getBasicArcs();
		System.out.println("Number of BasicArc : " + listeBasicArc.size());
		for(int i=0;i<listeBasicArc.size();i++){
			BasicArc BasicArc = listeBasicArc.get(i);
			newroot.getComponent().getPNStructureObjects().add(convertBasicArc(BasicArc));
			System.out.println("BasicArc " + (i+1) +" is migrated");
		}
		
		ArrayList<TestArc> listeTestArc = origiprojet.getTestArcs();
		System.out.println("Number of TestArc : " + listeTestArc.size());
		for(int i=0;i<listeTestArc.size();i++){
			TestArc TestArc = listeTestArc.get(i);
			newroot.getComponent().getPNStructureObjects().add(convertTestArc(TestArc));
			System.out.println("TestArc " + (i+1) +" is migrated");
		}
		
		ArrayList<InhibitorArc> listeInhibitorArc = origiprojet.getInhibitorArcs();
		System.out.println("Number of InhibitorArc : " + listeInhibitorArc.size());
		for(int i=0;i<listeInhibitorArc.size();i++){
			InhibitorArc InhibitorArc = listeInhibitorArc.get(i);
			newroot.getComponent().getPNStructureObjects().add(convertInhibitorArc(InhibitorArc));
			System.out.println("InhibitorArc " + (i+1) +" is migrated");
		}
		
		ArrayList<FusionArc> listeFusionArc = origiprojet.getFusionArcs();
		System.out.println("Number of FusionArc : " + listeFusionArc.size());
		for(int i=0;i<listeFusionArc.size();i++){
			FusionArc FusionArc = listeFusionArc.get(i);
			newroot.getComponent().getPNStructureObjects().add(convertFusionArc(FusionArc));
			System.out.println("FusionArc " + (i+1) +" is migrated");
		}
	}
	
	public void migrationConnection(){
		/**
		 * TODO verifier connection entre instance 
		 */
		EList<Connection> listeconnection = origiprojet.getConnections();
		System.out.println("Number of Connection : "+listeconnection.size());
		for(int i=0;i<listeconnection.size();i++){
			if(listeconnection.get(i).getClass().toString().equals("class hilecopComponent.impl.SimpleConnectionImpl")){
				hilecopComponent.SimpleConnection sconnection = (hilecopComponent.SimpleConnection) listeconnection.get(i);
				newroot.getComponent().getConnections().add(convertSimpleConnection(sconnection));
			}
			if(listeconnection.get(i).getClass().toString().equals("class hilecopComponent.impl.FusionConnectionImpl")){
				hilecopComponent.FusionConnection sconnection = (hilecopComponent.FusionConnection) listeconnection.get(i);
				newroot.getComponent().getConnections().add(convertFusionConnection(sconnection));
			}
		}
	}
	
	/*
	 * Field
	 */
	private VHDLPort convertPort(Port port){
		VHDLPort newport = FieldFactory.eINSTANCE.createVHDLPort(); 
		newport.setName(port.getName());
		setPortMode(newport, port);
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
	
	private field.SimpleConnection convertSimpleConnection(hilecopComponent.SimpleConnection Sconnection){
		SimpleConnection newSconnection = FieldFactory.eINSTANCE.createSimpleConnection();
		String name = "simpleconnection_"+Sconnection.getId();
		newSconnection.setName(name);
		setField(newSconnection,Sconnection);
		newSconnection.setSourceSelectionExpression(Sconnection.getSourceSelectionExpression());
		newSconnection.setTargetSelectionExpression(Sconnection.getTargetSelectionExpression());
		return newSconnection;
	}
	
	private field.FusionConnection convertFusionConnection(hilecopComponent.FusionConnection Fconnection){
		FusionConnection newFconnection = FieldFactory.eINSTANCE.createFusionConnection();
		String name = "fusionconnection_"+Fconnection.getId();
		newFconnection.setName(name);
		setField(newFconnection,Fconnection);
		return newFconnection;
	}
	
	/**
	 * @param pnaction
	 * @return
	 */
	private VHDLAction convertVHDLAction(PNAction pnaction){
		VHDLAction vhdlAction = ScriptFactory.eINSTANCE.createVHDLAction();
		setVHDLscript(vhdlAction,pnaction);
		return vhdlAction;
	}

	private VHDLCondition convertVHDLCondition(PNCondition pnCondition){
		VHDLCondition vhdlCondition = ScriptFactory.eINSTANCE.createVHDLCondition();
		setVHDLscript(vhdlCondition,pnCondition);
		return vhdlCondition;
	}
	
	private VHDLFunction convertVHDLFunction(PNFunction pnFunction){
		VHDLFunction vhdlFunction = ScriptFactory.eINSTANCE.createVHDLFunction();
		setVHDLscript(vhdlFunction,pnFunction);
		return vhdlFunction;
	}
	
	private VHDLTime convertVHDLTime(PNTime pntime){
		VHDLTime vhdlTime = ScriptFactory.eINSTANCE.createVHDLTime();
		setVHDLscript(vhdlTime,pntime);
		return vhdlTime;
	}
	
	private petriNet.Place convertPlace(Place place){
		petriNet.Place newplace = PetriNetFactory.eINSTANCE.createPlace();
		newplace.setName(place.getName());
		newplace.setMarking(Integer.parseInt(place.getMarkupExpression()));
		//add actions
		EList<PNEntityInterpretation> listeInterpretation = place.getInterpretation();
		for(int i=0;i<listeInterpretation.size();i++){
			if(listeInterpretation.get(i).getClass().equals("class hilecopComponent.impl.ActionImpl"))
			{
				Action action =  (Action) listeInterpretation.get(i);
				setAction(newplace,action);
			}
		}
		return newplace;
	}

	private petriNet.RefPlace convertRefPlace(RefPlace refplace){
		petriNet.RefPlace newrefplace = PetriNetFactory.eINSTANCE.createRefPlace();
		newrefplace.setName(refplace.getName());
		setRefPlaceMode(newrefplace, refplace);
		//check refplace.place exist ou pas
		ArrayList<petriNet.Place> listeplace = newprojet.getPlaces();
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
		int nbtime = 0;
		EList<PNEntityInterpretation> listeInterpretation = transition.getInterpretation();
		for(int i=0;i<listeInterpretation.size();i++){
			String interpretationtype = listeInterpretation.get(i).getClass().toString();
			if(interpretationtype.equals("class hilecopComponent.impl.ConditionImpl"))
			{
				Condition condition =  (Condition) listeInterpretation.get(i);
				setCondition(newtransition,condition);
			}
			if(interpretationtype.equals("class hilecopComponent.impl.FunctionImpl"))
			{
				Function function =  (Function) listeInterpretation.get(i);
				setFunction(newtransition,function);
			}
			if(interpretationtype.equals("class hilecopComponent.impl.TimeImpl")&&nbtime<2)
			{
				nbtime = nbtime+1;
				if(nbtime==1){
				Time time =  (Time) listeInterpretation.get(i);
				setTime(newtransition,time);
				}
				else{
					System.out.println("Error : More than one time for this transition");
				}
			}
		}
		return newtransition;
	}

	private petriNet.RefTransition convertRefTransition(RefTransition reftransition){
		petriNet.RefTransition newreftransition = PetriNetFactory.eINSTANCE.createRefTransition();
		newreftransition.setName(reftransition.getName());
		setRefTransitionMode(newreftransition, reftransition);		
		//check reftransition.transition exist ou pas
		ArrayList<petriNet.Transition> listetransition = newprojet.getTransitions();
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
	
	private petriNet.BasicArc convertBasicArc(BasicArc barc){
		petriNet.BasicArc newBarc = PetriNetFactory.eINSTANCE.createBasicArc();
		newBarc.setName(barc.getName());
		newBarc.setRuleExpression(barc.getRuleExpression());
		setNode(newBarc,barc);
		return newBarc;
	}
	
	private petriNet.TestArc convertTestArc(TestArc tarc){
		petriNet.TestArc newTarc = PetriNetFactory.eINSTANCE.createTestArc();
		newTarc.setName(tarc.getName());
		newTarc.setRuleExpression(tarc.getRuleExpression());
		setNode(newTarc,tarc);
		return newTarc;
	}
	
	private petriNet.InhibitorArc convertInhibitorArc(InhibitorArc iarc){
		petriNet.InhibitorArc newIarc = PetriNetFactory.eINSTANCE.createInhibitorArc();
		newIarc.setName(iarc.getName());
		newIarc.setRuleExpression(iarc.getRuleExpression());
		setNode(newIarc,iarc);
		return newIarc;
	}
	
	private petriNet.FusionArc convertFusionArc(FusionArc farc){
		petriNet.FusionArc newFarc = PetriNetFactory.eINSTANCE.createFusionArc();
		newFarc.setName(farc.getName());
		setNode(newFarc,farc);
		return newFarc;
	}
	
	private root.ComponentInstance convertInstance(ComponentInstance instance){
		root.ComponentInstance newinstance = RootFactory.eINSTANCE.createComponentInstance();
		newinstance.setName(instance.getName());
		newinstance.setInstanceOf(instance.getInstanceOf().getDescriptorName());
		if(!instance.getPorts().isEmpty()){
			for(int i=0;i<instance.getPorts().size();i++){
				newinstance.getFields().add(convertPort(instance.getPorts().get(i)));
			}
		}
		/*
		if(!instance.getGenerics().isEmpty()){
			for(int i=0;i<instance.getGenerics().size();i++){
				newinstance.getFields().add(convertGeneric(instance.getGenerics().get(i)));
			}
		}
		*/
		if(!instance.getRefPlaces().isEmpty()){
			for(int i=0;i<instance.getRefPlaces().size();i++){
				newinstance.getPNStructureObjects().add(convertRefPlace(instance.getRefPlaces().get(i)));
			}
		}
		if(!instance.getRefTransitions().isEmpty()){
			for(int i=0;i<instance.getRefTransitions().size();i++){
				newinstance.getPNStructureObjects().add(convertRefTransition(instance.getRefTransitions().get(i)));
			}
		}
		return newinstance;
	}
	
	
	/**
	 * Donner mode du port selon mode de l'oldprojet port
	 * @param newport
	 * @param port
	 */
	private void setPortMode(VHDLPort newport, Port port){
		if(port.getMode().getValue()==0){
			newport.setMode(PortMode.IN);
		}
		if(port.getMode().getValue()==1){
			newport.setMode(PortMode.OUT);
		}
		if(port.getMode().getValue()==2){
			newport.setMode(PortMode.INOUT);
		}
	}

	private void setRefPlaceMode(petriNet.RefPlace newrefplace, RefPlace refplace){
		if(refplace.getMode().getValue()==0){
			newrefplace.setMode(PortMode.IN);
		}
		if(refplace.getMode().getValue()==1){
			newrefplace.setMode(PortMode.OUT);
		}
		if(refplace.getMode().getValue()==2){
			newrefplace.setMode(PortMode.INOUT);
		}
	}
	/**
	 * @param newrefTransition
	 * @param refTransition
	 */
	private void setRefTransitionMode(petriNet.RefTransition newrefTransition, RefTransition refTransition){
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
	
	private void setOperator(petriNet.Condition newcondition, Condition condition){
		if(condition.getOperator().getValue()==0){
			newcondition.setOperator(petriNet.Operator.ID);
		}
		if(condition.getOperator().getValue()==1){
			newcondition.setOperator(petriNet.Operator.NOT);
		}
	}
	
	private void setAction(petriNet.Place newplace, Action action){
		petriNet.Action newaction = PetriNetFactory.eINSTANCE.createAction();
		newaction.setName(action.getName());
		String VHDLActionName = action.getAction().getName();
		ArrayList<VHDLAction> listeAction = newprojet.getVHDLActions();
		for(int i=0;i<listeAction.size();i++){
			if(listeAction.get(i).getName().equals(VHDLActionName)){
				newaction.setScript_action(listeAction.get(i));
				newplace.getActions().add(newaction);
			}
		}
	}
	
	private void setFunction(petriNet.Transition newTransition, Function Function){
		petriNet.Function newFunction = PetriNetFactory.eINSTANCE.createFunction();
		newFunction.setName(Function.getName());
		String VHDLFunctionName = Function.getFunction().getName();
		ArrayList<VHDLFunction> listeFunction = newprojet.getVHDLFunctions();
		for(int i=0;i<listeFunction.size();i++){
			if(listeFunction.get(i).getName().equals(VHDLFunctionName)){
				newFunction.setScript_function(listeFunction.get(i));
				newTransition.getFunctions().add(newFunction);
			}
		}
	}
	
	private void setCondition(petriNet.Transition newTransition, Condition condition){
		petriNet.Condition newcondition = PetriNetFactory.eINSTANCE.createCondition();
		newcondition.setName(condition.getName());
		setOperator(newcondition,condition);
		String VHDLConditionName = condition.getCondition().getName();
		ArrayList<VHDLCondition> listeCondition = newprojet.getVHDLConditions();
		for(int i=0;i<listeCondition.size();i++){
			if(listeCondition.get(i).getName().equals(VHDLConditionName)){
				newcondition.setScript_condition(listeCondition.get(i));
				newTransition.getConditions().add(newcondition);
			}
		}
	}
	
	private void setTime(petriNet.Transition newtransition, Time time){
		petriNet.Time newtime = PetriNetFactory.eINSTANCE.createTime();
		newtime.setTmin(time.getTmin());
		newtime.setTmax(time.getTmax());
		newtime.setDescription(time.getDescription());
		if(!time.getDynamicTime().getTimes().isEmpty()){
			newtime.setScript_time(convertVHDLTime(time.getDynamicTime()));
		}
		newtransition.setTime(newtime);
	}
	
	
	private void setVHDLscript(VHDLElement vhdl, PNInterpretationElement pn){
		vhdl.setName(pn.getName());
		String script = pn.getBehaviourInterpretation().getScript();
		int begin = script.indexOf("is begin");
		int fin = script.lastIndexOf("end");
		String content = script.substring(begin+8, fin);
		vhdl.setContent(content);
	}
	
	private void setNode(petriNet.Arc newArc, Arc arc){
		String nameSource = arc.getSourceNode().getName();
		String nameTarget = arc.getTargetNode().getName();
		ArrayList<petriNet.Place> listePlace = newprojet.getPlaces();
		ArrayList<petriNet.Transition> listeTransition = newprojet.getTransitions();
		for(int i=0;i<listePlace.size();i++){
			if(listePlace.get(i).getName().equals(nameSource)){
				newArc.setSourceNode(listePlace.get(i));
			}
			if(listePlace.get(i).getName().equals(nameTarget)){
				newArc.setTargetNode(listePlace.get(i));
			}
		}
		for(int i=0;i<listeTransition.size();i++){
			if(listeTransition.get(i).getName().equals(nameSource)){
				newArc.setSourceNode(listeTransition.get(i));
			}
			if(listeTransition.get(i).getName().equals(nameTarget)){
				newArc.setTargetNode(listeTransition.get(i));
			}
		}
	}
	
	private void setField(field.Connection newconnection, hilecopComponent.BasicConnection connection){
		String nameInput = connection.getSourceField().getName();
		Boolean notfindInput = true;
		String nameOutput = connection.getTargetField().getName();
		Boolean notfindOutput = true;
		EList<field.Field> listefield = newroot.getComponent().getFields();
		for(int i=0;i<listefield.size()&&(notfindInput||notfindOutput);i++){
			if(listefield.get(i).getName().equals(nameInput)&&notfindInput){
				newconnection.setInputField(listefield.get(i));
			}
			if(listefield.get(i).getName().equals(nameOutput)&&notfindOutput){
				newconnection.setOutputField(listefield.get(i));
			}
		}
	}
}
