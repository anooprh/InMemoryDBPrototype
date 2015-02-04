package com.anoop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * This class represents the class invoked upon command line i.e class listening to input from stdin
 * and contains the main method. This class is the point of entry for this program
 *
 * Author : Anoop Hallur
 * Date   : 02/04/2014
 */
public class MainClass {
    public static void main(String[] args) {

        // Buffered reader for reading from stdin
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        CommandHandler commandHandler = new CommandHandler();

        try {
            String line ;

            // Loop on until the file ends i.e stop reading from file as soon as
            // nothing is remaining to be read
            while((line = br.readLine()) != null){

                // Extract the main command(ex, SET), and arguments(a 10) to the command and delegate the
                // responsibility to command handler
                String[] inputWords = line.split(" ");
                String command = inputWords[0];
                String[] commandArgs = Arrays.copyOfRange(inputWords, 1, inputWords.length);
                commandHandler.handle(command, commandArgs);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
