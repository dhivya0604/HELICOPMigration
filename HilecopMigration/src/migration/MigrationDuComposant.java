package migration;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;

import field.*;
import hilecopComponent.*;
import hilecopComponent.Field;
import petriNet.Node;
import petriNet.PetriNetFactory;
import root.HilecopRoot;
import script.ScriptFactory;
import script.VHDLAction;
import script.VHDLCondition;
import script.VHDLFunction;
import script.VHDLTime;

public class MigrationDuComposant {

	private ProjetNouveau newprojet;
	private HilecopRoot newroot;
	private ProjetAncien origiprojet;
	private HilecopComponentDesignFile designfile;

	public MigrationDuComposant(ProjetNouveau nouveau,ProjetAncien ancien){
		newprojet = nouveau;
		newroot = nouveau.getRoot();
		origiprojet = ancien;
		designfile = ancien.getRoot();
	}

	public void migeration(){
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

		int nbPlace = origiprojet.getPlace().size();
		System.out.println("Number of Place : " + nbPlace);
		for(int i=0;i<nbPlace;i++){
			Place place = origiprojet.getPlace().get(i);
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
		
		int nbTransition = origiprojet.getTransition().size();
		System.out.println("Number of Transition : " + nbTransition);
		for(int i=0;i<nbTransition;i++){
			Transition Transition = origiprojet.getTransition().get(i);
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
		
		int nbBasicArc = origiprojet.getBasicArc().size();
		System.out.println("Number of BasicArc : " + nbBasicArc);
		for(int i=0;i<nbBasicArc;i++){
			BasicArc BasicArc = origiprojet.getBasicArc().get(i);
			newroot.getComponent().getPNStructureObjects().add(convertBasicArc(BasicArc));
			System.out.println("BasicArc " + (i+1) +" is migrated");
		}
		int nbTestArc = origiprojet.getTestArc().size();
		System.out.println("Number of TestArc : " + nbTestArc);
		for(int i=0;i<nbTestArc;i++){
			TestArc TestArc = origiprojet.getTestArc().get(i);
			newroot.getComponent().getPNStructureObjects().add(convertTestArc(TestArc));
			System.out.println("TestArc " + (i+1) +" is migrated");
		}
		int nbInhibitorArc = origiprojet.getInhibitorArc().size();
		System.out.println("Number of InhibitorArc : " + nbInhibitorArc);
		for(int i=0;i<nbInhibitorArc;i++){
			InhibitorArc InhibitorArc = origiprojet.getInhibitorArc().get(i);
			newroot.getComponent().getPNStructureObjects().add(convertInhibitorArc(InhibitorArc));
			System.out.println("InhibitorArc " + (i+1) +" is migrated");
		}
		int nbFusionArc = origiprojet.getFusionArc().size();
		System.out.println("Number of FusionArc : " + nbFusionArc);
		for(int i=0;i<nbFusionArc;i++){
			FusionArc FusionArc = origiprojet.getFusionArc().get(i);
			newroot.getComponent().getPNStructureObjects().add(convertFusionArc(FusionArc));
			System.out.println("FusionArc " + (i+1) +" is migrated");
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

	private petriNet.Place convertPlace(Place place){
		petriNet.Place newplace = PetriNetFactory.eINSTANCE.createPlace();
		newplace.setName(place.getName());
		newplace.setMarking(Integer.parseInt(place.getMarkupExpression()));
		//add actions
		EList<PNEntityInterpretation> listeInterpretation = place.getInterpretation();
		for(int i=0;i<listeInterpretation.size();i++){
			if(listeInterpretation.get(i).getClass().equals("class hilecopComponent.impl.ActionImpl"))
			/**
			 * @TODO ifelse?
			 */
			{
				Action action =  (Action) listeInterpretation.get(i);
				newplace.getActions().add(convertAction(action));
			}
		}
		/**
		 * @TODO chaque fois "add to liste Action dans nouveau root?"
		 */
		
		return newplace;
	}

	private petriNet.RefPlace convertRefPlace(RefPlace refplace){
		petriNet.RefPlace newrefplace = PetriNetFactory.eINSTANCE.createRefPlace();
		newrefplace.setName(refplace.getName());
		setRefPlaceMode(newrefplace, refplace);
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
		EList<PNEntityInterpretation> listeInterpretation = transition.getInterpretation();
		for(int i=0;i<listeInterpretation.size();i++){
			String interpretationtype = listeInterpretation.get(i).getClass().toString();
			if(interpretationtype.equals("class hilecopComponent.impl.ConditionImpl"))
			{
				Condition condition =  (Condition) listeInterpretation.get(i);
				newtransition.getConditions().add(convertCondition(condition));
			}
			if(interpretationtype.equals("class hilecopComponent.impl.FunctionImpl"))
			{
				Function function =  (Function) listeInterpretation.get(i);
				newtransition.getFunctions().add(convertFunction(function));
			}
			if(interpretationtype.equals("class hilecopComponent.impl.TimeImpl"))
			{
				Time time =  (Time) listeInterpretation.get(i);
				newtransition.setTime(convertTime(time));
				/**
				 * TODO s'il y plusieurs times dans l'oldprojet?
				 */
			}
		}
		return newtransition;
	}

	private petriNet.RefTransition convertRefTransition(RefTransition reftransition){
		petriNet.RefTransition newreftransition = PetriNetFactory.eINSTANCE.createRefTransition();
		newreftransition.setName(reftransition.getName());
		setRefTransitionMode(newreftransition, reftransition);		
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
	
	private petriNet.Action convertAction(Action action){
		petriNet.Action newaction = PetriNetFactory.eINSTANCE.createAction();
		newaction.setName(action.getName());
		newaction.setScript_action(getVHDLAction(action.getAction()));
		return newaction;
	}
	
	private petriNet.Condition convertCondition(Condition condition){
		petriNet.Condition newcondition = PetriNetFactory.eINSTANCE.createCondition();
		newcondition.setName(condition.getName());
		newcondition.setScript_condition(getVHDLCondition(condition.getCondition()));
		setOperator(newcondition,condition);
		return newcondition;
	}

	private petriNet.Function convertFunction(Function function){
		petriNet.Function newfunction = PetriNetFactory.eINSTANCE.createFunction();
		newfunction.setName(function.getName());
		newfunction.setScript_function(getVHDLFunction(function.getFunction()));
		return newfunction;
	}
	
	private petriNet.Time convertTime(Time time){
		petriNet.Time newtime = PetriNetFactory.eINSTANCE.createTime();
		newtime.setTmin(time.getTmin());
		newtime.setTmax(time.getTmax());
		newtime.setDescription(time.getDescription());
		/**
		 * TODO time.getDynamicTime()!=null ?
		 */
		newtime.setScript_time(getVHDLTime(time.getDynamicTime()));
		return newtime;
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
	/**
	 * Donner mode du port selon mode de l'oldprojet port
	 * @param newport
	 * @param port
	 */
	private void setPortMode(VHDLPort newport, Port port){
		/**
		 * TODO comparer les autres champs?
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
		/**
		 * TODO peut = ConvertPortMode?
		 */
	}
	/**
	 * TODO comment utiliser un method pour port/place/transition
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
	
	/**
	 * TODO utilise un method pour les 3
	 * @param pnaction
	 * @return
	 */
	private VHDLAction getVHDLAction(PNAction pnaction){
		VHDLAction vhdlAction = ScriptFactory.eINSTANCE.createVHDLAction();
		vhdlAction.setName(pnaction.getName());
		String script = pnaction.getBehaviourInterpretation().getScript();
		int begin = script.indexOf("is begin");
		int fin = script.lastIndexOf("end");
		String content = script.substring(begin+8, fin);
		vhdlAction.setContent(content);
		return vhdlAction;
	}

	private VHDLCondition getVHDLCondition(PNCondition pnCondition){
		VHDLCondition vhdlCondition = ScriptFactory.eINSTANCE.createVHDLCondition();
		vhdlCondition.setName(pnCondition.getName());
		String script = pnCondition.getBehaviourInterpretation().getScript();
		int begin = script.indexOf("is begin");
		int fin = script.lastIndexOf("end");
		String content = script.substring(begin+8, fin);
		vhdlCondition.setContent(content);
		return vhdlCondition;
	}
	
	private VHDLFunction getVHDLFunction(PNFunction pnFunction){
		VHDLFunction vhdlFunction = ScriptFactory.eINSTANCE.createVHDLFunction();
		vhdlFunction.setName(pnFunction.getName());
		String script = pnFunction.getBehaviourInterpretation().getScript();
		int begin = script.indexOf("is begin");
		int fin = script.lastIndexOf("end");
		String content = script.substring(begin+8, fin);
		vhdlFunction.setContent(content);
		return vhdlFunction;
	}
	
	private VHDLTime getVHDLTime(PNTime pntime){
		VHDLTime vhdlTime = ScriptFactory.eINSTANCE.createVHDLTime();
		vhdlTime.setName(pntime.getName());
		String script = pntime.getBehaviourInterpretation().getScript();
		int begin = script.indexOf("is begin");
		int fin = script.lastIndexOf("end");
		String content = script.substring(begin+8, fin);
		vhdlTime.setContent(content);
		return vhdlTime;
	}
	
	private void setNode(petriNet.Arc newArc, Arc arc){
		String nameSource = arc.getSourceNode().getName();
		String nameTarget = arc.getTargetNode().getName();
		ArrayList<Place> listePlace = origiprojet.getPlace();
		ArrayList<Transition> listeTransition = origiprojet.getTransition();
		/**
		 * TODO besoin Ref ?
		 */
		for(int i=0;i<listePlace.size();i++){
			if(listePlace.get(i).getName().equals(nameSource)){
				newArc.setSourceNode((Node) listePlace.get(i));
			}
			if(listePlace.get(i).getName().equals(nameTarget)){
				newArc.setTargetNode((Node) listePlace.get(i));
			}
		}
		for(int i=0;i<listeTransition.size();i++){
			if(listeTransition.get(i).getName().equals(nameSource)){
				newArc.setSourceNode((Node) listeTransition.get(i));
			}
			if(listeTransition.get(i).getName().equals(nameTarget)){
				newArc.setTargetNode((Node) listeTransition.get(i));
			}
		}
	}
	
}
