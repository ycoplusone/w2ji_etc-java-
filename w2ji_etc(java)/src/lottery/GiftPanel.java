package lottery;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
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

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.google.gson.*;
import com.mysql.fabric.xmlrpc.base.Array;

import word_20200614.WordDbConnect;




public class GiftPanel extends JFrame  implements ActionListener , KeyListener {

		String url_base = MemberVarible.url;
		
		String _id = "";
		String _thisinfo = "";
		
		ButtonGroup  cbg0 = new ButtonGroup();
		JCheckBox cb0_1 = null;
		JCheckBox cb0_2 = null;
		JTextField jt0_3 = null;
		
		ButtonGroup cbg1 = new ButtonGroup();
		JCheckBox cb1_1 = null;
		JCheckBox cb1_2 = null;
		JCheckBox cb1_3 = null;
		JCheckBox cb1_4 = null;
		
		JTextField jt2_1 = null;
		JTextField jt2_2 = null;
		JTextField jt2_3 = null;
		JTextField jt2_4 = null;
		
		
		JLabel jl4_1 = null;
		JLabel jl4_2 = null;
		JLabel jl4_3 = null;
		
		JButton bnt1_1 = null;
		JButton bnt1_2 = null;
		
		JButton bnt2_1 = null;
		JButton bnt2_2 = null;
		
		JButton bnt3_1 = null;
		JButton bnt3_2 = null;

		JTextField jt4_1 = null;
		JTextField jt4_2 = null;
		
		File f1 = null;
		File f2 = null;
		File f3 = null;
		
		JTextField jt5_1 = null;
		JTextField jt5_2 = null;
		
		JButton summit_bnt = null;
		JButton cancel_bnt = null;
		SendFile sf = new SendFile();
		
	
		public GiftPanel(String nickname , String id){
			this._id = nickname;
			this._thisinfo = id;
			this.setLayout(null);
			
			JPanel jp1 = new JPanel();
			jp1.setLayout(null);
			jp1.setBorder( BorderFactory.createTitledBorder("지역") );
			jp1.setBounds(10 , 10 , 200 , 150);
	        
			cb0_1 = new JCheckBox("전체", true);    
			cb0_1.setBounds(10,30, 100,20);
			cbg0.add(cb0_1);
			//jp1.add(cb0_1);
			
			cb0_2 = new JCheckBox("기타", false);    
			cb0_2.setBounds(10,70, 100,20);
			cbg0.add(cb0_2);
			
			jp1.add(cb0_1);
			jp1.add(cb0_2);
			
			jt0_3 = new JTextField();
			jt0_3.setBounds(10,110, 130,20);
			jp1.add( jt0_3 );
			
			this.add(jp1);
			
			JPanel jp2 = new JPanel();
			jp2.setLayout(null);
			jp2.setBorder( BorderFactory.createTitledBorder("등수 선물") );
			jp2.setBounds(210 , 10 , 250 , 150);
			
			cb1_1 = new JCheckBox("3개 일치 4등",  true);    
			cb1_1.setBounds(10,20, 180,20);
			cbg1.add(cb1_1);
			jp2.add(cb1_1);
			
			cb1_2 = new JCheckBox("4개 일치 3등", false);    
			cb1_2.setBounds(10,50, 180,20);
			cbg1.add(cb1_2);
			jp2.add(cb1_2);
			
			cb1_3 = new JCheckBox("5개 일치 2등", false);    
			cb1_3.setBounds(10,80, 180,20);
			cbg1.add(cb1_3);
			jp2.add(cb1_3);
			
			cb1_4 = new JCheckBox("6개 일치 1등", false);    
			cb1_4.setBounds(10,110, 180,20);
			cbg1.add(cb1_4);
			jp2.add(cb1_4);
			
			this.add(jp2);
			
			
			JPanel jp3 = new JPanel();
			jp3.setLayout(null);
			jp3.setBorder( BorderFactory.createTitledBorder("연 락 처") );
			jp3.setBounds(10 , 170 , 200 , 180);
			
			JLabel jl1 = new JLabel("전 화 : ");
			jl1.setBounds(10,20, 60 ,20);
			jp3.add( jl1 );
			
			jt2_1 = new JTextField();
			jt2_1.setBounds(55,20, 120,20);
			jp3.add( jt2_1 );
			
			JLabel jl2 = new JLabel("카 톡 : ");
			jl2.setBounds(10,50, 60 ,20);
			jp3.add( jl2 );
			
			jt2_2 = new JTextField();
			jt2_2.setBounds(55,50, 120,20);
			jp3.add( jt2_2 );
			
			JLabel jl3 = new JLabel("페 북 : ");
			jl3.setBounds(10,80, 60 ,20);
			jp3.add( jl3 );
			
			jt2_3 = new JTextField();
			jt2_3.setBounds(55,80, 120,20);
			jp3.add( jt2_3 );
			
			JLabel jl4 = new JLabel("기 타 : ");
			jl4.setBounds(10,110, 60 ,20);
			jp3.add( jl4 );
			
			jt2_4 = new JTextField();
			jt2_4.setBounds(55,110, 120,20);
			jp3.add( jt2_4 );
			
			this.add(jp3);
			
			JPanel jp4 = new JPanel();
			jp4.setLayout(null);
			jp4.setBorder( BorderFactory.createTitledBorder("사 진 등 록 하 기") );
			jp4.setBounds(210 , 170 , 250 , 180);
			
			jl4_1 = new JLabel();
			jl4_1.setBounds(10 , 20 , 60 , 60 );
			jl4_1.setBorder( new LineBorder(Color.gray) );
			jp4.add( jl4_1 );
			
			bnt1_1 = new JButton("추가");			
			bnt1_1.setBounds(10 , 85 , 60 , 30 );
			bnt1_1.addActionListener(this);
			jp4.add( bnt1_1 );
			
			
			jl4_2 = new JLabel();
			jl4_2.setBounds(90 , 20 , 60 , 60 );
			jl4_2.setBorder( new LineBorder(Color.gray) );
			jp4.add( jl4_2 );
			
			bnt2_1 = new JButton("추가");			
			bnt2_1.setBounds(90 , 85 , 60 , 30 );
			bnt2_1.addActionListener(this);
			jp4.add( bnt2_1 );
			
			
			jl4_3 = new JLabel();
			jl4_3.setBounds(170 , 20 , 60 , 60 );
			jl4_3.setBorder( new LineBorder(Color.gray) );
			jp4.add( jl4_3 );
			
			bnt3_1 = new JButton("추가");			
			bnt3_1.setBounds(170 , 85 , 60 , 30 );
			bnt3_1.addActionListener(this);
			jp4.add( bnt3_1 );
			
			JLabel jl6 = new JLabel("설명 : ");
			jl6.setBounds(10,120, 60 ,20);
			jp4.add( jl6 );
			
			jt4_1 = new JTextField();
			jt4_1.setBounds(55,120, 180,20);
			jp4.add( jt4_1 );
			
			JLabel jl7 = new JLabel("기 타 : ");
			jl7.setBounds(10,150, 60 ,20);
			jp4.add( jl7 );
			
			jt4_2 = new JTextField();
			jt4_2.setBounds(55,150, 180,20);
			jp4.add( jt4_2 );
			this.add(jp4);
			
			JPanel jp5 = new JPanel();
			jp5.setLayout(null);
			jp5.setBorder( BorderFactory.createTitledBorder("선물") );
			jp5.setBounds( 10 , 350 , 450 , 50);
			
			JLabel jl8 = new JLabel("금액 : ");
			jl8.setBounds(10,15, 60 ,20);
			jp5.add( jl8 );
			
			jt5_1 = new JTextField();
			jt5_1.setBounds(55,15, 130,20);
			jp5.add( jt5_1 );
			
			JLabel jl9 = new JLabel("물품 : ");
			jl9.setBounds(200,15, 60 ,20);
			jp5.add( jl9 );
			
			jt5_2 = new JTextField();
			jt5_2.setBounds(260,15, 130,20);
			jp5.add( jt5_2 );
			
			this.add(jp5);

			
			summit_bnt = new JButton("확인");
			summit_bnt.setBounds(108 , 405 , 100 , 40 );
			summit_bnt.addActionListener(this);
			this.add(summit_bnt);
			
			cancel_bnt = new JButton("취소");
			cancel_bnt.setBounds(212 , 405 , 100 , 40 );
			cancel_bnt.addActionListener(this);
			this.add(cancel_bnt);
		    
	        this.setVisible(true);	 
	        this.setTitle(nickname+" 선물하기");
	        this.setSize( 500 , 500);	 
	        this.setLocationRelativeTo(null);	 
	        this.setResizable(false);	 
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
	    }
   

		public void actionPerformed(ActionEvent e){
			
			if( e.getSource() == bnt1_1 ){
				try {
					loadImage( jl4_1 , "f1" );
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}else if( e.getSource() == bnt2_1 ){
				try {
					loadImage( jl4_2 , "f2");
				} catch (Exception e1) {
					e1.printStackTrace();
				}				
			}else if( e.getSource() == bnt3_1 ){
				try {
					loadImage( jl4_3 , "f3" );
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else if( e.getSource() == summit_bnt ){
				System.out.println("확인 summit_bnt");
				
				try {
					String _tmp = sf.sendtest(f1,f2,f3 , getValue());
					JsonParser Parser = new JsonParser();
					JsonObject jsonObj = (JsonObject) Parser.parse( _tmp );
					String aa = jsonObj.get("boolean").toString().replace("\"", "");					
					
					if(aa.equals("true")) {
						//this.setVisible(false);
						this.dispose();
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
			}else if( e.getSource() == cancel_bnt ){
				//this.setVisible(false);
				this.dispose();
				
				//this.EXIT_ON_CLOSE;
				
			}
			
		}
		
	public GiftVO getValue() {
		GiftVO _vo = new GiftVO();
		
		_vo.setNickname( this._id );
		
		if( cb0_1.isSelected() ) {
			_vo.setLocal("전체");
		}else {
			_vo.setLocal( jt0_3.getText() );
		}
		
		if( cb1_1.isSelected() ) {
			_vo.setRankgift("3개 일치 4등");
		}else if( cb1_2.isSelected() ) {
			_vo.setRankgift("4개 일치 3등");
		}else if( cb1_3.isSelected() ) {
			_vo.setRankgift("5개 일치 2등");
		}else if( cb1_4.isSelected() ) {
			_vo.setRankgift("6개 일치 1등");
		}
		
		_vo.setTel( 	jt2_1.getText() );
		_vo.setKakao( 	jt2_2.getText() );
		_vo.setFacebook(jt2_3.getText() );
		_vo.setTeletc(  jt2_4.getText() );
		_vo.setPhoto_comment( jt4_1.getText() );
		_vo.setPhoto_etc( jt4_2.getText() );
		_vo.setAmt( jt5_1.getText() );
		_vo.setProdct( jt5_2.getText());
		_vo.setInfo_id( this._thisinfo);
		
		return _vo;
	} 


	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		/*
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
		}*/		
	}

	   public void loadImage(JLabel jl , String str) throws Exception {
		   	jl.setIcon(null);
			File _f = null;
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("파일 불러오기");
	        fileChooser.setFileFilter(new FileNameExtensionFilter("","jpg", "png","gif","bmp")); // 파일필터
	        
	        fileChooser.setMultiSelectionEnabled(false);// 다중 선택 불가
	        int returnVal = fileChooser.showOpenDialog(this); // show openDialog 
	        if (returnVal == JFileChooser.APPROVE_OPTION) { // 파일을 선택하였을 때
	            try{
	            	
	                //loadImage(fileChooser.getSelectedFile().toString());
	            	String url = fileChooser.getSelectedFile().toString();
	       
	            	_f = new File(url);
	            	if(str.equals("f1")) {
	            		this.f1 = null;
	            		this.f1 = _f;	            		
	            	}else if( str.equals("f2") ) {
	            		this.f2 = null;
	            		this.f2 = _f;
	            	}else if( str.equals("f3") ) {
	            		this.f3 = null;
	            		this.f3 = _f;
	            	}
	            	System.out.println("f : "+_f.getName());
	            	
	            	BufferedImage base = new BufferedImage(56, 56 , BufferedImage.TYPE_INT_RGB);
	            	BufferedImage  imgIcon = ImageIO.read( _f );
	            	Image reimg = imgIcon.getScaledInstance(56, 56, Image.SCALE_SMOOTH);
	            	Graphics2D g = (Graphics2D) base.getGraphics();
	            	g.drawImage(reimg, 0, 0 , null);
	            	
	            	jl.setIcon( new ImageIcon(base) );

	            	
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

	   

	
	   
	   
}