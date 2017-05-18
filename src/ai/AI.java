package ai;

import gui.Constants;

import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;

import NewClass.*;

import logic.LogicManager;


public class AI extends AbstractAI {
	private List<CouplePionAntiEnemy> PlayThisIfEnemyPlayThat;
	private List<PionValue> ListPionAlreadyPlace;
	private int[][] boardOfImportance;
	private int depthExploration;
	
	public AI()
	{
		super("This is our AI, Group J!");
		this.PlayThisIfEnemyPlayThat = new ArrayList<CouplePionAntiEnemy>();
	}
	
	@Override
	public int[] chooseMove(LogicManager logicManager) {
		if(this.boardOfImportance==null)
		{
			int size = logicManager.getBoardSize();
			this.boardOfImportance=new int[size][size];
		}
		return logicManager.getAllPossibleMoves().get(0);
	}
	
	// Creation of List<PionValue> =============================================================================
	/**
	 * A verifier
	 * 	X	A	X	A	X
	 * 	A	X	X	X	A
	 * 	X	X	D	X	X
	 * 	A	X	X	X	A
	 * 	X	A	A	A	X
	 * @param lPion
	 * @param color
	 * @param logicManager
	 * @return
	 */
	public List<PionValue> NextBridge(List<int[]> lPion, int color, LogicManager logicManager)
	{
		List<PionValue> result  = new ArrayList<PionValue>();	
		int[][] knightMoves = {{-1, -2}, {-1, 2}, {1, -2}, {1, 2},{-2,-1}, {-2, 1}, {2, -1}, {2, 1}};
		
		for (int[] p: lPion) {
			for (int[] delta: knightMoves) {
				int[] possibleSuccessor = {p[0] + delta[0], p[1] + delta[1]};
				if (logicManager.isPossibleConnection(p, possibleSuccessor, color)
						&& logicManager.isPossibleMove(possibleSuccessor, color)) {
					//if the pion is to go to the left or right border
					if (Math.abs(delta[0]) > Math.abs(delta[1])) {
						//if the pion have to go up or down
						if (color == 1) {
							result.add(new PionValue(possibleSuccessor[0],possibleSuccessor[1],color,1));
						}
						//if the pion have to go left or right
						else {
							result.add(new PionValue(possibleSuccessor[0],possibleSuccessor[1],color,2));
						}
					}
					//if the pion is to go to the top or bottom border
					else {
						//if the pion have to go up or down
						if (color == 1) {
							result.add(new PionValue(possibleSuccessor[0],possibleSuccessor[1],color,2));
						}
						//if the pion have to go left or right
						else {
							result.add(new PionValue(possibleSuccessor[0],possibleSuccessor[1],color,1));
						}
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * A Verifier
	 * 	X	D	X	
	 * 	X	O	X
	 * 	X	O	X
	 * 	X	O	X
	 * 	X	A	X
	 * 
	 * @param lPion
	 * @param color
	 * @return
	 */
	public List<PionValue> DoubleBridgeY4Down(int[][] board, List<int[]> lPion, int color, LogicManager logicManager )
	{
		List<PionValue> result = new ArrayList<PionValue>();
		boolean notCountered = true;
		int valueToAdd;
		//If player one (up down)
		if(color==1)
		{
			for(int[] p:lPion)
			{
				//Init the importance of the pion
				valueToAdd=0;
				notCountered = true;
				//If A is on the board and D not on the edge
				if(p[1]+4<=logicManager.getBoardSize() && p[0]+1<logicManager.getBoardSize() && p[0]>1)
				{
					//Verification of A, if the A is empty or not
					int[] pionYPlus4 = {p[0],p[1]+4};
					//If A empty
					if(logicManager.isPossibleMove(pionYPlus4))
					{
						//Verification of Xs
						//if X is an enemy, playing A would be garbage (notCountered would be false)
						int i=0;
						while(notCountered && i<5)
						{
							if(board[p[0]-1][p[1]+i]==2 || board[p[0]+1][p[1]+i]==2)
							{
								notCountered = false;
								valueToAdd-=5;
							}
							i++;
						}
						//Verification of Os
						//if notCountered and if the enemy has placed O, it's better to play A
						if(notCountered)
						{
							valueToAdd+=1;
							for(i=1;i<4;i++)
							{
								if(board[p[0]][p[1]+i]==2)
								{
									valueToAdd++;
								}
							}
						}
					}
					else //if A is not empty
					{
						//We can't place this pion...
						valueToAdd = -10000;
					}
				}
				else //if on the edge, we can't build a double bridge, so it's not that important
				{
					valueToAdd--;
				}
				result.add(new PionValue(p[0],p[1]+4,color, valueToAdd));
			}
		}
		return result;
	}
	
	/**	
	 * A Verifier
	 * 	X	A	X
	 * 	X	O	X		
	 * 	X	O	X
	 * 	X	O	X
	 * 	X	D	X
	 */
	public List<PionValue> DoubleBridgeY4Up(int[][] board, List<int[]> lPion, int color, LogicManager logicManager )
	{
		List<PionValue> result = new ArrayList<PionValue>();
		boolean notCountered;
		int valueToAdd;
		//If player one (up down)
		if(color==1)
		{
			for(int[] p:lPion)
			{
				//Init the importance of the pion and the boolean notCountered
				valueToAdd=0;
				notCountered = true;
				//If A is on the board and D not on the edge
				if(p[1]-4>0 && p[0]+1<logicManager.getBoardSize() && p[0]>1)
				{
					//Verification of A, if the A is empty or not
					int[] pionYMoins4 = {p[0],p[1]-4};
					//If A empty
					if(logicManager.isPossibleMove(pionYMoins4))
					{
						//if X is an enemy, playing A would be garbage (notCountered would be false)
						int i=0;
						while(notCountered && i<5)
						{
							//Verification of Xs, on both sides
							if(board[p[0]-1][p[1]-i]==2 || board[p[0]+1][p[1]+i]==2)
							{
								notCountered = false;
								valueToAdd-=5;
							}
							i++;
						}
						//Verification of Os
						//if notCountered and if the enemy has placed O, it's better to play A
						if(notCountered)
						{
							valueToAdd+=1;
							for(i=1;i<4;i++)
							{
								if(board[p[0]][p[1]-i]==2)
								{
									valueToAdd++;
								}
							}
						}
					}
					else //if A is not empty
					{
						//We can't place this pion...
						valueToAdd = -10000;
					}
				}
				else //if on the edge, we can't build a double bridge, so it's not that important
				{
					valueToAdd--;
				}
				result.add(new PionValue(p[0],p[1]-4,color, valueToAdd));
			}
		}
		return result;
	}
	
	/**
	 * A Verifier
	 * 	X	X	X	X	X
	 * 	A	O	O	O	D
	 * 	X	X	X	X	X
	 * @param board
	 * @param lPion
	 * @param color
	 * @param logicManager
	 * @return
	 */
	public List<PionValue> DoubleBridgeX4Left(int[][] board, List<int[]> lPion, int color, LogicManager logicManager )
	{
		List<PionValue> result = new ArrayList<PionValue>();
		boolean notCountered;
		int valueToAdd;
		//If player Two (Left Right)
		if(color==2)
		{
			for(int[] p:lPion)
			{
				//Init the importance of the pion and the boolean notCountered
				valueToAdd=0;
				notCountered = true;
				//If A is on the board and D not on the edge
				if(p[0]-4>=0 && p[1]>1 && p[1]+1<logicManager.getBoardSize())
				{
					//Verification of A, if the A is empty or not
					int[] pionXMoins4 = {p[0]-4,p[1]};
					//If A empty
					if(logicManager.isPossibleMove(pionXMoins4))
					{
						//if X is an enemy, playing A would be garbage (notCountered would be false)
						int i=0;
						while(notCountered && i<5)
						{
							//Verification of Xs, on both sides
							if(board[p[0]-i][p[1]-1]==1 || board[p[0]-i][p[1]+1]==1)
							{
								notCountered = false;
								valueToAdd-=5;
							}
							i++;
						}
						//Verification of Os
						//if notCountered and if the enemy has placed O, it's better to play A
						if(notCountered)
						{
							valueToAdd+=1;
							for(i=1;i<4;i++)
							{
								if(board[p[0]-i][p[1]]==2)
								{
									valueToAdd++;
								}
							}
						}
					}
					else //if A is not empty
					{
						//We can't place this pion...
						valueToAdd = -10000;
					}
				}
				else //if on the edge, we can't build a double bridge, so it's not that important
				{
					valueToAdd--;
				}
				result.add(new PionValue(p[0]-4,p[1],color, valueToAdd));
			}
		}
		return result;
	}
	
	
	/**
	 * A Verifier
	 * 	X	X	X	X	X
	 *  D	O	O	O	A
	 * 	X	X	X	X	X
	 * 
	 * @param board
	 * @param lPion
	 * @param color
	 * @param logicManager
	 * @return
	 */
	public List<PionValue> DoubleBridgeX4Right(int[][] board, List<int[]> lPion, int color, LogicManager logicManager )
	{
		List<PionValue> result = new ArrayList<PionValue>();
		boolean notCountered;
		int valueToAdd;
		//If player Two (Left Right)
		if(color==2)
		{
			for(int[] p:lPion)
			{
				//Init the importance of the pion and the boolean notCountered
				valueToAdd=0;
				notCountered = true;
				//If A is on the board and D not on the edge
				if(p[0]+4<logicManager.getBoardSize() && p[1]>1 && p[1]+1<logicManager.getBoardSize())
				{
					//Verification of A, if the A is empty or not
					int[] pionXPlus4 = {p[0]+4,p[1]};
					//If A empty
					if(logicManager.isPossibleMove(pionXPlus4))
					{
						//if X is an enemy, playing A would be garbage (notCountered would be false)
						int i=0;
						while(notCountered && i<5)
						{
							//Verification of Xs, on both sides
							if(board[p[0]+i][p[1]-1]==1 || board[p[0]+i][p[1]+1]==1)
							{
								notCountered = false;
								valueToAdd-=5;
							}
							i++;
						}
						//Verification of Os
						//if notCountered and if the enemy has placed O, it's better to play A
						if(notCountered)
						{
							valueToAdd+=1;
							for(i=1;i<4;i++)
							{
								if(board[p[0]+i][p[1]]==2)
								{
									valueToAdd++;
								}
							}
						}
					}
					else //if A is not empty
					{
						//We can't place this pion...
						valueToAdd = -10000;
					}
				}
				else //if on the edge, we can't build a double bridge, so it's not that important
				{
					valueToAdd--;
				}
				result.add(new PionValue(p[0]+4,p[1],color, valueToAdd));
			}
		}
		return result;
	}
	
	//ADD ======================================================================================================
	public void AddCouplePionAntiEnemy(CouplePionAntiEnemy cpae)
	{
		this.PlayThisIfEnemyPlayThat.add(cpae);
	}
	
	public void AddCouplePionAntiEnemy(int[] p1, int[] p2, List<int[]> listPE)
	{
		this.PlayThisIfEnemyPlayThat.add(new CouplePionAntiEnemy(p1,p2,listPE));
	}
	
	private int[] LastPionPlaced(LogicManager logicManager)
	{
		return logicManager.getMoves().getMove();
	}
	
	public void AddImportance(List<PionValue> lp)
	{
		for(PionValue p:lp)
		{
			this.boardOfImportance[p.x][p.y] += p.value;
		}
	}
	
	/**
	 * Return the list of Important pions from the this.boardOfImportance
	 * @param number
	 * @return
	 */
	public List<PionValue> MaxImportance(int color,int number, int[][] boardOfImportance)
	{
		List<PionValue> lp = new ArrayList<PionValue>();
		PionValue[] ImportantPion = new PionValue[number];
		boolean found = false;
		int value;
		
		for(int i=0;i<this.boardOfImportance.length;i++)
		{
			for(int j=0;j<this.boardOfImportance.length;j++)
			{
				for(int k = 0; k<number; k++)
				{
					value = boardOfImportance[i][j];
					if(!found && ImportantPion[k].value<value)
					{
						ImportantPion[k] = new PionValue(i,j,color, value);
						found = true;
					}
					found = false;
				}
			}	
		}
		return lp;
}
	//===================================================================================================
	
	public List<PionValue> mostImportantPoint(List<PionValue> lPion){
		List<PionValue> listPion = new ArrayList<PionValue>();
		PionValue pion = new PionValue(0, 0, 1, 5);
		PionValue pion1 = new PionValue(4, 4, 1, 3);
		PionValue pion2 = new PionValue(0, 2, 1, 7);
		listPion.add(pion);
		listPion.add(pion1);
		listPion.add(pion2);
		return listPion;
	}
	
	// Generation of list wich include all the pion place on the board ==================================
	/*public PionValue(int x, int y,int color, int value)*/
	private List<PionValue> listPionAlreadyPlaceGenerate(int[][] board) {
		List<PionValue> listPionAlreadyPlace = new ArrayList<PionValue>();
		int n = board.length;
		int x,y;
		for (x=0; x < n; x++){
			for (y=0; y<n; y++){
				//if a pion is set at this place we add it at our list
				if (board[x][y] != 0){
					listPionAlreadyPlace.add(new PionValue(x, y, board[x][y], 0));
				}
			}
		}
		
		return listPionAlreadyPlace;
	}
	
	//===================================================================================================
	//Alpha Beta 
	//===================================================================================================
	public int alphaBeta(List<PionValue> l, int alpha, int beta, boolean ia){
		System.out.println("utilisation alpha beta");
		int i = 0;
		List<PionValue> newliste;
		if (l.size()==1){
			return l.get(0).value;
		}
		else {
			//If it is the turn of our AI (We consider the AI as a MAX node)
			if (ia){
				while( (i < l.size()) && (alpha < beta)){
					this.ListPionAlreadyPlace.add(0,l.get(i));
					newliste = mostImportantPoint(getListPionAlreadyPlace());
					alpha = Math.max(alpha, alphaBeta(newliste,alpha,beta, !ia));
					this.ListPionAlreadyPlace.remove(l.get(i));
					i++;
					System.out.println("coucou");
				}
				return alpha;
			}
			//If is not the turn of our AI
			else{
				while( (i < l.size()) && (alpha < beta)){
					this.ListPionAlreadyPlace.add(0,l.get(i));
					newliste = mostImportantPoint( getListPionAlreadyPlace());
					beta = Math.max(alpha, alphaBeta(newliste,alpha,beta, !ia));
					this.ListPionAlreadyPlace.remove(l.get(i));
					i++;
				}
				return beta;
			}
		}
	}
	
	
	//CHOOSE MOVE =======================================================================================
	
	public int[] chooseMove(int mycolor, int[][] board, Connections myConnections, Connections opConnections) 
	{
        int[] point = new int[2];
        int alphabeta;   
        List<PionValue> l;
        List<PionValue> lPion;
        boolean ia = true;
        
        this.setListPionAlreadyPlace(listPionAlreadyPlaceGenerate(board));
        
        l = mostImportantPoint(lPion);
        
        alphabeta = alphaBeta(l, 0, 0, ia);
		return point;
    }

	//GETTTER AND SETTER===================================================================================
	public int getDepthExploration() {
		return depthExploration;
	}

	public void setDepthExploration(int depthExploration) {
		this.depthExploration = depthExploration;
	}

	public List<PionValue> getListPionAlreadyPlace() {
		return ListPionAlreadyPlace;
	}

	public void setListPionAlreadyPlace(List<PionValue> listPionAlreadyPlace) {
		ListPionAlreadyPlace = listPionAlreadyPlace;
	}

}

