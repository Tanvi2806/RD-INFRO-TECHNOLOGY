public class SudokuSolver 
{
    private static boolean isValid(char[][]board, int row, int col, char num )
    {
        for(int i=0;i<9;i++)
        {
            if(board[row][i]==num || board[i][col] == num || board[3*(row/3)+i/3][3*(col/3)+i%3] == num)
            {
                return false;
            }
        }
        return true;
    }
    private static boolean solve(char[][]board)
    {
        for(int row=0;row<9;row++)
        {
            for(int col=0;col<9;col++)
            {
                if(board[row][col]=='.')
                {
                    for(char num = '1';num<='9';num++)
                    {
                        if(isValid(board, row, col, num))
                        {
                            board[row][col]=num;
                            if(solve(board))
                            {
                                return true;
                            }
                            board[row][col]='.';
                        }
                    }
                    return false;
                }

            }
        }
        return true;
    }
    public static void main(String[] args)
    {
        char[][] board ={
            {'8','.','.','.','.','.','.','.','.'},
            {'.','.','3','6','.','.','.','.','.'},
            {'.','7','.','.','9','.','2','.','.'},
            {'.','5','.','.','.','7','.','.','.'},
            {'.','.','.','.','4','5','7','.','.'},
            {'.','.','.','1','.','.','.','3','.'},
            {'.','.','1','.','.','.','.','6','8'},
            {'.','.','8','5','.','.','.','1','.'},
            {'.','9','.','.','.','.','4','.','.'}
        };

        if(solve(board))
        {
            System.out.println("Solved Sudoku Board: ");
            for(char[] row: board)
            {
                System.out.println(java.util.Arrays.toString(row));
            }
        }
        else{
            System.out.println("No Solution exists.");
        }
    }
}
