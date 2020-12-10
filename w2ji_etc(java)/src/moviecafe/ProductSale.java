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


//상품 판매
class ProductSale extends JFrame implements ActionListener {
	JLabel l0 , l1, l2, l3, l4;
	JLabel l_total_cnt , l_total_amt;
	JTextField tf0,tf1, tf2, tf3;
	JButton btnOK, doubleCheck , cancel ;
	JPanel p1, p2;
	JComboBox type_List;
	
	String data[][]= {};			  
    String column[]={"상품번호","상품명","판매가" ,"수량","합계"};     
    DefaultTableModel dtm = new DefaultTableModel(data,column){ public boolean isCellEditable(int i, int c){ return false; } };
	
			
	ProductSale() {	
		setTitle("상품 입출고");
		setSize(768, 320);
		setLocationRelativeTo(null);
		setResizable(false);	
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		JTable jt=new JTable( dtm );
		jt.getColumnModel().getColumn(0).setMaxWidth(60);
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
				 Object price = jt.getValueAt(row, 2);
				 tf0.setText(prod_cd.toString());
				 tf1.setText(prod_nm.toString());
				 tf2.setText(price.toString());
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
		
		
		l2 = new JLabel("판매가");
		l2.setBounds(500, 95, 90, 25);		
		p1.add(l2);
		
		tf2 = new JTextField();
		tf2.setBounds(570, 95, 150, 25);
		tf2.setText("0");
		p1.add(tf2);
		
		l3 = new JLabel("판매수량");
		l3.setBounds(500, 135, 90, 25);
		p1.add(l3);
		
		tf3 = new JTextField();
		tf3.setBounds(570, 135, 150, 25);
		tf3.setText("0");
		p1.add(tf3);
		
		l_total_cnt = new JLabel("총 : 0 개");
		l_total_cnt.setBounds(20, 210, 90, 25);		
		p1.add(l_total_cnt);
		
		l_total_amt = new JLabel("합계 : 0 원");
		l_total_amt.setBounds(20, 235, 90, 25);		
		p1.add(l_total_amt);
		
		
		btnOK = new JButton("판매");
		btnOK.setBounds(570, 175, 70, 25);
		btnOK.addActionListener(this);
		p1.add(btnOK);
		
		cancel = new JButton("닫기");
		cancel.setBounds(650, 175, 70, 25);
		cancel.addActionListener(this);
		p1.add(cancel);
		
		ct.add(p1);
		initList();
		initTotal();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOK ) {
			System.out.println("btnOK");
			String temp_cd = tf0.getText();
			String temp_nm = tf1.getText();
			String price = tf2.getText();
			String temp_out = tf3.getText();
			if( !temp_cd.equals("") && !temp_nm.equals("")&& !temp_out.equals("")&& !price.equals("")  ) {
				int out = Integer.parseInt( temp_out );
				int p = Integer.parseInt( price );
				int result = JOptionPane.showConfirmDialog(null,"("+temp_nm+") 판매하시겠습니까", "Confirm",JOptionPane.YES_NO_OPTION);
		    		if(result == JOptionPane.YES_OPTION) {
		    			DBConnect.setProdHist(temp_cd, 0, out, p, "S");
		    			DBConnect.updateProd(temp_cd, 0, out);
		    		}
		    		tf0.setText("");
		    		tf1.setText("");
		    		tf2.setText("0");
		    		tf3.setText("0");
		    		initList();	
		    		initTotal();
			}
		}else if( e.getSource() == cancel ) {
			System.out.println("cancel");
			this.dispose();
		}
		
	}
	
	public void initList() {		
		dtm.setRowCount(0);
		ArrayList<String[]> al = DBConnect.getSaleList();
		for (String[] strings : al) {
			String [] ss = new String[5];
			ss[0] = strings[0];//object.get("id").toString().replace("\"", "");
			ss[1] = strings[1];//object.get("mynum").toString().replace("\"", "");
			ss[2] = strings[2];//object.get("goal").toString().replace("\"", "");
			ss[3] = strings[3];//object.get("point").toString().replace("\"", "");
			ss[4] = strings[4];//object.get("point").toString().replace("\"", "");
			dtm.addRow(ss);
			
		}
	}
	public void initTotal() {
		String[] al = DBConnect.getSaleTotal();
		l_total_cnt.setText("총 : "+al[0]+" 개");
		l_total_amt.setText("합계 : "+al[1]+" 원");
		/*
		 * 		l_total_cnt = new JLabel("총 : 0 개");
		l_total_cnt.setBounds(20, 210, 90, 25);		
		p1.add(l_total_cnt);
		
		l_total_amt = new JLabel("합계 : 0 원");
		l_total_amt.setBounds(20, 235, 90, 25);		
		p1.add(l_total_amt);
		 * */
	}
	
}
