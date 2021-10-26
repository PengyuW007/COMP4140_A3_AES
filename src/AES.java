import java.io.*;
import java.util.*;

public class AES {

    public static void main(String[] args) {

        String sBoxFile = "sbox.txt";//name of the S-box file

        sBox(sBoxFile);

        Scanner scan = new Scanner(System.in);

        //get input of plaintext and key files
        System.out.println("Please type the name of the PLAINTEXT file: ");
        String filename1 = scan.nextLine();
        System.out.println("Please type the name of the KEY file: ");
        String filename2 = scan.nextLine();

        //process file1 and file2

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
            int change = 0;//

            while (nextLine != null) {
                if(change==1){
                    change = 0;
                }
                nextLine = inFile.readLine();
                change++;
            }//end reading

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
