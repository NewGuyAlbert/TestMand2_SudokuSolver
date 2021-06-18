import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    //Member variables
    int[][] sudoku;
    private SudokuSolver solver;

    //The following function will run before each test
    @BeforeMethod
    void setUp(){

        sudoku = new int[][]{
                { 5, 3, 0, 0, 7, 0, 0, 0, 0},
                { 6, 0, 0, 1, 9, 5, 0, 0, 0},
                { 0, 9, 8, 0, 0, 0, 0, 6, 0},
                { 8, 0, 0, 0, 6, 0, 0, 0, 3},
                { 4, 0, 0, 8, 0, 3, 0, 0, 1},
                { 7, 0, 0, 0, 2, 0, 0, 0, 6},
                { 0, 6, 0, 0, 0, 0, 2, 8, 0},
                { 0, 0, 0, 4, 1, 9, 0, 0, 5},
                { 0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        solver = new SudokuSolver(sudoku);

    }

    //The following data provider returns all integers between 1 and 9
    @DataProvider (name = "nValue")
    public Object[][] returnNValue(){
        return new Object[][] {{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}};
    }

    //The following test checks if a number n (between 1 and 9) fits inside an [i,j] field
    @Test (dataProvider = "nValue")
    public void possibleTest(int n){

        //the method "possible" takes 3 parameters i,j,n and returns true if n can fit inside the [i,j] field
        if(n == 5){
            assertTrue(solver.possible(4,4,n));
        }
        else{
            assertFalse(solver.possible(4,4,n));
        }
    }

    //The following data provider returns two objects that represent a field that is not empty and a field that is empty within the sudoku
    @DataProvider (name = "field")
    public Object[][] returnField(){
        return new Object[][] {{0,0}, {0,2}};
    }

    //The following test checks if field represented by a i and j value is empty
    @Test (dataProvider = "field")
    public void checkIfEmptyTest(int[] field){

        if(field[1] == 0){
            assertFalse(solver.checkIfEmpty(field[0],field[1]));
        }
        else{
            assertTrue(solver.checkIfEmpty(field[0],field[1]));
        }

    }

    //The following test checks if the sudoku is solved
    @Test
    public void solveTest(){


        solver.solve();
        sudoku = solver.getSudoku();
        assertTrue(assertSudokuSolved(sudoku));

    }

    //The following function return true if the sudoku is solved or false if it isn't
    public static boolean assertSudokuSolved(int[][] sudoku){
        int count;

        //Check that each row has exactly one number between 1 and 9
        for(int i = 0; i < sudoku.length; i++) {
            for(int j = 0; j < sudoku.length; j++) {
                count = 0;
                for (int n = 1; n <= sudoku.length; n++) {
                    if (sudoku[i][j] == n)
                        count++;
                }
                if (count != 1)
                    return false;
            }

        }

        //Check that each column has exactly one number between 1 and 9
        for(int j = 0; j < sudoku.length; j++) {
            for(int i = 0; i < sudoku.length; i++) {
                count = 0;
                for (int n = 1; n <= sudoku.length; n++) {
                    if (sudoku[i][j] == n)
                        count++;
                }
                if (count != 1)
                    return false;
            }
        }

        //Check that each mini square has exactly one number between 1 and 9
        for(int i = 0; i < sudoku.length; i+=3){
            for(int j = 0; j < sudoku.length; j+=3){
                for(int x = (i / 3) * 3; x < (i / 3) * 3 + 3; x++) {
                    for (int y = (j / 3) * 3; y < (j / 3) * 3 + 3; y++) {
                        count = 0;
                        for (int n = 1; n <= sudoku.length; n++) {
                            if (sudoku[y][x] == n) {
                                count++;
                            }
                        }
                        if (count != 1)
                            return false;
                    }
                }
            }
        }

        return true;
    }
}
