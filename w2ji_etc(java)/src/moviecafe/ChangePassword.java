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


class ChangePassword extends JFrame implements ActionListener {
	JLabel l1, l2, l3, l4;
	JTextField tf1, tf2, tf3;
	JButton btnOK, doubleCheck , cancel;
	JPanel p1, p2;
	JComboBox type_List;	
	String user_type;

	ChangePassword(String type) {
		this.user_type = type;
		setTitle("비밀번호 변경");
		setSize(380, 220);
		setLocationRelativeTo(null);
		setResizable(false);	
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		l1 = new JLabel("현재 비밀번호");
		l1.setBounds(40, 15, 100, 25);
		p1.add(l1);
		
		tf1 = new JTextField();
		tf1.setBounds(140, 15, 200, 25);
		p1.add(tf1);
		
		l2 = new JLabel("새 비밀번호");
		l2.setBounds(40, 55, 100, 25);
		p1.add(l2);
		
		tf2 = new JTextField();
		tf2.setBounds(140, 55, 200, 25);
		p1.add(tf2);
		
		l3 = new JLabel("비밀번호 재확인");
		l3.setBounds(40, 95, 100, 25);
		p1.add(l3);
		
		tf3 = new JTextField();
		tf3.setBounds(140, 95, 200, 25);
		p1.add(tf3);

		
		
		btnOK = new JButton("확인");
		btnOK.setBounds(100, 135, 90, 25);
		btnOK.addActionListener(this);
		p1.add(btnOK);
		
		cancel = new JButton("취소");
		cancel.setBounds(200, 135, 90, 25);
		cancel.addActionListener(this);
		p1.add(cancel);
		
		ct.add(p1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOK ) {
			System.out.println("btnOK");
			String now_ps = tf1.getText();
			String new_ps = tf2.getText();
			String chk_ps = tf3.getText();
			if( now_ps.equals("") ||  new_ps.equals("") || chk_ps.equals("") ) {
				JOptionPane.showMessageDialog(null,"입력이 잘못되었습니다.");
			}else if( !new_ps.equals(chk_ps) ) {
				JOptionPane.showMessageDialog(null,"새로입력한 비밀번호가 맞지 않습니다.");				
			}else {
				boolean tt = DBConnect.setChangePassword( user_type , now_ps, new_ps);
				if(tt) {
					JOptionPane.showMessageDialog(null,"변경되었습니다.");	
					this.dispose();
				}else {
					JOptionPane.showMessageDialog(null,"비밀번호가 맞지 않습니다.");
				}
			}
			
		}else if( e.getSource() == cancel ) {
			System.out.println("cancel");
			this.dispose();
		}
		
	}
}