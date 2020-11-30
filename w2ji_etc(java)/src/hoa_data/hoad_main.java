package hoa_data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class hoad_main {
	public static void main(String[] args) { //main start
		String file_str = "C:\\Users\\DLIVE\\git\\w2ji_etc-java-\\w2ji_etc(java)\\src\\hoa_data\\Hoa_data_2011.txt";
		readText(file_str);
		
		
		
	}// main end
	
	
	public static void readText(String fileName) {
		try{
            //파일 객체 생성
            File file = new File(fileName);
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            int singleCh = 0;
            while((singleCh = filereader.read()) != -1){
                System.out.print((char)singleCh);
            }
            filereader.close();
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        	System.out.println("FileNotFoundException "+e);
        }catch(IOException e){
            System.out.println(e);
        }
		
	}
	
}