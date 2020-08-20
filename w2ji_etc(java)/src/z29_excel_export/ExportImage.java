package z29_excel_export;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import piechart.loadFile;


public class ExportImage extends JFrame  implements ActionListener{
    
	
    List<BookVo> _lb = new ArrayList<BookVo>();
    ArrayList<File> list_img = new ArrayList<File>();
    
    ArrayList<File> detail_img = new ArrayList<File>();
    
    ArrayList<File> big_img = new ArrayList<File>();
    
    String currentDate ="";
    int _size = 0;
    
	Font f1 = new Font("맑은 고딕", Font.PLAIN, 12);
	Color c1 = new Color(0, 112, 192);
	
	Font f2 = new Font("맑은 고딕", Font.BOLD, 15);	//빨간 큰		
	Color c2 = new Color(255, 0, 0);
	
	Font f3 = new Font("맑은 고딕", Font.BOLD, 15);		
	Color c3 = new Color(112, 48, 160);   
    
	// 배포용
	String path =  "c:\\mk_book_list\\";
	
	// ui 변수들
	JButton start_btn; //시작 버튼
	JButton exit_btn; //시작 버튼
	

	
    String data[][]= {};			  
    String column[]={"파일명","ISBN","대표(소스)","상세(소스)","간략(소스)","생성(t)","생성(d)"};	//대표   : BigThumb , 상세 : Detail , 간략 : img 
    DefaultTableModel dtm = new DefaultTableModel(data,column){ public boolean isCellEditable(int i, int c){ return false; } };

    String data1[][]= {};			  
    String column1[]={"로그"};     
    DefaultTableModel dtm1 = new DefaultTableModel(data1,column1){ public boolean isCellEditable(int i, int c){ return false; } };
	
	
	//생성자는 그냥 둔다.
    ExportImage(){} 
    
	public void MakeImage(){		
		this.setLayout(null);	//		   
		//readExcel();	//엑셀 자료 취합
		//start();		//이미지 생성
		
		start_btn = new JButton("시작");
		start_btn.addActionListener(this);
		start_btn.setBounds(7, 5, 235, 100); // x , y , w , h
		this.add(start_btn);
		
		exit_btn = new JButton("프로그램 종료");
		exit_btn.addActionListener(this);
		exit_btn.setBounds(247, 5, 235, 100); // x , y , w , h
		this.add(exit_btn);
        
		
		JPanel jp0 = new JPanel();
		jp0.setLayout(null);
		jp0.setBorder( BorderFactory.createTitledBorder("파일 생성 정보") );
		jp0.setBounds(5,110,480,270);
		
		JTable jt=new JTable( dtm );
		jt.getColumnModel().getColumn(0).setMaxWidth(60);
		jt.getColumnModel().getColumn(1).setMaxWidth(150);
		jt.getColumnModel().getColumn(2).setMaxWidth(60);
		jt.getColumnModel().getColumn(3).setMaxWidth(60);
		jt.getColumnModel().getColumn(4).setMaxWidth(60);
		jt.getColumnModel().getColumn(5).setMaxWidth(45);
		jt.getColumnModel().getColumn(6).setMaxWidth(45);
		dtm.fireTableDataChanged();
		JScrollPane jsp = new JScrollPane(jt);
		jsp.setBounds(10,20,460,240);	// x , y , w , h
		jp0.add(jsp);
		this.add(jp0);
		
		
		
		JPanel jp1 = new JPanel();
		jp1.setLayout(null);
		jp1.setBorder( BorderFactory.createTitledBorder("Log") );
		jp1.setBounds(5, 390, 480, 270);
		
		JTable jt1=new JTable( dtm1 );
		dtm1.fireTableDataChanged();
		JScrollPane jsp1 = new JScrollPane(jt1);
		jsp1.setBounds(10,20,460,240);	// x , y , w , h		  
		jp1.add(jsp1);
		this.add(jp1);

        this.setVisible(true);	 
        this.setTitle("이미지 병합 프로그램 입니다.");
        this.setSize( 500 , 700);	 
        this.setLocationRelativeTo(null);	 
        this.setResizable(false);	 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
    
    
    
	
    public void Test(){
    	setImgTitle(_lb.get(0).getFile_name() );
    	setImgDetail( _lb.get(0).getFile_name() );
    	setImgjpg( _lb.get(0).getFile_name() );
    }
	
    //엑셀 데이터 읽기
    public void readExcel() {
    	setLog("시작 - 엑셀 파일 불러오기");    
    	
		CreateFlod cf = new CreateFlod();			// 폴더 생성 클레스
		String dir_img = "c:\\mk_book_list\\img";
		cf.mkdir( "c:\\mk_book_list" );				//기본폴더 생성
		cf.mkdir( "c:\\mk_book_list\\BigThumb" );				//기본폴더 생성
		cf.mkdir( "c:\\mk_book_list\\Detail" );				//기본폴더 생성
		cf.mkdir( "c:\\mk_book_list\\Thumb" );				//기본폴더 생성
		cf.mkdir( dir_img );		//이미지 폴더
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate = dateFormat.format(new Date());
		
		cf.mkdir( "c:\\mk_book_list\\"+currentDate );
		
		ReadExcel re = new ReadExcel();
		this._lb = re.Read("c:\\mk_book_list\\");
		
		for( File info : new File( "c:\\mk_book_list\\img" ).listFiles() ) {
			list_img.add( info );
		}		
		for( File f : new File("c:\\mk_book_list\\Detail").listFiles() ) {
			detail_img.add( f );
		}		
		for( File f : new File("c:\\mk_book_list\\BigThumb").listFiles() ) {
			big_img.add( f );
		}		
		_size = (int)(this._lb.size() / 2)  * 394;
		this.currentDate = dateFormat.format(new Date());		
		setLog("총 - "+this._lb.size()+"건 읽어 왔습니다.");		
		setLog("종료 - 엑셀 파일 불러오기");
    }

	
	public void start() {
		setLog("이미지 합성 - 시작");
		try {
			List<String> _ls = new ArrayList<String>();
			String _str = "";
			
			setLog("이미지 합성 - 파일명 중복검사 시작");
			for( BookVo bv : _lb ){
				if( !_str.equals( bv.getFile_name() ) ){
					_str = bv.getFile_name();
					_ls.add(_str);
				}
			}
			setLog("이미지 합성 - 파일명 중복검사 종료");
			
			for( String str : _ls ){
				setImgTitle( 	str );
		    	setImgDetail( 	str );
		    	setImgjpg( 		str );
			}
			
			ArrayList<File> cru_dir = new ArrayList<File>();			
			for( File f : new File("c:\\mk_book_list\\"+currentDate).listFiles() ) {
				cru_dir.add( f );
			}
			
			// 상세 합성 이미지 생성
			setLog("이미지 합성 - 간략 + 상세 이미지 병합 시작 ");
			for(BookVo bb : _lb){
				String he = "he"+bb.getIsbn();
				String de = "de"+bb.getIsbn();
				BufferedImage bi_he = null;
				BufferedImage bi_de = null;
				for( File f: cru_dir){
					if( he.equals( getName(f.getName()) )  ){
						bi_he = ImageIO.read(new File( f.getPath() ));
					}
					
					if( de.equals( getName(f.getName()) ) ){
						bi_de = ImageIO.read(new File( f.getPath() ));
					}					
				}
				
				if( bi_he != null && bi_de != null){
					BufferedImage base = new BufferedImage(900, bi_he.getHeight() + bi_de.getHeight()+30 , BufferedImage.TYPE_INT_RGB);
					Graphics2D g = (Graphics2D) base.getGraphics();
					g.setBackground(Color.white);
					g.fillRect(0, 0, 900, bi_he.getHeight() + bi_de.getHeight()+30);
					
					g.drawImage( bi_he , 0, 0, null);
					g.drawImage( bi_de , 0, bi_he.getHeight()+30, null);				
					ImageIO.write(base, "png", new File("c:\\mk_book_list\\"+currentDate+"\\d"+bb.getIsbn()+".png"));
				}				
			}
			setLog("이미지 합성 - 간략 + 상세 이미지 병합 종료 ");
			
			
			//삭제 루틴
			setLog("이미지 합성 - 기타작업 시작 ");
			for( File f : new File("c:\\mk_book_list\\"+currentDate).listFiles() ) {
				System.out.println(f.getName()+" : "+  f.getName().substring(0, 2) );
				if( f.getName().substring(0, 2).equals("he") || f.getName().substring(0, 2).equals("de") ){
					f.delete();					
				}
			}
			setLog("이미지 합성 - 기타작업 종료 ");
		} catch (Exception e) {
			setLog("이미지 합성 - 오류 "+e.toString());
		}
		setLog("이미지 합성 - 종료");
	}
	
	/*
	public BufferedImage MakeBaseImage( BookVo bv) throws IOException{
		BufferedImage bi = null;
		BufferedImage head = null;
		BufferedImage detail = null;
		BufferedImage cover = null;
		Image resizeImage = null;
		
		BufferedImage bi1 = null;
		BufferedImage cover1 = null;
		Image resizeImage1 = null;		
		BufferedImage bi_d = null;
		int _x = 410;
		
		try {			
			System.out.println("isbn : "+bv.getIsbn());
					
			
			//bi 		= setImgjpg(bv);	// 리턴용 이미지
			//head 	= setImgjpg(bv);	// 합성용 머리
			//detail = setImgDetail(bv);	// 합성용 디테일
			
			setImg(bv);			// 해드 이미지 생성
			//setImgTitle(bv);	// 대표 이미지 생성
			
			BufferedImage _temp = new BufferedImage( detail.getWidth() , 394+detail.getHeight(), BufferedImage.TYPE_INT_RGB);
			System.out.println("_temp.getHeight() : "+ _temp.getHeight());
			Graphics2D graphics = (Graphics2D) _temp.getGraphics();
			graphics.setBackground(Color.white);
			graphics.fillRect(0, 0, detail.getWidth(), 394+detail.getHeight());
			
			graphics.drawImage(head, 0, 0, null);
			graphics.drawImage(detail, 0, 394, null);

			ImageIO.write(_temp, "png", new File("c:\\mk_book_list\\"+currentDate+"\\d"+bv.getIsbn()+".png"));
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return bi; 
	}
	*/

	//img_guide_s.jpg
	public BufferedImage setImgjpg(String fn) {
		setLog("이미지 합성 - 간략 이미지 생성 시작 : "+fn);
		BufferedImage _bi = null;
		BufferedImage bi = null;
		
		List<BookVo> _tmp = new ArrayList<BookVo>();
		for(BookVo bv : _lb){
			if(bv.getFile_name().equals(fn)){
				_tmp.add(bv);
			}
		}
		BufferedImage cover[] = new BufferedImage[_tmp.size()];
		Image resizeImage[] = new Image[_tmp.size()];
		
		int _x = 265;
		try {			
			
			for(int i=0; _tmp.size() > i ; i++){
				for( File f  : list_img ) {
					String file_name = getName( f.getName() );								
					if(file_name.equals( _tmp.get(i).getIsbn())) {
						cover[i] = ImageIO.read(new File( f.getPath()  ));
						resizeImage[i] = cover[i].getScaledInstance( 200 , 200 , Image.SCALE_SMOOTH);					
					}
				}
			}
			
			boolean _chk = true;
			int _h = 0;
			
			int _s = (int)(_tmp.size() / 2)  * 394;
			
			BufferedImage base = new BufferedImage(900, _s , BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) base.getGraphics();
			g.setBackground(Color.white);
			
			
			
			
			for(int i=0; _tmp.size() > i ; i++){
				_bi = ImageIO.read(new File( path +"img_guide_s.jpg"  ));
				Graphics2D graphics = (Graphics2D) _bi.getGraphics();
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setBackground(Color.white);
				//graphics.drawImage(_bi, 0, 0, null);
				
				graphics.setColor( c3 );
				graphics.setFont(f3);
				graphics.drawString( _tmp.get(i).getSeq() ,  50 ,  17);
				
				graphics.setColor( c2 );
				graphics.setFont(f2);
				graphics.drawString( _tmp.get(i).getTitle() ,  100 ,  17);
				
				graphics.setColor( c1 );
				graphics.setFont(f1);
				graphics.drawString( _tmp.get(i).getIsbn() ,  _x ,  66);			
				graphics.drawString( _tmp.get(i).getCompany() ,  _x ,  126);
				
				graphics.setColor( c2 );
				graphics.setFont(f2);
				graphics.drawString( _tmp.get(i).getSale_amt() ,  _x ,  187 );
				
				
				graphics.setColor( c1 );
				graphics.setFont(f1);
				graphics.drawString( _tmp.get(i).getList_amt() ,  _x ,  246 );
				
				graphics.drawString( _tmp.get(i).getWriter() ,  _x ,  306 );
				
				graphics.drawString( _tmp.get(i).getPub_date() ,  _x ,  366 );
				
				graphics.drawImage(resizeImage[i] , 4 , 110 , null);
				
				if(_chk) {
					g.drawImage( _bi , 0, _h, null);
					_chk = false;
				}else {
					g.drawImage( _bi , 450 , _h, null);
					_chk = true;
					_h += 394;					
				}
				
			}
			for(int i=0; _tmp.size() > i ; i++){
				ImageIO.write( base , "png", new File("c:\\mk_book_list\\"+currentDate+"\\he"+_tmp.get(i).getIsbn()+".png"));
			}
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		setLog("이미지 합성 - 간략 이미지 생성 종료 : "+fn);
		return _bi;		
	}
	
	
	//img_guide_s.png
	/*
	public BufferedImage setImg(BookVo bv) {
		BufferedImage _bi = null;
		
		BufferedImage bi1 = null;
		BufferedImage cover1 = null;
		Image resizeImage1 = null;
		
		int _x = 410;
		try {			
			
			for( File f  : big_img ) {
				String file_name = getName(f.getName());								
				if(file_name.equals(bv.getIsbn())) {
					cover1 = ImageIO.read(new File( f.getPath()  ));
					resizeImage1 = cover1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);					
				}
			}
			
			
			_bi = ImageIO.read(new File( path +"img_guide_s.png"  ));
			Graphics2D graphics = (Graphics2D) _bi.getGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setBackground(Color.white);
			//graphics.drawImage(_bi, 0, 0, null);
			
			graphics.setColor( c3 );
			graphics.setFont(f3);
			graphics.drawString( bv.getSeq() ,  50 ,  20);
			
			graphics.setColor( c2 );
			graphics.setFont(f2);
			graphics.drawString( bv.getTitle() ,  100 ,  20);
			graphics.setColor( c1 );
			graphics.setFont(f1);
			graphics.drawString( bv.getIsbn() ,  _x ,  78);			
			graphics.drawString( bv.getCompany() ,  _x ,  145);
			
			graphics.setColor( c2 );
			graphics.setFont(f2);
			graphics.drawString( bv.getSale_amt() ,  _x ,  215 );
			
			
			graphics.setColor( c1 );
			graphics.setFont(f1);
			graphics.drawString( bv.getList_amt() ,  _x ,  282 );
			
			graphics.drawString( bv.getWriter() ,  _x ,  349 );
			
			graphics.drawString( bv.getPub_date() ,  _x ,  418 );
			
			graphics.drawImage(resizeImage1 , 20 , 95 , null);
			
			ImageIO.write(_bi, "png", new File("c:\\mk_book_list\\"+currentDate+"\\he"+bv.getIsbn()+".png"));
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return _bi;		
	}
	*/
	
	//purple_base.jpg
	public BufferedImage setImgTitle( String fn ) { //파일 이름 보내 주면 처리
		
		setLog("이미지 합성 - 대표이미지 생성 시작 : "+fn);
		
		
		BufferedImage _bi = null;		
		BufferedImage bi1 = null;
		BufferedImage cover1[] = new BufferedImage[9];
		Image resizeImage1[] = new Image[9];

		List<BookVo> _tmp = new ArrayList<BookVo>();
		for(BookVo bv : _lb){
			if(bv.getFile_name().equals(fn)){
				_tmp.add(bv);
			}
		}
		
		try {		
			int _cnt = 0;
			for(int i=0; _tmp.size() > i ; i++){
				for( File f  : big_img ) {
					String file_name = getName( f.getName() );
					if( file_name.equals(_tmp.get(i).getIsbn() ) && _cnt <= 8 ) {
						cover1[_cnt] = ImageIO.read(new File( f.getPath()  ));
						resizeImage1[_cnt] = cover1[ _cnt ].getScaledInstance(132, 184, Image.SCALE_SMOOTH);
						_cnt ++;
					}
					
				}
			}				
			//System.out.println("cover1.cover1 : "+cover1.length);
			
			_bi = ImageIO.read(new File( path +"purple_base.jpg"  ));
			Graphics2D graphics = (Graphics2D) _bi.getGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setBackground(Color.white);
			
			
			graphics.drawImage(resizeImage1[0] , 88  , 10 , null);			
			graphics.drawImage(resizeImage1[1] , 234 , 10 , null);
			graphics.drawImage(resizeImage1[2] , 380 , 10 , null);
			
			graphics.drawImage(resizeImage1[3] , 88  , 208 , null);
			graphics.drawImage(resizeImage1[4] , 234 , 208 , null);
			graphics.drawImage(resizeImage1[5] , 380 , 208 , null);
			
			graphics.drawImage(resizeImage1[6] , 88  , 406 , null);
			graphics.drawImage(resizeImage1[7] , 234 , 406 , null);
			graphics.drawImage(resizeImage1[8] , 380 , 406 , null);
			
			for(int i=0; _tmp.size() > i ; i++){
				ImageIO.write(_bi, "png", new File("c:\\mk_book_list\\"+currentDate+"\\t"+_tmp.get(i).getIsbn()+".png"));
			}
			
		} catch (Exception e) {			
			JOptionPane.showMessageDialog(null, "이미지 합성 - 대표이미지 생성 오류"+e.toString());
		}
		setLog("이미지 합성 - 대표이미지 생성 종료 : "+fn);
		return _bi;		
	}	
	
	public BufferedImage setImgDetail(String fn ) {
		setLog("이미지 합성 - 상세 이미지 생성 시작 : "+fn);
		BufferedImage _bi = null;
		BufferedImage _bi1 = null;
		
		List<BookVo> _tmp = new ArrayList<BookVo>();
		for(BookVo bv : _lb){
			if(bv.getFile_name().equals(fn)){
				_tmp.add(bv);
			}
		}
		
		BufferedImage _bi2[] = new BufferedImage[_tmp.size()];
		
		try {			
			for(int i=0; _tmp.size() > i ; i++){
				for( File f  : detail_img ) {
					String file_name = getName(f.getName());								
					if(file_name.equals(_tmp.get(i).getIsbn())) {
						_bi2[i] = ImageIO.read(new File( f.getPath()  ));										
					}
				}	
			}
			int max_width = 0;
			int max_height = 0;
			for( BufferedImage bi : _bi2 ){
				if( bi != null ) {
					if( max_width <= bi.getWidth() ){
						max_width = bi.getWidth();
					}
					max_height += (50 + bi.getHeight());
				}				
			}
			
			BufferedImage __tmp = new BufferedImage( 900 , max_height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) __tmp.getGraphics();
			g.setBackground(Color.white);
			g.fillRect(0, 0, 900, max_height);
			
			boolean _aaa = true;
			int cur_height = 0;
			for(int i=0; _tmp.size() > i ; i++){
				if( _bi2[i] != null ) {
					BufferedImage test_bi = _bi2[i];				
					BufferedImage _t = new BufferedImage( 900 , test_bi.getHeight()+50, BufferedImage.TYPE_INT_RGB);
					Graphics2D graphics = (Graphics2D) _t.getGraphics();
					graphics.setBackground(Color.white);
					graphics.fillRect(0, 0, 900, test_bi.getHeight()+50);
					
					if(_aaa){
						_bi1 = ImageIO.read(new File( path +"detail_guide.png"  ));
						graphics.drawImage( _bi1, 0, 0, null);
						_aaa = false;
					}
					
					graphics.drawImage( test_bi, 100, 70, null);
					graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					graphics.setColor( c3 );
					graphics.setFont(f3);
					graphics.drawString( _tmp.get(i).getSeq() ,  10 ,  55);
					graphics.setColor( c2 );
					graphics.setFont(f2);
					graphics.drawString( _tmp.get(i).getTitle() ,  30 ,  55);				
					
					
					g.drawImage(_t , 0 , cur_height , null);
					cur_height += _t.getHeight();
					
				}
				
			}
			for(int i=0; _tmp.size() > i ; i++){
				ImageIO.write(__tmp, "png", new File("c:\\mk_book_list\\"+currentDate+"\\de"+_tmp.get(i).getIsbn()+".png"));
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		setLog("이미지 합성 - 상세 이미지 생성 종료 : "+fn);
		return _bi;
	}
	
	
	
	
	public String getName(String str) {
		int pos = str.lastIndexOf( "." );
		String _fileName = str.substring(0, pos);
		return _fileName;
	}


	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == start_btn ){ //시작 버튼
			// 리스트 초기화
			dtm.setRowCount(0);
			dtm1.setRowCount(0);			
			readExcel();
			start();
			setList();
			JOptionPane.showMessageDialog(null, "완료 되었습니다.");
		}else if(e.getSource() == exit_btn) {//종료
			JOptionPane.showMessageDialog(null, "종료 합니다.");
			System.exit(0);
		}
	}
	
	public void setLog(String str) {
		System.out.println("set Log - "+str);
		String [] _str = new String[1];
		_str[0] = str;
		dtm1.addRow(_str);
	}
	
	public void setList() {		
		ArrayList<File> c_img = new ArrayList<File>();
		for( File info : new File( "c:\\mk_book_list\\"+this.currentDate ).listFiles() ) {
			c_img.add( info );
		}
		
		for(BookVo bv : this._lb) {
			String [] _ss = new String[7];
			_ss[0] = bv.getFile_name();
			_ss[1] = bv.getIsbn();
			_ss[2] = "No";
			for( File f : big_img ) {
				String file_name = getName( f.getName() );
				if( file_name.equals( bv.getIsbn() )  ) {
					_ss[2] = "Yes";
				}
			}
			_ss[3] = "No";
			for( File f : detail_img ) {
				String file_name = getName( f.getName() );
				if( file_name.equals( bv.getIsbn() )  ) {
					_ss[3] = "Yes";
				}
			}
			
			_ss[4] = "No";
			for( File f : list_img ) {
				String file_name = getName( f.getName() );
				if( file_name.equals( bv.getIsbn() )  ) {
					_ss[4] = "Yes";
				}
			}
			
			_ss[5] = "없음";
			for( File f : c_img ) {
				String file_name = getName( f.getName() );
				if( file_name.equals( "t"+bv.getIsbn() )  ) {
					_ss[5] = "완료";
				}
			}
			
			_ss[6] = "없음";
			for( File f : c_img ) {
				String file_name = getName( f.getName() );
				if( file_name.equals( "d"+bv.getIsbn() )  ) {
					_ss[6] = "완료";
				}
			}
			dtm.addRow(_ss);			
		}
				
	}
	
	
	


	   
	
	
}
