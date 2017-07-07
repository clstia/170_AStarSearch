/*
Eurolfan, Jan Ellis D.
2010 - 29160
CMSC 170 U-7L
Exer 2 - 8 Puzzle Solver using A* Search

ResultGenerator.java
	A class that generates the next states from a certain state
*/

import java.util.LinkedList;

public class ResultGenerator
{
	LinkedList <State> resultList;
	LinkedList <Coord> actionList;
	State state;

	public ResultGenerator (LinkedList <Coord> actionList, State state)
	{
		this.actionList = actionList;
		this.state = state;
	}

	// returns a linked list of states that represents the possible next states
	public LinkedList <State> getResultStates ()
	{
		// create a linked list of the states
		resultList = new LinkedList <> ();

		// initialize a variable for the coordinate of the zero value
		int zeroX = 0, zeroY = 0;

		// find the coordinates of the zero value
		for (int i = 0; i < 3; i++)
		{
			int flag = 0;
			for (int j = 0; j < 3; j++)
			{
				if (state.getBoard ()[i][j] == 0)
				{
					zeroX = i;
					zeroY = j;
					flag = 1;
					break;
				}
			}
			if (flag == 1)
				break;
		}

		// for each possible action, generate a resulting state.
		for (Coord coord : actionList)
			resultList.add (new State (plotResult (zeroX, zeroY, coord.getX (), coord.getY (), state.getBoard ()), state.getGParam () + 1, state));
		return resultList;
	}

	// returns a 2d int array that represents a state after a certain action
	private int[][] plotResult (int zeroX, int zeroY, int swapX, int swapY, int[][] board)
	{
		int toSwap = board[swapX][swapY];
		int[][] newBoard = new int [3][3];

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (i == zeroX && j == zeroY)
					newBoard [i][j] = toSwap;
				else if (i == swapX && j == swapY)
					newBoard [i][j] = 0;
				else
					newBoard [i][j] = board[i][j];
			}
		}
		return newBoard;
	}
}
