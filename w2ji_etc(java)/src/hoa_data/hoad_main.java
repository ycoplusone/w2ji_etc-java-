package hoa_data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import hoa_data.DataModel;


public class hoad_main {
	
	static ArrayList<DataModel> dm_list = new ArrayList<DataModel>(); //text 파일을 읽어 datamodel 생성자 로 만든다.
	
	public static void main(String[] args) { //main start
		String file_str = "C:\\Hoa_data_2011.txt";
		//String file_str = "C:\\Users\\User\\git\\w2ji_etc-java-\\w2ji_etc(java)\\src\\hoa_data\\Hoa_data_2011.txt";
		
		
		dm_list = readText(file_str);	//text 파일 읽기 함수
		
		ArrayList<String> dis_kinds = new ArrayList<>();
		for (DataModel dd : dm_list) {
			 if(!dis_kinds.contains(dd.getKind()) ){	//중복을 제거한 종목 리스트 
				 dis_kinds.add(dd.getKind());
			 }
		}
		Collections.sort(dis_kinds);	// 종목 정렬
		for (String kind : dis_kinds) {
			anaysis_kind(kind);
		}
		

		/*
		int[] t_from = {1230 , 1300 , 1330 , 1400 , 1430 };	// 시간 배열 from
		int[] t_to =   {1259 , 1329 , 1359 , 1429 , 1500 }; // 시간 배열 to
		
		for (int i=0 ; i <= 4;i++) {
			anaysis(t_from[i] , t_to[i]);	//종목별 최초, 최고, 최종 거래의 가격을 분석한다.
		}
		*/
		
		
		
		
	}// main end
	
	public static void anaysis_kind( String kind ){
		String[] t_sub = {"12:30" , "13:00" , "13:30" , "14:00" , "14:30" };
		int[] t_from = {1230 , 1300 , 1330 , 1400 , 1430 };	// 시간 배열 from
		int[] t_to =   {1259 , 1329 , 1359 , 1429 , 1500 }; // 시간 배열 to
		System.out.println("\n"+kind+"\t최초가\t최고가\t최저가\t최종가");
		
		for (int i=0 ; i <= 4;i++) {
			ArrayList<DataModel> temp = new ArrayList<DataModel>(); //text 파일을 읽어 datamodel 생성자 로 만든다.
			for (DataModel dd : dm_list) { // 해당 시간대의 집합을 구한다.
				if( t_from[i] <= dd.getHhmm() && t_to[i] >= dd.getHhmm() && kind.equals(dd.getKind()) ){
					temp.add(dd); // 해당 품목과 시간대의 데이터 저장
				}
			}
			int first 	= 0;//최초
			int high 	= 0; //최고
			int low 	= 0; //최저
			int last 	= 0; //최종
			
			for (DataModel dd : temp) {	// 해당 시간대 임시 데이터를 순회 조회 를 한다.
				if( first == 0 ) { // 최초 가격 산출
					first = dd.getPrice();
				}					
				if(high <= dd.getPrice() ) { //최고가격산출
					high = dd.getPrice();						
				}
				
				if( dd.getPrice() != 0 ) {	//최저가격 산출
					if( low >= dd.getPrice() ) {
						low = dd.getPrice(); //	
					}else if(low ==0) {
						low = dd.getPrice(); //
					}					
				}
				last = dd.getPrice(); //최종가격 산출
			}
			System.out.println( t_sub[i]+"\t"+first+"\t"+high+"\t"+low+"\t"+last );
		}
	}// end anaysis_kind
	
	
	public static void anaysis( int f , int t ){
		System.out.println(f+" ~ "+t+" ------------------------------------------------------------");
		ArrayList<DataModel> temp = new ArrayList<DataModel>(); //text 파일을 읽어 datamodel 생성자 로 만든다.
		ArrayList<String> dis_kind = new ArrayList<>();
		for (DataModel dd : dm_list) { // 해당 시간대의 집합을 구한다.
			if( f <= dd.getHhmm() && t >= dd.getHhmm() ){
				temp.add(dd); // 해당 시간대의 집합을 저장한다.
				 if(!dis_kind.contains(dd.getKind()) ){	//중복을 제거한 종목 리스트 
					 dis_kind.add(dd.getKind());
				 }				        
			}
		}
		
		for(String str : dis_kind) {	// 종복을 제거한 종목을 이용해서 최초, 최고 , 최저, 최종 가격을 산출한다.
			int first 	= 0;//최초
			int high 	= 0; //최고
			int low 	= 9999999; //최저
			int last 	= 0; //최종
			
			int first_amt 	= 0;//최초
			int high_amt 	= 0; //최고
			int low_amt 	= 9999999; //최저
			int last_amt 	= 0; //최종
			
			for (DataModel dd : temp) {	// 해당 시간대 임시 데이터를 순회 조회 를 한다.
				if( str.equals( dd.getKind() ) ) {	//해당 품목을  찾는다.					
					if( first == 0 ) { // 최초 가격 산출
						first = dd.getPrice();
						first_amt = dd.getAmount();
					}					
					if(high <= dd.getPrice() ) { //최고가격산출
						high = dd.getPrice();						
					}
					if(high_amt <= dd.getAmount() ) { //최고거래산출
						high_amt = dd.getAmount();
					}
					
					if( dd.getPrice() != 0 ) {	//최저가격 산출
						if( low >= dd.getPrice() ) {
							low = dd.getPrice(); //	
						}else if(low ==0) {
							low = dd.getPrice(); //
						}					
					}
					if( low_amt >= dd.getAmount() ) {	//최저거래 산출
						low_amt = dd.getAmount();
					}
					last = dd.getPrice(); //최종가격 산출
					last_amt = dd.getAmount(); //최종 거래량 산출
				}
			}
			System.out.print( str+" (금액/수량)\n(최초:"+first+" / "+first_amt+")\n" );
			System.out.print( "(최고:"+high+" / "+high_amt+")\n" );
			System.out.print( "(최저:"+low+" / "+low_amt+")\n" );
			System.out.print( "(최종:"+last+" / "+last_amt+")\n" );
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
            	dm.setPrice(	Integer.parseInt( line.substring(26, 35) ) );
            	dm.setAmount(	Integer.parseInt( line.substring(35, 47) ) );
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