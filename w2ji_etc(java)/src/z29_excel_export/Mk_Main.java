package z29_excel_export;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

public class Mk_Main {

	public static void main(String[] args) {
		System.out.println("mk start");
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
		List<BookVo> lb = re.Read("c:\\mk_book_list\\list.xlsx");
		
			
		//ExportExcel ee = new ExportExcel( lb ); 사용안함.
		ExportImage ei = new ExportImage(  lb , dir_img);
		ei.MakeImage();
		//ei.Test();
		/*
		JFrame fr = new JFrame();
    	fr.setTitle( "그림입니다." );
		fr.getContentPane().add(ei );
		fr.setSize(700, 500);
		fr.setVisible(true);
		//MakeImage();
		*/
		
		
		
		
		System.out.println("mk end");
	}

}
