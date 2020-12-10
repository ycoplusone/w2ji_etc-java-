package moviecafe;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



//상품 등록
class ProductRegist extends JFrame implements ActionListener {
	JLabel l0 , l1, l2, l3, l4;
	JTextField tf0,tf1, tf2, tf3;
	JButton btnOK, cancel , prod ,del ;
	JPanel p1, p2;
	JComboBox type_List;
	
	JTable jt;
	
	String data[][]= {};			  
    String column[]={"상품번호","상품명","재고","판매가"};     
    DefaultTableModel dtm = new DefaultTableModel(data,column){ public boolean isCellEditable(int i, int c){ return false; } };
				
	ProductRegist() {	
		setTitle("상품 등록");
		setSize(768, 250);
		setLocationRelativeTo(null);
		setResizable(false);	
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		jt=new JTable( dtm );
		jt.getColumnModel().getColumn(0).setMaxWidth(80);
		jt.getColumnModel().getColumn(1).setMaxWidth(100);
		jt.getColumnModel().getColumn(2).setMaxWidth(100);
		jt.getColumnModel().getColumn(3).setMaxWidth(100);
		dtm.fireTableDataChanged();
		JScrollPane jsp = new JScrollPane(jt);
		jsp.setBounds(20,15,450,190);	// x , y , w , h	
		

		
		p1.add(jsp);
		
		/*
		l0 = new JLabel("상품코드");
		l0.setBounds(500, 15, 90, 25);
		p1.add(l0);
		
		tf0 = new JTextField();
		tf0.setBounds(570, 15, 150, 25);
		tf0.setEditable(false);
		p1.add(tf0);*/
		
		l1 = new JLabel("상품명");
		l1.setBounds(500, 55, 90, 25);
		p1.add(l1);
		
		tf1 = new JTextField();
		tf1.setBounds(570, 55, 150, 25);
		p1.add(tf1);
		
		
		l2 = new JLabel("판매가");
		l2.setBounds(500, 95, 90, 25);		
		p1.add(l2);
		
		tf2 = new JTextField();
		tf2.setBounds(570, 95, 150, 25);
		IntegerDocument  id2 = new IntegerDocument (); //숫자만 입력하기 위한 기능 클래스
		tf2.setDocument(id2);
		p1.add(tf2);
		
		
		btnOK = new JButton("확인");
		btnOK.setBounds(500, 135, 105, 25);
		btnOK.addActionListener(this);
		p1.add(btnOK);
		
		cancel = new JButton("닫기");
		cancel.setBounds(615, 135, 105, 25);
		cancel.addActionListener(this);
		p1.add(cancel);
		
		prod = new JButton("상품입출고");
		prod.setBounds(500, 175, 105, 25);
		prod.addActionListener(this);
		p1.add(prod);
		
		del = new JButton("상품삭제");
		del.setBounds(615, 175, 105, 25);
		del.addActionListener(this);
		p1.add(del);
		
		ct.add(p1);
		initList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOK ) {
			System.out.println("btnOK");
			System.out.println( "상품명 : "+tf1.getText() );
			System.out.println( "판매가 : "+tf2.getText() );
			String t1 = tf1.getText().trim();
			String t2 = tf2.getText().trim();
			if( !t1.equals("") && !t2.equals("")) {
				DBConnect.setProdAdd(tf1.getText(), tf2.getText());
				tf1.setText("");
				tf2.setText("");
				JOptionPane.showMessageDialog(null,"상품이 등록되었습니다.");
				initList(); //리스트 초기화
			}		
			
			
		
		}else if( e.getSource() == cancel ) { //그냥 닫기
			System.out.println("cancel");
			this.dispose();
		}else if(e.getSource() == prod) { //상품 입출고 이동
			System.out.println("prod");
			ProductManagement pm = new ProductManagement();
			pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			pm.setLocationRelativeTo(null);
			pm.show();
			this.dispose();			
		}else if( e.getSource() == del ) { //상품삭제
			
			try {
				int row = jt.getSelectedRow();	    	
				Object prod_cd = jt.getValueAt(row, 0);	//상품코드 가져오기
				Object prod_nm = jt.getValueAt(row, 1);	//상품코드 가져오기
				int result = JOptionPane.showConfirmDialog(null,"("+prod_nm+") 상품을 삭제하시겠습니다?", "Confirm",JOptionPane.YES_NO_OPTION);
			    	if(result == JOptionPane.YES_OPTION) {
			    		DBConnect.delProd(prod_cd.toString());
			    		initList();
			     	}					
			}catch(Exception ex) {
				ex.getStackTrace();
			}	
		}
	    	
		
	}
	
	public void initList() {
		
		dtm.setRowCount(0);
		ArrayList<String[]> al = DBConnect.getProdList();
		for (String[] strings : al) {
			String [] ss = new String[4];
			ss[0] = strings[0];//object.get("id").toString().replace("\"", "");
			ss[1] = strings[1];//object.get("mynum").toString().replace("\"", "");
			ss[2] = strings[2];//object.get("goal").toString().replace("\"", "");
			ss[3] = strings[3];//object.get("point").toString().replace("\"", "");						
			dtm.addRow(ss);
			
		}
		
		

	}
}
