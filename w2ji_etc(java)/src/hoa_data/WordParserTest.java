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


public class WordParserTest {
	public static void main(String[] args) { //main start
		//String file_str = "C:\\Users\\DLIVE\\Downloads\\Hoa_data_2011).doc";
		String file_str = "C:\\Hoa_data_2011.doc";
		readDocFile(file_str);
		System.out.println("+++++++++++++++++++++++++");
		//readDocxFile(file_str);
		System.out.println("+++++++++++++++++++++++++");
		//readText(file_str);
		
		/*
		try {
			
			

		}catch(Exception e) {
			System.out.println("e"+e.toString());
		}*/
		
	}// main end
	
	
	public static void readDocFile(String fileName) {

		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			HWPFDocument doc = new HWPFDocument(fis);

			WordExtractor we = new WordExtractor(doc);

			String[] paragraphs = we.getParagraphText();
			
			System.out.println("Total no of paragraph "+paragraphs.length);
			for (String para : paragraphs) {
				System.out.println(para.toString());
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void readDocxFile(String fileName) {

		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument document = new XWPFDocument(fis);

			List<XWPFParagraph> paragraphs = document.getParagraphs();
			
			System.out.println("Total no of paragraph "+paragraphs.size());
			for (XWPFParagraph para : paragraphs) {
				System.out.println(para.getText());
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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