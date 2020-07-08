package word_20200614;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Menuframe extends JFrame  implements ActionListener {
	
	JMenuItem wsave ;
	JMenuItem wupdate ;	
	JMenuItem wserch ;
	
	JMenuItem walllist ;
	JMenuItem waplist ;
	JMenuItem wtypelist ;
	
	JMenuItem wexam1 ;
	
	JButton wexit;
	
	int tq_cnt = 0;
	int wq_cnt = 0;
	String wq_str = "";
	
	
	JInternalFrame jp_wsave =  new JInternalFrame("단어 등록", true, true, true, true);
	JInternalFrame jp_wupdate =  new JInternalFrame("단어  수정/삭제", true, true, true, true);	
	JInternalFrame jp_wserch = new JInternalFrame("단어 찾기", true, true, true, true);
	
	JInternalFrame jp_walllist = new JInternalFrame("전체 단어 목록", true, true, true, true);
	JInternalFrame jp_waplist = new JInternalFrame("알파벳 목록", true, true, true, true);
	JInternalFrame jp_wtypelist = new JInternalFrame("사용자 목록", true, true, true, true);
	
	JInternalFrame jp_wexam1 = new JInternalFrame("단어 시험 설정 창", true, true, true, true);
	
	JInternalFrame jp_wexam2 = new JInternalFrame("단어 시험 창", true, true, true, true);
	
	Timer total_time;
	Timer onesencond;
	WMember wm = new WMember();
	
	String[] ss ;
	
	public Menuframe( WMember wm ) {
		this.wm = wm;
		
		this.setTitle("만들어가는 전자사전");
		createMenu(); // 메뉴 생성, 프레임에 삽입
		this.setSize(1024,768);
		this.setLayout(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void createMenu() {
		//JPanel p1 = new JPanel();
		//p1.setBounds(0, 0, 350, 40); // x , y , w , h		
		
		
		JMenuBar mb = new JMenuBar(); 
		JMenu menu1 = new JMenu("단어");
		wsave = new JMenuItem("단어등록");
		wsave.addActionListener(this);
		menu1.add(wsave);
		
		
		
		wupdate = new JMenuItem("단어 수정/삭제");
		wupdate.addActionListener(this);
		menu1.add(wupdate);
		
		wserch = new JMenuItem("단어검색");
		wserch.addActionListener(this);
		menu1.add(wserch);
		
		mb.add(menu1);
		
		JMenu menu2 = new JMenu("단어리스트");
		walllist  = new JMenuItem("전체 단어 리스트");
		walllist.addActionListener(this);
		menu2.add(walllist);
		
		waplist  = new JMenuItem("알파벳 단어 리스트");
		waplist.addActionListener(this);
		menu2.add(waplist);
		
		wtypelist  = new JMenuItem("사용자 정의 리스트");
		wtypelist.addActionListener(this);
		menu2.add(wtypelist);
		
		mb.add(menu2);
		
		JMenu menu3 = new JMenu("시험보기");
		wexam1 = new JMenuItem("단어 시험 보기");
		wexam1.addActionListener(this);
		menu3.add(wexam1);
		mb.add(menu3);
		
		
		wexit = new JButton("Exit");
		wexit.addActionListener(this);		
		mb.add( wexit );
		
		
		this.setJMenuBar(mb);		
		
	}
	
	public void actionPerformed(ActionEvent e){
		//System.out.println(e.getSource());
		setVisible();	// 내부 프레임 전체 제거
		if( e.getSource() == wsave ){	//단어등록
			
			jp_wsave  = new JInternalFrame("단어 등록", true, true, true, true);  
			jp_wsave.setBounds(0, 45, 900, 600);
			jp_wsave.setVisible(true);
			this.add(jp_wsave);
	  
			JPanel jp = new JPanel();
			jp.setLayout(null);
			
			JLabel jl1 = new JLabel("단어를 등록하세요");
			jl1.setBounds(0, 0, 800, 25);
			jp.add(jl1);
			
			JLabel jl2 = new JLabel("스펠링");
			jl2.setBounds(0, 30, 50, 25);
			jp.add(jl2);
			
			JTextField txt1 = new JTextField();
			txt1.setBounds(60, 30, 150, 25);
			jp.add(txt1);
			
			JLabel jl3 = new JLabel("뜻");
			jl3.setBounds(220, 30, 50, 25);
			jp.add(jl3);
			
			JTextField txt2 = new JTextField();
			txt2.setBounds(280, 30, 150, 25);
			jp.add(txt2);
			
			JLabel jl4 = new JLabel("사용자구분");
			jl4.setBounds(440 , 30 , 70 , 25 );
			jp.add(jl4);
			
			JTextField txt3 = new JTextField();
			txt3.setBounds(520, 30 , 200, 25);
			jp.add(txt3);
			
			
			  String data[][]= {};			  
			  String column[]={"번호","단어","뜻","구분"};     
			  DefaultTableModel dtm = new DefaultTableModel(data,column); 
			  JTable jt=new JTable( dtm );			  
			  dtm.fireTableDataChanged();
			  JScrollPane jsp = new JScrollPane(jt);
			  jsp.setBounds(5,110,700,400);			
			  jp.add(jsp);
			  
			  ArrayList<String[]> al = WordDbConnect.getWordAllList(wm);
			  for (String[] strings : al) {			
				dtm.addRow(strings);
			  }
			
			jp_wsave.add(jp);
			
			
			JButton btn1 = new JButton("저장");
			btn1.setBounds(0, 60, 250, 35);
			jp.add(btn1);			
	        btn1.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {

	            	WordDbConnect.setWord( wm.getMId() , txt1.getText() , txt2.getText() , txt3.getText()  );	            	
	                
	                dtm.setRowCount(0);	//테이블 초기화
	                ArrayList<String[]> al = WordDbConnect.getWordAllList(wm);
		  			  for (String[] strings : al) {			
		  				dtm.addRow(strings);
		  			  }
		  			txt1.setText("");txt2.setText("");txt3.setText("");
	            }	            
	        });
			
			jp_wsave.add(jp);
			
			JButton btn2 = new JButton("취소");
			btn2.setBounds(260 , 60 , 250 , 35);
	        btn2.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {
	            	txt1.setText("");txt2.setText("");txt3.setText("");	                
	            }	            
	        });
			jp.add(btn2);	
			
		}else if ( e.getSource() == wupdate ){	//단어 삭제 수정
			
			
			jp_wupdate  = new JInternalFrame("단어 수정/삭제", true, true, true, true);  
			jp_wupdate.setBounds(0, 45, 900, 600);
			jp_wupdate.setVisible(true);
			this.add(jp_wupdate);
	  
			JPanel jp = new JPanel();
			jp.setLayout(null);
			
			JLabel jl1 = new JLabel("단어를 선택하세요하세요");
			jl1.setBounds(0, 0, 800, 25);
			jp.add(jl1);
			
			JTextField txt0 = new JTextField();	// 순번을 넣기위한 값			
			txt0.setEditable(false);			
			jp.add(txt0);
			
			JLabel jl2 = new JLabel("스펠링");
			jl2.setBounds(0, 30, 50, 25);
			jp.add(jl2);
			
			JTextField txt1 = new JTextField();
			txt1.setBounds(60, 30, 150, 25);
			
			jp.add(txt1);
			
			JLabel jl3 = new JLabel("뜻");
			jl3.setBounds(220, 30, 50, 25);
			jp.add(jl3);
			
			JTextField txt2 = new JTextField();
			txt2.setBounds(280, 30, 150, 25);
			
			jp.add(txt2);
			
			JLabel jl4 = new JLabel("사용자구분");
			jl4.setBounds(440 , 30 , 70 , 25 );
			jp.add(jl4);
			
			JTextField txt3 = new JTextField();
			txt3.setBounds(520, 30 , 200, 25);
			
			jp.add(txt3);
			
			txt1.setEditable(false);
			txt2.setEditable(false);
			txt3.setEditable(false);
			
			  String data[][]= {};			  
			  String column[]={"번호","단어","뜻","구분"};     
			  DefaultTableModel dtm = new DefaultTableModel(data,column); 
			  JTable jt=new JTable( dtm );			  
			  jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			  
			  jt.addMouseListener(new MouseAdapter() {
				  public void mouseClicked(MouseEvent e) {
					  int row = jt.getSelectedRow();
					  String a_seq = (String) jt.getModel().getValueAt( row   , 0 );
					  String a_spell = (String) jt.getModel().getValueAt( row   , 1 );
					  String a_mean = (String) jt.getModel().getValueAt( row   , 2 );
					  String a_type = (String) jt.getModel().getValueAt( row   , 3 );					  
					  txt0.setText( a_seq );
					  txt1.setText( a_spell );
					  txt2.setText( a_mean );
					  txt3.setText( a_type );
					  txt1.setEditable(true);
					  txt2.setEditable(true);
					  txt3.setEditable(true);
					  
				  }
			  });
			
			  
			  dtm.fireTableDataChanged();
			  JScrollPane jsp = new JScrollPane(jt);
			  jsp.setBounds(5,110,700,400);			
			  jp.add(jsp);
			  
			  ArrayList<String[]> al = WordDbConnect.getWordAllList(wm);
			  for (String[] strings : al) {			
				dtm.addRow(strings);
			  }
			
			  jp_wupdate.add(jp);
			
			
			JButton btn1 = new JButton("수정");
			btn1.setBounds(0, 60, 250, 35);
			jp.add(btn1);			
	        btn1.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {

	            	WordDbConnect.setWordUpdate( txt0.getText() , txt1.getText() , txt2.getText() , txt3.getText()  );	            	
	                
	                dtm.setRowCount(0);	//테이블 초기화
	                ArrayList<String[]> al = WordDbConnect.getWordAllList(wm);
		  			  for (String[] strings : al) {			
		  				dtm.addRow(strings);
		  			  }
		  			txt0.setText("");txt1.setText("");txt2.setText("");txt3.setText("");
		  			txt1.setEditable(false); txt2.setEditable(false); txt3.setEditable(false);
	            }	            
	        });
			
	        jp_wupdate.add(jp);
			
			JButton btn2 = new JButton("삭제");
			btn2.setBounds(260 , 60 , 250 , 35);
	        btn2.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {
	            	WordDbConnect.setWordDelete( txt0.getText() ); 
	            	
	            	dtm.setRowCount(0);	//테이블 초기화
	                ArrayList<String[]> al = WordDbConnect.getWordAllList(wm);
		  			  for (String[] strings : al) {			
		  				dtm.addRow(strings);
		  			 }
		  			  
	            	txt0.setText("");txt1.setText("");txt2.setText("");txt3.setText("");
		  			txt1.setEditable(false); txt2.setEditable(false); txt3.setEditable(false);
	            }	            
	        });
			jp.add(btn2);		
			
		}else if( e.getSource() == wserch) {
			
			jp_wserch  = new JInternalFrame("단어 찾기", true, true, true, true);  
			jp_wserch.setBounds(0, 45, 900, 600);
			jp_wserch.setVisible(true);
			this.add(jp_wserch);
	  
			JPanel jp = new JPanel();
			jp.setLayout(null);
			
			JLabel jl1 = new JLabel("스펠링 혹은 뜻으로 단어를 검색하세요");
			jl1.setBounds(0, 0, 800, 25);
			jp.add(jl1);
			
			JTextField txt0 = new JTextField();	// 순번을 넣기위한 값			
			txt0.setEditable(false);			
			jp.add(txt0);
			
			JLabel jl2 = new JLabel("스펠링");
			jl2.setBounds(0, 30, 50, 25);
			jp.add(jl2);
			
			JTextField txt1 = new JTextField();
			txt1.setBounds(60, 30, 150, 25);
			
			jp.add(txt1);
			
			JLabel jl3 = new JLabel("뜻");
			jl3.setBounds(220, 30, 50, 25);
			jp.add(jl3);
			
			JTextField txt2 = new JTextField();
			txt2.setBounds(280, 30, 150, 25);
			
			jp.add(txt2);
			
						
			
			  String data[][]= {};			  
			  String column[]={"단어","뜻"};     
			  DefaultTableModel dtm = new DefaultTableModel(data,column); 
			  JTable jt=new JTable( dtm );			  
			    
			  dtm.fireTableDataChanged();
			  JScrollPane jsp = new JScrollPane(jt);
			  jsp.setBounds(5,80,700,400);			
			  jp.add(jsp);
			
			JButton btn1 = new JButton("검색");
			btn1.setBounds(440, 30, 250, 25);
			jp.add(btn1);			
	        btn1.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {	            	            	
	                
	                dtm.setRowCount(0);	//테이블 초기화
	                ArrayList<String[]> al = WordDbConnect.getWordSearch(wm, txt1.getText() , txt2.getText() , "%");
		  			  for (String[] strings : al) {			
		  				dtm.addRow(strings);
		  			  }
		  			
	            }	            
	        });
			
	        jp_wserch.add(jp);
		
			
		}else if( e.getSource() == walllist ){	//전체 단어 리스트
			

			jp_walllist  = new JInternalFrame("저장된 모든 단어", true, true, true, true);  
			jp_walllist.setBounds(0, 45, 900, 600);
			jp_walllist.setVisible(true);
			this.add(jp_walllist);
	  
			JPanel jp = new JPanel();
			jp.setLayout(null);
			
					
			JTextField txt0 = new JTextField();	// 순번을 넣기위한 값			
			txt0.setEditable(false);			
			jp.add(txt0);
			
			JLabel jl2 = new JLabel("저장된 모든 단어");
			jl2.setBounds(0, 30, 120, 25);
			jp.add(jl2);
			
			JTextField txt1 = new JTextField();
			txt1.setBounds(130, 30, 150, 25);
			txt1.setEditable(false);
			txt1.setText( WordDbConnect.getWordCount(wm)  );
			jp.add(txt1);
			
			  String data[][]= {};			  
			  String column[]={"단어","뜻"};     
			  DefaultTableModel dtm = new DefaultTableModel(data,column); 
			  JTable jt=new JTable( dtm );			  
			  
			  
			  dtm.fireTableDataChanged();
			  JScrollPane jsp = new JScrollPane(jt);
			  jsp.setBounds(5,60,700,400);			
			  jp.add(jsp);
			  
			  ArrayList<String[]> al = WordDbConnect.getWordSearch(wm, "%", "%" ,"%");
			  for (String[] strings : al) {			
				dtm.addRow(strings);
			  }
			
			  jp_walllist.add(jp);
			
			
			
			
			
			
		}else if( e.getSource() == waplist ){	//알파벳 단어 리스트
			
			
			jp_waplist  = new JInternalFrame("알파벳 리스트 창", true, true, true, true);  
			jp_waplist.setBounds(0, 45, 900, 600);
			jp_waplist.setVisible(true);
			this.add(jp_waplist);
	  
			JPanel jp = new JPanel();
			jp.setLayout(null);
			
					
			JTextField txt0 = new JTextField();	// 순번을 넣기위한 값			
			txt0.setEditable(false);			
			jp.add(txt0);
			
			JLabel jl2 = new JLabel("알파벳 리스트");
			jl2.setBounds(0, 30, 120, 25);
			jp.add(jl2);
			
			
			
			

			
			  String data[][]= {};			  
			  String column[]={"단어","뜻"};     
			  DefaultTableModel dtm = new DefaultTableModel(data,column); 
			  JTable jt=new JTable( dtm );			  
			  
			  
			  dtm.fireTableDataChanged();
			  JScrollPane jsp = new JScrollPane(jt);
			  jsp.setBounds(5,60,700,400);			
			  jp.add(jsp);
			  
			  ArrayList<String[]> al = WordDbConnect.getWordSearch(wm, "A", "%" , "%");
			  for (String[] strings : al) {			
				dtm.addRow(strings);
			  }
			  
			  
			  JComboBox combo1 = new JComboBox();
				combo1.setBounds(130, 30, 120, 25);
				
				combo1.addActionListener( new ActionListener(){	            
		            public void actionPerformed(ActionEvent e) {
		            	
		                
		                dtm.setRowCount(0);	//테이블 초기화
		                ArrayList<String[]> al = WordDbConnect.getWordSearch(wm, (String) combo1.getSelectedItem() , "%" ,"%");
			  			  for (String[] strings : al) {			
			  				dtm.addRow(strings);
			  			  }
			  			
		            }	            
		        });
				
				char c;
		        for(c = 'A'; c <= 'Z'; ++c) {
		            System.out.print(c + " ");
		            combo1.addItem(c+"");
		        }
				
		        jp.add(combo1);
			  
			
			  jp_waplist.add(jp);
			
			
		}else if( e.getSource() == wtypelist ){	//사용자 정의 단어 리스트
			
			
			jp_wtypelist  = new JInternalFrame("사용자 정의 리스트", true, true, true, true);  
			jp_wtypelist.setBounds(0, 45, 900, 600);
			jp_wtypelist.setVisible(true);
			this.add(jp_wtypelist);
	  
			JPanel jp = new JPanel();
			jp.setLayout(null);
			
					
			JTextField txt0 = new JTextField();	// 순번을 넣기위한 값			
			txt0.setEditable(false);			
			jp.add(txt0);
			
			JLabel jl2 = new JLabel("사용자 정의 리스트");
			jl2.setBounds(0, 30, 120, 25);
			jp.add(jl2);
			
			
			
			

			
			  String data[][]= {};			
			  
			  String column0[]={"목록"};     
			  DefaultTableModel dtm0 = new DefaultTableModel(data,column0); 
			  JTable jt0=new JTable( dtm0 );			  
			  dtm0.fireTableDataChanged();
			  JScrollPane jsp0 = new JScrollPane(jt0);
			  jsp0.setBounds(5,60,120,400);			
			  jp.add(jsp0);
			  
			  ArrayList<String[]> al0 = WordDbConnect.getWordType(wm);
			  for (String[] strings : al0) {			
				dtm0.addRow(strings);
			  }
			  
			  
			  
			  
			  
			  
			  String column[]={"단어","뜻"};     
			  DefaultTableModel dtm = new DefaultTableModel(data,column); 
			  JTable jt=new JTable( dtm );			  
			  dtm.fireTableDataChanged();
			  JScrollPane jsp = new JScrollPane(jt);
			  jsp.setBounds(140,60,700,400);			
			  jp.add(jsp);
			  
			  ArrayList<String[]> al = WordDbConnect.getWordSearch(wm, "%", "%" , al0.get(0)[0] );
			  for (String[] strings : al) {			
				dtm.addRow(strings);
			  }
			  		  
			  
			  
			  jt0.addMouseListener(new MouseAdapter() {
				  public void mouseClicked(MouseEvent e) {
					  int row = jt0.getSelectedRow();
					  String a_type = (String) jt0.getModel().getValueAt( row   , 0 );
					  ArrayList<String[]> al = WordDbConnect.getWordSearch(wm, "%", "%" , a_type );
					  dtm.setRowCount(0);	//테이블 초기화
					  for (String[] strings : al) {			
						dtm.addRow(strings);
					  }					  
				  }
			  });
			  			  
			
			  jp_wtypelist.add(jp);
			
		}else if( e.getSource() == wexam1 ){
			
			jp_wexam1  = new JInternalFrame("단어 시험 설정 창", true, true, true, true);  
			jp_wexam1.setBounds(0, 45, 400, 400);
			jp_wexam1.setVisible(true);
			this.add(jp_wexam1);
	  
			JPanel jp = new JPanel();
			jp.setLayout(null);
			
					
						
			JLabel jl2 = new JLabel("단어시험");
			jl2.setBounds(0, 30, 150, 25);
			jp.add(jl2);
			
			JLabel jl3 = new JLabel("총 제한 시간(초)");
			jl3.setBounds(0, 60, 150, 25);
			jp.add(jl3);
			
			JTextField txt1 = new JTextField(); 
			txt1.setBounds(0, 90, 150, 25);
			txt1.setText("60");
			jp.add(txt1);
			
			JLabel jl4 = new JLabel("1문제당 제한 시간(초)");
			jl4.setBounds(0, 120, 150, 25);
			jp.add(jl4);
			
			JTextField txt2 = new JTextField(); 
			txt2.setBounds(0, 150, 150, 25);
			txt2.setText("5");
			jp.add(txt2);
			

			
			  String data[][]= {};			
			  
			  String column0[]={"범위선택"};     
			  DefaultTableModel dtm0 = new DefaultTableModel(data,column0); 
			  JTable jt0=new JTable( dtm0 );			  
			  dtm0.fireTableDataChanged();
			  JScrollPane jsp0 = new JScrollPane(jt0);
			  jsp0.setBounds(200,30,120,200);			
			  jp.add(jsp0);
			  
			  ArrayList<String[]> al0 = WordDbConnect.getWordType(wm);
			  for (String[] strings : al0) {			
				dtm0.addRow(strings);
			  }
			  
			  ButtonGroup  group = new ButtonGroup();
			  
			  JRadioButton jcb1 = new JRadioButton("모든 단어 랜덤 활성화");
			  jcb1.setBounds(0,180,200,25);
			  JRadioButton jcb2 = new JRadioButton("잘주 틀리는 단어");
			  jcb2.setBounds(0,210,200,25);
			  
			  group.add(jcb1);
			  group.add(jcb2);
			  
			  jp.add(jcb1);
			  jp.add(jcb2);
			  
			  JButton btn1 = new JButton("시험보기");
				btn1.setBounds(0, 240, 350, 25);
				jp.add(btn1);			
		        btn1.addActionListener( new ActionListener(){	            
		            public void actionPerformed(ActionEvent e) {
		            	String tot_time = txt1.getText();
		            	String one_time = txt2.getText();
		            	int row = jt0.getSelectedRow();		            	
		            	String selected_type = row==-1?"":(String) jt0.getModel().getValueAt( row   , 0 );		            	
		            	String radio1_selected = jcb1.isSelected()?"T":"F";
		            	String radio2_selected = jcb2.isSelected()?"T":"F";
		            	System.out.println("tot_time : "+tot_time);
		            	System.out.println("one_time : "+one_time);
		            	System.out.println("selected_type : "+selected_type);
		            	System.out.println("radio1_selected : "+radio1_selected);
		            	System.out.println("radio2_selected : "+radio2_selected);
		            	showExam(tot_time , one_time , selected_type , radio1_selected , radio2_selected);
		            	
		            }	            
		        });
			  
			  
			
			  jp_wexam1.add(jp);
			
			
		}else if( e.getSource() == wexit ){	//나가기
			System.exit(0);
		}
		this.revalidate();
		this.repaint();
		//this.show();
		
	}
	
	public void showExam( String ttime , String onetime , String type , String opt1 , String opt2) {
		tq_cnt = 0;
		wq_cnt = 0;
		wq_str = "";
		int tt = Integer.parseInt( ttime ) * 1000;
		int oo = Integer.parseInt( onetime ) * 1000;
		
		int ontime = Integer.parseInt( onetime );
		
		
		
		
		this.remove(jp_wexam1);
		
		jp_wexam2  = new JInternalFrame("단어 시험 진행 창", true, true, true, true);  
		jp_wexam2.setBounds(50, 50, 250, 250);
		jp_wexam2.setVisible(true);
		this.add(jp_wexam2);
  
		JPanel jp = new JPanel();
		jp.setLayout(null);
		
		JLabel jl1 = new JLabel("단어");
		jl1.setBounds(0, 30, 50, 25);
		jp.add(jl1);
		
		JTextField txt1 = new JTextField();	// 순번을 넣기위한 값
		txt1.setBounds(60, 30, 150, 25);
		txt1.setEditable(false);			
		jp.add(txt1);
		
		JLabel jl2 = new JLabel("뜻");
		jl2.setBounds(0, 60, 50, 25);
		jp.add(jl2);
		
		JTextField txt2 = new JTextField();
		txt2.setBounds(60, 60, 150, 25);
		
		
		
		jp.add(txt2);
		
		JTextField txt3 = new JTextField();
		txt3.setEditable(false);
		txt3.setBounds(0, 90, 100, 25);		
		jp.add(txt3);
		
		JTextField txt4 = new JTextField();
		txt4.setEditable(false);
		txt4.setBounds(100, 90, 100, 25);		
		jp.add(txt4);
		
		ss = WordDbConnect.getWordExam(wm, type, opt1, opt2);
		txt1.setText( ss[1] );
		
		
		txt2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					tq_cnt ++;
					if( txt2.getText().equals( ss[2] ) ) {	//정답 기준
						wq_cnt ++;
						JOptionPane.showMessageDialog(null, "정답");
	                	WordDbConnect.upDateExam( ss[0] , "+");
	                }else {	//오답 기준
	                	JOptionPane.showMessageDialog(null, "오답");
	                	WordDbConnect.upDateExam( ss[0] , "-");
	                	wq_str += ss[1]+" , ";
	                }
	                txt2.setText("");
	                ss = WordDbConnect.getWordExam(wm, type, opt1, opt2); //문제 제출제
	                txt1.setText( ss[1] );
	                
	                onesencond.stop();
	                onesencond = new Timer(1000, new ActionListener(){
	                	int ontime = 0;
	                	public void actionPerformed (ActionEvent e){        		
	                		ontime = ontime + 1;
	                		if( ontime >  Integer.parseInt(onetime) ) {	// 한무제당 시간 제한 을 제약
	                			WordDbConnect.upDateExam( ss[0] , "-");
	                			ontime= 0;
	                			onesencond.start();
	                		}else {
	                			txt3.setText( ontime +" / "+onetime  );
	                		}
	                		//
	                	}
	                });
	                onesencond.start();
					
				}
			}
		});
		
		
		
		JButton btn1 = new JButton("다음 문제(엔터키 가능)");
		btn1.setBounds(0, 120, 210, 25);
		jp.add(btn1);			
        btn1.addActionListener( new ActionListener(){	            
            public void actionPerformed(ActionEvent e) {
    			tq_cnt ++;
    			
            	
                if( txt2.getText().equals( ss[2] ) ) {	//정답 기준
                	wq_cnt ++;        			
                	JOptionPane.showMessageDialog(null, "정답");
                	WordDbConnect.upDateExam( ss[0] , "+");
                }else {	//오답 기준
                	wq_str += ss[1]+" , ";
                	JOptionPane.showMessageDialog(null, "오답");
                	WordDbConnect.upDateExam( ss[0] , "-");
                }
                txt2.setText("");
                ss = WordDbConnect.getWordExam(wm, type, opt1, opt2); //문제 제출제
                txt1.setText( ss[1] );
                
                onesencond.stop();
                onesencond = new Timer(1000, new ActionListener(){
                	int ontime = 0;
                	public void actionPerformed (ActionEvent e){        		
                		ontime = ontime + 1;
                		if( ontime >  Integer.parseInt(onetime) ) {	// 한무제당 시간 제한 을 제약
                			WordDbConnect.upDateExam( ss[0] , "-");
                			ontime= 0;
                			onesencond.start();
                		}else {
                			txt3.setText( ontime +" / "+onetime  );
                		}
                		//
                	}
                });
                onesencond.start();
                
            }	            
        });
        
        
        JButton btn2 = new JButton("시험 종료");
		btn2.setBounds(0, 150, 210, 25);
		jp.add(btn2);			
        btn2.addActionListener( new ActionListener(){	            
            public void actionPerformed(ActionEvent e) {	            	            	
                
      			JOptionPane.showMessageDialog(null, wq_cnt+"/"+tq_cnt+" \n 틀린단어( "+wq_str+" ) \n 시험이 종료되었습니다.");
    			total_time.stop();
        		onesencond.stop();
        		setVisible();
            }	            
        });
		
        jp_wexam2.add(jp);
        
        total_time = new Timer( 1000 , new ActionListener(){
        	int thistime = 0;
        	public void actionPerformed (ActionEvent e){
        		thistime = thistime + 1;
        		if( thistime > Integer.parseInt(ttime) ) {	//종료
        			JOptionPane.showMessageDialog(null, wq_cnt+"/"+tq_cnt+" \n 틀린단어( "+wq_str+" ) \n 시험이 종료되었습니다.");
        			total_time.stop();
            		onesencond.stop();
            		setVisible();
            		
        		}else {
        			txt4.setText( thistime +" / "+ttime  );
        		}        		
        	}
        });

        onesencond = new Timer(1000, new ActionListener(){
        	int ontime = 0;
        	public void actionPerformed (ActionEvent e){        		
        		ontime = ontime + 1;
        		if( ontime >  Integer.parseInt(onetime) ) {	// 한무제당 시간 제한 을 제약
        			WordDbConnect.upDateExam( ss[0] , "-");
        			ss = WordDbConnect.getWordExam(wm, type, opt1, opt2); //문제 제출제
        			txt1.setText(ss[1]);
        			ontime= 0;
        			onesencond.start();
        			tq_cnt ++;
        			wq_str += ss[1]+" , ";
        		}else {
        			txt3.setText( ontime +" / "+onetime  );
        		}        		
        	}
        });
        total_time.start();
        onesencond.start();
        
        
        

        this.revalidate();
		this.repaint();
		
	}
	
	public void setVisible(  ){
		this.remove(jp_wsave); // 완료
		this.remove(jp_wupdate); ///완료
		this.remove(jp_wserch); //완료
		
		this.remove(jp_walllist);	//완료
		this.remove(jp_waplist); //완료
		this.remove(jp_wtypelist);		//완료	
		
		this.remove(jp_wexam1);
		this.remove( jp_wexam2 );
		  this.revalidate();
			this.repaint();
	}
	
	
}