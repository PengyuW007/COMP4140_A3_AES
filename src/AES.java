//-----------------------------------------
// NAME		: Pengyu Wang
// STUDENT NUMBER	: 7863401
// COURSE		: COMP 4140
// INSTRUCTOR	: Ben Li
// ASSIGNMENT	: assignment 3
// QUESTION	: question 1
//
// REMARKS: implement the encryption and decryption process of AES algorithm as described in the FIPS 197 document.
//
//-----------------------------------------

import java.io.*;

public class AES {

    public static void main(String[] args) {

        /******************************
         * Initialize sBox and inv_sBox
         ******************************/
        String[][] sbox = sBox();
        String[][] inv_sbox = inv_sBox();

        String plaintextFile = args[0];
        String keyFile = args[1];

        /***************************
         * process file1 and file2,plaintext and key txt files
         ***************************/
        //process plaintext
        System.out.println("Plaintext");
        String[] plaintext = processFile(plaintextFile);
        String[][] inputArray = new String[4][4];
        String[][] stateArrayTemp = oneToTwo(plaintext, inputArray);
        String[][] stateArray = rotate2DArray(stateArrayTemp);

        //process key text
        System.out.println("Key");
        String[] key = processFile(keyFile);
        //String[][] keyArray2D = new String[4][4];
        //String[][] keyArrayTemp = oneToTwo(key, keyArray2D);
        //String[][] keyArray = rotate2DArray(keyArrayTemp);

        System.out.println("Key Schedule: ");
        String[] wi = KeyExpansion(key, sbox);
        String[][] wiTemp = new String[11][4];
        String[][] wi2D = oneToTwo(wi, wiTemp);
        print2DArray(wi2D);//4*11

        //Encryption and Decryption
        String[][] cipher = Encryption(stateArray, wi2D, plaintext, sbox);
        Decryption(cipher, wi2D, plaintext, inv_sbox);
        System.out.println("\n\nEnd of Processing");
    }//end main


    /***********************
     * SBox Methods
     ***********************/
    /*
    inv_sBox

    Purpose: get inv_sBox

    Return:
        Type: String[][]

     */
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

    /*
   sBox

   Purpose: get sBox

   Return:
       Type: String[][]

    */
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
    /*
    oneToTwo

    Parameters:
        String[]: data, 1D Array
        String[][]: twoD, the 2D array of size

    Return:
        String[][]: 2D array

    Purpose: Switch from 1D array to 2D array
     */
    private static String[][] oneToTwo(String[] data, String[][] twoD) {
        int count = 0;
        int row = twoD.length;
        int col = twoD[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                twoD[i][j] = data[count];
                count++;
            }
        }
        return twoD;
    }//end oneToTwo

    /*
        rotate2DArray

        Parameters:
            String[][] arr

         Return: After rotation what 2D will be like
                Type: String[][]

         Purpose: do the rotation from vertical to horizontal
     */
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

    /*
    processFile

    Parameters:
        String: text of the input

    Return:
        String[] array of the input

    Purpose: process the input file, do what we need

     */
    public static String[] processFile(String plaintext) {
        BufferedReader inFile;
        String nextLine;
        String[] inTokens = new String[16];

        try {
            inFile = new BufferedReader(new FileReader(plaintext));
            nextLine = inFile.readLine();
            inTokens = nextLine.trim().split(" ");

            for (int i = 0; i < inTokens.length; i++) {
                System.out.print(inTokens[i]);

            }
            System.out.println();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }//end try-catch

        return inTokens;
    }//end processPlaintext

    /***************************
     * Encryption and Decryption
     ***************************/
    /*
    Encryption

    Parameters:
           String[][]state
           String[][]wi
           String[][]plaintext: print the plaintext
           String[][]sBox

     Return:
        Type: String[][]

      Purpose:Return the cipher text

     */
    public static String[][] Encryption(String[][] state, String[][] wi, String[] plaintext, String[][] sBox) {

        System.out.println("\n\nENCRYPTION PROCESS");
        System.out.println("------------------");
        System.out.println("Plain Text:");

        //print plaintext
        for (int i = 0; i < plaintext.length; i++) {
            System.out.print(plaintext[i] + " ");
        }
        //step 0
        String[][] key = getKey(wi, 0);
        String[][] start = AddRounds(state, key);

        //step 1~9
        for (int i = 1; i < 10; i++) {
            String[][] subBytes = SubBytes(start, sBox);
            String[][] shiftRows = ShiftRows(subBytes);
            String[][] mixCols = MixColumns(shiftRows);
            System.out.println("\nState after call " + i + " to MixColumns()");
            System.out.println("--------------------------------------");
            printVertically(mixCols);
            key = getKey(wi, i);
            start = AddRounds(mixCols, key);
        }
        System.out.println();
        String[][] subBytes = SubBytes(start, sBox);
        String[][] shiftRows = ShiftRows(subBytes);
        key = getKey(wi, 10);
        System.out.println("CipherText:");
        start = AddRounds(shiftRows, key);
        printVertically(start);
        return start;
    }//end Encryption

    /*
    Decryption

    Parameters:
           String[][]state
           String[][]wi, the key schedule array
           String[][]cipher: print the plaintext
           String[][]inv_sBox

     Return:
        Type: String[][]

      Purpose:Return the plain text

     */
    public static String[][] Decryption(String[][] state, String[][] wi, String[] cipher, String[][] inv_sBox) {

        System.out.println("\n\nDECRYPTION PROCESS");
        System.out.println("------------------");
        System.out.println("CipherText:");

        //print plaintext
        for (int i = 0; i < cipher.length; i++) {
            System.out.print(cipher[i] + " ");
        }
        //step 10
        String[][] key = getKey(wi, 10);
        String[][] cipher2D = AddRounds(state, key);
        String[][] invShiftRows = InvShiftRows(cipher2D);
        String[][] invSubBytes = InvSubBytes(invShiftRows, inv_sBox);

        //step 1~9
        for (int i = 1; i < 10; i++) {
            key = getKey(wi, 10 - i);
            String[][] start = AddRounds(invSubBytes, key);
            String[][] invMixColumns = InvMixColumns(start);
            System.out.println("\nState after call " + i + " to InvMixColumns()");
            System.out.println("--------------------------------------");
            printVertically(invMixColumns);
            invShiftRows = InvShiftRows(invMixColumns);
            invSubBytes = InvSubBytes(invShiftRows, inv_sBox);
        }
        key = getKey(wi, 0);
        String[][] start = AddRounds(invSubBytes, key);
        System.out.println("Plaintext:");
        printVertically(start);
        return start;
    }//end Encryption


    /************
     * AddRound
     ************/
    /*
    AddRounds

    Parameters:
        String[][]state
        String[][]key

    Return: After Addition of the state Array
            Type: String[][]

    Purpose: After Addition of the state Array

     */
    public static String[][] AddRounds(String[][] state, String[][] key) {
        String[][] res = new String[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String rStr = state[i][j];
                String kStr = key[i][j];
                int r = Integer.parseInt(rStr, 16);
                int k = Integer.parseInt(kStr, 16);

                res[i][j] = numToHex(r ^ k);
            }
        }
        return res;
    }//end AddRounds

    /*********************
     * KeyExpansion
     *********************/
    /*
    KeyExpansion

    Parameters:
        String[]key
        String[][]sBox

    Return:
    Type: String[]

    Purpose:Key Expanded Array

     */
    public static String[] KeyExpansion(String[] key, String[][] sBox) {
        String[] w = new String[44];
        String tempStr, roTWord, subWord, rConiNk, wiNkStr;
        long rc, wiNk, temp;

        //w0~w4
        w[0] = key[0] + key[1] + key[2] + key[3];
        w[1] = key[4] + key[5] + key[6] + key[7];
        w[2] = key[8] + key[9] + key[10] + key[11];
        w[3] = key[12] + key[13] + key[14] + key[15];

        //w4~w43
        int k = -1;
        for (int i = 4; i < 44; i++) {
            tempStr = w[i - 1];
            wiNkStr = w[i - 4];
            wiNk = Long.parseLong(wiNkStr, 16);
            temp = Long.parseLong(tempStr, 16);

            if (i % 4 == 0) {
                roTWord = RotWord(tempStr);
                subWord = SubWord(roTWord, sBox);
                k++;
                rConiNk = rCon(k, subWord);//string
                rc = Long.parseLong(rConiNk, 16);//long
                w[i] = Long.toHexString(rc ^ wiNk);
            } else {
                w[i] = Long.toHexString(temp ^ wiNk);
            }
        }

        return w;
    }//end KeyExpansion

    /*
    RotWord

    Parameter
        String temp

    Return:
        String

    Purpose: Do Reversion
     */
    //RotWord,SubWord,Rcon,Wi
    private static String RotWord(String temp) {
        return temp.substring(2) + temp.substring(0, 2);
    }//end RotWord

    /*
    SubWord

    Parameter
        String rotWord,
        String[][]sBox

    Return:
        String

    Purpose: Substitute Word
     */
    private static String SubWord(String rotWord, String[][] sBox) {
        String res = "";

        for (int i = 1; i < rotWord.length(); i += 2) {
            char r = rotWord.charAt(i - 1);
            char c = rotWord.charAt(i);

            int row = rcTransform(r);
            int col = rcTransform(c);

            res += sBox[row][col];
        }

        return res;
    }//end SubWord

    /*
    rCon

    Parameter
        int i
        String subWord

    Return:
        String

    Purpose: Return the substituted word will be rCon by i of constant

     */
    private static String rCon(int i, String subWord) {
        String[] constant = {"01000000", "02000000",
                "04000000", "08000000",
                "10000000", "20000000",
                "40000000", "80000000",
                "1B000000", "36000000"};

        long hexCons = Long.parseLong(constant[i], 16);
        long hexWord = Long.parseLong(subWord, 16);

        return Long.toHexString(hexCons ^ hexWord);
    }//end constant

    /*
    getKey

    Parameters:
           String[][]keyExpansion
           int index

     Return:
        Type: String[][]

     Purpose: key of Round i
     */
    private static String[][] getKey(String[][] keyExpansion, int index) {
        //keyExpansion== wi[11][4];
        String[][][] rounds = new String[11][4][4];

        String[] curr = keyExpansion[index];

        String[] keyArray = keyArray(curr);
        System.out.println();

        String[][] rTemp = new String[4][4];
        String[][] r = oneToTwo(keyArray, rTemp);

        String[][] round = rotate2DArray(r);

        return rounds[index] = round;
    }//end key

    /*
    keyArray

    Parameters:
        String[]keyCurr

    Return:
        Type: String[]

    Purpose: From 32 elements to 16 elements

     */
    private static String[] keyArray(String[] keyCurr) {
        //32 elements to 16
        String[] res = new String[16];

        int k = 0;
        System.out.println();
        for (int i = 0; i < 4; i++) {
            String curr = keyCurr[i];

            for (int j = 1; j < 8; j += 2) {
                res[k] = curr.charAt(j - 1) + "" + curr.charAt(j);
                k++;
            }
        }
        return res;
    }//end keyArray

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

    /*
    rcTransform

    Parameters:
        char c

    Purpose: change from char to int, match them between char and int

     */
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
        }//end if-else-if block

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

        //int preInt = Integer.parseInt("01", 16);
        //int currInt = Integer.parseInt("FA", 16);
        //System.out.println(numToHex(dotMul(preInt, currInt)));

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                for (int k = 0; k < len; k++) {
                    int preInt = Integer.parseInt(preMatrix[i][k], 16);
                    int currInt = Integer.parseInt(matrix[k][j], 16);
                    ints[i][j] = dotMul(preInt, currInt);//(preM[i][k] * m[k][j]);
                }

            }
        }
        return intToString(ints);
    }//end matrixMul

    private static String[][] intToString(int[][] ints) {
        int strLen = ints.length;
        String[][] hexStrs = new String[strLen][strLen];

        for (int i = 0; i < strLen; i++) {
            for (int j = 0; j < strLen; j++) {
                hexStrs[i][j] = numToHex(ints[i][j]);
            }
        }
        return hexStrs;
    }//end stringToInt

    /*
    dotMul

    Parameters:
        int pre: 01,02,03's array
        int num

    Purpose: dot multiplication in hex
    */
    private static int dotMul(int pre, int num) {
        int res = 0;
        if (pre == 1) {
            res = num;
        } else if (pre == 2) {
            res = two(num);
        } else if (pre == 3) {
            res = three(num);
        }
        return res;
    }//end dotMul

    /*
    two, three

    Parameters:
        int num

    Return:
           int, result when {02}.{XY}

    Purpose: do multiplication in hex way

     */
    private static int two(int num) {
        int c = num << 1;
        int and = c & 128;
        String curr = numToHex(c);
        String resStr = "";
        int hex = 0;

        if (curr.length() > 2) {
            resStr = curr.charAt(1) + "" + curr.charAt(2);
            hex = Integer.parseInt(resStr, 16);
            if (and != 0) {
                //shift out was 1
                hex ^= 27;//num XOR 1b
            }
        } else {
            //shift out was 0
            resStr = curr;
            hex = Integer.parseInt(resStr, 16);
        }

        return hex;
    }//end two

    private static int three(int num) {
        return two(num) ^ num;
    }//end three

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


    private static String[][] InvMixColumns(String[][] arr) {
        return MixColumns(arr);
    }

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
    }//end print2DArray

    public static void print1DArray(String[] oneDArray) {
        for (int i = 0; i < oneDArray.length; i++) {
            System.out.print(oneDArray[i] + " ");
        }
    }

    public static void printVertically(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[j][i] + " ");
            }
        }
    }

    /*
    numToHex

    Parameters:
        int num: the number should be convert to hex value

     Return:
        String
        int to toHexString

     Purpose: Quiet similar with toHexString, but it fixed, "9" to "09"

     */
    private static String numToHex(int num) {
        return String.format("%02x", num);
    }
}
