package com;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import javax.crypto.SecretKey;
public class UserScreen extends JFrame{
	JPanel p1, p2;
	JButton b1, b2, b3;
	Font f1;
	JTextArea area;
	JScrollPane jsp;
	String user, pass;
	JFileChooser chooser, chooser1; 
	File file;
	QuantumEncryption qc = new  QuantumEncryption();
public UserScreen(String u, String p){
	super("User Screen");
	user = u;
	pass = p;
	f1 = new Font("Courier New",Font.BOLD,14);
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());
	area = new JTextArea();
	jsp = new JScrollPane(area);
	p1.add(jsp, BorderLayout.CENTER);
	area.setFont(f1);
	chooser = new JFileChooser(new File("testVideos"));
	chooser1 = new JFileChooser(new File("EncryptedVideos"));
	JPanel p2 = new JPanel();
	b1 = new JButton("Upload Video");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int result = chooser.showOpenDialog(UserScreen.this);
			if(result == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				area.setText("");
				area.append("Choosen Video File : "+file.getName()+"\n");
			}
		}
	});

	b2 = new JButton("Encrypt Video using Quantum Computing");
	b2.setFont(f1);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			encryptVideo();
		}
	});

	b3 = new JButton("Decrypt Video using Quantum Computing");
	b3.setFont(f1);
	p2.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int result = chooser1.showOpenDialog(UserScreen.this);
			if(result == JFileChooser.APPROVE_OPTION) {
				file = chooser1.getSelectedFile();
				decryptVideo();
			}
		}
	});
	
	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(p2,BorderLayout.NORTH);
}
public void encryptVideo() {
	try{
		File file_key = new File("keys/"+user+".ubr");
		if(!file_key.exists()) {
			qc.generateKeyStore(user, pass);
		}
		FileInputStream fin = new FileInputStream(file);
		byte b[] = new byte[fin.available()];
		fin.read(b,0,b.length);
		fin.close();
		SecretKey key = qc.loadSecretKeyFromKeyStore("keys/"+user+".ubr", pass);
		ArrayList<byte[]> encrypt = qc.encrypt(key, b);
		ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream("EncryptedVideos/"+file.getName()));
		fout.writeObject(encrypt);
		fout.close();
		area.append(file.getName()+" encrypted video saved inside EncryptedVideos folder\n");
	}catch(Exception e) {
		e.printStackTrace();
	}
}

public void decryptVideo() {
	try{
		ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
		Object obj = (Object)oin.readObject();
		ArrayList<byte[]> encrypt = (ArrayList<byte[]>)obj;
		SecretKey key = qc.loadSecretKeyFromKeyStore("keys/"+user+".ubr", pass);
		byte decrypt[] = qc.decrypt(key, encrypt.get(0), encrypt.get(1));
		FileOutputStream fout = new FileOutputStream("decrypt.avi");
		fout.write(decrypt, 0, decrypt.length);
		fout.close();
		area.append("Decrypted Video saved as 'decrypt.avi'\n");
	}catch(Exception e) {
		e.printStackTrace();
	}
}
}