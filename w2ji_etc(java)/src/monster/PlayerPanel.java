package monster;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class PlayerPanel  extends JFrame  implements ActionListener {
    JButton btn1;
    
    JTextField jtf;
    Font f = new Font("", Font.PLAIN, 20);
    int ppoint = 0;
    
    
	public PlayerPanel(int point) {
		this.setLayout(null);
		this.ppoint = point;
		
		JLabel tmp0 = new JLabel("Your Point : "+point);
		tmp0.setFont(f);
		tmp0.setHorizontalAlignment(JLabel.CENTER);
		tmp0.setBounds(10, 10, 225, 40);
		this.add(tmp0);
		
		
		JLabel tmp1 = new JLabel("Input Your Name");
		tmp1.setFont(f);
		tmp1.setHorizontalAlignment(JLabel.CENTER);
		tmp1.setBounds(10, 50, 225, 40);
		this.add(tmp1);
		
		jtf = new JTextField();
		jtf.setFont(f);
		jtf.setHorizontalAlignment(JTextField.CENTER);
		jtf.setBounds(10, 90, 225, 40);
		this.add(jtf);
		
		btn1 = new JButton("저장");
		btn1.addActionListener(this);
		btn1.setBounds(10, 140, 225, 40); // x , y , w , h
        this.add(btn1);

        this.setVisible(true);	 
        this.setTitle("점수조회");
        this.setSize( 250 , 220);	 
        this.setLocationRelativeTo(null);	 
        this.setResizable(false);	 
        this.setDefaultCloseOperation(0);	
		
	}// 생성자
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == btn1){//게임 시작
			Util.setRecord(jtf.getText(), ppoint);
			this.dispose();
		}
	}

}
