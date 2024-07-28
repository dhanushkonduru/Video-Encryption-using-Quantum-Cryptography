package com;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
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
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class Register extends JFrame{
	JPanel p1;
	JLabel l1,l2,l3,l4,l5;
	JTextField tf1,tf2,tf3,tf4,tf5;
	JButton b1;
	Font f1;
	JTextArea area;
	Login login;
public Register(Login log){
	super("New User Signup Screen");
	login = log;
	p1 = new JPanel();
	p1.setLayout(null);
	
	JPanel main = new JPanel();
	main.setLayout(new BorderLayout());

	f1 = new Font("Courier New",Font.BOLD,14);
	
	l1 = new JLabel("Username");
	l1.setFont(f1);
	l1.setBounds(50,50,100,30);
	p1.add(l1);
	tf1 = new JTextField();
	tf1.setFont(f1);
	tf1.setBounds(150,50,120,30);
	p1.add(tf1);

	l2 = new JLabel("Password");
	l2.setFont(f1);
	l2.setBounds(50,100,100,30);
	p1.add(l2);
	tf2 = new JPasswordField();
	tf2.setFont(f1);
	tf2.setBounds(150,100,120,30);
	p1.add(tf2);

	l3 = new JLabel("Contact No");
	l3.setFont(f1);
	l3.setBounds(50,150,100,30);
	p1.add(l3);
	tf3 = new JTextField();
	tf3.setFont(f1);
	tf3.setBounds(150,150,120,30);
	p1.add(tf3);

	l4 = new JLabel("Email ID");
	l4.setFont(f1);
	l4.setBounds(50,200,100,30);
	p1.add(l4);
	tf4 = new JTextField();
	tf4.setFont(f1);
	tf4.setBounds(150,200,120,30);
	p1.add(tf4);

	l5 = new JLabel("Address");
	l5.setFont(f1);
	l5.setBounds(50,250,100,30);
	p1.add(l5);
	tf5 = new JTextField();
	tf5.setFont(f1);
	tf5.setBounds(150,250,120,30);
	p1.add(tf5);

	b1 = new JButton("Sign up");
	b1.setFont(f1);
	b1.setBounds(70,300,100,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			register();
		}
	});

	
	getContentPane().add(p1,BorderLayout.CENTER);
}
public static boolean checkMail(String mailid){
	 boolean flag=false;
	 String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
	 Pattern p = Pattern.compile(regEx);
	 Matcher m = p.matcher(mailid);
	 if(m.find())
		flag=true;
	 else
		flag=false;
     return flag;
 }
public void register(){
	String user = tf1.getText();
	String password = tf2.getText();
	String contact = tf3.getText();
	String email = tf4.getText();
	String address = tf5.getText();
	if(user == null || user.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Username must be enter");
		tf1.requestFocus();
		return;
	}
	if(password == null || password.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Password must be enter");
		tf2.requestFocus();
		return;
	}
	if(contact == null || contact.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Contact no must be enter");
		tf3.requestFocus();
		return;
	}
	if(email == null || email.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Email id must be enter");
		tf4.requestFocus();
		return;
	}
	if(!checkMail(email)){
		JOptionPane.showMessageDialog(this,"Enter valid mailid");
		tf4.requestFocus();
		return;
	}
	if(address == null || address.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Address must be enter");
		tf5.requestFocus();
		return;
	}
	try{
		FileWriter fw = new FileWriter("lib/Users.txt",true);
		fw.write(user+","+password+","+contact+","+email+","+address+System.getProperty("line.separator"));
		fw.close();
		JOptionPane.showMessageDialog(this,"You can login now");
		setVisible(false);
		login.setVisible(true);
	}catch(Exception e){
		e.printStackTrace();
	}
}
}