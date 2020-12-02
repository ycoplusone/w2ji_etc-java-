package monster;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import booking.JoinFrame;
import booking.Menuframe;
import booking.WMember;
import booking.WordDbConnect;

public class RankingPanel  extends JFrame  implements ActionListener {
    JButton btn1;
    
    String data[][]= {};			  
    String column[]={"이름","점수"};     
    DefaultTableModel dtm = new DefaultTableModel(data,column){ public boolean isCellEditable(int i, int c){ return false; } };
    
	public RankingPanel() {
		this.setLayout(null);
		
		btn1 = new JButton("닫기");
		btn1.addActionListener(this);
		btn1.setBounds(10, 220, 225, 40); // x , y , w , h
		
		
		JTable jt=new JTable( dtm );
		jt.getColumnModel().getColumn(0).setMaxWidth(80);
		jt.getColumnModel().getColumn(1).setMaxWidth(145);

		  dtm.fireTableDataChanged();
		  JScrollPane jsp = new JScrollPane(jt);
		  jsp.setBounds(10,10,225,200);	// x , y , w , h		  
		  this.add(jsp);
		  
		  dtm.setRowCount(0);
		  ArrayList<RecordModel> tmp = Util.getRecord();
		  int cnt = 0;
		  for (RecordModel recordModel : tmp) {
			  String [] ss = new String[4];
				ss[0] = recordModel.getName();
				ss[1] = recordModel.getScore()+"";
				if(cnt < 10) {
					dtm.addRow(ss);
					cnt++;
				}				
		  }
        
        this.add(btn1);

        this.setVisible(true);	 
        this.setTitle("게임하기");
        this.setSize( 250 , 300);	 
        this.setLocationRelativeTo(null);	 
        this.setResizable(false);	 
        this.setDefaultCloseOperation(0);	
		
	}// 생성자
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == btn1){//게임 시작
			this.dispose();
		}
	}

}
