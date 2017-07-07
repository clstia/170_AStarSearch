/*
Eurolfan, Jan Ellis D.
2010 - 29160
CMSC 170 U-7L
Exer 2 - 8 Puzzle Solver using A* Search

ActionGenerator.java
	A class that returns the list of possible actions from a certain state
*/

import java.util.LinkedList;

public class ActionGenerator
{
	int[][] board;
	LinkedList <Coord> actionList;

	public ActionGenerator (int[][] board)
	{
		this.board = board;
	}

	// returns a linked list of possible actions from a certain state
	public LinkedList <Coord> getAvailableActions ()
	{
		actionList = new LinkedList <> ();

		// set variable for coordinate of the zero value
		int zeroX = 0, zeroY = 0;

		// find the zero value
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (board [i][j] == 0)
				{
					zeroX = i;
					zeroY = j;
					break;
				}
			}
		}

		// since the state space is small, it is easier to hard code the possible actions from a certain state.
		if (zeroX == 0 && zeroY == 0)
		{
			actionList.add (new Coord (0, 1));
			actionList.add (new Coord (1, 0));
		}
		else if (zeroX == 0 && zeroY == 2)
		{
			actionList.add (new Coord (0, 1));
			actionList.add (new Coord (2, 1));
		}
		else if (zeroX == 2 && zeroY == 0)
		{
			actionList.add (new Coord (1, 0));
			actionList.add (new Coord (2, 1));
		}
		else if (zeroX == 2 && zeroY == 2)
		{
			actionList.add (new Coord (2, 1));
			actionList.add (new Coord (1, 2));
		}
		else if (zeroX == 0)
		{
			actionList.add (new Coord (0, 2));
			actionList.add (new Coord (0, 0));
			actionList.add (new Coord (1, 1));
		}
		else if (zeroX == 2)
		{
			actionList.add (new Coord (2, 0));
			actionList.add (new Coord (2, 2));
			actionList.add (new Coord (1, 1));
		}
		else if (zeroY == 0)
		{
			actionList.add (new Coord (0, 0));
			actionList.add (new Coord (2, 0));
			actionList.add (new Coord (1, 1));
		}
		else if (zeroY == 2)
		{
			actionList.add (new Coord (0, 2));
			actionList.add (new Coord (1, 1));
			actionList.add (new Coord (2, 2));
		}
		else
		{
			actionList.add (new Coord (0, 1));
			actionList.add (new Coord (1, 0));
			actionList.add (new Coord (1, 2));
			actionList.add (new Coord (2, 1));
		}

		return actionList;
	}
}
