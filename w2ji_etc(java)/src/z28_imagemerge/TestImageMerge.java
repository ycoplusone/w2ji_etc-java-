package z28_imagemerge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

public class TestImageMerge {
	 public static void main(String[] args) {
		 
		CreateFlod cf = new CreateFlod();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
		String currentDate = dateFormat.format(new Date());
		System.out.println(currentDate);
		
		cf.mkdir( "c:\\mergetest" );			//기본폴더 생성
		cf.mkdir( "c:\\mergetest\\thumb" );		//합성대상
		cf.mkdir( "c:\\mergetest\\image" );		//합성 벵스
		cf.mkdir( "c:\\mergetest\\result" );		//합성 벵스
		cf.mkdir( "c:\\mergetest\\result\\"+currentDate );	
		
		
		 		 
		 String thumb = "c:\\mergetest\\thumb";
		 String image = "c:\\mergetest\\image";
		 ArrayList<File> al_thumb = new ArrayList<File>();
		 ArrayList<File> al_image = new ArrayList<File>();
	        
		for( File info : new File(thumb).listFiles() ) {
			al_thumb.add( info );
		}		
		for( File info : new File(image).listFiles() ) {
			al_image.add( info );
		}
		
		 
			 for( int i=0 ; i < al_image.size() ; i++ ){
				 for( int j=0 ; j < al_thumb.size() ; j++ ){
					 try {
					   BufferedImage img  = ImageIO.read(new File( al_thumb.get(j).getPath()  ));
					   BufferedImage base = ImageIO.read(new File( al_image.get(i).getPath()  ));
					   String str1 = al_thumb.get(j).getName();
					   String str2 = al_image.get(i).getName();
					   
					   getFileName gfn = new getFileName();
					   String nm1 = gfn.getName(str1);
					   String nm2 = gfn.getName(str2);
					   String nm3 = nm1+"_"+str2;
					   
					
					   int width = base.getWidth(); //Math.max(image2.getWidth(), image2.getWidth());
					   int height = base.getHeight();// + image2.getHeight();			   
					   int cw = Math.round( (base.getWidth() - img.getWidth()) / 2 ) ;
					   int ch = Math.round( (base.getHeight() - img.getHeight()) / 2 ) ;
					   
					
					   BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					   Graphics2D graphics = (Graphics2D) mergedImage.getGraphics();
					
					   graphics.setBackground(Color.WHITE);
					   graphics.drawImage(base, 0, 0, null);
					   graphics.drawImage(img, cw, ch, null);				   
					   
					   
					   ImageIO.write(mergedImage, "jpg", new File("c:\\mergetest\\result\\"+currentDate+"\\"+nm3));
					   System.out.println("c:\\mergetest\\result\\"+currentDate+"\\"+nm3+" : 파일이 생성되었습니다.");
					 } catch (IOException ioe) {
						  ioe.printStackTrace();
					 }
					 
				}
				
			}

		  
	
		  //System.out.println("이미지 합성이 완료되었습니다... 에헤라 디야~~");
	 }
}