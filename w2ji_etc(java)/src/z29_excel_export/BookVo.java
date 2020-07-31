package z29_excel_export;

public class BookVo {
	private String seq;			//0
	private String isbn;		//1
	private String title;		//2
	private String company;		//3
	private String cost_amt;	//4
	private String sale_amt;	//5
	private String list_amt;	//6
	private String writer;		//11
	private String pub_date;	//17
	
	public String getlist() {
		return seq+" : "+isbn+" : "+title+" : "+company+" : "+sale_amt+" : "+list_amt+" : "+writer+" : "+pub_date;
	}
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCost_amt() {
		return cost_amt;
	}
	public void setCost_amt(String cost_amt) {
		this.cost_amt = cost_amt;
	}
	public String getSale_amt() {
		return sale_amt;
	}
	public void setSale_amt(String sale_amt) {
		this.sale_amt = sale_amt;
	}
	public String getList_amt() {
		return list_amt;
	}
	public void setList_amt(String list_amt) {
		this.list_amt = list_amt;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getPub_date() {
		return pub_date;
	}
	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}
	
	
	
	
	
	
	
}
