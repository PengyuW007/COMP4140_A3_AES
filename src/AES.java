import java.io.*;
import java.util.*;

public class AES {

    public static void main(String[] args) {

        /******************************
         * Initialize sBox and inv_sBox
         ******************************/
        String sbox[][] = sBox();
        String inv_sbox[][] = inv_sBox();
        //print2DArray(sbox);

        //String plaintext = args[0];
        //String key = args[1];
        String plaintextFile = "test1plaintext.txt";
        String keyFile = "test1key.txt";

        /***************************
         * process file1 and file2
         ***************************/
        //process plaintext
        System.out.println("Plaintext");
        String plaintext[] = processFile(plaintextFile);
        String inputArray[][] = new String[4][4];
        String stateArrayTemp[][] = oneToTwo(plaintext, inputArray);
        String stateArray[][] = rotate2DArray(stateArrayTemp);
        print2DArray(stateArray);
        String subByteArray[][] = SubBytes(stateArray, sbox);
        print2DArray(subByteArray);


        //process key text
        System.out.println("Key");
        String key[] = processFile(keyFile);

        //printBox(sbox);

    }//end main

    public static String[][] inv_sBox() {
        String inv_sbox[][] = new String[16][16];

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
        String sbox[][] = new String[16][16];

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
        String inTokens[] = new String[16];
        //String hexStrings[] = new String[16];
        int hexInts[] = new int[16];

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
        }

        return inTokens;
    }//end processPlaintext

    public static String[][] SubBytes(String[][] state, String[][] s_Box) {
        int k = 0;
        int row = 0, col = 0;
        //System.out.println(s_Box[10][8]);
        int parseInt = Integer.parseInt(s_Box[10][8], 16);
        System.out.println(parseInt);
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                String s = state[i][j];
                //int parseInt = Integer.parseInt(s,16);
                char r = s.charAt(0);//row
                char c = s.charAt(1);//col
                row = r - '0';
                col = c - '0';
                /*
                if (r == '1') {
                    row = 1;
                } else if (c == '1') {
                    col = 1;
                } else if (r == '2') {
                    row = 2;
                } else if (c == '2') {
                    col = 2;
                } else if (r == '3') {
                    row = 3;
                } else if (c == '3') {
                    col = 3;
                } else if (r == '4') {
                    row = 4;
                } else if (c == '4') {
                    col = 4;
                } else if (r == '5') {
                    row = 5;
                } else if (c == '5') {
                    col = 5;
                } else if (r == '6') {
                    row = 6;
                } else if (c == '6') {
                    col = 6;
                } else if (r == '7') {
                    row = 7;
                } else if (c == '7') {
                    col = 7;
                } else if (r == '8') {
                    row = 8;
                } else if (c == '8') {
                    col = 8;
                } else if (r == '9') {
                    row = 9;
                } else if (c == '9') {
                    col = 9;
                } else
                */
                if (r == 'a') {
                    row = 10;
                } else if (c == 'a') {
                    col = 10;
                } else if (r == 'b') {
                    row = 11;
                } else if (c == 'b') {
                    col = 11;
                } else if (r == 'c') {
                    row = 12;
                } else if (c == 'c') {
                    col = 12;
                } else if (r == 'd') {
                    row = 13;
                } else if (c == 'd') {
                    col = 13;
                } else if (r == 'e') {
                    row = 14;
                } else if (c == 'e') {
                    col = 14;
                } else if (r == 'f') {
                    row = 15;
                } else if (c == 'f') {
                    col = 15;
                } //end if-else-if block
                
                state[i][j] = s_Box[row][col];
            }
        }

        return state;
    }//end SubBytes

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
