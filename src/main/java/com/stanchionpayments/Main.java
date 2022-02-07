package com.stanchionpayments;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Driver class.
 * This class makes use of the Madlibs class to simulate the classic Madlibs Game.
 *
 * @author Khomotjo Modipa.
 */
public class Main {
    /* Madlib instance */
    private static Madlibs madlibs = new Madlibs();
    /*property to store file contents*/
    private static String fileContents;
    /* Map to store tokens from story */
    private static SortedMap<Integer, String> tokens = new TreeMap<>();
    /* List to story shuffled tokens from story */
    private static List<String> shuffledList = new ArrayList<>();
    /* Map to store shuffled user inputs */
    private static SortedMap<Integer, String> shuffledUserInput = new TreeMap<>();
    /* Property to store file contents copy */
    private static String fileContentsCopy;

    /**
     * Driver method
     * @param args console arguments.
     * @exception FileNotFoundException will be thrown if file is not found.
     */
    public static void main(String[] args) throws FileNotFoundException {
        try {
            PrintWelcomeMessage();

            fileContents = madlibs.ReadFile("storyfile.txt");
            fileContentsCopy = new StringBuffer(fileContents).toString();

            tokens = madlibs.ExtractTokensFromString(fileContents);

            shuffledList = madlibs.GenerateShuffledList(tokens);

            shuffledUserInput = madlibs.GetUserInput(shuffledList);

            String finalStory = madlibs.ReplaceTokensInTheStory(shuffledUserInput, fileContentsCopy);

            PrintResultantStory(finalStory);

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * Method to print welcome message to the game
     */
    public static void PrintWelcomeMessage() {
        System.out.println("*** Welcome to the Java Madlibs Game ***");
        System.out.println("");
        System.out.println("Mad Libs is a phrasal template word game created by Leonard Stern and Roger Price.");
        System.out.println("Fill out the following questions to generate your own silly mad-libs story instantly from the loaded story");
        System.out.println("");
        System.out.println("Please enter words based on their grammatical type – e.g. verbs, adjectives, nouns, etc");
    }

    /**
     * Method to print Resultant User Story
     *
     * @param story final content to print
     */
    public static void PrintResultantStory(String story) {
        System.out.println(story);
    }
}
