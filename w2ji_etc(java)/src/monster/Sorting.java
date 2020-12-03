package monster;
import java.util.Comparator;

// 순위를 정렬해주기 위한 함수
public class Sorting implements Comparator<RecordModel>{
	public int compare(RecordModel a, RecordModel b)	{		
		//return b.getScore().compareTo( a.getScore());
		return b.getScore()- a.getScore();
	}
}
