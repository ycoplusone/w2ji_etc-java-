package z29_excel_export;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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


public class ExportImage extends JPanel{
    
    
    List<BookVo> _lb = new ArrayList<BookVo>();
    ArrayList<File> list_img = new ArrayList<File>();
    String currentDate ="";
    int _size = 0;
    
    ExportImage( List<BookVo> lb , String file){
    	this._lb = lb;
		for( File info : new File(file).listFiles() ) {
			list_img.add( info );
		}
		_size = (int)(lb.size() / 2)  * 450;
		System.out.println( lb.size()+" : "+_size);
		//System.out.println( getName(list_img.get(0).getName()) );
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
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
			ImageIO.write(base, "jpg", new File("c:\\mk_book_list\\"+currentDate+"_img.jpg"));
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage MakeBaseImage( BookVo bv) throws IOException{
		BufferedImage bi = null;
		BufferedImage cover = null;
		Image resizeImage = null;
		//File ff = new File(".\\src\\z29_excel_export\\");
		String path =  ".\\";//ExportImage.class.getResource("").getPath();//new File(".").getAbsolutePath();//this.getClass().getResource("").toString();
		System.out.println("path : "+path);
		Font f1 = new Font("", Font.PLAIN, 12);
		Color c1 = new Color(0, 112, 192);
		
		Font f2 = new Font("", Font.BOLD, 15);	//빨간 큰		
		Color c2 = new Color(255, 0, 0);
		
		Font f3 = new Font("", Font.BOLD, 15);		
		Color c3 = new Color(112, 48, 160);
		
		int _x = 410;
		try {			
			for( File f  : list_img ) {
				String file_name = getName(f.getName());
				if(file_name.equals(bv.getIsbn())) {
					cover = ImageIO.read(new File( f.getPath()  ));
					resizeImage = cover.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
				}
			}
			
			
			System.out.println( cover.getHeight()+" : "+cover.getWidth() );
			
			bi = ImageIO.read(new File( path +"img_guide_s.png"  ));
			Graphics2D graphics = (Graphics2D) bi.getGraphics();
			graphics.setBackground(Color.white);
			graphics.drawImage(bi, 0, 0, null);
			
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
			
			graphics.drawImage(resizeImage , 20 , 95 , null);
			
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return bi; 
	}
	
	
	public String getName(String str) {
		int pos = str.lastIndexOf( "." );
		String _fileName = str.substring(0, pos);
		return _fileName;
	}
	
	
	
	


	   
	
	
}
