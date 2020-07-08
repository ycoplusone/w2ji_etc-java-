package booking;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class JoinFrame extends JFrame  implements ActionListener{

    JButton logBtn;
    JButton joinbtn;
    JTextField txtID;
    JTextField txtPass;
    JTextField txtPass_chk;
    
    JTextField email;
    JTextField name;
    
       
    
    
	
	
	   public JoinFrame() {		   
		    this.setLayout(null);
		   
	        //JPanel panel = new JPanel();
	        JLabel title = new JLabel("가입 하기");
	        title.setBounds(80, 5, 250, 20); // x , y , w , h
	        
	        JLabel label = new JLabel("아 이 디  : ");	 
	        label.setBounds(20, 30, 70, 20); // x , y , w , h
	        
	        
	        txtID= new JTextField(10);
	        txtID.setBounds(90, 30, 180, 20); // x , y , w , h
	        
	        JLabel pswrd = new JLabel("비밀번호 : ");
	        pswrd.setBounds(20, 60, 70, 20); // x , y , w , h
	        
	        txtPass = new JTextField(10);
	        txtPass.setBounds(90, 60, 180, 20); // x , y , w , h
	        
	        JLabel pswrd_chk = new JLabel("번호확인 : ");
	        pswrd_chk.setBounds(20, 90, 70, 20); // x , y , w , h
	        
	        txtPass_chk = new JTextField(10);
	        txtPass_chk.setBounds(90, 90, 180, 20); // x , y , w , h
	        
	        
	        JLabel mail = new JLabel("이메일 : ");
	        mail.setBounds(20, 120, 70, 20); // x , y , w , h
	        
	        email = new JTextField(10);
	        email.setBounds(90, 120, 180, 20); // x , y , w , h
	        
	        JLabel nm = new JLabel("이름 : ");
	        nm.setBounds(20, 150, 70, 20); // x , y , w , h
	        
	        name = new JTextField(10);
	        name.setBounds(90, 150, 180, 20); // x , y , w , h
	        
	        
	        logBtn = new JButton("확인");
	        logBtn.addActionListener(this);
	        logBtn.setBounds(20, 180, 250, 20); // x , y , w , h
	        
	        	        
	        
	        
	          
	        
	        this.add(title);
	        this.add(label);
	        this.add(txtID);
	        this.add(pswrd);
	        this.add(txtPass);
	        
	        this.add(pswrd_chk);
	        this.add(txtPass_chk);
	        
	        this.add(mail);
	        this.add(email);
	        
	        this.add(nm);
	        this.add(name);
	        
	        this.add(logBtn);
	        	        

	        this.setVisible(true);	 
	        this.setSize( 300 , 240);	 
	        this.setLocationRelativeTo(null);	 
	        this.setResizable(false);	 
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	 
	    }
	   
	   public void actionPerformed(ActionEvent e){		  
		   if( e.getSource() == logBtn){
			   WMember wm = WordDbConnect.getLoginSucc(txtID.getText(), "%");
			   if( wm.getSeq() != null ){
				   JOptionPane.showMessageDialog(null, "이미 아이디가 존재 합니다.");
			   }else if( !(txtPass.getText().equals( txtPass_chk.getText()) )  ){
				   JOptionPane.showMessageDialog(null, "패스워드가 같지 않습니다.");
			   }else{				   
				   WordDbConnect.setMember( txtID.getText() , txtPass.getText() , email.getText() , name.getText() );
				   JOptionPane.showMessageDialog(null, "가입되었습니다.");
				   this.setVisible(false);				   
			   }	   
			   
		   
		   }
	   }
	   
	   
	   
}