public class SudokuState
{
  private int[][] puzzle;
  
  public SudokuState(int[][] grid)
  {
    puzzle = new int[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
        puzzle[i][j] = grid[i][j];
    }
  }
  
  //Child state
  public SudokuState(int[][] grid, int x, int y, int entry)
  {
    puzzle = new int[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
        puzzle[i][j] = grid[i][j];
    }
    puzzle[x][y] = entry;
  }
  
  public int[][] getPuzzle()
  {
    return puzzle;
  }
}