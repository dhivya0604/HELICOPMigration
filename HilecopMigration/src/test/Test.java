package test;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import field.Connection;
import field.Field;
import field.Mode;
import field.PortMode;
import field.VHDLGeneric;
import field.VHDLPort;
import field.VHDLSignal;
import hilecopComponent.BehaviourField;
import hilecopComponent.ComponentDescriptor;
import hilecopComponent.Constant;
import hilecopComponent.Generic;
import hilecopComponent.HilecopComponentDesignFile;
import hilecopComponent.PNAction;
import hilecopComponent.PNCondition;
import hilecopComponent.PNFunction;
import hilecopComponent.PNInterpretation;
import hilecopComponent.PNTime;
import hilecopComponent.PetriNetComponentBehaviour;
import hilecopComponent.Port;
import hilecopComponent.RefPlace;
import hilecopComponent.RefTransition;
import hilecopComponent.Signal;
import interfacegraphique.Interface;
import petriNet.PNEntity;
import root.HilecopComponent;
import root.HilecopRoot;

public class Test {
	ArrayList<String> ErreurList;
	
	public Test()
	{
		ErreurList=new ArrayList<>();
	}
	
	public HilecopRoot open(String path)
	{
		System.out.println("Open "+path);
		ResourceSet ancienResourceSet = new ResourceSetImpl();
		ancienResourceSet.getPackageRegistry().put(root.RootPackage.eNS_URI,root.RootPackage.eINSTANCE);
		File fic = new File(path);
System.out.println(fic);		
		URI ancienfileURI = URI.createFileURI(path);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("root",new XMIResourceFactoryImpl());
		Resource res2 = ancienResourceSet.getResource(ancienfileURI, true);
		HilecopRoot designfile = (HilecopRoot) res2.getContents().get(0);
		return designfile;
	}
	
	public HilecopComponentDesignFile read(String path){
		ResourceSet ancienResourceSet = new ResourceSetImpl();
		ancienResourceSet.getPackageRegistry().put(hilecopComponent.HilecopComponentPackage.eNS_URI,hilecopComponent.HilecopComponentPackage.eINSTANCE);
		URI ancienfileURI = URI.createFileURI(path);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("hilecopcomponent",new XMIResourceFactoryImpl());
		Resource res2 = ancienResourceSet.getResource(ancienfileURI, true);
		HilecopComponentDesignFile designfile = (HilecopComponentDesignFile) res2.getContents().get(0);
		return designfile;
	}
	//parcours ancien modele:done
	///trouver le bon element(name)(fct apart):done
	
	public void TestField(HilecopRoot root, HilecopComponentDesignFile designfile)
	{
		HilecopComponent newHelicopComponent=root.getComponent();
	
	int cFieldsSize=newHelicopComponent.getFields().size();
	//size=getpublic+getprivate
	int designfileFieldsSize=designfile.getHilecopComponent().getPorts().size();
	ComponentDescriptor anciennComponent=designfile.getHilecopComponent();
	int i=0;
	
	assert cFieldsSize == designfileFieldsSize;
	
		while(i<cFieldsSize && i<designfileFieldsSize)
		{
			Field f=newHelicopComponent.getFields().get(i);
			
		
			if(f instanceof VHDLPort)
			{	
				VHDLPort port = (VHDLPort) f;
				Port oport=PortMatchbyName(anciennComponent, port.getName());
				//trouver le bon port(name)
				assert port.getName()== oport.getName(): ErreurList.add("portname is mismatched !!");
				assert port.getType() == oport.getType(): ErreurList.add("port type is mismatched");
				
				if(TestPortMode(oport,port)) System.out.println("ok");
				else ErreurList.add("port type is mismatched");
				
				assert port.getDefaultValue()== oport.getDefaultValue(): "default Value is mismateched ";
			}
			
			else if( f instanceof VHDLSignal )
			{
				
				VHDLSignal sig=(VHDLSignal) f;
				Signal s=SignalMatchbyName(anciennComponent, sig.getName());
				if(s!=null){
				assert sig.getName()==s.getName() : ErreurList.add( "signal name is mismatched");
				assert sig.getType()==s.getType() : ErreurList.add("signal type is mismatched");
				assert sig.getDefaultValue()==s.getDefaultValue() : ErreurList.add("signal default value is mismatched ");
				}
				//assert s.getInputConnections() == sig.getInputConnections():" "
				
			}
			
			
			else if(f instanceof Constant)
			{Constant c=(Constant) f;
				TestConstant(c,anciennComponent);
			}
			
			
			
			else if( f instanceof VHDLGeneric)
			{	
				
				VHDLGeneric g= (VHDLGeneric) f;
				Generic gen=GenericMatchbyName(anciennComponent, g.getName());
				assert g.getName()== gen.getName() :"genereic Name is mismatched";
				assert g.getType() == gen.getType(): "generic Type is mismatched";
				assert g.getDefaultValue() == gen.getDefaultValue() : "generic DefaultValue is mismatched";
				//??assert g.getInputConnections() == (Connection)gen.getInputConnections();
				
			}
			
			
			else if( f instanceof RefPlace)
			{
				
				RefPlace rpl=(RefPlace) f;
				RefPlace rp=RefPlaceMatchbyName(anciennComponent, f.getName());
				assert rpl.getName() == rp.getName(): "RefPlace name is mismatching";
				assert rpl.getPlace() == rp.getPlace(): "refPlace place is mismatching";
				assert rpl.getInputArcs()==rp.getInputArcs(): "refPlace inputArcs is mismatching";
				assert rpl.getOutputArcs() == rp.getOutputArcs(): "refplace outputArcs is mismatching";
				//type???
				//rp.get
				assert rpl.getMode() == rp.getMode(): "refplace Mode is mismatching";
				
			}
			
			else if(f instanceof RefTransition)
			{
				
				RefTransition rtr=(RefTransition) f;
				RefTransition rt=RefTransitionMatchbyName(anciennComponent, rtr.getName());
				
				assert rtr.getName()==rt.getName();
				assert rtr.getInputArcs()==rt.getInputArcs();
				assert rtr.getOutputArcs() == rt.getOutputArcs();
				
				//type
				//place
				assert rtr.getMode()== rt.getMode();
				
				
			}
			
		}i++;
	}
	
	
	public boolean  TestPortMode(Port ancienPort, VHDLPort nouveauPort)
	{
		return nouveauPort.getMode().equals(ancienPort.getMode());
	}
	
	
	public boolean TestConstant(Constant newConstant, ComponentDescriptor oldc)
	{ int PrivateFieldssize=oldc.getComponentBehaviour().getPrivateFields().size();
		
		int i=0;
		while(i<PrivateFieldssize)
		{BehaviourField bf=oldc.getComponentBehaviour().getPrivateFields().get(i);
			//Field f=newc.getFields().get(i);
		if(bf instanceof Constant )
			{Constant oc= (Constant) bf;
			Constant nc= ConstantMatchbyName(oldc, newConstant.getName());
				if(oc.equals(nc))
				{
				assert oc.getName()== newConstant.getName():ErreurList.add("ConstantName mismatched ");
				assert oc.getType()==newConstant.getType():ErreurList.add("ConstantType mismatched  ");
				assert oc.getDefaultValue()==newConstant.getDefaultValue():ErreurList.add("ConstantDefaultValue mismatched ");
					return true;
				}
			}
			
		i++;
		}
	
		return false;
	}
	
	public void TestPnStructure(HilecopRoot root, HilecopComponentDesignFile designfile)
	{PetriNetComponentBehaviour pcb=(PetriNetComponentBehaviour) designfile.getHilecopComponent().getComponentBehaviour();
	//root.getComponent().getVHDLElements();
	
	}

	
	// recherche du nom d'un compnant dans l'ancien modele
	public Port PortMatchbyName(ComponentDescriptor hcd, String newPortName)
	{Port resPort=null;
		for(Port p:hcd.getPorts())
		{
			if(p.getName().equals(newPortName))
			{
				resPort=p;
				return resPort;
			}
		}
		return resPort;
	}
	
	public Signal SignalMatchbyName(ComponentDescriptor hcd, String newSignalName)
	{
		Signal resSignal=null;
		for(Signal s:hcd.getSignals())
		{
			if(s.getName().equals(newSignalName))
			{
				resSignal=s;
			}
		}
		return resSignal;
	}

	public Generic GenericMatchbyName(ComponentDescriptor hcd, String newGenericName)
	{
		Generic ResGeneric=null;
		
		for(Generic g:hcd.getGenerics())
		{
			if(g.getName().equals(newGenericName))
			{
				ResGeneric=g;
			}
		}
		return ResGeneric;
	}

	public RefPlace RefPlaceMatchbyName(ComponentDescriptor hcd, String newRefPlaceName)
	{
		RefPlace ResRefPlace=null;
		for(RefPlace rp:hcd.getRefPlaces())
		{
			if(rp.getName().equals(newRefPlaceName))
			{
				ResRefPlace=rp;
			}
		}
		
		return ResRefPlace;
	}
	
public RefTransition RefTransitionMatchbyName(ComponentDescriptor hcd, String newRefTransitionName)
{
	RefTransition ResRefTransition=null;
	for(RefTransition r:hcd.getRefTransitions())
	{
		if(r.getName().equals(newRefTransitionName))
		{
			ResRefTransition=r;
		}
	}return ResRefTransition;
}

public Constant ConstantMatchbyName(ComponentDescriptor hcd, String newConstantName)
{
	Constant ResConstant=null;
	for(BehaviourField bf:hcd.getComponentBehaviour().getPrivateFields())
	{
		if(bf instanceof Constant)
		{Constant c=(Constant) bf;
			if(c.getName().equals(newConstantName))
			{
				ResConstant=c;
			}
		}
	}return ResConstant;
}
	
	public PNFunction FunctionnMatchbyName(PNInterpretation pi, String newPNFunctionName)
	{PNFunction resFunction=null;
	//PetriNetComponentBehaviour pcb=(PetriNetComponentBehaviour)hcd.getComponentBehaviour();
		for(PNFunction pnf: pi.getFunctions())
		{
			if(pnf.getName().equals(newPNFunctionName))
				resFunction=pnf;
		}

		return resFunction;
		
	}
	
	public PNAction ActionMatchbyName(PNInterpretation pi, String newPNActionName)
	{PNAction resAction=null;
	//PetriNetComponentBehaviour pcb=(PetriNetComponentBehaviour)hcd.getComponentBehaviour();
		for(PNAction pnf: pi.getActions())
		{
			if(pnf.getName().equals(newPNActionName))
				resAction=pnf;
		}

		return resAction;
		
	}
	
	
	public PNCondition ConditionMatchbyName(PNInterpretation pi, String newPNActionName)
	{PNCondition resCondition=null;
	//PetriNetComponentBehaviour pcb=(PetriNetComponentBehaviour)hcd.getComponentBehaviour();
		for(PNCondition pnf: pi.getConditions())
		{
			if(pnf.getName().equals(newPNActionName))
				resCondition=pnf;
		}

		return  resCondition;
		
	}
	
	public PNTime TimeMatchbyName(PNInterpretation pi, String newPNTimeName)
	{PNTime resCondition=null;
	//PetriNetComponentBehaviour pcb=(PetriNetComponentBehaviour)hcd.getComponentBehaviour();
		for(PNTime pnf:pi.getTimes() )
		{
			if(pnf.getName().equals(newPNTimeName))
				resCondition=pnf;
		}

		return  resCondition;
		
	}
	
	
//les parcours
	public void ParcoursField(HilecopComponentDesignFile designfile)

	{
		int designfileFieldsSize=designfile.getHilecopComponent().getPublicFields().size();
		int s=designfile.getHilecopComponent().getComponentBehaviour().getPrivateFields().size();
		int tSize=designfileFieldsSize+s;
		
		System.out.println(tSize);
		
					
					for(Port p:designfile.getHilecopComponent().getPorts() )
					{System.out.println("\n"+"PORT ");
						System.out.println(p.getName());
						System.out.println(p.getType());
						System.out.println(p.getMode());
						System.out.println(p.getDefaultValue());
					}
				
					for(Signal s1:designfile.getHilecopComponent().getSignals() )
					{System.out.println("\n"+"Signal ");
						System.out.println(s1.getName());
						System.out.println(s1.getType());
						System.out.println(s1.getDefaultValue());
					}
				
					
					for(BehaviourField b: designfile.getHilecopComponent().getComponentBehaviour().getPrivateFields())
					{ 
						if(b instanceof Constant)
						{Constant cb=(Constant) b;
						System.out.println("\n"+"Constant");
							System.out.println(cb.getName());
							System.out.println(cb.getType());
							System.out.println(cb.getDefaultValue());
						}
					}
					
					for(Generic g:designfile.getHilecopComponent().getGenerics())
					{System.out.println("\n"+"Generic ");
						System.out.println(g.getName());
						System.out.println(g.getDefaultValue());
						System.out.println(g.getType());
						
					}
				
					for(RefPlace rf:designfile.getHilecopComponent().getRefPlaces())
					{System.out.println("\n"+"RefPlace ");
						System.out.println(rf.getName());
						System.out.println(rf.getPlace());
						System.out.println(rf.getMode());
						//type
					}
				
					for(RefTransition rt: designfile.getHilecopComponent().getRefTransitions())
					{System.out.println("\n"+"RefTransition ");
						System.out.println(rt.getName());
						System.out.println(rt.getMode());
						
						
					}
		
		
	}
	
	

	public void ParcoursPnSructureObjects(HilecopComponentDesignFile designfile)
	{
		System.out.println("Parcours PNStructure...");
		
		PetriNetComponentBehaviour pcb=(PetriNetComponentBehaviour) designfile.getHilecopComponent().getComponentBehaviour();
		
		
			for(PNAction pns:pcb.getInterpretation().getActions())
			{
				System.out.println("\n"+"Action :");
				System.out.println(pns.getName());
				System.out.println(pns.getINOUTs());
				System.out.println(pns.getOUTs());
				System.out.println(pns.getSensibilityIN());
				System.out.println(pns.getSensibilityOUT());
				System.out.println(pns.getSpecificReset());
			}
			
			for(PNFunction pnf:pcb.getInterpretation().getFunctions())
			{
				System.out.println("\n"+"Function");
				System.out.println(pnf.getName());
				System.out.println(pnf.getINOUTs());
				System.out.println(pnf.getOUTs());
				System.out.println(pnf.getINOUTs());
			}
			
			for(PNCondition pnc:pcb.getInterpretation().getConditions())
			{	
				System.out.println("\n"+"Condition");
				System.out.println(pnc.getName());
				System.out.println(pnc.getINOUTs());
				System.out.println(pnc.getOUTs());
				System.out.println(pnc.getSensibilityIN());
				System.out.println(pnc.getSensibilityOUT());
				System.out.println(pnc.getINs());
			}
			for(PNTime pnt: pcb.getInterpretation().getTimes())
			{
				System.out.println(pnt.getName());
				System.out.println(pnt.getName());
				System.out.println(pnt.getINOUTs());
				System.out.println(pnt.getOUTs());
				System.out.println(pnt.getSensibilityIN());
				System.out.println(pnt.getSensibilityOUT());
				System.out.println(pnt.getINs());
			}
		
		
		
		
		}
	
	
	public void ParcoursRoot(HilecopRoot root)
	{
		HilecopComponent c=root.getComponent();
		//HilecopComponentDesignFile hc=(HilecopComponentDesignFile) designfile.getHilecopComponent();
		
		int cFieldsSize=c.getFields().size();
	
		
		for(Field field : c.getFields()) {
		
			if(field instanceof VHDLPort) {
				VHDLPort port = (VHDLPort) field;
				System.out.println("port mode :"+port.getMode());
				System.out.println("port Name :"+port.getName());
				System.out.println("port mode :"+port.getDefaultValue());
				System.out.println("port mode :"+port.getMode());
			}
		}
		

		
		int nbConnections=c.getConnections().size();
		
		if(nbConnections!=0)
		{
			for(int i=0;i<nbConnections;i++)
			{
				System.out.println("connection Name"+c.getConnections().get(i).getName()+"\n");
				System.out.println("connection InputField"+c.getConnections().get(i).getInputField().getName());
				
			}
		}
		
		int nbPNObjects=c.getPNStructureObjects().size();
		if(nbPNObjects!=0)
		{
			for(int i=0;i<nbPNObjects;i++)
			{
				System.out.println("connection Name"+c.getPNStructureObjects().get(i)+"\n");	
			}
		}
		
		int nbInstances=c.getListOfInstances().size();
		if(nbInstances!=0)
		{
			for(int i=0;i<nbInstances;i++)
			{
				System.out.println("connection Name"+c.getListOfInstances().get(i).getName()+"\n");	
				//System.out.println("connection Name"+c.getListOfInstances().get(i).get()+"\n");	
			}
		}
		
		int nb=c.getVHDLElements().size();
		//action???
		//c.getVHDLElements().get(0).get
		
	}

	public static void main(String[] args) {
		
	Test t=new Test();
	t.ParcoursField(t.read("C:/Users/dhivyalaptop/Desktop/stage/ex_workspace/Switch_Led/Switch_Led/Switch_Led/Switch_Led.hilecopcomponent"));
	//t.ParcoursPnSructureObjects(t.read("C:/Users/dhivyalaptop/Desktop/stage/ex_workspace/Switch_Led/Switch_Led/Switch_Led/Switch_Led.hilecopcomponent"));
	}
}
