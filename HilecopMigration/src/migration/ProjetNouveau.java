/**
 * @author shui
 */
package migration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import field.*;
import root.*;
import script.VHDLAction;
import script.VHDLCondition;
import script.VHDLElement;
import script.VHDLFunction;
import script.VHDLTime;

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
	
	public HilecopRoot getRoot(){
		return newroot;
	}

	public ArrayList<VHDLAction> getVHDLActions(){
		EList<VHDLElement> listeVHDL = newroot.getComponent().getVHDLElements();
		ArrayList<VHDLAction> listeAction = new ArrayList<VHDLAction>();
		for(int i=0;i<listeVHDL.size();i++){
			if(listeVHDL.get(i).getClass().toString().equals("class hilecopComponent.impl.PNActionImpl")){
				listeAction.add((VHDLAction) listeVHDL.get(i));
			}
		}
		return listeAction;
	}
	
	public ArrayList<VHDLCondition> getVHDLConditions(){
		EList<VHDLElement> listeVHDL = newroot.getComponent().getVHDLElements();
		ArrayList<VHDLCondition> listeCondition = new ArrayList<VHDLCondition>();
		for(int i=0;i<listeVHDL.size();i++){
			if(listeVHDL.get(i).getClass().toString().equals("class hilecopComponent.impl.VHDLConditionImpl")){
				listeCondition.add((VHDLCondition) listeVHDL.get(i));
			}
		}
		return listeCondition;
	}
	
	public ArrayList<VHDLFunction> getVHDLFunctions(){
		EList<VHDLElement> listeVHDL = newroot.getComponent().getVHDLElements();
		ArrayList<VHDLFunction> listeFunction = new ArrayList<VHDLFunction>();
		for(int i=0;i<listeVHDL.size();i++){
			if(listeVHDL.get(i).getClass().toString().equals("class hilecopComponent.impl.VHDLFunctionImpl")){
				listeFunction.add((VHDLFunction) listeVHDL.get(i));
			}
		}
		return listeFunction;
	}
	
	public ArrayList<VHDLTime> getVHDLTimes(){
		EList<VHDLElement> listeVHDL = newroot.getComponent().getVHDLElements();
		ArrayList<VHDLTime> listeTime = new ArrayList<VHDLTime>();
		for(int i=0;i<listeVHDL.size();i++){
			if(listeVHDL.get(i).getClass().toString().equals("class hilecopComponent.impl.VHDLTimeImpl")){
				listeTime.add((VHDLTime) listeVHDL.get(i));
			}
		}
		return listeTime;
	}
	public ArrayList<petriNet.Place> getPlaces(){
		EList<petriNet.PNEntity> pn = newroot.getComponent().getPNStructureObjects();
		ArrayList<petriNet.Place> listeplace = new ArrayList<petriNet.Place>();
		for(int i=0;i<pn.size();i++){
			if(pn.get(i).getClass().toString().equals("class hilecopComponent.impl.PlaceImpl")){
				listeplace.add((petriNet.Place) pn.get(i));
			}
		}
		return listeplace;
	}

	public ArrayList<petriNet.Transition> getTransitions(){
		EList<petriNet.PNEntity> pn = newroot.getComponent().getPNStructureObjects();
		ArrayList<petriNet.Transition> listeTransition = new ArrayList<petriNet.Transition>();
		for(int i=0;i<pn.size();i++){
			if(pn.get(i).getClass().toString().equals("class hilecopComponent.impl.TransitionImpl")){
				listeTransition.add((petriNet.Transition) pn.get(i));
			}
		}
		return listeTransition;
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


