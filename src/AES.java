import java.io.*;

public class AES {

    public static void main(String[] args) {

        /******************************
         * Initialize sBox and inv_sBox
         ******************************/
        String[][] sbox = sBox();
        String[][] inv_sbox = inv_sBox();

        //String plaintext = args[0];
        //String key = args[1];
        String plaintextFile = "test1plaintext.txt";
        String keyFile = "test1key.txt";

        /***************************
         * process file1 and file2,plaintext and key txt files
         ***************************/
        //process plaintext
        System.out.println("Plaintext");
        String[] plaintext = processFile(plaintextFile);
        String[][] inputArray = new String[4][4];
        String[][] stateArrayTemp = oneToTwo(plaintext, inputArray);
        String[][] stateArray = rotate2DArray(stateArrayTemp);
        print2DArray(stateArray);

        //process key text
        System.out.println("Key");
        String[] key = processFile(keyFile);
        String [][]keyArray2D=new String[4][4];
        String [][]keyArrayTemp = oneToTwo(key,keyArray2D);
        String[][] keyArray=rotate2DArray(keyArrayTemp);
        print2DArray(keyArray);

        String temp = "09cf4f3c";
        System.out.println(RotWord(temp));
/*
        String[][] subByteArray = SubBytes(stateArray, sbox);
        //print2DArray(subByteArray);
        ShiftRows(stateArray);
        String[][] shiftRowsArray = ShiftRows(subByteArray);
        print2DArray(shiftRowsArray);

        String[][] invShiftRowsArray = InvShiftRows(subByteArray);
        //print2DArray(invShiftRowsArray);

        String[][] matrixMulp = MixColumns(shiftRowsArray);
        print2DArray(matrixMulp);
*/
    }//end main

    /***********************
     * SBox Methods
     ***********************/
    public static String[][] inv_sBox() {
        String[][] inv_sbox = new String[16][16];

        String[] data = {"52", "09", "6a", "d5", "30", "36", "a5", "38", "bf", "40", "a3", "9e", "81", "f3", "d7", "fb",
                "7c", "e3", "39", "82", "9b", "2f", "ff", "87", "34", "8e", "43", "44", "c4", "de", "e9", "cb",
                "54", "7b", "94", "32", "a6", "c2", "23", "3d", "ee", "4c", "95", "0b", "42", "fa", "c3", "4e",
                "08", "2e", "a1", "66", "28", "d9", "24", "b2", "76", "5b", "a2", "49", "6d", "8b", "d1", "25",
                "72", "f8", "f6", "64", "86", "68", "98", "16", "d4", "a4", "5c", "cc", "5d", "65", "b6", "92",
                "6c", "70", "48", "50", "fd", "ed", "b9", "da", "5e", "15", "46", "57", "a7", "8d", "9d", "84",
                "90", "d8", "ab", "00", "8c", "bc", "d3", "0a", "f7", "e4", "58", "05", "b8", "b3", "45", "06",
                "d0", "2c", "1e", "8f", "ca", "3f", "0f", "02", "c1", "af", "bd", "03", "01", "13", "8a", "6b",
                "3a", "91", "11", "41", "4f", "67", "dc", "ea", "97", "f2", "cf", "ce", "f0", "b4", "e6", "73",
                "96", "ac", "74", "22", "e7", "ad", "35", "85", "e2", "f9", "37", "e8", "1c", "75", "df", "6e",
                "47", "f1", "1a", "71", "1d", "29", "c5", "89", "6f", "b7", "62", "0e", "aa", "18", "be", "1b",
                "fc", "56", "3e", "4b", "c6", "d2", "79", "20", "9a", "db", "c0", "fe", "78", "cd", "5a", "f4",
                "1f", "dd", "a8", "33", "88", "07", "c7", "31", "b1", "12", "10", "59", "27", "80", "ec", "5f",
                "60", "51", "7f", "a9", "19", "b5", "4a", "0d", "2d", "e5", "7a", "9f", "93", "c9", "9c", "ef",
                "a0", "e0", "3b", "4d", "ae", "2a", "f5", "b0", "c8", "eb", "bb", "3c", "83", "53", "99", "61",
                "17", "2b", "04", "7e", "ba", "77", "d6", "26", "e1", "69", "14", "63", "55", "21", "0c", "7d"};
        return oneToTwo(data, inv_sbox);

    }//end inv_sBox

    public static String[][] sBox() {
        String[][] sbox = new String[16][16];

        String[] data = {"63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76",
                "ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0",
                "b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15",
                "04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75",
                "09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84",
                "53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf",
                "d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8",
                "51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2",
                "cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73",
                "60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db",
                "e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79",
                "e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08",
                "ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a",
                "70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e",
                "e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df",
                "8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"};


        return oneToTwo(data, sbox);
    }//end sBox

    /***************************
     * Functions for 2D arrays
     ***************************/
    private static String[][] oneToTwo(String[] data, String[][] twoD) {
        int count = 0;
        int row = (int) Math.sqrt(data.length);
        int col = row;
        //String hex[][] = new String[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                twoD[i][j] = data[count];
                //int parseInt = Integer.parseInt(twoD[i][j], 16);
                //hex[i][j] = Integer.toHexString(parseInt);
                count++;
            }
        }

        return twoD;
    }//end oneToTwo

    //do the rotation from vertical to horizontal
    private static String[][] rotate2DArray(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            // [0][1],[1][0] switch
            // [0][2],[2][0] switch
            // [1][2],[2][1] switch
            // j<i, otherwise it switched back
            for (int j = 0; j < i; j++) {
                String temp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = temp;
            }
        }
        return arr;
    }//end rotate2DArray

    public static String[] processFile(String plaintext) {
        BufferedReader inFile;
        String nextLine;
        String[] inTokens = new String[16];
        //String hexStrings[] = new String[16];
        int[] hexInts = new int[16];

        try {
            inFile = new BufferedReader(new FileReader(plaintext));
            nextLine = inFile.readLine();
            inTokens = nextLine.trim().split(" ");

            for (int i = 0; i < inTokens.length; i++) {
                //hexInts[i] = Integer.parseInt(inTokens[i], 16);
                //String hexString = Integer.toHexString(hexInts[i]);
                //hexStrings[i] = hexString;
                System.out.print(inTokens[i]);

            }
            System.out.println();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }//end try-catch

        return inTokens;
    }//end processPlaintext

    /************************
     * SubBytes
     ************************/
    public static String[][] SubBytes(String[][] state, String[][] s_Box) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                String s = state[i][j];
                char r = s.charAt(0);//row
                char c = s.charAt(1);//col

                int row = rcTransform(r);
                int col = rcTransform(c);

                state[i][j] = s_Box[row][col];
            }
        }
        return state;
    }//end SubBytes

    private static int rcTransform(char c) {
        int val = c - '0';

        if (c == 'a') {
            val = 10;
        } else if (c == 'b') {
            val = 11;
        } else if (c == 'c') {
            val = 12;
        } else if (c == 'd') {
            val = 13;
        } else if (c == 'e') {
            val = 14;
        } else if (c == 'f') {
            val = 15;
        } //end if-else-if block

        return val;
    }

    public static String[][] InvSubBytes(String[][] state, String[][] inv_Box) {
        return SubBytes(state, inv_Box);
    }//end InvSubBytes

    /****************************
     * ShiftRows and InvShiftRows
     ****************************/
    /********* ShiftRows ********/
    public static String[][] ShiftRows(String[][] state) {
        for (int i = 0; i < state.length; i++) {
            state[i] = shiftRow(state[i], i);
        }
        return state;
    }//end ShiftRows

    private static String[] shiftRow(String[] arr, int shift) {
        //arr: 1 2 3 4 5,shift 3
        //tmp: 4 5 1 2 3
        String[] temp = new String[arr.length];

        System.arraycopy(arr, shift, temp, 0, arr.length - shift);
        System.arraycopy(arr, 0, temp, arr.length - shift, shift);
        return temp;
    }//end leftShiftRow

    /********* InvShiftRows ******/
    public static String[][] InvShiftRows(String[][] state) {
        for (int i = 0; i < state.length; i++) {
            state[i] = invShiftRow(state[i], i);
        }
        return state;
    }//end InvShiftRows

    private static String[] invShiftRow(String[] arr, int shift) {
        //right shifted
        //arr: 1 2 3 4 5,shift 3
        //tmp: 3 4 5 1 2
        String[] temp = new String[arr.length];

        System.arraycopy(arr, arr.length - shift, temp, 0, shift);
        System.arraycopy(arr, 0, temp, shift, arr.length - shift);
        return temp;
    }//end invShiftRow

    /******************************
     * MixColumns and InvMixColumns
     ******************************/
    public static String[][] MixColumns(String[][] matrix) {
        String[][] preMatrix = {{"02", "03", "01", "01"},
                {"01", "02", "03", "01"},
                {"01", "01", "02", "03"},
                {"03", "01", "01", "02"}};

        return matrixMul(preMatrix, matrix);
    }

    private static String[][] matrixMul(String[][] preMatrix, String[][] matrix) {
        String[][] res = new String[4][4];
        int len = matrix.length;
        int[][] ints = new int[4][4];

        int[][] preM = stringToInt(preMatrix);
        int[][] m = stringToInt(matrix);

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                String hexString = "";
                for (int k = 0; k < len; k++) {
                    ints[i][j] ^= dotMul(preM[i][k], m[k][j]);//(preM[i][k] * m[k][j]);
                }
                hexString = Integer.toHexString(ints[i][j]);
                res[i][j]=hexString;
            }
        }
        return res;
    }//end matrixMul

    private static int dotMul(int a, int b) {
        int c = a << 1;
        int and = c & 128;

        if (and != 0) {
            c ^=27;
        }

        return c;
    }//end dotMul

    private static int[][] stringToInt(String[][] strings) {
        int strLen = strings.length;
        int[][] hexInts = new int[strLen][strLen];

        for (int i = 0; i < strLen; i++) {
            for (int j = 0; j < strLen; j++) {
                hexInts[i][j] = Integer.parseInt(strings[i][j], 16);
            }
        }

        return hexInts;
    }//end stringToInt

    /*********************
     * KeyExpansion
     *********************/
    public static String KeyExpansion(String key){
        return key;
    }//end KeyExpansion

    //RotWord,SubWord,Rcon,Wi
    private static String RotWord(String temp){
        return temp.substring(2)+temp.substring(0,2);
    }//end RotWord

    private static String SubWord(String rotWord){
        String res = "";

        for(int i=1;i<rotWord.length();i+=2){
            char r = rotWord.charAt(i-1);
            char c = rotWord.charAt(i);

            int row = rcTransform(r);
            int col = rcTransform(c);
        }

        return res;
    }//end SubWord

    private static String rCon(int i){
        String[] constant={"01000000","02000000",
                            "04000000","08000000",
                            "10000000","20000000",
                            "40000000","80000000",
                            "1B000000","36000000"};
        return constant[i];
    }//end constant

    /*********************
     * other helper method
     *********************/
    public static void print2DArray(String[][] twoDArray) {
        for (int i = 0; i < twoDArray.length; i++) {
            System.out.println();
            for (int j = 0; j < twoDArray[0].length; j++) {
                System.out.print(twoDArray[i][j] + " ");
            }
        }
        System.out.println();
    }//end printBox
}
