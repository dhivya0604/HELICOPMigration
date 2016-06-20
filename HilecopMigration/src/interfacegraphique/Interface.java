/**
 * @author Dhivya
 */
package interfacegraphique;

import java.awt.Cursor;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import migration.MigrationDuProjet;


@SuppressWarnings("serial")
public class Interface extends JFrame implements ActionListener {

	private JFrame frame;
	private JFileChooser fc=new JFileChooser();
	private JTextField text1=new JTextField();
	private JTextField text2=new JTextField();
	private JLabel label1;
	private JLabel label2;
	private JButton b1=new JButton("Browse");
	private JButton b2=new JButton("Browse");
	private JButton migrer=new JButton("Migrate");
	//private JButton explorer=new JButton("FileExplorer");
	private ArrayList<File> listeFichier;

	FileExplorer f = new FileExplorer();

	JOptionPane jp=new JOptionPane();

	/**
	 * constructor
	 */
	public Interface()
	{
		frame = new JFrame();
		//frame.getContentPane().setBackground(SystemColor.yellow);
		frame.getContentPane().setForeground(SystemColor.desktop);
		frame.setSize(500,350);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setTitle("Hilecop Migration Tool");
		frame.getContentPane().setLayout(null );

		label1=new JLabel("choose an old project location");
		label1.setBounds(74, 40, 200, 14);
		frame.getContentPane().add(label1);

		label2 = new JLabel("choose a new project location");
		label2.setBounds(74, 102, 200, 14);
		frame.getContentPane().add(label2);
		frame.setIconImage(new ImageIcon("img/icon1.png").getImage());

		text1.setBounds(74, 62, 200, 20);
		frame.getContentPane().add(text1);
		text1.setColumns(10);

		b1.setBounds(300,62,100,25);
		frame.getContentPane().add(b1);

		b1.addActionListener(this);

		text2.setBounds(74, 120, 200, 20);

		frame.getContentPane().add(text2);
		text2.setColumns(10);

		b2.setBounds(300,120,100,25);
		frame.getContentPane().add(b2);
		b2.addActionListener(this);

		migrer.setBounds(150,200,100,25);
		frame.getContentPane().add(migrer);
		migrer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					migration();
				} catch (IOException e1) {
					e1.printStackTrace();
				}}
		});

		frame.setVisible(true);
	}


	/***getters and setters***/
	public JFileChooser getChemin() {
		return fc;
	}
	public JTextField getText1() {
		return text1;
	}
	public JTextField getText2() {
		return text2;
	}
	public JLabel getLabel1() {
		return label1;
	}
	public JLabel getLabel2() {
		return label2;
	}
	public JButton getB1() {
		return b1;
	}
	public JButton getB2() {
		return b2;
	}
	public JButton getStart() {
		return migrer;
	}

	public void setText1(String s) {
		text1.setText(s);
	}
	public void setText2(String s) {
		text2.setText(s);
	}

	/***Actions of buttons b1 and b2***/
	public void actionPerformed(ActionEvent e){
		int input = 0;
		fc.setCurrentDirectory(new java.io.File("C:/"));
		fc.setDialogTitle("Choose a file");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		if (fc.showOpenDialog(b1) == JFileChooser.APPROVE_OPTION){
			input=JOptionPane.showConfirmDialog(null, fc.getSelectedFile().getAbsolutePath(),"confirm", JOptionPane.YES_NO_OPTION);
		}

		if(input==0){
			if(e.getSource()==b1){
				b1.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				setText1(fc.getSelectedFile().getAbsolutePath());
				frame.setSize(600, 600);
				JTextArea jt=new JTextArea(10,10);
				jt.setLineWrap(true);
				jt.setWrapStyleWord(true);
				jt.setBounds(150,250,300,300);
				JScrollPane scroll=new JScrollPane(jt,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

				frame.getContentPane().add(jt);
				frame.getContentPane().add(scroll);
				listeFichier=f.parcours(backToSlash(text1.getText()));
				AddTextJpanel(jt, listeFichier);
				frame.setVisible(true);
			}
			else{
				setText2(fc.getSelectedFile().getAbsolutePath());
			}
		}
	}

	/**
	 * action for button "migrate"
	 * @throws IOException 
	 */
	public void migration() throws IOException{
		if(text1.getText().equals("")){
			JOptionPane.showMessageDialog(null,"old project location is Empty");
		}
		else if(text2.getText().equals("")){
			JOptionPane.showMessageDialog(null,"new project location is Empty");
		}
		else{
			MigrationDuProjet migtool = new MigrationDuProjet();
			for(int i=0;i<listeFichier.size();i++){
				if(listeFichier.get(i).getName().endsWith(".hilecopcomponent")){
					String locate = text2.getText()+"\\"+listeFichier.get(i).getParentFile().getName();
					File f1=new File(locate);
					f1.mkdir();
					String fichierpath = listeFichier.get(i).getAbsolutePath();
					System.out.println(fichierpath);
					migtool.migrationComposant(fichierpath, locate);
				}
			}
			JOptionPane.showMessageDialog(null,"migration is done!");
		}
	}




	public String backToSlash(String s) {
		return s.replace("\\", "/");
	}

	/*	public String SlashToDoubleSlash(String s) {
		StringBuilder sb=new StringBuilder(s);
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i)=='\\')
			{
				sb.append("\\");
			}

		}
		return sb.toString();
	}*/


	/***ajout de la contenu de ArrayList JTextArea****/
	public void AddTextJpanel(JTextArea jta, ArrayList<File>f){	
		for(int i=0;i<f.size();i++)
		{
			if(f.get(i).isDirectory())
			{
				jta.append("[projet]"+f.get(i).getName()+"\n");
			}
			else
			{jta.append("\t"+f.get(i).getName()+"\n");}

		}
	}
}

