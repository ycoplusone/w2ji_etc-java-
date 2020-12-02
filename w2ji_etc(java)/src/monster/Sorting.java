package monster;
import java.util.Comparator;

public class Sorting implements Comparator<RecordModel>{
	public int compare(RecordModel a, RecordModel b)	{		
		//return b.getScore().compareTo( a.getScore());
		return b.getScore()- a.getScore();
	}
}
