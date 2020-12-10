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


//상품 입출고
class ProductManagement extends JFrame implements ActionListener {
	JLabel l0 , l1, l2, l3, l4;
	JTextField tf0,tf1, tf2, tf3;
	JButton btnOK, cancel , prod  ;
	JPanel p1, p2;
	JComboBox type_List;
	
	String data[][]= {};			  
    String column[]={"상품번호","상품명","재고","판매가"};     
    DefaultTableModel dtm = new DefaultTableModel(data,column){ public boolean isCellEditable(int i, int c){ return false; } };
	
			
	ProductManagement() {	
		setTitle("상품 입출고");
		setSize(768, 250);
		setLocationRelativeTo(null);
		setResizable(false);	
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		JTable jt=new JTable( dtm );
		jt.getColumnModel().getColumn(0).setMaxWidth(80);
		jt.getColumnModel().getColumn(1).setMaxWidth(100);
		jt.getColumnModel().getColumn(2).setMaxWidth(100);
		jt.getColumnModel().getColumn(3).setMaxWidth(100);
		dtm.fireTableDataChanged();
		JScrollPane jsp = new JScrollPane(jt);
		jsp.setBounds(20,15,450,190);	// x , y , w , h
		
		jt.addMouseListener(new java.awt.event.MouseAdapter() {
			    @Override
			    public void mouseClicked(java.awt.event.MouseEvent evt) {
			    	int row = jt.getSelectedRow();
					 Object prod_cd = jt.getValueAt(row, 0);
					 Object prod_nm = jt.getValueAt(row, 1);
					 tf0.setText(prod_cd.toString());
					 tf1.setText(prod_nm.toString());
			    }
		});
		
		
		p1.add(jsp);
		
		
		
		l0 = new JLabel("상품코드");
		l0.setBounds(500, 15, 90, 25);
		p1.add(l0);
		
		tf0 = new JTextField();
		tf0.setBounds(570, 15, 150, 25);
		tf0.setEditable(false);
		p1.add(tf0);
		
		l1 = new JLabel("상품명");
		l1.setBounds(500, 55, 90, 25);
		p1.add(l1);
		
		tf1 = new JTextField();
		tf1.setBounds(570, 55, 150, 25);
		tf1.setEditable(false);
		p1.add(tf1);
		
		
		l2 = new JLabel("입고수량");
		l2.setBounds(500, 95, 90, 25);		
		p1.add(l2);
		
		tf2 = new JTextField();
		tf2.setBounds(570, 95, 150, 25);
		IntegerDocument  id2 = new IntegerDocument (); //숫자만 입력하기 위한 기능 클래스
		tf2.setDocument(id2);		
		tf2.setText("0");
		p1.add(tf2);
		
		l3 = new JLabel("출고수량");
		l3.setBounds(500, 135, 90, 25);
		p1.add(l3);
		
		tf3 = new JTextField();
		tf3.setBounds(570, 135, 150, 25);
		IntegerDocument  id3 = new IntegerDocument (); //숫자만 입력하기 위한 기능 클래스
		tf3.setDocument(id3);
		tf3.setText("0");
		p1.add(tf3);

		prod = new JButton("상품등록");
		prod.setBounds(500, 175, 90, 25);
		prod.addActionListener(this);
		p1.add(prod);
		
		btnOK = new JButton("확인");
		btnOK.setBounds(600, 175, 70, 25);
		btnOK.addActionListener(this);
		p1.add(btnOK);
		
		cancel = new JButton("닫기");
		cancel.setBounds(680, 175, 70, 25);
		cancel.addActionListener(this);
		p1.add(cancel);
		
		ct.add(p1);
		
		initList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOK ) {
			System.out.println("btnOK");
			String temp_cd = tf0.getText();
			String temp_nm = tf1.getText();
			String temp_in = tf2.getText();
			String temp_out = tf3.getText();
			if( !temp_cd.equals("") && !temp_nm.equals("")&& !temp_in.equals("")&& !temp_out.equals("")  ) {
				int in = Integer.parseInt( temp_in );
				int out = Integer.parseInt( temp_out );
				int result = JOptionPane.showConfirmDialog(null,"입출고 수량을 등록 하시겠습니까?", "Confirm",JOptionPane.YES_NO_OPTION);
		    		if(result == JOptionPane.YES_OPTION) {
		    			DBConnect.setProdHist(temp_cd, in, out, 0, "M");
		    			DBConnect.updateProd(temp_cd, in, out);
		    		}
		    		tf0.setText("");
		    		tf1.setText("");
		    		tf2.setText("0");
		    		tf3.setText("0");
		    		initList();		    		
			}
		}else if( e.getSource() == cancel ) {
			System.out.println("cancel");
			this.dispose();	
		}else if( e.getSource() == prod ) {
			System.out.println("prod");
			ProductRegist pm = new ProductRegist();
			pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			pm.setLocationRelativeTo(null);
			pm.show();
			this.dispose();		
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
