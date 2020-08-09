package lottery;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


import javax.net.ssl.HttpsURLConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import com.google.gson.*;
import com.mysql.fabric.xmlrpc.base.Array;

import word_20200614.WordDbConnect;




public class LotteryMain extends JFrame  implements ActionListener , KeyListener {

	
	String url_base = MemberVarible.url;
	SendPost sp = new SendPost();
	
	Timer getInfo; //정보 가져오기.
	
    JButton logBtn;
   
    
    JTextField nick_name;
    
    JTextField num1;
    JTextField num2;
    JTextField num3;
    JTextField num4;
    JTextField num5;
    JTextField num6;
    
    JButton ok_btn;
    JButton getMyNumber;    
    JButton setMyNumber;
    JButton fullAuto;	//완전 자동 번호 생성
    
    
    
    JButton notice_btn;
    JButton notice_btn2;	//시청자 선물하기
    
    String notice_txt = "";
    
    JButton send_btn;    
    JButton gift_btn;	//좌측 선물하기 버튼
    
    
    
    
    String str ="";
    
    String id = "";
    String d_day = "";
    
    Boolean id_chk = false; //정상 아이디 체크 
    
    String data[][]= {};			  
    String column[]={"번호","나의번호","추첨번호","당첨"};     
    DefaultTableModel dtm = new DefaultTableModel(data,column){ public boolean isCellEditable(int i, int c){ return false; } };
    
    String data1[][]= {};			  
    String column1[]={"번호","회차"};     
    DefaultTableModel dtm1 = new DefaultTableModel(data1 , column1){ public boolean isCellEditable(int i, int c){ return false; } };
    
    String data2[][]= {};			  
    public String column2[]={"번호","닉네임","전번","카톡","페이스","금액","물품","기타 텍스트","지역"};     
    DefaultTableModel dtm2 = new DefaultTableModel(data2 , column2){ public boolean isCellEditable(int i, int c){ return false; } };    
    

    JTextField lab3;
    
    JFrame f_gift = null;
	
	
	   public void Lottery() {		   
		    this.setLayout(null);
		   
	        JLabel nick_lab = new JLabel("닉네임  : ");	 
	        nick_lab.setBounds(20, 5, 60, 20); // x , y , w , h
	        	        
	        nick_name= new JTextField(10);
	        nick_name.setBounds(70, 5, 130, 20); // x , y , w , h
	        
	        ok_btn = new JButton("확인");
	        ok_btn.addActionListener(this);
	        ok_btn.setBounds(210, 5, 65, 20); // x , y , w , h
	        
	        gift_btn = new JButton("선물하기");	        
	        gift_btn.addActionListener(this);
	        gift_btn.setBounds(280, 5, 90, 20); // x , y , w , h
	        this.add(gift_btn);
	        
	        JTextField lab1 = new JTextField("이번 회차 정보");	 
	        lab1.setEditable(false);
	        lab1.setBounds(20, 30, 350, 20); // x , y , w , h	        
	        lab1.setHorizontalAlignment(JTextField.CENTER);
	        
	        JTextField lab2 = new JTextField("회차가 없습니다.");
	        lab2.setEditable(false);
	        lab2.setBounds(20, 55, 350, 20); // x , y , w , h
	        
	        String aa[] = getLotteryInfo();
	        this.id = aa[1]; // 이번 회차 id값
	        d_day = aa[3]; // 마감일자.
	        lab2.setText(   aa[0] );	        
	        
	        JTextField lab5 = new JTextField("선물한 내역 보기");
	        lab5.setEditable(false);
	        lab5.setBounds(20, 415, 710, 20); // x , y , w , h	        
	        this.add(lab5);
	        
			  JTable jt2=new JTable( dtm2 );
			  //jt2.getColumnModel().getColumn(0).setMaxWidth(40);
			  //jt2.getColumnModel().getColumn(1).setMaxWidth(310);
			  dtm2.fireTableDataChanged();
			  JScrollPane jsp2 = new JScrollPane(jt2);
			  jsp2.setBounds(20,440,710,200);	// x , y , w , h

			  /*
			  jt2.addMouseListener(new java.awt.event.MouseAdapter() {
				    @Override
				    public void mouseClicked(java.awt.event.MouseEvent evt) {
				    	int row = jt2.getSelectedRow();
						 Object value0 = jt2.getValueAt(row, 0);
						 Object value1 = jt2.getValueAt(row, 1);
						 System.out.println(value0+" : "+value1);
						 getMyLotteryNumber( value0.toString() , nick_name.getText() );
						 lab3.setText(value1.toString());
				    }
				});*/
			  
			  this.add(jsp2);
	        
	        
	        
	        
	        num1 = new JTextField(2);
	        num1.setBounds(20, 90, 50, 35); // x , y , w , h
	        num1.setHorizontalAlignment(JTextField.CENTER);
	        
	        num1.addKeyListener(this);	        
	        IntegerDocument  id1 = new IntegerDocument ();
	        num1.setDocument(id1);

	        
	        
	        num2 = new JTextField(2);
	        num2.setBounds(80, 90, 50, 35); // x , y , w , h
	        num2.addKeyListener(this);
	        num2.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id2 = new IntegerDocument ();
	        num2.setDocument(id2);
	        
	        num3 = new JTextField(2);
	        num3.setBounds(140, 90, 50, 35); // x , y , w , h
	        num3.addKeyListener(this);
	        num3.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id3 = new IntegerDocument ();
	        num3.setDocument(id3);
	        	        
	        num4 = new JTextField(2);
	        num4.setBounds(200, 90, 50, 35); // x , y , w , h
	        num4.addKeyListener(this);
	        num4.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id4 = new IntegerDocument ();
	        num4.setDocument(id4);
	        
	        num5 = new JTextField(2);
	        num5.setBounds(260, 90, 50, 35); // x , y , w , h
	        num5.addKeyListener(this);
	        num5.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id5 = new IntegerDocument ();
	        num5.setDocument(id5);
	        
	        num6 = new JTextField(2);
	        num6.setBounds(320, 90, 50, 35); // x , y , w , h
	        num6.addKeyListener(this);
	        num6.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id6 = new IntegerDocument ();
	        num6.setDocument(id6);
	        
	        setMyNumber = new JButton("번호 등록하기");
	        setMyNumber.addActionListener(this);
	        setMyNumber.setBounds(20, 140, 250, 30); // x , y , w , h
	        
	        
	        fullAuto = new JButton("자동 생성");
	        fullAuto.addActionListener(this);
	        fullAuto.setEnabled(false);
	        fullAuto.setBounds(275, 140, 95, 30); // x , y , w , h
	        this.add(fullAuto);
	        
	        lab3 = new JTextField("이번 회차 나의 등록번호");	 
	        lab3.setEditable(false);
	        lab3.setBounds(20, 180, 350, 20); // x , y , w , h	        
	        lab3.setHorizontalAlignment(JTextField.CENTER);
	        this.add(lab3);
	        
	        
		   
		  JTable jt=new JTable( dtm );
		  jt.getColumnModel().getColumn(0).setMaxWidth(30);
		  jt.getColumnModel().getColumn(1).setMaxWidth(145);
		  jt.getColumnModel().getColumn(2).setMaxWidth(145);
		  jt.getColumnModel().getColumn(3).setMaxWidth(30);
		  dtm.fireTableDataChanged();
		  JScrollPane jsp = new JScrollPane(jt);
		  jsp.setBounds(20,210,350,200);	// x , y , w , h		  
		  this.add(jsp);
		  
	        notice_btn = new JButton("후원하기");
	        notice_btn.addActionListener(this);
	        notice_btn.setBounds(380, 5, 175, 90); // x , y , w , h
	        this.add(notice_btn);
	        
	        
	        notice_btn2 = new JButton("선물하기");
	        notice_btn2.addActionListener(this);	        
	        notice_btn2.setBounds(560, 5, 170, 90); // x , y , w , h
	        this.add(notice_btn2);
	        
	        
		  
	        JTextField lab4 = new JTextField("지난 회차 보기");	 
	        lab4.setEditable(false);
	        lab4.setBounds(380, 100, 350, 30); // x , y , w , h	        
	        lab4.setHorizontalAlignment(JTextField.CENTER);
	        this.add(lab4);		  
		  
		  JTable jt1=new JTable( dtm1 );
		  jt1.getColumnModel().getColumn(0).setMaxWidth(40);
		  jt1.getColumnModel().getColumn(1).setMaxWidth(310);
		  dtm1.fireTableDataChanged();
		  JScrollPane jsp1 = new JScrollPane(jt1);
		  jsp1.setBounds(380,140,350,270);	// x , y , w , h

		  
		  jt1.addMouseListener(new java.awt.event.MouseAdapter() {
			    @Override
			    public void mouseClicked(java.awt.event.MouseEvent evt) {
			    	int row = jt1.getSelectedRow();
					 Object value0 = jt1.getValueAt(row, 0);
					 Object value1 = jt1.getValueAt(row, 1);
					 System.out.println(value0+" : "+value1);
					 getMyLotteryNumber( value0.toString() , nick_name.getText() );
					 lab3.setText(value1.toString());
			    }
			});
		  
		  this.add(jsp1);
		          
	
	        
	        
	        this.add(nick_lab);
	        this.add(nick_name);
	        this.add(ok_btn);
	        
	        this.add( lab1 );
	        this.add( lab2 );
	        
	        this.add( num1 );
	        this.add( num2 );
	        this.add( num3 );
	        this.add( num4 );
	        this.add( num5 );
	        this.add( num6 );
	        this.add(setMyNumber);
	        
	        id_load();	//별칭 가져오기
	        startInfo(); // 오토 정보 가져오기
	        
	        getMyLotteryNumber(id , nick_name.getText() ); // 나의 등록 번호 가져오기
	        getPastLotteryInfo(); // 지난 이력 가져오기
	        getLotteryGift();	// 선물 이력 가져오기

	        this.setVisible(true);	 
	        this.setTitle("환영합니다-----------------!");
	        this.setSize( 753 , 690);	 
	        this.setLocationRelativeTo(null);	 
	        this.setResizable(false);	 
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
	        
	        
	        startInfo();
			this.getInfo = new Timer( 60000 , new ActionListener(){	//60초마다 한번씩
	        	public void actionPerformed (ActionEvent e){
	        		startInfo();
	        	}
	        });	
			getInfo.start();
	        
	        
	    }
	   

	   public void actionPerformed(ActionEvent e){
		   
		   if( !id_chk && e.getSource() != ok_btn ){
			   JOptionPane.showMessageDialog(null, "닉네임을 등록하세요.");
			   return;
		   }
		   
		   
		   if( e.getSource() == ok_btn ){ //별칭 확인 버튼
			   id_creat();
		   }else if( e.getSource() == fullAuto ){ // 번호 자동 생성
			   System.out.println("번호 자동 생성");
			   List<String> li = new ArrayList<String>();
			   for( int i=0; i <= 50 ; i++ ){
				   Random rd = new Random();
				   int tt = ( rd.nextInt(45) + 1);
				   String t = Integer.toString(tt);
				   boolean _chk = true;
				   for( String str : li ){
					   if( str.equals(t)){
						   _chk = false;
					   }
				   }
				   if(_chk){
					   li.add(t);
				   }
			   }
			   num1.setText( li.get(0) );
			   num2.setText( li.get(1) );
			   num3.setText( li.get(2) );
			   num4.setText( li.get(3) );
			   num5.setText( li.get(4) );
			   num6.setText( li.get(5) );
			   
		   }else if( e.getSource() == getMyNumber ){			   
		   }else if( e.getSource() == setMyNumber ){
			   Boolean chk = checkNumber( num1.getText() , num2.getText() , num3.getText() , num4.getText() , num5.getText() , num6.getText()  );
			   if(chk ==true && id_chk == true ) {
				   JOptionPane.showMessageDialog(null, "등록되었습니다.");
					HashMap hm = new HashMap<String, String>();
					hm.put("id"		, id);
					hm.put("nick"	,nick_name.getText() );
					hm.put("num1"	,num1.getText() );
					hm.put("num2"	,num2.getText() );
					hm.put("num3"	,num3.getText() );
					hm.put("num4"	,num4.getText() );
					hm.put("num5"	,num5.getText() );
					hm.put("num6"	,num6.getText() );
					String url = url_base+"/userlotteradd";
					String temp = sp.postRequest(url , hm);
					JsonParser Parser = new JsonParser();
					JsonObject jsonObj = (JsonObject) Parser.parse(temp);
					JsonArray memberArray = (JsonArray) jsonObj.get("list");					  
					dtm.setRowCount(0);
					for (int i = 0; i < memberArray.size(); i++) {
						JsonObject object = (JsonObject) memberArray.get(i);
						String [] ss = new String[4];
						ss[0] = object.get("id").toString().replace("\"", "");
						ss[1] = object.get("mynum").toString().replace("\"", "");
						ss[2] = object.get("goal").toString().replace("\"", "");
						ss[3] = object.get("point").toString().replace("\"", "");						
						dtm.addRow(ss);
					}
					num1.setText("");
					num2.setText("");
					num3.setText("");
					num4.setText("");
					num5.setText("");
					num6.setText("");	
					lab3.setText("이번 회차 나의 등록번호");

			   }else {
				   
			   }
			   
		   }else if( e.getSource() == notice_btn ){
			   //JOptionPane.showMessageDialog(null, notice_txt);
			   try {
				   Desktop.getDesktop().browse(new URI(url_base+"/lottery/usernotice.jsp"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   

		   }else if( e.getSource() == gift_btn) {
			   f_gift = new GiftPanel( nick_name.getText() , this.id );
			   f_gift.addWindowListener(new WindowAdapter() {
				   public void windowClosed(WindowEvent we) {	//종료됨 이벤트					   
					   getLotteryGift();	//
				   }
			   	}
			   
			   );
		   }
		   
		   
	   }
	   
	   // 등록번호 체크
	   public Boolean checkNumber(String n1 , String n2 , String n3 , String n4 , String n5 , String n6){
		   Boolean rtn = false;
		   SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		   Date d1 = null;
		   Date d2 = new Date();
		   try {
			   d1 = df.parse( d_day ) ;   
		   }catch(Exception e) {
			   
		   }
		   int compare = d1.compareTo(d2);
		   //System.out.println(compare);
		   
		   int maxNum = 45;
		   int a1 , a2 , a3 , a4 , a5 , a6;
		   a1 = n1.equals("")?0:Integer.parseInt(n1);
		   a2 = n2.equals("")?0:Integer.parseInt(n2);
		   a3 = n3.equals("")?0:Integer.parseInt(n3);
		   a4 = n4.equals("")?0:Integer.parseInt(n4);
		   a5 = n5.equals("")?0:Integer.parseInt(n5);
		   a6 = n6.equals("")?0:Integer.parseInt(n6);
		   if( a1 == a2 || a2 == a3 || a3 == a4 || a4 == a5 || a5 == a6 || a6 == a1){
			   JOptionPane.showMessageDialog(null, "같은수가 있습니다.");
			   rtn = false;
		   }else if( a1>maxNum || a2>maxNum || a3>maxNum || a4>maxNum || a5>maxNum || a6>maxNum ){
			   JOptionPane.showMessageDialog(null, "45 이상 입력할수 없습니다.");
			   rtn = false;
		   }else if(compare < 0 ){
			   JOptionPane.showMessageDialog(null, "마감일이 지났습니다.");
			   rtn = false;			   
		   }else {
			   rtn = true;
		   }		   
		   System.out.println(n1+" : "+n2+" : "+n3+" : "+n4+" : "+n5+" : "+n6);
		   return rtn;
	   }// 등록번호 체크
	   

	   // start - 회차 정보 가져오기
	   public String[] getLotteryInfo(){
		   StringBuilder sb = new StringBuilder();
			try {
				URL url = new URL(url_base+"/thislottery");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
				con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
				con.setRequestMethod("POST");
				//json으로 message를 전달하고자 할 때 
				con.setRequestProperty("Content-Type", "application/json");
				con.setDoInput(true);
				con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정 
				con.setUseCaches(false);
				con.setDefaultUseCaches(false);

				OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
				wr.write(""); //json 형식의 message 전달 
				wr.flush();				
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					//Stream을 처리해줘야 하는 귀찮음이 있음.
					BufferedReader br = new BufferedReader(
							new InputStreamReader(con.getInputStream(), "utf-8"));
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line).append("\n");
					}
					br.close();
					System.out.println("" + sb.toString());
				} else {
					System.out.println(con.getResponseMessage());
				}
			} catch (Exception e){
				System.err.println(e.toString());
			}
			String txt[] = new String[5];
			txt[0] = "";
			txt[1] = "";
			txt[2] = "";
			txt[3] = "";
			txt[4] = "";
			try {
				JsonParser Parser = new JsonParser();
				JsonObject jsonObj = (JsonObject) Parser.parse(sb.toString());				
				txt[0] = jsonObj.get("txt").toString().replace("\"", "");
				txt[1] = jsonObj.get("id").toString().replace("\"", "");
				txt[2] = jsonObj.get("title").toString().replace("\"", "");
				txt[3] = jsonObj.get("d_day").toString().replace("\"", "");				
				txt[4] = jsonObj.get("auto").toString().replace("\"", "");
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "진행중인 회차가 없습니다.");
			}
			
			return txt;
	   }// end - 회차 정보 가져오기
	        
	
	public void id_load() {
	    try{
	        File file = new File("c:\\lottery_info\\info.dat");
	        FileReader filereader = new FileReader(file);
	        int singleCh = 0;
	        while((singleCh = filereader.read()) != -1){
	        	str += (char)singleCh;	                
	        }
	        filereader.close();
	    }catch (FileNotFoundException e) {
	
	    }catch(IOException e){
	        System.out.println(e);
	    }
	    String test = str.replace(" ", "");
	    
		HashMap hm = new HashMap<String, String>();		
		hm.put("div"	, "id_check" );
		hm.put("p1"	, str );
		hm.put("p2"	, "" );
		hm.put("p3"	, "" );
		hm.put("p4"	, "" );
		hm.put("p5"	, "" );
		hm.put("p6"	, "" );
		hm.put("p7"	, "" );
		hm.put("p8"	, "" );
		hm.put("p9"	, "" );
		hm.put("p10", "" );
		
		String url = url_base+"/sql";
		
		
		String temp = sp.postRequest(url , hm);
		JsonParser Parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) Parser.parse(temp);
		JsonArray memberArray = (JsonArray) jsonObj.get("list");
		System.out.println(memberArray);
		String [] ss = new String[100];
		for (int i = 0; i < memberArray.size(); i++) {
			JsonObject object = (JsonObject) memberArray.get(i);			
			ss[0] = object.get("c0").toString().replace("\"", "");
			ss[1] = object.get("c1").toString().replace("\"", "");
		}    
	    
	    
	    if( test.length() > 0) {	    	
	    	if( ss[0].equals("1") ){
	    		nick_name.setText( str );
	    		nick_name.setEditable(false);
	    		id_chk = true;
	    	}else{
	    		nick_name.setText( str );
	    		nick_name.setEditable(true);
	    		JOptionPane.showMessageDialog(null, "닉네임을 다시 등록 하셔야 합니다.");
	    		id_chk = false;
	    	}	    	
	    }else {
	    	JOptionPane.showMessageDialog(null, "별칭을 등록하셔야 합니다.");
	    	id_chk = false;
	    }
	    
	    
	    
	}
   
	public void id_creat() {
		   String fileName = "c:\\lottery_info\\info.dat" ;			   
		   String path = "c:\\lottery_info"; //폴더 경로
		   File Folder = new File(path);
		   
		   HashMap hm = new HashMap<String, String>();		
			hm.put("div"	, "id_create" );
			hm.put("p1"	, nick_name.getText() );
			hm.put("p2"	, "" );
			hm.put("p3"	, "" );
			hm.put("p4"	, "" );
			hm.put("p5"	, "" );
			hm.put("p6"	, "" );
			hm.put("p7"	, "" );
			hm.put("p8"	, "" );
			hm.put("p9"	, "" );
			hm.put("p10", "" );			
			String url = url_base+"/sql";
			
			
			String temp = sp.postRequest(url , hm);
			JsonParser Parser = new JsonParser();
			JsonObject jsonObj = (JsonObject) Parser.parse(temp);
			String aa = jsonObj.get("boolean").toString().replace("\"", "");
			
			
			
		   
		   
			// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
			if (!Folder.exists()) {
				try{
				    Folder.mkdir(); //폴더 생성합니다.
				    System.out.println("폴더가 생성되었습니다.");
				}catch(Exception ex){
				    ex.getStackTrace();
				}        
		         }else {
				System.out.println("이미 폴더가 생성되어 있습니다.");
			}
	     try{
	         File file = new File(fileName) ;
	         file.delete();
	         FileWriter fw = new FileWriter(file, true) ;
	         String test = nick_name.getText().replace(" ", "");
	         if( test.length() > 0 && aa.equals("true") ) {
	        	 JOptionPane.showMessageDialog(null, "닉네임이 등록되었습니다.");
	        	 fw.write( nick_name.getText() );
	        	 nick_name.setEditable(false);
	        	 id_chk = true;
	         }else if( aa.equals("false") ){
	        	JOptionPane.showMessageDialog(null, "사용중인 닉네임입니다.");
	 	    	id_chk = false;
	         }else{
	        	JOptionPane.showMessageDialog(null, "다시 별칭을 등록하셔야 합니다.");
		 	    id_chk = false; 
	         }
	         
	         
	         fw.flush();		 
	         fw.close();
	     }catch(Exception ex){
	         ex.printStackTrace();
	     }
	}  

	public void getMyLotteryNumber(String _id , String _name) {		
		HashMap hm = new HashMap<String, String>();
		hm.put("id"		, _id);
		hm.put("nick"	, _name );
		String url = url_base+"/mylottery";
		String temp = sp.postRequest(url , hm);
		JsonParser Parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) Parser.parse(temp);
		JsonArray memberArray = (JsonArray) jsonObj.get("list");
		dtm.setRowCount(0);
		for (int i = 0; i < memberArray.size(); i++) {
			JsonObject object = (JsonObject) memberArray.get(i);
			String [] ss = new String[4];
			ss[0] = object.get("id").toString().replace("\"", "");
			ss[1] = object.get("mynum").toString().replace("\"", "");
			ss[2] = object.get("goal").toString().replace("\"", "");
			ss[3] = object.get("point").toString().replace("\"", "");
			dtm.addRow(ss);
		}
	}
	
	//지난 회차 보기
	public void getPastLotteryInfo() {		
		HashMap hm = new HashMap<String, String>();
		String url = url_base+"/pastlottery";
		//String test = postRequest(url , hm);
		String temp = sp.postRequest(url , hm);
		JsonParser Parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) Parser.parse(temp);
		JsonArray memberArray = (JsonArray) jsonObj.get("list");
		dtm1.setRowCount(0);
		for (int i = 0; i < memberArray.size(); i++) {
			JsonObject object = (JsonObject) memberArray.get(i);
			String [] ss = new String[2];
			ss[0] = object.get("id").toString().replace("\"", "");
			ss[1] = object.get("txt").toString().replace("\"", "");
			dtm1.addRow(ss);
		}
		notice_txt = jsonObj.get("notice").toString().replace("\"", "");
	}
	
	// 선물 내역 보기
	public void getLotteryGift() {		
		HashMap hm = new HashMap<String, String>();		
		hm.put("div"	, "select_lottery_gift" );
		hm.put("p1"	, nick_name.getText() );	//닉네임
		hm.put("p2"	, "" );
		hm.put("p3"	, "" );
		hm.put("p4"	, "" );
		hm.put("p5"	, "" );
		hm.put("p6"	, "" );
		hm.put("p7"	, "" );
		hm.put("p8"	, "" );
		hm.put("p9"	, "" );
		hm.put("p10", "" );			
		String url = url_base+"/sql";
		
		String temp = sp.postRequest(url , hm);
		System.out.println("temp : "+temp);
		JsonParser Parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) Parser.parse(temp);
		JsonArray memberArray = (JsonArray) jsonObj.get("list");
		dtm2.setRowCount(0);
		for (int i = 0; i < memberArray.size(); i++) {
			JsonObject object = (JsonObject) memberArray.get(i);
			String [] ss = new String[9];
			ss[0] = object.get("c0").toString().replace("\"", "");
			ss[1] = object.get("c1").toString().replace("\"", "");
			ss[2] = object.get("c4").toString().replace("\"", "");
			ss[3] = object.get("c5").toString().replace("\"", "");
			ss[4] = object.get("c6").toString().replace("\"", "");
			ss[5] = object.get("c15").toString().replace("\"", "");
			ss[6] = object.get("c16").toString().replace("\"", "");
			ss[7] = object.get("c17").toString().replace("\"", "");
			ss[8] = object.get("c2").toString().replace("\"", "");
			
			dtm2.addRow(ss);
		}
	}	
	
	public void startInfo(){
		StringBuilder sb = new StringBuilder();
			try {
				URL url = new URL(url_base+"/thislottery");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
				con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
				con.setRequestMethod("POST");
				//json으로 message를 전달하고자 할 때 
				con.setRequestProperty("Content-Type", "application/json");
				con.setDoInput(true);
				con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정 
				con.setUseCaches(false);
				con.setDefaultUseCaches(false);

				OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
				wr.write(""); //json 형식의 message 전달 
				wr.flush();				
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					//Stream을 처리해줘야 하는 귀찮음이 있음.
					BufferedReader br = new BufferedReader(
							new InputStreamReader(con.getInputStream(), "utf-8"));
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line).append("\n");
					}
					br.close();
					System.out.println("" + sb.toString());
				} else {
					System.out.println(con.getResponseMessage());
				}
			} catch (Exception ee){
				System.err.println(ee.toString());
			}
			String txt[] = new String[5];
			txt[0] = "";
			txt[1] = "";
			txt[2] = "";
			txt[3] = "";
			txt[4] = "";
			try {
				JsonParser Parser = new JsonParser();
				JsonObject jsonObj = (JsonObject) Parser.parse(sb.toString());				
				txt[0] = jsonObj.get("txt").toString().replace("\"", "");
				txt[1] = jsonObj.get("id").toString().replace("\"", "");
				txt[2] = jsonObj.get("title").toString().replace("\"", "");
				txt[3] = jsonObj.get("d_day").toString().replace("\"", "");				
				txt[4] = jsonObj.get("auto").toString().replace("\"", "");
			} catch (Exception ee) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "진행중인 회차가 없습니다.");
			}
			if( txt[4].equals("y") ){
				fullAuto.setEnabled(true);	   				
			}
		
	}



	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode(); // 37 왼쪽 , 39 오른쪽
		if( e.getSource() == num1 ){
			if( keycode==37 ){ num6.requestFocus(); }else{ num2.requestFocus(); }			
		}else if( e.getSource() == num2 ){
			if( keycode==37 ){ num1.requestFocus(); }else{ num3.requestFocus(); }
		}else if( e.getSource() == num3 ){
			if( keycode==37 ){ num2.requestFocus(); }else{ num4.requestFocus(); }
		}else if( e.getSource() == num4 ){
			if( keycode==37 ){ num3.requestFocus(); }else{ num5.requestFocus(); }
		}else if( e.getSource() == num5 ){
			if( keycode==37 ){ num4.requestFocus(); }else{ num6.requestFocus(); }
		}else if( e.getSource() == num6 ){
			if( keycode==37 ){ num5.requestFocus(); }else{ num1.requestFocus(); }
		}		
	}



	
	   
	   
}