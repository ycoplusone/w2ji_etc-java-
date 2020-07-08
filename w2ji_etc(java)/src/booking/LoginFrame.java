package booking;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class LoginFrame extends JFrame  implements ActionListener{

    JButton logBtn;
    JButton ExitBtn;
    JButton joinbtn;
    JTextField txtID;
    JPasswordField txtPass;
       
    
    
	
	
	   public LoginFrame() {		   
		    this.setLayout(null);
		   
	        //JPanel panel = new JPanel();
	        JLabel title = new JLabel("예약 하기");
	        title.setBounds(80, 5, 250, 20); // x , y , w , h
	        
	        JLabel label = new JLabel("아 이 디  : ");	 
	        label.setBounds(20, 30, 70, 20); // x , y , w , h
	        
	        
	        txtID= new JTextField(10);
	        txtID.setBounds(90, 30, 180, 20); // x , y , w , h
	        
	        JLabel pswrd = new JLabel("비밀번호 : ");
	        pswrd.setBounds(20, 60, 70, 20); // x , y , w , h
	        
	        txtPass = new JPasswordField(10);
	        txtPass.setBounds(90, 60, 180, 20); // x , y , w , h
	        
	        logBtn = new JButton("접속");
	        logBtn.addActionListener(this);
	        logBtn.setBounds(20, 90, 250, 20); // x , y , w , h
	        
	        joinbtn = new JButton("가입");
	        joinbtn.addActionListener(this);
	        joinbtn.setBounds(20, 120, 250, 20); // x , y , w , h
	        
	        
	        
	        ExitBtn = new JButton("종료");
	        ExitBtn.addActionListener(this);
	        ExitBtn.setBounds(20, 150, 250, 20); // x , y , w , h  
	        
	        this.add(title);
	        this.add(label);
	        this.add(txtID);
	        this.add(pswrd);
	        this.add(txtPass);
	        this.add(logBtn);
	        this.add(joinbtn);
	        this.add(ExitBtn);

	        this.setVisible(true);	 
	        this.setSize( 300 , 210);	 
	        this.setLocationRelativeTo(null);	 
	        this.setResizable(false);	 
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	 
	    }
	   
	   public void actionPerformed(ActionEvent e){		  
		   if( e.getSource() == logBtn){
			   WMember wm = WordDbConnect.getLoginSucc(txtID.getText(), txtPass.getText());
			   if( wm.getSeq() == null ){
				   System.out.println("null");
				   
			   }else{
				   this.setVisible(false);
				   Menuframe mf = new Menuframe(wm);
			   }
			   
			   
		   }else if( e.getSource() == ExitBtn ){
			   System.exit(0);
		   }else if( e.getSource() == joinbtn ) { //가입
			   System.out.println("가입");
			   JoinFrame jf = new JoinFrame();
		   }
	   }
	   
	   
}