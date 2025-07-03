class SudokuSolver {
    private static final int SIZE = 9; // Sudoku grid size

    public void solveSudoku(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == '.') { // Empty cell found
                    for (char num = '1'; num <= '9'; num++) { // Try numbers 1-9
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num; // Place the number

                            if (solve(board)) { // Recursive call
                                return true;
                            }

                            board[row][col] = '.'; // Backtrack
                        }
                    }
                    return false; // No valid number found, trigger backtracking
                }
            }
        }
        return true; // Solved
    }

    private boolean isValid(char[][] board, int row, int col, char num) {
        for (int i = 0; i < SIZE; i++) {
            // Check row, column, and 3x3 subgrid
            if (board[row][i] == num || board[i][col] == num ||
                    board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    // Helper function to print the board
    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SudokuSolver solver = new SudokuSolver();
        char[][] board = {
                { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
                { '6', '.', '.', '1', '9', '5', '.', '.', '.' },
                { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
                { '8', '.', '.', '.', '6', '.', '.', '.', '3' },
                { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
                { '7', '.', '.', '.', '2', '.', '.', '.', '6' },
                { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
                { '.', '.', '.', '4', '1', '9', '.', '.', '5' },
                { '.', '.', '.', '.', '8', '.', '.', '7', '9' }
        };

        System.out.println("Original Sudoku:");
        printBoard(board);

        solver.solveSudoku(board);

        System.out.println("\nSolved Sudoku:");
        printBoard(board);
    }
}
