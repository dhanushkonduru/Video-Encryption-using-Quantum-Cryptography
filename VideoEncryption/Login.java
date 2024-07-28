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
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.io.BufferedReader;
import java.io.FileReader;
public class Login extends JFrame{
	JPanel p1;
	JPanel p2;
	JLabel l1,l2,title,l3;
	JTextField tf1,tf2;
	JButton b1,b2,b3;
	Font f1;
	LineBorder border;
	TitledBorder titles;
public Login(){
	super("Login Screen");
	p1 = new JPanel();
	p1.setLayout(null);
	border = new LineBorder(new Color(42,140,241),1,true);
	titles = new TitledBorder (border,"LoginScreen",TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION, new Font("Tahoma",Font.BOLD,16),Color.darkGray);
	p1.setBorder(titles);
	f1 = new Font("Courier New",Font.BOLD,14);
	p2 = new TitlePanel(600,60);
	p2.setBackground(new Color(204, 110, 155));
	title = new JLabel("<html><body><center>Video Encryption using Quantum Encryption</center></body></html>".toUpperCase());
	title.setForeground(Color.white);
	title.setFont(new Font("Courier New",Font.BOLD,16));
	p2.add(title);

	l3 = new JLabel("Login Screen");
	l3.setFont(new Font("Courier New",Font.BOLD,13));
	l3.setBounds(250,20,200,30);
	p1.add(l3);

	l1 = new JLabel("Username");
	l1.setFont(f1);
	l1.setBounds(200,60,100,30);
	p1.add(l1);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	tf1.setBounds(300,60,130,30);
	p1.add(tf1);
	
	l2 = new JLabel("Password");
	l2.setFont(f1);
	l2.setBounds(200,110,100,30);
	p1.add(l2);
	tf2 = new JPasswordField(15);
	tf2.setFont(f1);
	tf2.setBounds(300,110,130,30);
	p1.add(tf2);

	JPanel pan3 = new JPanel(); 
	b1 = new JButton("Login");
	b1.setFont(f1);
	b1.setBounds(140,160,120,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			login();
		}
	});

	b2 = new JButton("New User");
	b2.setFont(f1);
	b2.setBounds(280,160,120,30);
	p1.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Register reg = new Register(Login.this);
			reg.setVisible(true);
			reg.setSize(650,600);
		}
	});

	b3 = new JButton("Reset");
	b3.setFont(f1);
	b3.setBounds(420,160,120,30);
	p1.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			tf1.setText("");
			tf2.setText("");
		}
	});

		
	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(p2,BorderLayout.NORTH);
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
	Login login = new Login();
	login.setVisible(true);
	login.setSize(600,360);
	login.setLocationRelativeTo(null);
	login.setResizable(false);
}
public void clear(){
	tf1.setText("");
	tf2.setText("");
}
public void login(){
	String user = tf1.getText();
	String pass = tf2.getText();
	
	if(user == null || user.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Username must be enter");
		tf1.requestFocus();
		return;
	}
	if(pass == null || pass.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Password must be enter");
		tf2.requestFocus();
		return;
	}
	boolean flag = false;
	try{
		BufferedReader br = new BufferedReader(new FileReader("lib/Users.txt"));
		String line = null;
		while((line = br.readLine())!= null){
			line = line.trim();
			if(line.length() > 0){
				String arr[] = line.split(",");
				if(arr[0].equals(user) && arr[1].equals(pass)){
					flag = true;
					break;
				}
			}
		}
		br.close();
		if(flag){
			setVisible(false);
			UserScreen ud = new UserScreen(user, pass);
			ud.setVisible(true);
			ud.setSize(1000,600);
		}
		if(!flag){
			JOptionPane.showMessageDialog(this,"invalid user");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

}
