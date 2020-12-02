package monster;

public class RecordModel{

	private String name ="";
	private int score = 0;
	public void print() {
		System.out.println("record print name : "+this.getName()+" , score : "+this.getScore());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}


	

}
