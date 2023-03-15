package Model;


import java.util.Random;
public class SudokuUtilities {

    public enum SudokuLevel {EASY, MEDIUM, HARD}

    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    /**
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param level The level, i.e. the difficulty, of the initial standing.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is not 2*81 characters and
     *                                  for characters other than '0'-'9'.
     */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        String representationString;
        switch (level) {
            case EASY: representationString = easy; break;
            case MEDIUM: representationString = medium; break;
            case HARD: representationString = hard; break;
            default: representationString = medium;
        }
        return shuffle(convertStringToIntMatrix(representationString));
    }

    /**
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param stringRepresentation A string of 2*81 characters, 0-9. The first 81 characters represents
     *                             the initial values, '0' representing an empty cell.
     *                             The following 81 characters represents the solution.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is not 2*81 characters and
     *                                  for characters other than '0'-'9'.
     */
    /*package private*/
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " + stringRepresentation.length());

        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();

        int charIndex = 0;
        // initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        // solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        return values;
    }

    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " + ch);
        return ch - '0';
    }

    /**
     * Shuffles sudoku numbers everytime a new game is created
     *
     * @param matrix the three-dimensional matrix that gets shuffled
     * @return the shuffled matrix
     */

    private static int[][][] shuffle(int[][][] matrix){
        Random rand = new Random();

        int n4 = rand.nextInt(2)+1;
        int n5 = rand.nextInt(2)+1;
        if(n4 == 1){
            //Interchange first 3 and last 3 columns
            n4 = 3;
        }else{
            //Mirror columns
            n4 = 5;
        }

        if(n5 == 1){
            //Interchange first 3 and last 3 rows
            n5 = 3;
        }else{
            //Mirror rows
            n5 = 5;
        }

        int n3 = rand.nextInt(3)+1;

        //Inverse columns
        if(n3 == 1){
            for (int x = 0; x <= 1; x++) {
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int col = 0; col < n4; col++) {
                        int val = matrix[row][(GRID_SIZE-col)-1][x];
                        matrix[row][(GRID_SIZE-col)-1][x] = matrix[row][col][x];
                        matrix[row][col][x] = val;
                    }
                }
            }
        }

        //Inverse rows
        if(n3 == 2){
            for (int x = 0; x <= 1; x++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    for (int row = 0; row < n5; row++) {
                        int val = matrix[(GRID_SIZE-row)-1][col][x];
                        matrix[(GRID_SIZE-row)-1][col][x] = matrix[row][col][x];
                        matrix[row][col][x] = val;
                    }
                }
            }
        }

        //Inverse both
        if(n3 == 3){
            for (int x = 0; x <= 1; x++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    for (int row = 0; row < n4; row++) {
                        int val = matrix[(GRID_SIZE-row)-1][col][x];
                        matrix[(GRID_SIZE-row)-1][col][x] = matrix[row][col][x];
                        matrix[row][col][x] = val;
                    }
                }
            }
            for (int x = 0; x <= 1; x++) {
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int col = 0; col < n5; col++) {
                        int val = matrix[row][(GRID_SIZE-col)-1][x];
                        matrix[row][(GRID_SIZE-col)-1][x] = matrix[row][col][x];
                        matrix[row][col][x] = val;
                    }
                }
            }
        }

        //Interchange 2 random numbers 20 times
        for(int n = 0;n < 20;n++) {
            int n1 = rand.nextInt(9)+1;
            int n2 = rand.nextInt(9)+1;
            for (int x = 0; x <= 1; x++) {
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int col = 0; col < GRID_SIZE; col++) {
                        if (matrix[row][col][x] == n1) {
                            matrix[row][col][x] = n2;
                        } else if (matrix[row][col][x] == n2) {
                            matrix[row][col][x] = n1;
                        }
                    }
                }
            }
        }

        return matrix;
    }

    private static final String easy =
            "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" + // solution values after this substring
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" +
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";

    private static final String medium =
            "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" + // solution values after this substring
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";

    private static final String hard =
            "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" + // solution values after this substring
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";
}
