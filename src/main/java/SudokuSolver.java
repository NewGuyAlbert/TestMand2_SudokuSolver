public class SudokuSolver {


    private int[][] sudoku;
    private int[][] solvedSudoku = new int[9][9];


    //Constructor
    public SudokuSolver(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    //Getter
    public int[][] getSudoku() {
        prettyPrint();
        return solvedSudoku;
    }

    //Following method will return true of the [y,x] field is empty and false if it doesn't
    public boolean checkIfEmpty(int y, int x){

        return this.sudoku[y][x] == 0;
    }

    //Following method will return true if n fits inside the [y,x] field and false if it doesn't
    public boolean possible(int y, int x, int n) {

        //We check on the y axis
        for(int i = 0; i < sudoku.length; i++){
            if(sudoku[y][i] == n){
                return false;
            }
        }

        //We check on the x axis
        for(int j = 0; j < sudoku.length; j++){
            if(sudoku[j][x] == n){
                return false;
            }
        }

        //The sudoku is one big square formed by 9 3x3 mini squares
        //since an int cannot be repeated twice in the same mini square we need to check inside of it as well
        //The following two lines of code represent the x and y value of the top left element of the mini square we are in
        int x0 = (x/3)*3;
        int y0 = (y/3)*3;

        //We check inside the mini square
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(sudoku[y0+i][x0+j] == n)
                    return false;

        //If it passed all searches we return true
        return true;
    }

    //The following method can find all solution to a sudoku
    //The current implementation returns the sudoku back to its original state because of backtracking
    //If we manage to solve the sudoku we quickly copy it by value into a solvedSudoku matrix
    //The solvedSudoku object will hold the last solution found
    public void solve(){
        for(int y = 0; y < sudoku.length; y++) {
            for (int x = 0; x < sudoku.length; x++) {
                if (checkIfEmpty(y,x)){
                    for (int n = 1; n <= 9; n++) {
                        if (possible(y, x, n)){
                            sudoku[y][x] = n;
                            solve();
                            sudoku[y][x] = 0;
                        }
                    }
                    return;
                }
            }
        }
        //If we ever get here it means we managed to find a solution
        //Thus we quickly save it in the solvedSudoku matrix
        for(int i=0; i<sudoku.length; i++)
            for(int j=0; j<sudoku[i].length; j++)
                solvedSudoku[i][j]=sudoku[i][j];
    }

    public void prettyPrint(){
        for(int i = 0; i < solvedSudoku.length; i++) {
            for (int j = 0; j < solvedSudoku.length; j++)
                System.out.print(solvedSudoku[i][j] + " ");
            System.out.println();
        }
    }
}
