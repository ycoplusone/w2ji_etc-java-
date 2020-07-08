package word_20200614;

//w_member 회원테이블
public class WMember {

	 // seq 고유번호
	 private String seq;
	 // m_id 회원id
	 private String mId;
	 // m_pw 비밀번호
	 private String mPw;
	 // m_name 이름
	 private String mName;
	 // m_email 이메일
	 private String mEmail;
	 // c_date 생성일시
	 private String cDate;
	 
	 public void Print(){
		 System.out.println(seq+" : "+mId+" : "+mName);
	 }
	
	 public String getSeq() {
	     return seq;
	 }
	
	 public void setSeq(String seq) {
	     this.seq = seq;
	 }
	
	 public String getMId() {
	     return mId;
	 }
	
	 public void setMId(String mId) {
	     this.mId = mId;
	 }
	
	 public String getMPw() {
	     return mPw;
	 }
	
	 public void setMPw(String mPw) {
	     this.mPw = mPw;
	 }
	
	 public String getMName() {
	     return mName;
	 }
	
	 public void setMName(String mName) {
	     this.mName = mName;
	 }
	
	 public String getMEmail() {
	     return mEmail;
	 }
	
	 public void setMEmail(String mEmail) {
	     this.mEmail = mEmail;
	 }
	
	 public String getCDate() {
	     return cDate;
	 }
	
	 public void setCDate(String cDate) {
	     this.cDate = cDate;
	 }

}