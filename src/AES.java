import java.io.*;
import java.util.*;

public class AES {

    public static void main(String[] args) {

        String sBoxFile = "sbox.txt";//name of the S-box file

        sBox(sBoxFile);
/*
        Scanner scan = new Scanner(System.in);

        //get input of plaintext and key files
        System.out.println("Please type the name of the PLAINTEXT file: ");
        String filename1 = scan.nextLine();
        System.out.println("Please type the name of the KEY file: ");
        String filename2 = scan.nextLine();

        //process file1 and file2
*/
    }//end main

    public static String[][] sBox(String sboxFile) {
        BufferedReader inFile;
        String nextLine;
        String[] inTokens;

        String[][] aes = new String[16][16];

        System.out.println("Reading sbox...");

        try {
            inFile = new BufferedReader(new FileReader(sboxFile));
            nextLine = inFile.readLine();
            int even = 0;

            while (nextLine != null) {
                System.out.println(nextLine);
                nextLine = inFile.readLine();
                even++;
            }//end reading
            System.out.println(even);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
        }

        return aes;
    }

    public static void processPlaintext(String plaintext) {
        BufferedReader inFile;
        String nextLine;

        System.out.println("Processing file " + plaintext + "...");


    }//end processPlaintext
}
