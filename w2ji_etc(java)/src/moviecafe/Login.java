package moviecafe;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import booking.WordDbConnect;


// 로그인 화면
class Login extends JFrame implements ActionListener {
	JLabel l1, l2, l3, l4;
	JTextField tf1, tf2, tf3;
	JButton btnOK, doubleCheck , cancel;
	JPanel p1, p2;
	JComboBox type_List;
	
	
	
	String[] user_type = {"관리자", "일반"};
	
	Login() {	
		setTitle("시스템 로그인");
		setSize(380, 200);
		setLocationRelativeTo(null);
		setResizable(false);	
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		l1 = new JLabel("구분");
		l1.setBounds(40, 15, 100, 25);
		p1.add(l1);
		
		type_List = new JComboBox<String>(user_type);
		type_List.setBounds(140, 15, 200, 25);
		p1.add(type_List);
		
		l2 = new JLabel("패스워드");
		l2.setBounds(40, 55, 100, 25);
		p1.add(l2);

		tf2 = new JTextField();
		tf2.setBounds(140, 55, 200, 25);
		p1.add(tf2);
		
		
		btnOK = new JButton("확인");
		btnOK.setBounds(100, 100, 180, 25);
		btnOK.addActionListener(this);
		p1.add(btnOK);
		
		/*
		cancel = new JButton("취소");
		cancel.setBounds(200, 100, 90, 25);
		cancel.addActionListener(this);
		p1.add(cancel);*/
		
		ct.add(p1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOK ) {
			System.out.println("btnOK");
			System.out.println("구분 : "+type_List.getSelectedItem());
			System.out.println("비밀 : "+tf2.getText());
			boolean tt =  DBConnect.getLogin(type_List.getSelectedItem()+"", tf2.getText());
			if(tt) {				
				JOptionPane.showMessageDialog(null,"로그인되었습니다.");
				/*
				MainScreen ms = new MainScreen("MovieCafe" , type_List.getSelectedItem().toString() );
				ms.setSize(1920, 1020);
				ms.setLocationRelativeTo(null);
				ms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ms.show();*/
				
				Time_remaining tm = new Time_remaining(10);
				tm.start();
				MainScreen ms = new MainScreen("MovieCafe" , type_List.getSelectedItem().toString() );
				ms.setSize(1500, 800);
				ms.setLocationRelativeTo(null);
				ms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ms.show();
				
				
				this.dispose();				
			}else {
				JOptionPane.showMessageDialog(null, "비밀번호를 확인하세요.");
			}		
			
		}		
	}
}