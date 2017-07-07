/*
Eurolfan, Jan Ellis D.
2010 - 29160
CMSC 170 U-7L
Exer 2 - 8 Puzzle Solver using A* Search

Solver.java
	The main class that contains the implementation of the A* Search algorithm
*/

import java.util.LinkedList;
import java.util.Random;

public class Solver
{
	public static void main (String[] args)
	{
		// create an initial board
		// sample solvable board => int[][] board = {{0,1,2}, {3,4,5}, {6,7,8}};

		// instantiate board
		State initState;
		while (true)
		{
			initState = new State (randomizeInitState ());
			if (checkIfSolvable (initState.getBoard ()))
			{
				System.out.println ("Solvable!");
				break;
			}
			else
			{
				System.out.println ("Not Solvable!");
			}

		}

		// initialize open and closed lists
		LinkedList <State> openList = new LinkedList <> ();
		LinkedList <State> closedList = new LinkedList <> ();

		// add initial state to openlist
		openList.add (initState);

		// implementation of A* Search
		while (!openList.isEmpty ())
		{
			// get node with minimum F value
			State bestNode = openList.remove (removeMinF (openList));
			// add to closed list
			closedList.add (bestNode);
			// if this is already the goal
			if (goalTest (bestNode.getBoard ()))
			{
				// backtrack the parents
				// create a stack of states
				LinkedList <State> solutionList = new LinkedList <> ();
				// push goal state and parent of said goal state
				solutionList.push (bestNode);
				solutionList.push (bestNode.getParent ());
				// track the parents
				// while top of stack is not the initial state, track the parent/s of the current top of stack
				while (!initState.compareState (solutionList.peekFirst ()))
					// push parent to stack once found
					solutionList.push (solutionList.peekFirst ().getParent ());

				// pass stack to file out
				fileOut (solutionList);
				/*
				// print to console
				for (int i = 0; i < solutionList.size (); i++)
				{
					if (i == 0)
						System.out.println ("Initial State:");
					else
						System.out.println ("Step " + i);

					print (solutionList.get (i).getBoard ());
				}
				*/
				break;
			}

			/*
			// print to console
			System.out.println ("Current Best Node");
			print (bestNode.getBoard ());
			System.out.println ("G: " + bestNode.getGParam () + " F: " + bestNode.getFParam () + " H" + bestNode.getHParam ());
			System.out.println ("===");
			System.out.println ("Next States");
			System.out.println ("===");
			*/

			// returns the next states based on the available actions
			for (State nextState : new ResultGenerator (new ActionGenerator (bestNode.getBoard ()).getAvailableActions (), bestNode).getResultStates ())
			{
				/*
				// print to console
				System.out.println ("G: " + nextState.getGParam () + " F: " + nextState.getFParam () + " H" + nextState.getHParam ());
				print (nextState.getBoard ());
				System.out.println ("===");
				*/
				// condition for screening out redundant states
				if ((!ifPartOfList (openList, nextState) || !ifPartOfList (closedList, nextState)) || ((ifPartOfList (openList, nextState) || ifPartOfList (closedList, nextState)) && duplicateIsLess (openList, closedList, nextState)))
				{
					// add to open list
					openList.add (nextState);
				}
			}
		}
	}

	// writes the solution to a text file
	private static void fileOut (LinkedList <State> solutionList)
	{
		try
		{
			// create stream writer and output file
			java.io.BufferedWriter bw = new java.io.BufferedWriter (new java.io.FileWriter (new java.io.File ("8puzzle.out")));

			// traverse the list
			for (int i = 0; i < solutionList.size (); i++)
			{
				// write the needed prompt
				if (i == 0)
					bw.write (new String ("Initial State"));
				else
					bw.write (new String ("Step " + i + ":"));
				bw.newLine ();

				// get element
				State currState = solutionList.get (i);

				// write the board state
				for (int j = 0; j < 3; j++)
				{
					for (int k = 0; k < 3; k++)
					{
						bw.write (new String (currState.getBoard () [j][k] + " "));
					}
					bw.newLine ();
				}
			}
			bw.close ();
		}
		catch (java.io.IOException ioe)
		{
			ioe.printStackTrace ();
		}
	}

	// checks if the G Parameter of the found state is less than that of the duplicate state in either open or closed lists
	// returns true if (Result (s,a).G < duplicate.G)
	private static boolean duplicateIsLess (LinkedList <State> openList, LinkedList <State> closedList, State state)
	{
		// if part of open list, traverse open list for the duplicate
		if (ifPartOfList (openList, state))
		{
			for (State element : openList)
			{
				// find the state first
				if (state.compareState (element))
					// compare their G Parameter
					if (state.compareGParam (element))
						return true;
			}
		}
		// else, traverse the closed list for the duplicate
		else
		{
			for (State element : closedList)
			{
				// find the state first
				if (state.compareState (element))
					// compare their G Parameter
					if (state.compareGParam (element))
						return true;
			}
		}
		return false;
	}

	// checks if a state is part of a certain list
	private static boolean ifPartOfList (LinkedList <State> list, State state)
	{
		for (State element : list)
		{
			if (state.compareState (element))
				return true;
		}

		return false;
	}

	// returns the index of the state with the minimum F Parameter
	private static int removeMinF (LinkedList <State> openList)
	{
		int minF = openList.peek ().getFParam ();
		int index = 0;
		for (int i = 0; i < openList.size (); i++)
		{
			if (minF > openList.get (i).getFParam ())
			{
				minF = openList.get (i).getFParam ();
				index = i;
			}
		}
		return index;
	}

	// checks if said state is already the goal state
	private static boolean goalTest (int[][] board)
	{
		if (board [0][0] != 1)
			return false;
		else if (board [0][1] != 2)
			return false;
		else if (board [0][2] != 3)
			return false;
		else if (board [1][0] != 4)
			return false;
		else if (board [1][1] != 5)
			return false;
		else if (board [1][2] != 6)
			return false;
		else if (board [2][0] != 7)
			return false;
		else if (board [2][1] != 8)
			return false;
		else if (board [2][2] != 0)
			return false;

		return true;
	}

	// creates a 2d int array that represents a random 8 Puzzle state
	private static int[][] randomizeInitState ()
    {
        Random random = new Random ();
        int[][] board = new int [3][3];

        // pre fill the array with zeroes
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board [i][j] = 0;

        // create a randomized state
        int counter = 1;
        while (counter != 9)
        {
            int x = random.nextInt (3);
            int y = random.nextInt (3);
            if (board [x][y] == 0)
                board [x][y] = counter ++;
        }

        System.out.println ("Randomized Initial State");
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                System.out.print (board [i][j] + " ");
            }
            System.out.println (" ");
        }

        return (board);
    }

	// checks if the puzzle is solvable
	// source: http://math.stackexchange.com/questions/293527/how-to-check-if-a-8-puzzle-is-solvable
	private static boolean checkIfSolvable (int[][] array)
	{
		// flatten the 2d array
		LinkedList <Integer> flatArray = new LinkedList <> ();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (array [i][j] != 0)
					flatArray.add (array [i][j]);

		// set number of inversions
		int inversions = 0;
		for (int i = 0; i < flatArray.size (); i++)
		{
			// increment inversions for every element out of position
			// by position, i refer to the natural position of the number in the number line
			for (int j = (i+1); j < flatArray.size (); j++)
			{
				if (flatArray.get (j) > flatArray.get (i))
					inversions ++;
			}
		}

		return ((inversions % 2) == 0);
	}

	// convenience method in printing an array
	private static void print (int[][] array)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				System.out.print (array [i][j] + " ");
			}
			System.out.println (" ");
		}
	}
}
