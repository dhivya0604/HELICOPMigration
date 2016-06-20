package interfacegraphique;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class FileExplorer{
	
	public ArrayList<File> liste_fichiers;
	
	public FileExplorer()
	{
		liste_fichiers = new ArrayList<File>();
	}
	
	/***parcours de repertoire***/
	public void parcours(String path)
	{	
	File file=new File (path);
		File[] Lrep=file.listFiles();
		int size=Lrep.length;
			for(int i=0;i<size;i++)
			{
			if(Lrep[i].isDirectory()==true)
				{
					parcours(Lrep[i].getAbsolutePath());
						int j=0;
						 File[] Lfiles=Lrep[i].listFiles();
						 if(Lfiles!=null){
							 
							 for(j=0;j<Lfiles.length;j++){
							 {if(Lfiles[j].getName().contains(".project")==true)
							 { if(test_projet_hilecop(Lfiles[j]))
							 	{
								 //test de contenu
								 //System.out.println("repertoire:  "+Lrep[i].getName());
							 	
								 liste_fichiers.add(Lrep[i]);
							 	for(int k=0;k<Lfiles.length;k++){
							 		if(Lfiles[k].getName().endsWith(".hilecopcomponent") && !(Lfiles[k].getName().equals(".metadata") )){
							 			liste_fichiers.add(Lfiles[k]);
							 		}	
							 	
							 	}
							 
							 	}
							 	}
							
						 }
					}
				}
							
						 			
		}
						 }
	}
	/*public void parcours(String path)
	{
		
	File file=new File (path);
		File[] Lrep=file.listFiles();
		
	//	DefaultMutableTreeNode noeud;
	
			for(int i=0;i<Lrep.length;i++)
			{//noeud= new DefaultMutableTreeNode(Lrep[i].getName());

			if(Lrep[i].isDirectory()==true)
				{ 						System.out.println("repertoire"+Lrep[i].getName());
						//System.out.println("noeud"+noeud);
						//root.add(noeud);
						parcours(Lrep[i].getAbsolutePath());
						//DefaultMutableTreeNode noeud1=new DefaultMutableTreeNode(Lrep[i].getName());
						//noeud.add(noeud);
					
						int j=0;
						 File[] Lfiles=Lrep[i].listFiles();
						 if(Lfiles!=null){
							 
							 for(j=0;j<Lfiles.length;j++){
							 {if(Lfiles[j].getName().contains(".project")==true)
							 	{
								 	System.out.println("fichier"+Lfiles[j].getName());
								 	//noeud.add(new DefaultMutableTreeNode(Lfiles[j].getName()));
								 	
							 	}
							// }
							 
						 }
					}
				}
			if(i==Lrep.length-3)
			{
				tree=new JTree(root);
				p1.add(tree);
				f.add(p1);
				f.setBounds(100, 100, 446, 351);
				f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				f.setVisible(true);
			}
		}}
	}*/
	
	private boolean test_projet_hilecop(File file) {
		// TODO Auto-generated method stub
		BufferedReader reader;
		try
		{
			InputStream is=new FileInputStream(file);
			reader=new BufferedReader(new InputStreamReader(is));
			
			String line=reader.readLine();
			while(line!=null)
			{
				
				if(line.contains("fr.demar.hilecop.component.editor.HilecopNature"))
				{
					return true;
				}
				line=reader.readLine();
			}
		}
		catch(IOException e)
		{
			//e.printStackTrace();
		}
		return false;
	}

	public void afficheArrayList(ArrayList<String> v)
	{
		for(int d=0;d<v.size();d++)
		{
				System.out.println(v.get(d));
		
		}
	}

}