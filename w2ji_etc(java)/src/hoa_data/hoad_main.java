package hoa_data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import hoa_data.DataModel;


public class hoad_main {
	
	static ArrayList<DataModel> dm_list = new ArrayList<DataModel>(); //text 파일을 읽어 datamodel 생성자 로 만든다.
	
	public static void main(String[] args) { //main start
		//String file_str = "C:\\Users\\DLIVE\\git\\w2ji_etc-java-\\w2ji_etc(java)\\src\\hoa_data\\Hoa_data_2011.txt";
		String file_str = "C:\\Users\\User\\git\\w2ji_etc-java-\\w2ji_etc(java)\\src\\hoa_data\\Hoa_data_2011.txt";
		
		
		dm_list = readText(file_str);	//text 파일 읽기 함수
		

		int[] t_from = {1230 , 1300 , 1330 , 1400 , 1430 };	// 시간 배열 from
		int[] t_to =   {1259 , 1329 , 1359 , 1429 , 1500 }; // 시간 배열 to
		
		for (int i=0 ; i <= 4;i++) {
			anaysis(t_from[i] , t_to[i]);	//종목별 최초, 최고, 최종 거래의 가격을 분석한다.
		}		
		
		
	}// main end
	
	public static void anaysis( int f , int t ){
		System.out.println(f+" ~ "+t+" -----------------------------------------");
		ArrayList<DataModel> temp = new ArrayList<DataModel>(); //text 파일을 읽어 datamodel 생성자 로 만든다.
		for (DataModel dd : dm_list) {
			if( f <= dd.getHhmm() && t >= dd.getHhmm() ){
				temp.add(dd);
			}
		}
		
		for (DataModel dd : temp) {
			dd.print();
		}
		
	}
	
	
	public static ArrayList<DataModel> readText(String fileName) {
		ArrayList<DataModel> al = new ArrayList<DataModel>(); //text 파일을 읽어 datamodel 생성자 로 만든다.
		StringBuffer sb = new StringBuffer();
		try{
			int cnt = 0;
            //파일 객체 생성
            File file = new File(fileName);
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            int singleCh = 0;
            while((singleCh = filereader.read()) != -1){
                sb.append((char)singleCh); //텍스트를 한글자씩 뽑아서 stringbutter 에 넣는다.
            }
            filereader.close();// 파일 작업이 끝나 파일객체를 종료한다.
            
            String[] lines = sb.toString().split("\r\n|\r|\n"); //읽어들이 파일의 엔터값을 기준으로 데이터  행 기준 처리를 한다.
            for (String line : lines) {
            	//System.out.print("시간 : "+ line.substring(0, 15) ); //string 데이터 분할 테스트
            	//System.out.print("종목코드 : "+ line.substring(15, 21) ); //string 데이터 분할 테스트
            	//System.out.print("일련번호 : "+ line.substring(21, 26) ); //string 데이터 분할 테스트
            	//System.out.print("가격 : "+ line.substring(26, 35) ); //string 데이터 분할 테스트
            	//System.out.println("수량 : "+ line.substring(35, 47) ); //string 데이터 분할 테스트
            	DataModel dm = new DataModel();
            	dm.setTime( 	line.substring(0, 15)  );
            	dm.setHhmm( 	Integer.parseInt( line.substring(0, 5).replaceAll(":", "") )  );
            	dm.setKind( 	line.substring(15, 21) );
            	dm.setSeq_no( 	line.substring(21, 26) );
            	dm.setPrice(	line.substring(26, 35) );
            	dm.setAmount(	line.substring(35, 47) );
            	al.add(dm);            	
            }           
            
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        	System.out.println("FileNotFoundException "+e);
        }catch(IOException e){
            System.out.println(e);
        }
		return al;
		
	}
	
}