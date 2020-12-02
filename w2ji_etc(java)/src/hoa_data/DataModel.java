package hoa_data;

public class DataModel {
	private String time; //시간
	private int hhmm; //시 분 데이터
	private String kind; //종목
	private String seq_no; //일련번호
	private int price; //가격
	private int amount; //수량
	
	public void print(){
		System.out.print( "time : "+this.time );
		System.out.print( ",시분 : "+this.hhmm );
		System.out.print( ",종목 : "+this.kind );
		System.out.print( ",일련번호 : "+this.seq_no );
		System.out.print( ",가격 : "+this.price );
		System.out.println( ",수량 : "+this.amount );		
	}
	
	public int getHhmm() {
		return hhmm;
	}
	public void setHhmm(int hhmm) {
		this.hhmm = hhmm;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	

}
