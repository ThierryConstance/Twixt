package ai;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import NewClass.*;
import logic.Connections;
import logic.LogicManager;


public class AI extends AbstractAI {
	public int depthExploration;
	public List<PionValue> ListPionAlreadyPlace;

	public List<PionValue> listPionAlreadyPlaceGenerate(int[][] board){
		
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
	
	//===================================================================================================
	public List<PionValue> MostImportantPoint(List<PionValue> lPion){
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
		}@Override
		else {
			//If it is the turn of our AI (We consider the AI as a MAX node)
			if (ia){
				while( (i < l.size()) && (alpha < beta)){
					this.ListPionAlreadyPlace.add(0,l.get(i));
					newliste = MostImportantPoint(getListPionAlreadyPlace());
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
					newliste = MostImportantPoint( getListPionAlreadyPlace());
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