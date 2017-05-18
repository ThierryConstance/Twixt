package NewClass;

import java.util.ArrayList;
import java.util.List;

public class CouplePionAntiEnemy {
	public int[] pion1; //Pion already placed
	public int[] pion2;	//Pion to place
	public List<int[]> lEnemyPion;	//List of Pion that enemy can place, if a pion in this list is placed,it's highly recommended to play the pion2
	
	public CouplePionAntiEnemy()
	{
		this.pion1 = new int[2];
		this.pion2 = new int[2];
		
		this.lEnemyPion = new ArrayList<int[]>();
	}
	
	public CouplePionAntiEnemy(int[] pion1, int[] pion2, List<int[]> listEnemyPion)
	{
		this.pion1 = pion1;
		this.pion2 = pion2;
		this.lEnemyPion = listEnemyPion;
	}
	
	
}
