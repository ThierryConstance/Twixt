package ai.group_j;

import java.util.ArrayList;
import java.util.Random;

import twixt.Player;

import logic.Connections;
import logic.LogicManager;
import logic.Moves;
import ai.AbstractAI;

public class AI extends AbstractAI {

	public AI() {
		super("group_j.AI");
	}

	@Override
	public int[] chooseMove(LogicManager logicManager) {
		return chooseMove(logicManager.getMoves().getCurrentColor(), logicManager.getBoard().clone(),
				logicManager.cloneConnections(logicManager.getMoves().getCurrentColor()),
				logicManager.cloneConnections(logicManager.getMoves().getColor()));
	}
	
	/**
	 * Return the number of moves left in order to win for the chosen player.
	 * @param board The board of the game.
	 * @param moves The moves done on this game.
	 * @param connectionsWhite All the connections of the white player.
	 * @param connectionsBlack All the connections of the black player.
	 * @param numberOfMoves The current number of moves needed.
	 * @param player Color of the player we are interested in.
	 * @return The number of moves left.
	 */
	public int howManyMovesLeftToWin(int[][] board, Moves moves, Connections connectionsWhite, Connections connectionsBlack, int numberOfMoves, int player) {
		ArrayList<int[][]> a = new ArrayList<int[][]>(); 
		if(oneMoveToWin(board, moves, connectionsWhite, connectionsBlack, player)){
			numberOfMoves+=1;
		}else{
			if(player == 1){ //The player is the yellow one.
				for(int i=0 ; i<connectionsWhite.getSize() ; i++){
					
				}
			}else{ //The player is the red one.
				
			}
		}
		return numberOfMoves;
	}
	
	/**
	 * Return true if we can move in one more move, false otherwise.
	 * @param board The board of the game.
	 * @param moves The moves done on this game.
	 * @param connectionsWhite All the connections of the white player.
	 * @param connectionsBlack All the connections of the black player.
	 * @param player Color of the player we are interested in.
	 * @return A boolean which is true if one move is enough to win.
	 */
	public boolean oneMoveToWin(int[][] board, Moves moves, Connections connectionsWhite, Connections connectionsBlack, int player){
		boolean oneMove = false;
		ArrayList<int[]> a = new ArrayList<int[]>(); 
		
		if(player == 1){ //The player is the yellow one.
			for(int i=0 ; i<connectionsWhite.getSize() ; i++){
				a.add(connectionsWhite.getConnection(i));
			}
			
		}else{ //The player is the red one.
			
		}
		return oneMove;
	}
	
	/**
	 * Return the longest connection done by a player.
	 * @param board The board of the game.
	 * @param moves The moves done on this game.
	 * @param connectionsWhite All the connections of the white player.
	 * @param connectionsBlack All the connections of the black player.
	 * @param player Color of the player we are interested in.
	 * @return The longest connection done by the player we are interested in.
	 */
	public Connections[] longestConnectionOnBoard(int[][] board, Moves moves, Connections connectionsWhite, Connections connectionsBlack, int player){
		Connections[] c = new Connections[2];  
		
		return c;
	}
	
	public int[] chooseMove(int mycolor, int[][] board, Connections myConnections, Connections opConnections) 
	{
        int[] point = new int[2];
        // point[0] represents the row you want to place your peg (values from 0 to getBoardSize()-1)
        // point[1] represents the column you want to place your peg (values from 0 to getBoardSize()-1)
        Random rand = new Random();

		do
		{
			point[0] = rand.nextInt(board.length);
			point[1] = rand.nextInt(board.length);
		} while (!LogicManager.isPossibleMove(board, point, mycolor));
		

//		try
//		{
//			Thread.sleep((int)(Math.random() * (500)));
//		}
//		catch (InterruptedException ex)
//		{
//		}

		return point;
    }
	
	public static void main(String[] args) {
		Connections connectionsWhite = new Connections();
		int[] point1 = {1,2};
		int[] point2 = {3,3};
		
		connectionsWhite.add(point1, point2);
		
		ArrayList<int[]> a = new ArrayList<int[]>(); 
		for(int i=0 ; i<connectionsWhite.getSize() ; i++){
			a.add(connectionsWhite.getConnection(i));
		}
		for(int i=0 ; i<a.size() ; i++){
			System.out.println(a.get(i));
		}
	}
}
