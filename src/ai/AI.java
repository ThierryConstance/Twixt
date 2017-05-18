package ai;

import java.util.List;
import java.util.ArrayList;

import NewClass.*;

import logic.LogicManager;


public class AI extends AbstractAI {

	@Override
	public int[] chooseMove(LogicManager logicManager) {
		// TODO Auto-generated method stub
		return null;
	}
	
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
	 * #brouillon
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
	public List<PionValue> DoubleBridgqsfqsfqsdfeY4(int[][] board, List<int[]> lPion, int color, LogicManager logicManager )
	{
		List<PionValue> result = new ArrayList<PionValue>();
		boolean notCountered = true;
		int valueToAdd=0;
		//If player one (up down)
		if(color==1)
		{
			for(int[] p:lPion)
			{
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
							if(board[p[0]-i][p[1]]==2 || board[p[0]+i][p[1]]==2)
							{
								notCountered = false;
							}
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
									valueToAdd+=1;
								}
							}
							result.add(new PionValue(p[0],p[1]+4,color, valueToAdd));
						}
					}
				}
			}
		}
		return result;
	}
	

	/**
	 * 		
	 * 	
	 * 	X	A	X
	 * 	X	O	X		
	 * 	X	O	X
	 * 	X	O	X
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
	public List<PionValue> DoubleBridgeY4(int[][] board, List<int[]> lPion, int color, LogicManager logicManager )
	{
		List<PionValue> result = new ArrayList<PionValue>();
		boolean notCountered = true;
		int valueToAdd=0;
		//If player one (up down)
		if(color==1)
		{
			for(int[] p:lPion)
			{
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
							if(board[p[0]-i][p[1]]==2 || board[p[0]+i][p[1]]==2)
							{
								notCountered = false;
							}
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
									valueToAdd+=1;
								}
							}
							result.add(new PionValue(p[0],p[1]+4,color, valueToAdd));
						}
					}
				}
			}
		}
		return result;
	}
===================================================================================================
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
        List<PionValue> listPionAlreadyPlace = new ArrayList<PionValue>();
        
        listPionAlreadyPlace = listPionAlreadyPlaceGenerate(board);
        this.setListPionAlreadyPlace(listPionAlreadyPlace);
        
		


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
}

