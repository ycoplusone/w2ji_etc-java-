package test1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class urlTestMain {

	public static void main(String[] args) {
		System.out.println("-----urlTest-----");
		HashMap hm = new HashMap<String, String>();
		hm.put("test", "엠병fuck1!@#");
		String url = "http://localhost:8080/w2ji_web/gettest";
		String test = postRequest(url , hm);
		
		JsonParser Parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) Parser.parse(test);
		System.out.println("-----main start-----");
		System.out.println( "address : "+jsonObj.get("address") );
		System.out.println("-----list start-----");
		JsonArray list = (JsonArray) jsonObj.get("list");
		for (int i = 0; i < list.size(); i++) {       
			JsonObject object = (JsonObject) list.get(i);
			System.out.println("id : " + object.get("id"));
			System.out.println("title : " + object.get("title"));
		}		
		System.out.println("-----list end-----");
		System.out.println("-----main end-----");
		
	}
	
	
    public static String postRequest(String pURL, HashMap < String, String > pList) {

        String myResult = "";

        try {
            //   URL 설정하고 접속하기 
            URL url = new URL(pURL); // URL 설정 

            HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속 
            //-------------------------- 
            //   전송 모드 설정 - 기본적인 설정 
            //-------------------------- 
            http.setDefaultUseCaches(false);
            http.setDoInput(true); // 서버에서 읽기 모드 지정 
            http.setDoOutput(true); // 서버로 쓰기 모드 지정  
            http.setRequestMethod("POST"); // 전송 방식은 POST



            //--------------------------
            // 헤더 세팅
            //--------------------------
            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다 
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");


            //-------------------------- 
            //   서버로 값 전송 
            //-------------------------- 
            StringBuffer buffer = new StringBuffer();

            //HashMap으로 전달받은 파라미터가 null이 아닌경우 버퍼에 넣어준다
            if (pList != null) {

                Set key = pList.keySet();

                for (Iterator iterator = key.iterator(); iterator.hasNext();) {
                    String keyName = (String) iterator.next();
                    String valueName = pList.get(keyName);
                    buffer.append(keyName).append("=").append(valueName);
                }
            }

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();


            //--------------------------
            //   Response Code
            //--------------------------
            //http.getResponseCode();


            //-------------------------- 
            //   서버에서 전송받기 
            //-------------------------- 
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str + "\n");
            }
            myResult = builder.toString();
            return myResult;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResult;
    }

}
