package z29_excel_export;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import piechart.loadFile;


public class ExportImage extends JPanel{
    
    
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
	// 로컬 실행용
	//String path =  ExportImage.class.getResource("").getPath().substring(1);
    
    ExportImage( List<BookVo> lb , String file){
    	this._lb = lb;
    	
		for( File info : new File(file).listFiles() ) {
			list_img.add( info );
		}
		
		for( File f : new File("c:\\mk_book_list\\Detail").listFiles() ) {
			detail_img.add( f );
		}
		
		for( File f : new File("c:\\mk_book_list\\BigThumb").listFiles() ) {
			big_img.add( f );
		}
		
		_size = (int)(lb.size() / 2)  * 394;

		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		currentDate = dateFormat.format(new Date());
    } 
    
    
    public void Test(){
    	//setImgjpg(_lb.get(0));    	
    	setImgTitle(_lb.get(0).getFile_name() );
    	setImgDetail( _lb.get(0).getFile_name() );
    	setImgjpg( _lb.get(0).getFile_name() );
    }
	
	public void MakeImage( ){
		System.out.println("Start - MakeImage");
		try {
			List<String> _ls = new ArrayList<String>();
			String _str = "";
			for( BookVo bv : _lb ){
				if( !_str.equals( bv.getFile_name() ) ){
					_str = bv.getFile_name();
					_ls.add(_str);
				}
			}
			
			for( String str : _ls ){
				setImgTitle( 	str );
		    	setImgDetail( 	str );
		    	setImgjpg( 		str );
			}
			
			ArrayList<File> cru_dir = new ArrayList<File>();
			
			for( File f : new File("c:\\mk_book_list\\"+currentDate).listFiles() ) {
				cru_dir.add( f );
			}
			
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
					BufferedImage base = new BufferedImage(900, bi_he.getHeight() + bi_de.getHeight() , BufferedImage.TYPE_INT_RGB);
					Graphics2D g = (Graphics2D) base.getGraphics();
					g.setBackground(Color.white);
					g.fillRect(0, 0, 900, bi_he.getHeight() + bi_de.getHeight());
					
					g.drawImage( bi_he , 0, 0, null);
					g.drawImage( bi_de , 0, bi_he.getHeight(), null);				
					ImageIO.write(base, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\d"+bb.getIsbn()+".jpg"));
				}		
				
							
				
			}
			
			//삭제 루틴
			for( File f : new File("c:\\mk_book_list\\"+currentDate).listFiles() ) {
				System.out.println(f.getName()+" : "+  f.getName().substring(0, 2) );
				if( f.getName().substring(0, 2).equals("he") || f.getName().substring(0, 2).equals("de") ){
					f.delete();					
				}
			}
			

			//ImageIO.write(base, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\total_image.jpg"));
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		System.out.println("End - MakeImage");
	}
	
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

			ImageIO.write(_temp, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\d"+bv.getIsbn()+".jpg"));
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return bi; 
	}
	

	//img_guide_s.jpg
	public BufferedImage setImgjpg(String fn) {
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
					String file_name = getName(f.getName());								
					if(file_name.equals( _tmp.get(i).getIsbn())) {
						cover[i] = ImageIO.read(new File( f.getPath()  ));
						resizeImage[i] = cover[i].getScaledInstance(200, 200, Image.SCALE_SMOOTH);					
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
				//ImageIO.write(_bi, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\h"+_tmp.get(i).getIsbn()+".jpg"));
				
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
				ImageIO.write( base , "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\he"+_tmp.get(i).getIsbn()+".jpg"));
			}
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return _bi;		
	}
	
	
	//img_guide_s.png
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
			
			ImageIO.write(_bi, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\he"+bv.getIsbn()+".jpg"));
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return _bi;		
	}
	
	//purple_base.jpg
	public BufferedImage setImgTitle( String fn ) { //파일 이름 보내 주면 처리
		System.out.println("파일명 : "+fn);
		
		BufferedImage _bi = null;		
		BufferedImage bi1 = null;
		BufferedImage cover1[] = new BufferedImage[9];
		Image resizeImage1[] = new Image[9];
		
		
		//List<BookVo> _lb = new ArrayList<BookVo>();
		List<BookVo> _tmp = new ArrayList<BookVo>();
		for(BookVo bv : _lb){
			if(bv.getFile_name().equals(fn)){
				_tmp.add(bv);
			}
		}
		System.out.println("_tmp.size : "+_tmp.size());
		
		try {			
			for(int i=0; _tmp.size() > i ; i++){
			for( File f  : big_img ) {
				String file_name = getName( f.getName() );
				if(file_name.equals(_tmp.get(i).getIsbn()) && i <= 8 ) {
					cover1[i] = ImageIO.read(new File( f.getPath()  ));
					resizeImage1[i] = cover1[i].getScaledInstance(132, 184, Image.SCALE_SMOOTH);
				}
				
			}
			}				
			System.out.println("cover1.cover1 : "+cover1.length);
			
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
				ImageIO.write(_bi, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\t"+_tmp.get(i).getIsbn()+".jpg"));
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return _bi;		
	}	
	
	public BufferedImage setImgDetail(String fn ) {
		BufferedImage _bi = null;
		BufferedImage _bi1 = null;
		
		//List<BookVo> _lb = new ArrayList<BookVo>();
		List<BookVo> _tmp = new ArrayList<BookVo>();
		for(BookVo bv : _lb){
			if(bv.getFile_name().equals(fn)){
				_tmp.add(bv);
			}
		}
		System.out.println("setImgDetail _tmp.size : "+_tmp.size());
		
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
				if( max_width <= bi.getWidth() ){
					max_width = bi.getWidth();
				}
				max_height += (50 + bi.getHeight());
				System.out.println(bi.getWidth()+" : "+bi.getHeight());
			}
			
			BufferedImage __tmp = new BufferedImage( max_width , max_height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) __tmp.getGraphics();
			int cur_height = 0;
			for(int i=0; _tmp.size() > i ; i++){
				BufferedImage test_bi = _bi2[i];				
				BufferedImage _t = new BufferedImage( test_bi.getWidth() , test_bi.getHeight()+50, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = (Graphics2D) _t.getGraphics();
				graphics.setBackground(Color.white);
				_bi1 = ImageIO.read(new File( path +"detail_guide.png"  ));
				graphics.drawImage( _bi1, 0, 0, null);
				graphics.drawImage( test_bi, 0, 70, null);
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
			for(int i=0; _tmp.size() > i ; i++){
				ImageIO.write(__tmp, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\de"+_tmp.get(i).getIsbn()+".jpg"));
			}
			//ImageIO.write(__tmp, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\de"+".jpg"));
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return _bi;
	}
	
	
	
	
	public String getName(String str) {
		int pos = str.lastIndexOf( "." );
		String _fileName = str.substring(0, pos);
		return _fileName;
	}
	
	
	
	


	   
	
	
}
