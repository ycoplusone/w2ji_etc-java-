package monster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import hoa_data.DataModel;


public class Util {
	
	
	public static ArrayList<RecordModel> getRecord() {
		String str="";
		
		ArrayList<RecordModel> al = new ArrayList<RecordModel>(); //text 파일을 읽어 datamodel 생성자 로 만든다.
	    try{	    	
	        File file = new File("c:\\temp_info\\info.dat");
	        FileReader filereader = new FileReader(file);
	        int singleCh = 0;
	        while((singleCh = filereader.read()) != -1){
	        	str += (char)singleCh;	                
	        }
	        filereader.close();
	        
	        String[] lines = str.toString().split("\r\n|\r|\n"); //읽어들이 파일의 엔터값을 기준으로 데이터  행 기준 처리를 한다.
            for (String line : lines) {
            	 RecordModel dm = new RecordModel();
            	 dm.setName( line.substring(0, line.indexOf("||")  ) );
            	 dm.setScore( Integer.parseInt(  line.substring(line.indexOf("||")+2 , line.length()  ) )  );
            	 al.add(dm);
            } 
            
            Collections.sort(al , new Sorting());	// 점수 순으로 정렬
            //for (RecordModel rm : al) { //정렬테스트
			//	rm.print();
			//}
	        
	    }catch (FileNotFoundException e) {
	    	System.out.println("ID_LOAD FileNotFoundException : "+e);
	    }catch(IOException e){
	    	System.out.println("ID_LOAD IOException : "+e);
	    }
	    return al;
	}
	
	public static void setRecord(String name , int point) {
		   String fileName = "c:\\temp_info\\info.dat" ;			   
		   String path = "c:\\temp_info"; //폴더 경로
		   File Folder = new File(path);
		  
		   
		   
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
	         //file.delete();
	         FileWriter fw = new FileWriter(file,true) ;
	         
	         fw.write(name+"||"+point+"\n" );	         
	         
	         
	         fw.flush();		 
	         fw.close();
	     }catch(Exception ex){
	         ex.printStackTrace();
	     }
	}  
	
	
	

}
