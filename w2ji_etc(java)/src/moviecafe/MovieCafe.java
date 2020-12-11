package moviecafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.sql.*;
import javax.swing.table.*;
import java.util.*;

class MainScreen extends JFrame implements ActionListener {	// 메인화면
	static Vector<Boolean> checkIn = new Vector<Boolean>();
    static Vector<String> memberCode = new Vector<String>();
    static Vector<String> movieCode = new Vector<String>();
    static Vector<String> name = new Vector<String>();
    static Vector<String> movieTitle = new Vector<String>();
    static Vector<String> movie = new Vector<String>();
    static Vector<Integer> basicSales = new Vector<Integer>();
    static Vector<Integer> productSales = new Vector<Integer>();
    
    //이용현황 테이블
	Vector<String> user_table;
	Vector<Vector<String>> user_data;
	JTable Utable = null;
	JScrollPane UtableSP;
	DefaultTableModel umodel = null;
	
    //요금 테이블
	Vector<String> price_table;
	Vector<Vector<String>> price_data;
	JTable Ptable = null;
	JScrollPane PtableSP;
	DefaultTableModel pmodel = null;
	
	//각종 버튼
	JButton MemberMgt, MovieMgt, ProductMgt, SalesMgt, ChangePassword;
	JButton ChangePrice;
	
	//상영관 버튼
	String[] num = new String[8];
	static JButton[] room = new JButton[8];
	String user_type = "";
	MainScreen(String title , String type) {
		this.user_type = type;
		// 상영관 번호 부여
		for(int i = 0; i<num.length; i++) {
			num[i] = Integer.toString(i+1);
		}
		
		setTitle(title);
		Container ct = getContentPane();
		ct.setLayout(null);	
		LineBorder lb = new LineBorder(Color.black);
		
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		top.setBackground(Color.white);
		
		JPanel left = new JPanel();
		left.setLayout(null);
		left.setBackground(Color.white);
		
		JPanel center = new JPanel();
		center.setLayout(null);

		MemberMgt = new JButton("회원관리");
		MovieMgt = new JButton("영화관리");
		ProductMgt = new JButton("상품관리");
		SalesMgt = new JButton("매출관리");
		ChangePassword = new JButton("비밀번호변경");
		
		MemberMgt.setBackground(new Color(240,240,240));
		MovieMgt.setBackground(new Color(240,240,240));
		ProductMgt.setBackground(new Color(240,240,240));
		SalesMgt.setBackground(new Color(240,240,240));
		ChangePassword.setBackground(new Color(240,240,240));
		
		MemberMgt.addActionListener(this);
		MovieMgt.addActionListener(this);
		ProductMgt.addActionListener(this);
		SalesMgt.addActionListener(this);
		ChangePassword.addActionListener(this);
		
		top.setBounds(0,0,1500,40);
		
		top.add(MemberMgt);
		top.add(MovieMgt);
		top.add(ProductMgt);
		top.add(SalesMgt);
		top.add(ChangePassword);
		
		ct.add(top);
		
		JLabel UsageStatus = new JLabel("이용현황");
		UsageStatus.setHorizontalAlignment(JLabel.CENTER);
		UsageStatus.setForeground(new Color(255,202,24));
		UsageStatus.setBounds(0,0,200,40);
		UsageStatus.setFont(new Font("맑은고딕", Font.BOLD, 15));

		user_table = new Vector<String>();
		user_table.add("상영관");
		user_table.add("남은시간");
		
		user_data = new Vector<Vector<String>>();
		umodel = new DefaultTableModel(user_data, user_table);
		Utable = new JTable(umodel);
		UtableSP = new JScrollPane(Utable);
		UtableSP.setBounds(5,30,190,185);
		Utable.getTableHeader().setReorderingAllowed(false);	// 헤더 이동 불가
		Utable.setRowHeight(40);	// 셀 높이 지정
		
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer centerTable1 = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		centerTable1.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel centerCell1 = Utable.getColumnModel();
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < centerCell1.getColumnCount(); i++) {
			centerCell1.getColumn(i).setCellRenderer(centerTable1);
		}
		
		
		JLabel Price = new JLabel("요금표");
		Price.setHorizontalAlignment(JLabel.CENTER);
		Price.setForeground(new Color(255,202,24));
		Price.setBounds(0,400,200,40);
		Price.setFont(new Font("맑은고딕", Font.BOLD, 15));
		
		price_table = new Vector<String>();
		price_table.add("인원수");
		price_table.add("요금");
		
		price_data = new Vector<Vector<String>>();
		pmodel = new DefaultTableModel(price_data, price_table);
		Ptable = new JTable(pmodel);
		PtableSP = new JScrollPane(Ptable);
		PtableSP.setBounds(5,430,190,185);
		Ptable.getTableHeader().setReorderingAllowed(false);	// 헤더 이동 불가
		Ptable.setRowHeight(40);	// 셀 높이 지정
		
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer centerTable2 = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		centerTable2.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel centerCell2 = Ptable.getColumnModel();
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < centerCell2.getColumnCount(); i++) {
			centerCell2.getColumn(i).setCellRenderer(centerTable2);
		}
		
		// 요금표 출력
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {};
        
        try {
 		String sql = "select * from price_info";
 		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
 		PreparedStatement pstmt = con.prepareStatement(sql);
 		ResultSet rs = pstmt.executeQuery();
 			
 		while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
 			pmodel.addRow(new Object[] {
 					rs.getInt("person"), rs.getString("price")
 					});
 		}
 			pstmt.close();
 			con.close();
        } catch(SQLException e) {}
        
		ChangePrice = new JButton("요금변경");
		ChangePrice.setBackground(new Color(240,240,240));
		ChangePrice.setBounds(50, 650, 100, 40);
		ChangePrice.addActionListener(this);
		
		left.setBounds(0, 40, 200, 800);
		left.add(UsageStatus);
		left.add(UtableSP);
		left.add(Price);
		left.add(PtableSP);
		left.add(ChangePrice);
		left.setBorder(lb);
		
		ct.add(left);
		
		for(int i=0; i<8; i++) {
			room[i] = new JButton(num[i]);
			room[i].setVisible(true);
			room[i].setBackground(Color.white);
			room[i].setFont(new Font("고딕", Font.BOLD | Font.ITALIC, 50));
			room[i].setForeground(new Color(80,124,52));
			room[i].addActionListener(this);
			center.add(room[i]);
		}
		room[0].setBounds(60,200,260,150);
		room[1].setBounds(360,200,260,150);
		room[2].setBounds(660,200,260,150);
		room[3].setBounds(960,200,260,150);
		room[4].setBounds(60,400,260,150);
		room[5].setBounds(360,400,260,150);
		room[6].setBounds(660,400,260,150);
		room[7].setBounds(960,400,260,150);
		

		for(int i = 0; i <= 8; i++) {
            checkIn.add(i, false);
            memberCode.add(i, null);
            movieCode.add(i, null);
            name.add(i, null);
            movieTitle.add(i, null);
            movie.add(i, null);
            basicSales.add(i, null);
            productSales.add(i, null);
        }
		
		center.setBounds(200, 40, 1300, 800);
		center.setBorder(lb);
		ct.add(center);

	}
	
	public void actionPerformed(ActionEvent ae) {
		String s= ae.getActionCommand();
		if(s == "회원관리") {
			MemberManagement mm = new MemberManagement();
			mm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mm.setSize(900, 500);
			mm.setLocationRelativeTo(null);
			mm.show();
		}
		else if(s == "영화관리") {
			MovieManagement mv = new MovieManagement();
			mv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mv.setSize(600, 400);
			mv.setLocationRelativeTo(null);
			mv.setResizable(false);
			mv.show();
		}
		else if(s == "상품관리") {
			/*
			ProductManagement pm = new ProductManagement("상품관리");
			pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			pm.setSize(1300, 700);
			pm.setLocationRelativeTo(null);
			pm.show();*/
			ProductRegist pr = new ProductRegist();
			pr.show();
		}
		else if(s == "매출관리") {
			/*SalesManagement pm = new SalesManagement("매출관리");
			pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			pm.setSize(1300, 700);
			pm.setLocationRelativeTo(null);
			pm.show();*/
			ProductSale ps = new ProductSale();
			ps.show();
		}
		else if(s == "비밀번호변경") {
			ChangePassword pm = new ChangePassword(this.user_type);
			pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			//pm.setSize(1300, 700);
			//pm.setLocationRelativeTo(null);
			pm.show();
		}
		else if(s == "요금변경") {
			ChangePrice cp = new ChangePrice("요금변경", this);
			cp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			cp.setSize(600, 400);
			cp.setLocationRelativeTo(null);
			cp.show();
		}
		else {
			int roomNum = Integer.valueOf(s);
			
			if(checkIn.get(roomNum-1)) {
				CheckOut co = new CheckOut(roomNum-1);
				co.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				co.setSize(600, 400);
				co.setLocationRelativeTo(null);
				co.show();
				co.setModal(true);
			}
			else if(!checkIn.get(roomNum)){
				CheckInMember ci = new CheckInMember(roomNum-1);
				ci.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ci.setSize(500, 300);
				ci.setLocationRelativeTo(null);
				ci.show();
				ci.setModal(true);
			}
        }
	}
}

class MemberManagement extends JDialog implements ActionListener, MouseListener {
	String listTitle[] = {"회원코드", "이름", "전화번호", "출생연도"};
	String selectSearch[] = {"이름", "전화번호"};
	Vector<String> years = new Vector<String>();
	Font rightPaneTitleFont;
	JLabel code, name, tel, year, rightPaneTitle;
	JTextField searchTF, tfCode, tfName, tfTel, tfYear;
	JButton search, newMember, modify, deleteMember;
	JPanel leftPane, rightPane, searchPane, rightInnerPane, rightUpPanel;
	JComboBox searchList, selectYear;
	JTable memberList;
	JScrollPane scrollPane;
	DefaultTableModel model;
	Insets insets = new Insets(0,0,0,0);
	Container ct;
	
	MemberManagement () {
		setTitle("회원관리");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		
		ct = getContentPane();
		ct.setLayout(new GridBagLayout());
		
		rightPaneTitleFont = new Font("", Font.BOLD, 20);
		
		leftPane = new JPanel();
		leftPane.setLayout(new BorderLayout());
		leftPane.setBorder(new TitledBorder(new LineBorder(Color.gray,1)));
		
		searchPane = new JPanel();
		searchPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		searchTF = new JTextField(10);
		
		search = new JButton("검색");		search.addActionListener(this);
		searchList = new JComboBox(selectSearch);
		
		searchPane.add(searchList);	searchPane.add(searchTF);
		searchPane.add(search);
		
		leftPane.add(searchPane, BorderLayout.NORTH);
		
		rightPaneTitle = new JLabel("상세정보");
		rightPaneTitle.setFont(rightPaneTitleFont);
		rightUpPanel = new JPanel();
		rightUpPanel.add(rightPaneTitle);
		
		rightInnerPane = new JPanel();
		rightInnerPane.setLayout(null);
		
		for(int i = 2010; i >= 1950; i--) {
			years.add(String.valueOf(i));
		}
		
		selectYear = new JComboBox<String>(years);
		
		code = new JLabel("코드");	code.setBounds(70, 50, 50, 20);
		name = new JLabel("이름");	name.setBounds(70, 90, 50, 20);
		tel = new JLabel("전화번호");	tel.setBounds(70, 130, 50, 20);
		year = new JLabel("출생연도"); year.setBounds(70, 170, 50, 20);
		
		tfCode = new JTextField();	tfCode.setBounds(175, 50, 100, 20);	tfCode.setEnabled(false);
		tfName = new JTextField();	tfName.setBounds(175, 90, 100, 20);	tfName.setEnabled(false);
		tfTel = new JTextField();	tfTel.setBounds(175, 130, 100, 20);
		tfYear = new JTextField();	selectYear.setBounds(175, 170, 100, 20);
		
		newMember = new JButton("신규등록");		newMember.setBounds(130, 230, 90, 30);
		modify = new JButton("정보수정");			modify.setBounds(130, 275, 90, 30);
		deleteMember = new JButton("회원삭제");	deleteMember.setBounds(130, 320, 90, 30);
		
		newMember.addActionListener(this);
		modify.addActionListener(this);
		deleteMember.addActionListener(this);	
		
		tfCode.setHorizontalAlignment(JTextField.CENTER);
		tfName.setHorizontalAlignment(JTextField.CENTER);
		tfTel.setHorizontalAlignment(JTextField.CENTER);
		tfYear.setHorizontalAlignment(JTextField.CENTER);
		
		rightInnerPane.add(code);	rightInnerPane.add(name);
		rightInnerPane.add(tel);	rightInnerPane.add(year);
		rightInnerPane.add(tfCode);	rightInnerPane.add(tfName);
		rightInnerPane.add(tfTel);	rightInnerPane.add(selectYear);
		rightInnerPane.add(newMember); rightInnerPane.add(modify);
		rightInnerPane.add(deleteMember);
		
		rightPane = new JPanel();
		rightPane.setLayout(new BorderLayout());
		
		rightPane.add(rightUpPanel, BorderLayout.NORTH);
		rightPane.add(rightInnerPane, BorderLayout.CENTER);
		
        createTable();
         
        GridBagConstraints constraints = new GridBagConstraints(
        		0, 0, 1, 1, 5, 1,
		        GridBagConstraints.CENTER,
		        GridBagConstraints.BOTH,
		        insets, 0, 0);
        
		ct.add(leftPane, constraints);
         
        constraints = new GridBagConstraints(
                1, 0, 1, 1, 3, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                insets, 0, 0);
        
        ct.add(rightPane, constraints);
	}
	
	public void createTable() {
		try {
			model = new DefaultTableModel(listTitle, 0);
	 		String sql = "select * from member_info";
	 			
	 		//Class.forName("com.mysql.cj.jdbc.Driver");
	 		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
	 		PreparedStatement pstmt = con.prepareStatement(sql);
	 		ResultSet rs = pstmt.executeQuery();
	 			
	 		while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
	 			model.addRow(new Object[] {
	 					rs.getString("code"),
	 					rs.getString("name"),
	 					rs.getString("tel"),
	 					rs.getString("year")
	 					});
	 		} 	//while 종료
	 		
	 		memberList = new JTable(model);
	 		memberList.setModel(model);
			memberList.getColumnModel().getColumn(0).setPreferredWidth(10);
			memberList.getColumnModel().getColumn(1).setPreferredWidth(40);
			memberList.getColumnModel().getColumn(2).setPreferredWidth(80);
			memberList.getColumnModel().getColumn(3).setPreferredWidth(20);
			memberList.addMouseListener(this);
			
			scrollPane = new JScrollPane(memberList);
			
	 		leftPane.add(scrollPane, BorderLayout.CENTER);
	 		
	 		memberList.getTableHeader().setReorderingAllowed(false);
	 		
	 		rs.close();
	 		pstmt.close();
	 		con.close();
	 		}	//try 종료
	 		catch (Exception e){;}
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		if(s == "신규등록") {
			NewMember win = new NewMember(this);
			win.show();
		}
		else if(s == "검색") {
	 		String sql;
	 		
	 		String searchTitle = searchList.getSelectedItem().toString();
	 		String searchText = searchTF.getText();
	 		
	 		model.setNumRows(0);
	 		
	 		if (searchTitle.equals("이름")) {
	 			sql = "select * from member_info where name like '%"+searchText+"%'";
	 		}
	 		else {
	 			sql = "select * from member_info where tel like '%"+searchText+"%'";
	 		}
			
			try {
		 		//Class.forName("com.mysql.cj.jdbc.Driver");
		 		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
		 		PreparedStatement pstmt = con.prepareStatement(sql);
		 		ResultSet rs = pstmt.executeQuery();
		 			
		 		while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
		 			model.addRow(new Object[] {
		 					rs.getString("code"),
		 					rs.getString("name"),
		 					rs.getString("tel"),
		 					rs.getString("year")
		 					});
		 		} 	//while 종료
		 			
		 		leftPane.add(scrollPane, BorderLayout.CENTER);
		 		rs.close();
		 		pstmt.close();
		 		con.close();
		 		}	//try 종료
		 		catch (Exception e){;}
		}
		else if(s == "회원삭제"){
			int row = memberList.getSelectedRow();
			Object code = memberList.getValueAt(row, 0);
			
			int result = JOptionPane.showConfirmDialog(this, "선택한 회원의 정보를 정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					String sql = "delete from member_info where code ='"+code+"';";
					
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
					Statement dbSt = con.createStatement();
					dbSt.execute(sql);
					ResultSet rs;
					
					dbSt.execute("set @cnt = 0;");
					dbSt.execute("update member_info set member_info.code = @cnt:=@cnt+1;");
					
					PreparedStatement pstmt = con.prepareStatement("select count(*) from member_info;");
					rs = pstmt.executeQuery();
					
					int rowCount = 0;
					if(rs.next()) {
						rowCount = rs.getInt(1);
					}
					rowCount = rowCount+1;
					
					dbSt.execute("alter table member_info AUTO_INCREMENT="+rowCount+";");
					
					rs.close();
					pstmt.close();
					con.close();
					}	//try 종료
				catch (Exception e){;}
			}
			leftPane.remove(scrollPane);
			createTable();
			revalidate();
			repaint();
		}
		else if(s == "정보수정") {
			int result = JOptionPane.showConfirmDialog(this, "선택한 회원의 정보가 입력한 내용으로 수정됩니다.", "수정 확인", JOptionPane.YES_NO_OPTION);
			
			try {
				if (result == JOptionPane.YES_OPTION) {
					String sql = "UPDATE member_info set tel = '"+tfTel.getText()+"', year = '"+selectYear.getSelectedItem().toString()+"' where code = "+Integer.valueOf(tfCode.getText())+";";
				
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
					Statement dbSt = con.createStatement();
					dbSt.execute(sql);
					con.close();
				}
			}catch (Exception e){System.out.println("에러");}
			leftPane.remove(scrollPane);
			createTable();
			revalidate();
			repaint();
		}
	}
	public void mouseClicked(MouseEvent e) {
		int row = memberList.getSelectedRow();
		int temp = Integer.valueOf(memberList.getValueAt(row, 3).toString());
		
		tfCode.setText(memberList.getValueAt(row, 0).toString());
		tfName.setText(memberList.getValueAt(row, 1).toString());
		tfTel.setText(memberList.getValueAt(row, 2).toString());
		selectYear.setSelectedIndex(2010-temp);
	}
	
	//MouseListener 인터페이스 기본 추상메소드
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}

class NewMember extends JDialog implements ActionListener{
	JLabel l1, l2, l3;
	JTextField tf1, tf2;
	JButton btnOK;
	JPanel p1, p2;
	JComboBox selectYear;
	Vector<String> years = new Vector<String>();
	static MemberManagement parents;
	
	public NewMember(MemberManagement m) {
		parents = m;
		
		setTitle("신규등록");
		setSize(380, 250);
		setLocationRelativeTo(null);
		setResizable(false);
		this.setModal(true);
		
		years.add("선택");
		for(int i = 2010; i >= 1950; i--) {
			years.add(String.valueOf(i));
		}
		
		selectYear = new JComboBox<String>(years);
		selectYear.setBounds(180, 115, 100, 20);
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		l1 = new JLabel("회    원    명");
		l2 = new JLabel("전  화   번  호");
		l3 = new JLabel("출  생   연  도");
		
		tf1 = new JTextField();
		tf2 = new JTextField();
		
		l1.setBounds(80, 15, 100, 25);
		l2.setBounds(80, 65, 100, 25);
		l3.setBounds(80, 115, 100, 25);
		
		tf1.setBounds(180, 15, 100, 20);
		tf2.setBounds(180, 65, 100, 20);
		
		p1.add(l1);	p1.add(tf1);
		p1.add(l2);	p1.add(tf2);
		p1.add(l3); p1.add(selectYear);
		
		p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		
		btnOK = new JButton("확인");
		btnOK.addActionListener(this);
		
		p2.add(btnOK);
		
		
		ct.add(p1, BorderLayout.CENTER);
		ct.add(p2, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent ae) {
		String sql;
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			Statement dbSt = con.createStatement();
			
		
			if(tf1.getText().equals("")||tf2.getText().equals("")||selectYear.getSelectedItem().equals("선택")) {
				JOptionPane.showMessageDialog(this, "모든 정보를 입력해주세요.", "입력 확인", JOptionPane.OK_OPTION);
			}
			else {
				sql = "insert into member_info(name, tel, year) values ('"+tf1.getText()+"', '"+tf2.getText()+"', '"+selectYear.getSelectedItem().toString()+"')";
				dbSt.execute(sql);
				dispose();
				parents.leftPane.remove(parents.scrollPane);
				parents.createTable();
				parents.leftPane.revalidate();
				parents.leftPane.repaint();
			}
		con.close();
		}catch(Exception e) {System.out.println("연결 실패");}
		
	}
}

class MovieManagement extends JDialog implements ActionListener{
	String listTitle[] = {"영화코드", "영화제목", "상영시간", "장르"};
	String listSeach[] = {"영화제목", "장르"};
	String[] genre = {"장르", "액션", "SF", "코미디", "스릴러", "범죄", "음악", "스포츠", "로멘스", "기타"};
	JLabel jtl;
	JTextField searchIn;
	JTable movieList;
	JPanel p1, p2, searchPane, menuPane;
	JScrollPane scrollPane;
	JComboBox searchGenre;
	JButton btnAdd,btnModify, btnDelete, btnSearch;
	DefaultTableModel model;
	
	public MovieManagement() {
		setTitle("영화관리");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p2 = new JPanel();
		menuPane = new JPanel();
		searchPane = new JPanel();
		
		ct.add(p1, BorderLayout.NORTH);
		ct.add(p2, BorderLayout.CENTER);
		
		p1.setLayout(new GridLayout(2, 1));
		
		menuPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		btnAdd = new JButton("등록");
		btnModify = new JButton("수정");
		btnDelete = new JButton("삭제");
		
		menuPane.add(btnAdd);
		menuPane.add(btnModify);
		menuPane.add(btnDelete);
		
		btnAdd.addActionListener(this);
		btnModify.addActionListener(this);
		btnDelete.addActionListener(this);
		
		p1.add(menuPane);
		
		searchPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		searchGenre = new JComboBox<String>(genre);
		jtl = new JLabel("제목 : ");
		searchIn = new JTextField("", 15);
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(this);
		
		searchPane.add(searchGenre);
		searchPane.add(jtl);
		searchPane.add(searchIn);
		searchPane.add(btnSearch);
		
		p1.add(searchPane);
		p2.setLayout(new GridLayout(1, 1, 0, 10));
		
		createTable();
		
	}
	
	public void createTable() {
		try {
			model = new DefaultTableModel(listTitle, 0);
			String sql = "select * from movie_info";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
				model.addRow(new Object[] {
						rs.getString("code"),
						rs.getString("title"),
						rs.getInt("time"),
						rs.getString("genre")
						});
			} 	//while 종료
			
			movieList = new JTable(model);
			movieList.setModel(model);
			movieList.getColumnModel().getColumn(0).setPreferredWidth(40);
			movieList.getColumnModel().getColumn(1).setPreferredWidth(200);
			movieList.getColumnModel().getColumn(2).setPreferredWidth(50);
			movieList.getColumnModel().getColumn(3).setPreferredWidth(80);
			
			scrollPane = new JScrollPane(movieList);
			
			p2.add(scrollPane);
			
			movieList.getTableHeader().setReorderingAllowed(false);
			
			rs.close();
			pstmt.close();
			con.close();
			}	//try 종료
		catch (Exception e){;}
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		if(s == "등록") {
			MovieAdd win = new MovieAdd(this);
			win.show();
		}
		else if(s == "수정") {
			int row = movieList.getSelectedRow();
			Object movieCode = movieList.getValueAt(row, 0);
			
			MovieModify win = new MovieModify(movieCode, this);
			win.show();
		}
		else if(s == "삭제"){
			int row = movieList.getSelectedRow();
			Object movieCode = movieList.getValueAt(row, 0);
			
			int result = JOptionPane.showConfirmDialog(this, "선택한 영화의 정보를 정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					String sql = "delete from movie_info where code ='"+movieCode+"';";
					
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
					
					Statement dbSt = con.createStatement();
					dbSt.execute(sql);
					
					PreparedStatement pstmt = con.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					
					while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
						model.addRow(new Object[] {
								rs.getString("code"),
								rs.getString("title"),
								rs.getInt("time"),
								rs.getString("genre")
								});
					} 	//while 종료
					
					remove(scrollPane);
					movieList = new JTable(model);
					scrollPane = new JScrollPane(movieList);
					
					rs.close();
					pstmt.close();
					con.close();
					}	//try 종료
				catch (Exception e){;}
			}
			
			//this.dispose();
			//new MovieManagement().setVisible(true);
		}
		else {
			String sql;
			
			String selectGenre = searchGenre.getSelectedItem().toString();
			String selectTitle = searchIn.getText();
			
			if(selectGenre == "장르" && selectTitle.isEmpty())sql = "select * from movie_info";
			else if (selectGenre == "장르") sql = "select * from movie_info where title like '%"+selectTitle+"%'";
			else if (selectTitle.isEmpty()) sql = "select * from movie_info where genre = '"+selectGenre+"'";
			else sql = "select * from movie_info where genre = '"+selectGenre+"' and title like '%"+selectTitle+"%'";
			
			try {
				model.setNumRows(0);
				//Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
					model.addRow(new Object[] {
							rs.getString("code"),
							rs.getString("title"),
							rs.getInt("time"),
							rs.getString("genre")
							});
				} 	//while 종료
				
				p2.add(scrollPane);
				rs.close();
				pstmt.close();
				con.close();
				}	//try 종료
			catch (Exception e){;}
		}
	}
}

class MovieAdd extends JDialog implements ActionListener{
	JLabel l1, l2, l3, l4;
	JTextField tf1, tf2, tf3;
	JButton btnOK, doubleCheck;
	JPanel p1, p2;
	JComboBox genreList;
	String[] genre = {"액션", "SF", "코미디", "스릴러", "범죄", "음악", "스포츠", "로멘스", "기타"};
	static MovieManagement parents;
	
	public MovieAdd(MovieManagement m) {
		parents = m;
		
		setTitle("영화 등록");
		setSize(380, 250);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		l1 = new JLabel("영  화   코  드");
		l2 = new JLabel("영  화   제  목");
		l3 = new JLabel("상영시간(분)");
		l4 = new JLabel("장              르");
		
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		
		doubleCheck = new JButton("중복확인");
		doubleCheck.addActionListener(this);

		genreList = new JComboBox<String>(genre);
		
		l1.setBounds(40, 15, 100, 25);
		l2.setBounds(40, 55, 100, 25);
		l3.setBounds(40, 95, 100, 25);
		l4.setBounds(40, 135, 100, 25);
		
		tf1.setBounds(140, 15, 45, 25);
		tf2.setBounds(140, 55, 200, 25);
		tf3.setBounds(140, 95, 35, 25);
		
		doubleCheck.setBounds(200, 15, 88, 25);
		
		genreList.setBounds(140, 135, 90, 25);
		
		p1.add(l1);	p1.add(tf1); p1.add(doubleCheck);
		p1.add(l2);	p1.add(tf2);
		p1.add(l3);	p1.add(tf3);
		p1.add(l4); p1.add(genreList);
		
		p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		
		btnOK = new JButton("확인");
		btnOK.setEnabled(false);
		btnOK.addActionListener(this);
		
		p2.add(btnOK);
		
		
		ct.add(p1, BorderLayout.CENTER);
		ct.add(p2, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String sql;
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			Statement dbSt = con.createStatement();
			
		
		if(s == "중복확인") {
			boolean ok = true;
			sql = "select * from movie_info where code = '"+tf1.getText()+"';";
			ResultSet result = dbSt.executeQuery(sql);
			
			while(result.next()) ok = false;
			
			if(!(tf1.getText().isEmpty())) {
				if(ok) {
					JOptionPane.showMessageDialog(this, "사용할 수 있는 코드입니다.", "코드 확인", JOptionPane.INFORMATION_MESSAGE);
					tf1.setEnabled(false);
					btnOK.setEnabled(true);
				}
				else {
					JOptionPane.showMessageDialog(this, "중복되는 코드입니다.", "코드 확인", JOptionPane.OK_OPTION);
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "먼저 코드를 입력해주세요.", "코드 확인", JOptionPane.OK_OPTION);
			}
		}
		
		else {
			if(tf2.getText().equals("")||tf3.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "모든 정보를 입력해주세요.", "입력 확인", JOptionPane.OK_OPTION);
			}
			else {
				sql = "insert into movie_info values ('"+tf1.getText()+"', '"+tf2.getText()+"', "+tf3.getText()+", '"+genreList.getSelectedItem().toString()+"')";
				dbSt.execute(sql);						
				JOptionPane.showMessageDialog(this, "정상적으로 등록되었습니다.", "입력 확인", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				parents.p2.remove(parents.scrollPane);
				parents.createTable();
				parents.p2.revalidate();
				parents.p2.repaint();
			}
		}
		con.close();
		}catch(Exception e) {;}
	}
}

class MovieModify extends JDialog implements ActionListener{
	JLabel l1, l2, l3, l4;
	JTextField tf1, tf2, tf3;
	JButton btnOK;
	JPanel p1, p2;
	JComboBox genreList;
	String[] genre = {"액션", "SF", "코미디", "스릴러", "범죄", "음악", "스포츠", "로멘스", "기타"};
	static MovieManagement parents;
	
	public MovieModify(Object obj, MovieManagement m) {
		parents = m;
		
		setTitle("영화 정보 수정");
		setSize(380, 250);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		l1 = new JLabel("영  화   코  드");
		l2 = new JLabel("영  화   제  목");
		l3 = new JLabel("상영시간(분)");
		l4 = new JLabel("장              르");
		
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();

		genreList = new JComboBox<String>(genre);
		
		try {
			String sql = "select * from movie_info where code = '"+obj+"';";
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				tf1.setText(rs.getString("code"));
				tf2.setText(rs.getString("title"));
				tf3.setText(rs.getString("time"));
				
				for(int i = 0; i < 9; i++) {
					if(genre[i].equals(rs.getString("genre"))) {
						genreList.setSelectedIndex(i);
						break;
					}
				}
			}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {}
		tf1.setEnabled(false);
		
		
		
		l1.setBounds(40, 15, 100, 25);
		l2.setBounds(40, 55, 100, 25);
		l3.setBounds(40, 95, 100, 25);
		l4.setBounds(40, 135, 100, 25);
		
		tf1.setBounds(140, 15, 45, 25);
		tf2.setBounds(140, 55, 200, 25);
		tf3.setBounds(140, 95, 35, 25);
		
		genreList.setBounds(140, 135, 90, 25);
		
		p1.add(l1);	p1.add(tf1);
		p1.add(l2);	p1.add(tf2);
		p1.add(l3);	p1.add(tf3);
		p1.add(l4); p1.add(genreList);
		
		p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		
		btnOK = new JButton("수정");
		btnOK.addActionListener(this);
		
		p2.add(btnOK);
		
		
		ct.add(p1, BorderLayout.CENTER);
		ct.add(p2, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent ae) {
		try {
			String sql = "update movie_info set title='"+tf2.getText()+"', time="+tf3.getText()+", genre='"+genreList.getSelectedItem().toString()+"' where code='"+tf1.getText()+"';";
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			Statement dbSt = con.createStatement();
			dbSt.execute(sql);
			
			JOptionPane.showMessageDialog(this, "정상적으로 수정되었습니다.", "입력 확인", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			parents.p2.remove(parents.scrollPane);
			parents.createTable();
			parents.p2.revalidate();
			parents.p2.repaint();
			
		}catch(Exception e) {}
	}
}

/*
class ProductManagement extends JFrame implements ActionListener {
	ProductManagement (String title) {
		setTitle(title);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
	}
}*/
/*
class SalesManagement extends JFrame implements ActionListener {	// 매출관리
	Vector<String> columnName;
	Vector<Vector<String>> rowData;
	JTable table = null;
	DefaultTableModel model = null;
	JScrollPane scroll;
	JPanel p1, p2, p3;
	JRadioButton[] dates = new JRadioButton[3];
	String [] names = {"일별", "월별", "연도별"};
	JButton btnClose;
	
	SalesManagement (String title) {
		setTitle(title);
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		JPanel top = new JPanel();
		JPanel center = new JPanel();
		JPanel bottom = new JPanel();
		
		ButtonGroup date = new ButtonGroup();
		for(int i = 0; i<dates.length; i++) {
			dates[i] = new JRadioButton(names[i]);
			dates[i].addActionListener(this);
			date.add(dates[i]);
			top.add(dates[i]);
		}

		
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		center.setLayout(new FlowLayout());
		bottom.setLayout(null);
	
		columnName = new Vector<String>();
		columnName.add("날짜");
		columnName.add("상영관");
		columnName.add("상품판매");
		columnName.add("합계");
		
		rowData = new Vector<Vector<String>>();
		model = new DefaultTableModel(rowData, columnName);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(1250, 580));
		center.add(scroll);
		
		ct.add(top, BorderLayout.NORTH);
		ct.add(center, BorderLayout.CENTER);
		ct.add(bottom, BorderLayout.SOUTH);
		
		
		btnClose = new JButton("닫기");
		btnClose.addActionListener(this);
		bottom.setLayout(new FlowLayout());
		bottom.add(btnClose);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {};
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			Statement dbSt = con.createStatement();
			String strSql;
			strSql = "SELECT * FROM sales_info;";
			ResultSet result = dbSt.executeQuery(strSql);
			while(result.next()) {
			}
			dbSt.close();
			con.close();
		} catch(SQLException e) {}
	}
	
	public void actionPerformed(ActionEvent ae) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {};
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			Statement dbSt = con.createStatement();
			String strSql;
			strSql = "SELECT * FROM sales_info;";
			ResultSet result = dbSt.executeQuery(strSql);
			while(result.next()) {
			}
			if(ae.getActionCommand().equals("닫기")) {
				dispose();
			}
			else if(dates[0].isSelected()) {
				
			}
			else if(dates[1].isSelected()) {
				
			}
			else {
				
			}
			dbSt.close();
			con.close();
		} catch(SQLException e) {}

	}
}
*/
/*
class ChangePassword extends JFrame implements ActionListener {
	ChangePassword (String title) {
		setTitle(title);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
	}
}*/

class ChangePrice extends JFrame implements ActionListener {	// 요금변경
	JPanel p1, p2;
	JLabel l1, l2, l3, l4;
	String [] num = {"1", "2", "3", "4"};
	JTextField[] tf = new JTextField[4]; 
	JButton btnOK, btnNO;
	static MainScreen p;
	
	ChangePrice (String title, MainScreen p) {
		setTitle(title);
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		l1 = new JLabel("1인");
		l2 = new JLabel("2인");
		l3 = new JLabel("3인");
		l4 = new JLabel("4인");
		
		for(int i=0; i<4; i++) {
			tf[i] = new JTextField(num[i]);
			tf[i].setVisible(true);

			p1.add(tf[i]);
		}

		l1.setBounds(40, 15, 100, 25);
		l2.setBounds(40, 55, 100, 25);
		l3.setBounds(40, 95, 100, 25);
		l4.setBounds(40, 135, 100, 25);
		
		tf[0].setBounds(140, 15, 200, 25);
		tf[1].setBounds(140, 55, 200, 25);
		tf[2].setBounds(140, 95, 200, 25);
		tf[3].setBounds(140, 135, 200, 25);
		
		p1.add(l1);
		p1.add(l2);
		p1.add(l3);
		p1.add(l4);
		
		p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		
		btnOK = new JButton("변경");
		btnOK.addActionListener(this);
		
		btnNO = new JButton("취소");
		btnNO.addActionListener(this);
		
		p2.add(btnOK);
		p2.add(btnNO);
		
		
		ct.add(p1, BorderLayout.CENTER);
		ct.add(p2, BorderLayout.SOUTH);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {};
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			Statement dbSt = con.createStatement();
			String strSql;
			strSql = "SELECT price FROM price_info;";
			ResultSet result = dbSt.executeQuery(strSql);
			int i = 0;
			while(result.next()) {
				tf[i].setText(result.getString("price"));
				i++;
			}
			dbSt.close();
			con.close();
		} catch(SQLException e) {}
	}
	public void actionPerformed(ActionEvent ae) {
		String s= ae.getActionCommand();
		String p1 = "", p2 = "", p3 = "", p4 = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {};
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			Statement dbSt = con.createStatement();
			String strSql;
			if(s == "변경") {
				p1 = tf[0].getText();
				p2 = tf[1].getText();
				p3 = tf[2].getText();
				p4 = tf[3].getText();
				
				strSql = "UPDATE price_info SET price = '"+p1+"' WHERE person=1;";
				dbSt.executeUpdate(strSql);
				strSql = "UPDATE price_info SET price = '"+p2+"' WHERE person=2;";
				dbSt.executeUpdate(strSql);
				strSql = "UPDATE price_info SET price = '"+p3+"' WHERE person=3;";
				dbSt.executeUpdate(strSql);
				strSql = "UPDATE price_info SET price = '"+p4+"' WHERE person=4;";
				dbSt.executeUpdate(strSql);
				JOptionPane.showMessageDialog(this, "정상적으로 변경되었습니다.", "입력 확인", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				p.remove(p.PtableSP);
				
			}
			else {
				dispose();
			}
			dbSt.close();
			con.close();
		} catch(SQLException e) {}
	}
}

class CheckInMember extends JDialog implements ActionListener{
	int roomNumber;
	String listTitle[] = {"회원코드", "이름", "전화번호", "출생연도"};
	DefaultTableModel model = new DefaultTableModel(listTitle, 0);
	JScrollPane scroll;
	JPanel p1, p2, p3;
	JLabel label1;
	JTextField searchTF;
	JButton searchBtn, nextBtn, cancelBtn;
	JTable memberList;
	
	CheckInMember(int num){
		setModal(true);
		
		roomNumber = num;
		
		setTitle("입실 : 회원검색 " + (roomNumber+1) + "관");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		searchTF = new JTextField(10);
		searchBtn = new JButton("검색");
		searchBtn.addActionListener(this);
		
		p1.add(new JLabel("이름으로 검색 "));
		p1.add(searchTF);
		p1.add(searchBtn);
	
		memberList = new JTable(model);
		scroll = new JScrollPane(memberList);
		
		p2.add(scroll);
		
		nextBtn = new JButton("다음");
		nextBtn.addActionListener(this);
		cancelBtn = new JButton("취소");
		cancelBtn.addActionListener(this);
		
		p3.add(nextBtn);	p3.add(cancelBtn);
		
		ct.add(p1, BorderLayout.NORTH);
		ct.add(p2, BorderLayout.CENTER);
		ct.add(p3, BorderLayout.SOUTH);
	}
	
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		if (s == "검색") {
			String sql;
	 		
	 		String searchText = searchTF.getText();
	 		
	 		model.setNumRows(0);
	 	
	 		sql = "select * from member_info where name like '%"+searchText+"%'";
			
			try {
		 		//Class.forName("com.mysql.cj.jdbc.Driver");
		 		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
		 		PreparedStatement pstmt = con.prepareStatement(sql);
		 		ResultSet rs = pstmt.executeQuery();
		 			
		 		while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
		 			model.addRow(new Object[] {
		 					rs.getString("code"),
		 					rs.getString("name"),
		 					rs.getString("tel"),
		 					rs.getString("year")
		 					});
		 		} 	//while 종료
		 		
		 		rs.close();
		 		pstmt.close();
		 		con.close();
		 		}	//try 종료
		 		catch (Exception e){;}
		}
		else if(s == "다음") {
			int temp = memberList.getSelectedRow();
			
			if(temp == -1) {
				JOptionPane.showMessageDialog(this, "선택을 먼저 해주세요.", "선택 확인", JOptionPane.OK_OPTION);
			}
			else {
				MainScreen.memberCode.set(roomNumber, memberList.getValueAt(temp, 0).toString());
				MainScreen.name.set(roomNumber, memberList.getValueAt(temp, 1).toString());
				dispose();
				
				CheckInMovie ci = new CheckInMovie(roomNumber);
				ci.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ci.setSize(500, 300);
				ci.setLocationRelativeTo(null);
				ci.show();
			}
			
		}
		else if(s =="취소") {
			dispose();
		}
	}
}

class CheckInMovie extends JDialog implements ActionListener{
	int roomNumber;
	String listTitle[] = {"영화코드", "영화제목", "상영시간", "장르"};
	DefaultTableModel model = new DefaultTableModel(listTitle, 0);
	JScrollPane scroll;
	JPanel p1, p2, p3;
	JLabel label1;
	JTextField searchTF;
	JButton searchBtn, nextBtn, cancelBtn;
	JTable movieList;
	
	CheckInMovie(int num){
		setModal(true);
		
		roomNumber = num;
		
		setTitle("입실 : 영화검색 " + (roomNumber+1) + "관");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		searchTF = new JTextField(10);
		searchBtn = new JButton("검색");
		searchBtn.addActionListener(this);
		
		p1.add(new JLabel("제목으로 검색 "));
		p1.add(searchTF);
		p1.add(searchBtn);
	
		movieList = new JTable(model);
		scroll = new JScrollPane(movieList);
		
		p2.add(scroll);
		
		nextBtn = new JButton("다음");
		nextBtn.addActionListener(this);
		cancelBtn = new JButton("취소");
		cancelBtn.addActionListener(this);
		
		p3.add(nextBtn);	p3.add(cancelBtn);
		
		ct.add(p1, BorderLayout.NORTH);
		ct.add(p2, BorderLayout.CENTER);
		ct.add(p3, BorderLayout.SOUTH);
	}
	
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		if (s == "검색") {
			String sql;
	 		
	 		String searchText = searchTF.getText();
	 		
	 		model.setNumRows(0);
	 	
	 		sql = "select * from movie_info where title like '%"+searchText+"%'";
			
			try {
		 		//Class.forName("com.mysql.cj.jdbc.Driver");
		 		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
		 		PreparedStatement pstmt = con.prepareStatement(sql);
		 		ResultSet rs = pstmt.executeQuery();
		 			
		 		while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
		 			model.addRow(new Object[] {
		 					rs.getString("code"),
		 					rs.getString("title"),
		 					rs.getString("time"),
		 					rs.getString("genre")
		 					});
		 		} 	//while 종료
		 		
		 		rs.close();
		 		pstmt.close();
		 		con.close();
		 		}	//try 종료
		 		catch (Exception e){;}
		}
		else if(s == "다음") {
			int temp = movieList.getSelectedRow();
			
			if(temp == -1) {
				JOptionPane.showMessageDialog(this, "선택을 먼저 해주세요.", "선택 확인", JOptionPane.OK_OPTION);
			}
			else {
				MainScreen.movieCode.set(roomNumber, movieList.getValueAt(temp, 0).toString());
				MainScreen.movieTitle.set(roomNumber, movieList.getValueAt(temp, 1).toString());
				dispose();
				
				CheckInCheck cc = new CheckInCheck(roomNumber);
				cc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				cc.setSize(600, 400);
				cc.setLocationRelativeTo(null);
				cc.show();
			}
			
		}
		else if(s =="취소") {
			dispose();
		}
	}
}

class CheckInCheck extends JDialog implements ActionListener{
	int roomNumber;
	JPanel[] pane = new JPanel[7];
	JButton checkInBtn, cancelBtn, selectProduct;
	JRadioButton[] peopleNumber = new JRadioButton[4];
	ButtonGroup group;
	JLabel basicPrice, productPrice, totalPrice;
	
	CheckInCheck(int num){
		setModal(true);
		
		roomNumber = num;
		
		setTitle("입실 : 최종확인 " + (roomNumber+1) + "관");
		Container ct = getContentPane();
		ct.setLayout(new GridLayout(7, 1));
		

		group = new ButtonGroup();
		
		for(int i = 0; i < 7; i++) {
			pane[i] = new JPanel();
			pane[i].setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 50));
			pane[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			if(i == 6) pane[i].setLayout(new FlowLayout(FlowLayout.RIGHT));
		}
		
		
		pane[0].add(new JLabel("회원명           :"));
		pane[0].add(new JLabel(MainScreen.name.get(roomNumber)));
		
		pane[1].add(new JLabel("영화제목       :"));
		pane[1].add(new JLabel(MainScreen.movieTitle.get(roomNumber)));
		
		
		pane[2].add(new JLabel("입실인원       "));
		
		for(int i = 0; i < 4; i++) {
			peopleNumber[i] = new JRadioButton(String.valueOf(i+1));
			group.add(peopleNumber[i]);
			pane[2].add(peopleNumber[i]);
			peopleNumber[i].addActionListener(this);
		}
		
		pane[3].add(new JLabel("기본요금       :"));
		basicPrice = new JLabel("0");
		pane[3].add(basicPrice);
		
		pane[4].add(new JLabel("상품금액       :"));
		productPrice = new JLabel("0");
		productPrice.setBorder(BorderFactory.createEmptyBorder(0 , 0, 0 , 60));
		pane[4].add(productPrice);
		selectProduct = new JButton("상품판매");
		pane[4].add(selectProduct);
		
		checkInBtn = new JButton("입실");
		cancelBtn = new JButton("취소");
		checkInBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		pane[5].add(new JLabel("결제금액       :"));
		totalPrice = new JLabel("0");
		pane[5].add(totalPrice);
		
		pane[6].add(checkInBtn);
		pane[6].add(cancelBtn);
		
		
		for(int i = 0; i < 7; i++) {ct.add(pane[i]);}
		
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String sql;
		
		if(s == "입실") {
			if(group.isSelected(null)) {
				JOptionPane.showMessageDialog(this, "인원 수를 선택하세요.", "인원 확인", JOptionPane.OK_OPTION);
			}
			else {
				int result = JOptionPane.showConfirmDialog(this, "입실을 진행하시겠습니까?", "입실 확인", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
				if(result == JOptionPane.YES_OPTION) {
					MainScreen.room[roomNumber].setBackground(new Color(0xFFD232));
					MainScreen.checkIn.set(roomNumber, true);
					dispose();
				}
			}
		}
		else if(s == "취소") {
			dispose();
		}
		else {
			JRadioButton rb = (JRadioButton) ae.getSource();
		
			sql = "select * from price_info where person = "+ rb.getText() +";";
		
 			try {
 				Class.forName("com.mysql.cj.jdbc.Driver");
 				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
 				PreparedStatement pstmt = con.prepareStatement(sql);
 				ResultSet rs = pstmt.executeQuery();
 				while(rs.next()) {
 					basicPrice.setText(rs.getString("price"));
 					totalPrice.setText(String.valueOf((Integer.valueOf(basicPrice.getText()) + Integer.valueOf(productPrice.getText()))));
 				}
 				rs.close();
		 		pstmt.close();
		 		con.close();
	 			}	//try 종료
	 			catch (Exception e){;}
		}
	}
}

class CheckOut extends JDialog implements ActionListener{
	int roomNumber;
	JPanel[] pane = new JPanel[7];
	JButton checkInBtn, cancelBtn, selectProduct;
	JLabel basicPrice, productPrice, totalPrice;
	
	CheckOut(int num){
		setModal(true);
		
		roomNumber = num;
		
		setTitle("입실 정보 " + (roomNumber+1) + "관");
		Container ct = getContentPane();
		ct.setLayout(new GridLayout(7, 1));
		
		for(int i = 0; i < 7; i++) {
			pane[i] = new JPanel();
			pane[i].setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 50));
			pane[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			if(i == 6) pane[i].setLayout(new FlowLayout(FlowLayout.RIGHT));
		}
		
		
		pane[0].add(new JLabel("회원명           :"));
		pane[0].add(new JLabel(MainScreen.name.get(roomNumber)));
		
		pane[1].add(new JLabel("영화제목       :"));
		pane[1].add(new JLabel(MainScreen.movieTitle.get(roomNumber)));
		
		
		pane[2].add(new JLabel("입실인원       :"));
		
		pane[3].add(new JLabel("기본요금       :"));
		basicPrice = new JLabel("0");
		pane[3].add(basicPrice);
		
		pane[4].add(new JLabel("상품금액       :"));
		productPrice = new JLabel("0");
		productPrice.setBorder(BorderFactory.createEmptyBorder(0 , 0, 0 , 60));
		pane[4].add(productPrice);
		selectProduct = new JButton("추가판매");
		pane[4].add(selectProduct);
		
		checkInBtn = new JButton("퇴실");
		cancelBtn = new JButton("취소");
		checkInBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		pane[5].add(new JLabel("결제금액       :"));
		totalPrice = new JLabel("0");
		pane[5].add(totalPrice);
		
		pane[6].add(checkInBtn);
		pane[6].add(cancelBtn);
		
		
		for(int i = 0; i < 7; i++) {ct.add(pane[i]);}
		
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		if(s == "퇴실") {
			
		}
		else if (s == "취소") {
			dispose();
		}
	}
}

class Time_remaining extends Thread {
	private int i = 0;
	Time_remaining(int time) {
		i = time;
	}
	public void run() {
		while(true) {
			try {
				showMin();
				sleep(60000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void showMin() {
		System.out.println((i--)+"분");
	}
}

public class MovieCafe {

	public static void main(String[] args) {
		/*
		Time_remaining tm = new Time_remaining(10);
		tm.start();
		MainScreen ms = new MainScreen("MovieCafe");
		ms.setSize(1500, 800);
		ms.setLocationRelativeTo(null);
		ms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ms.show();
		*/
		Login li = new Login();
		li.show();
	}

}