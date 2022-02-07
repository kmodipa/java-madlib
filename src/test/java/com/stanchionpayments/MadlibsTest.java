package com.stanchionpayments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class MadlibsTest {

    @Test
    void replaceTokensInTheStory() {
        Madlibs madlibs = new Madlibs();
        String fileContentsCopy = "Once upon a time there was a {1,adjective} {2,noun} who liked to {3,verb}.";
        SortedMap<Integer, String> tokens = new TreeMap<>();
        tokens.put(1, "Mighty");
        tokens.put(1, "Bruce");
        tokens.put(2, "Lee");

        String result = madlibs.ReplaceTokensInTheStory(tokens, fileContentsCopy);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertFalse(result.matches("\\{(.*?)\\}"));
    }

    @Test
    void generateShuffledList() {
        Madlibs madlibs = new Madlibs();
        SortedMap<Integer, String> tokens = new TreeMap<>();
        tokens.put(1, "adjective");
        tokens.put(2, "noun");
        tokens.put(3, "verb");
        tokens.put(3, "plural verb");
        List<String> expectedShuffledList = new ArrayList<>();
        expectedShuffledList.add("adjective");
        expectedShuffledList.add("noun");
        expectedShuffledList.add("verb");
        expectedShuffledList.add("plural verb");
        List<String> shuffledList = new ArrayList<>();

        expectedShuffledList = madlibs.GenerateShuffledList(tokens);
        Assertions.assertFalse(expectedShuffledList.isEmpty(), "The returned list should not be empty");
        Assertions.assertNotEquals(expectedShuffledList, shuffledList);
    }

    @Test
    void extractTokensFromString() {
        Madlibs madlibs = new Madlibs();
        SortedMap<Integer, String> expected = new TreeMap<>();
        SortedMap<Integer, String> results = new TreeMap<>();
        String fileContents = "Once upon a time there was a {1,adjective} {2,noun} who liked to {3,verb}.";
        expected.put(1, "adjective");
        expected.put(2, "noun");
        expected.put(3, "verb");

        results = madlibs.ExtractTokensFromString(fileContents);
        Assertions.assertEquals(expected, results, "ExtractTokensFromString method should return a SortedMap of tokens from the string provided");
    }

    @Test
    void readFile() throws FileNotFoundException {
        String expected = "test";
        Madlibs madlibs = new Madlibs();
        String result = madlibs.ReadFile("testFile.txt");

        Assertions.assertFalse(result.isEmpty(), "The file contents should not be empty");
        Assertions.assertEquals(expected, result, "The ReadFile method should return file contents");
    }
}