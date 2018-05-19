import java.io.*;
import java.util.Vector;
import java.util.Scanner;

public class SudokuSolver
{
  public static void main(String[] args)
  {
    int[][] grid = new int[9][9];
    Scanner keyboard = new Scanner(System.in);
    
    //Prompts for a file to read in a puzzle from
    System.out.println("Please enter filename to read from.");
    String file = keyboard.next();
    keyboard.close();
    
    //Reads the values of the puzzle and stores them in grid
    try{
      FileReader fr = new FileReader(file);
      int input = 0;

      for(int i = 0; i < 9; i++)
      {
        input = fr.read();
        for(int j = 0; j < 9; j++)
        {
          grid[i][j] = input - 48;
          input = fr.read();
          input = fr.read();
        }
      }   
      fr.close();
    }
    catch(FileNotFoundException e)
    {
      System.out.println("File not found.");
      System.exit(0);
    }
    catch(IOException e)
    {
      System.out.println("IO error");
      System.exit(0);
    }
    
    //Search for a solution
    SudokuState start = new SudokuState(grid);
    int sol = solve(start);
  }
  
  public static int solve(SudokuState state)
  {
    //will store the indices of the most restricted cell
    int x = -1;
    int y = -1;
    Vector<Integer> domain = new Vector<Integer>();
    double mostRestrictingValue = Double.POSITIVE_INFINITY;
    
    //find the cell with the most restricted domain
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
        //Evaluates the domain for cells that have a value of 0
        if(state.getPuzzle()[i][j] == 0){
          Vector<Integer> domainValues = getDomain(state.getPuzzle(), i, j);
          //Returns a failure because a cell has no possible domain values remaining
          if(domainValues.size() == 0)
          {
            return -1;
          }
          //stores the current cell's indices and domain if it has the most restricted domain so far
          else if(domainValues.size() < mostRestrictingValue)
          {
            x = i;
            y = j;
            domain = domainValues;
            mostRestrictingValue = domainValues.size();
          }
        }
      }
    }
    
    //If the indices remain unchanged then every cell has been successfully assigned a value
    if(x == -1 && y == -1)
    {
      //solution found
      for(int i = 0; i < 9; i++)
      {
        for(int j = 0; j < 9; j++)
        {
          System.out.print(state.getPuzzle()[i][j] + " ");
        }
        System.out.print("\n");
      }
      return 1;
    }
    else
    {
      //recursively evaluates every possible continuation from the current state for all possible domain 
      //values for the current cell until a solution is found
      for(int i = 0; i < domain.size(); i++)
      {
        SudokuState child = new SudokuState(state.getPuzzle(), x, y, domain.get(i));
        int solution = solve(child);
        if(solution == 1)
          return 1;
      }
      return -1;
    }
  }
  
  //Generates the possible domain values for a given cell in grid
  public static Vector<Integer> getDomain(int[][] grid, int x, int y)
  {
    Vector<Integer> domain = new Vector<Integer>();
    for(int i = 0; i < 9; i++)
    {
      domain.add(i + 1);
    }
    //Check row values
    for(int i = 0; i < 9; i++)
    {
      if(i != y && grid[x][i] != 0)
        domain.remove((Integer) grid[x][i]);
    }
    //Check column values
    for(int i = 0; i < 9; i++)
    {
      if(i != x && grid[i][y] != 0)
        domain.remove((Integer) grid[i][y]);
    }
    
    //Check 3X3 box values
    //1st column of boxes
    if(x < 3)
    {
      //1st row of boxes
      if(y < 3)
      {
        for(int i = 0; i < 3; i++)
        {
          for(int j = 0; j < 3; j++)
          {
            if(i != x && j != y && grid[i][j] != 0)
              domain.remove((Integer) grid[i][j]);
          }
        }
      }
      //third row of boxes
      else if(y > 5)
      {
        for(int i = 0; i < 3; i++)
        {
          for(int j = 6; j < 9; j++)
          {
            if(i != x && j != y && grid[i][j] != 0)
              domain.remove((Integer) grid[i][j]);
          }
        }
      }
      //second row of boxes
      else
      {
        for(int i = 0; i < 3; i++)
        {
          for(int j = 3; j < 6; j++)
          {
            if(i != x && j != y && grid[i][j] != 0)
              domain.remove((Integer) grid[i][j]);
          }
        }
      }
    }
    
    //third column of boxes
    else if(x > 5)
    {
      //first row of boxes
      if(y < 3)
      {
        for(int i = 6; i < 9; i++)
        {
          for(int j = 0; j < 3; j++)
          {
            if(i != x && j != y && grid[i][j] != 0)
              domain.remove((Integer) grid[i][j]);
          }
        }
      }
      //third row of boxes
      else if(y > 5)
      {
        for(int i = 6; i < 9; i++)
        {
          for(int j = 6; j < 9; j++)
          {
            if(i != x && j != y && grid[i][j] != 0)
              domain.remove((Integer) grid[i][j]);
          }
        }
      }
      //second row of boxes
      else
      {
        for(int i = 6; i < 9; i++)
        {
          for(int j = 3; j < 6; j++)
          {
            if(i != x && j != y && grid[i][j] != 0)
              domain.remove((Integer) grid[i][j]);
          }
        }
      }
    }
    
    //second column of boxes
    else
    {
      //first row of boxes
      if(y < 3)
      {
        for(int i = 3; i < 6; i++)
        {
          for(int j = 0; j < 3; j++)
          {
            if(i != x && j != y && grid[i][j] != 0)
              domain.remove((Integer) grid[i][j]);
          }
        }
      }
      //third row of boxes
      else if(y > 5)
      {
        for(int i = 3; i < 6; i++)
        {
          for(int j = 6; j < 9; j++)
          {
            if(i != x && j != y && grid[i][j] != 0)
              domain.remove((Integer) grid[i][j]);
          }
        }
      }
      //second row of boxes
      else
      {
        for(int i = 3; i < 6; i++)
        {
          for(int j = 3; j < 6; j++)
          {
            if(i != x && j != y && grid[i][j] != 0)
              domain.remove((Integer) grid[i][j]);
          }
        }
      }
    }
    return domain;
  }
}