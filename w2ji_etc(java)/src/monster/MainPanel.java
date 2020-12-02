package monster;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import booking.JoinFrame;
import booking.Menuframe;
import booking.WMember;
import booking.WordDbConnect;

public class MainPanel  extends JFrame  implements ActionListener {
    JButton btn1;
    JButton btn2;
    JButton btn3;
    
	public MainPanel() {
		this.setLayout(null);
		   
       
		btn1 = new JButton("시작");
		btn1.addActionListener(this);
		btn1.setBounds(10, 20, 225, 40); // x , y , w , h
        
		btn2 = new JButton("점수조회");
		btn2.addActionListener(this);
		btn2.setBounds(10, 70, 225, 40); // x , y , w , h
		
		btn3 = new JButton("종료");
		btn3.addActionListener(this);
		btn3.setBounds(10, 120, 225, 40); // x , y , w , h
        
        this.add(btn1);
        this.add(btn2);
        this.add(btn3);

        this.setVisible(true);	 
        this.setTitle("게임하기");
        this.setSize( 250 , 200);	 
        this.setLocationRelativeTo(null);	 
        this.setResizable(false);	 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
	}// 생성자
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == btn1){//게임 시작	
			new Monster();
		}else if( e.getSource() == btn2 ){ //순위			
			RankingPanel rp =  new RankingPanel();
			rp.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent we) {	//종료됨 이벤트
				}
			});
		}else if( e.getSource() == btn3 ) { //종료
			System.exit(0);
		}
	}
	
	
	

}
