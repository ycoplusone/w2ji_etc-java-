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
	String path =  ".\\";	
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
		
		_size = (int)(lb.size() / 2)  * 450;

		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		currentDate = dateFormat.format(new Date());
    } 
    
    public void paint(Graphics g){
    	//MakeImage((Graphics2D) g );	    
	}	
	
	public void MakeImage( ){
		System.out.println("paint -- ");
		//String path = ExportImage.class.getResource("").getPath().substring(1);
		boolean _chk = true;
		int _h = 0;
		try {
			//base = MakeBaseImage( _lb.get(0));//ImageIO.read(new File( path +"img_guide_s.png"  ));
			//g.drawImage( base , 0, _h, null);
			BufferedImage base = new BufferedImage(1200, _size, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) base.getGraphics();
			g.setBackground(Color.white);
			for(BookVo list : _lb) {
				BufferedImage zzbase = MakeBaseImage(list);//ImageIO.read(new File( path +"img_guide_s.png"  ));
				if(_chk) {
					g.drawImage( zzbase , 0, _h, null);
					_chk = false;
				}else {
					g.drawImage( zzbase , 600 , _h, null);
					_chk = true;
					_h += 450;					
				}
				
			}
			ImageIO.write(base, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\전체합본.jpg"));
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage MakeBaseImage( BookVo bv) throws IOException{
		BufferedImage bi = null;
		BufferedImage cover = null;
		Image resizeImage = null;
		
		BufferedImage bi1 = null;
		BufferedImage cover1 = null;
		Image resizeImage1 = null;
		
		BufferedImage bi_d = null;
		
		

		
		int _x = 410;
		try {			
			System.out.println("isbn : "+bv.getIsbn());
			for( File f  : list_img ) {
				String file_name = getName(f.getName());								
				if(file_name.equals(bv.getIsbn())) {
					cover = ImageIO.read(new File( f.getPath()  ));
					resizeImage = cover.getScaledInstance(300, 300, Image.SCALE_SMOOTH);					
				}
			}
			
			for( File f  : big_img ) {
				String file_name = getName(f.getName());								
				if(file_name.equals(bv.getIsbn())) {
					cover1 = ImageIO.read(new File( f.getPath()  ));
					resizeImage1 = cover1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);					
				}
			}
			
			System.out.println(resizeImage == null?"null 이다":"정상이다.");
			
			bi = setImg(bv);//ImageIO.read(new File( path +"img_guide_s.png"  ));			
			Graphics2D graphics = (Graphics2D) bi.getGraphics();
			graphics.setBackground(Color.white);
			graphics.drawImage(bi, 0, 0, null);			
			graphics.drawImage(resizeImage , 20 , 95 , null);
			
			bi1 = setImg(bv);//ImageIO.read(new File( path +"img_guide_s.png"  ));			
			Graphics2D graphics1 = (Graphics2D) bi1.getGraphics();
			graphics1.setBackground(Color.white);
			graphics1.drawImage(bi1, 0, 0, null);			
			graphics1.drawImage(resizeImage1 , 20 , 95 , null);
			ImageIO.write(bi1, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\t"+bv.getIsbn()+".jpg"));
			
			setImgDetail(bv);
			
			
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return bi; 
	}
	
	public BufferedImage setImg(BookVo bv) {
		BufferedImage _bi = null;
		
		int _x = 410;
		try {			
			
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
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		return _bi;		
	}
	
	public BufferedImage setImgDetail(BookVo bv) {
		BufferedImage _bi = null;
		BufferedImage _bi1 = null;
		
		
		try {			
			
			for( File f  : detail_img ) {
				String file_name = getName(f.getName());								
				if(file_name.equals(bv.getIsbn())) {
					_bi = ImageIO.read(new File( f.getPath()  ));										
				}
			}	
			
			
			_bi1 = ImageIO.read(new File( path +"detail_guide.png"  ));
			
			BufferedImage _temp = new BufferedImage( _bi.getWidth() , _bi.getHeight()+50, BufferedImage.TYPE_INT_RGB);
			
			Graphics2D graphics = (Graphics2D) _temp.getGraphics();
			graphics.setBackground(Color.white);
			
			graphics.drawImage(_bi1, 0, 0, null);
			graphics.drawImage(_bi, 0, 70, null);
			
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setColor( c3 );
			graphics.setFont(f3);
			graphics.drawString( bv.getSeq() ,  10 ,  55);
			
			graphics.setColor( c2 );
			graphics.setFont(f2);
			graphics.drawString( bv.getTitle() ,  30 ,  55);
			
			
			
			ImageIO.write(_temp, "jpg", new File("c:\\mk_book_list\\"+currentDate+"\\d"+bv.getIsbn()+".jpg"));
		
			
		} catch (IOException e) {			
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
