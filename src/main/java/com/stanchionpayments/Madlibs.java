package com.stanchionpayments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for java Madlibs game
 *
 * Madlibs is a simple children’s game one can play on a text-based console where the user is asked for
 * several words based on their grammatical type – e.g. verbs, adjectives, nouns, etc – and then these
 * words are inserted into a preexisting piece of text to make a silly story.
 *
 * @author Khomotjo Modipa.
 */
public class Madlibs {
    /**
     * vowels to help print the corrent question to user
     */
    public String vowels = "aeiou";

    /**
     * Query user to provide values for tokens
     *
     * @param shuffledList shuffled list of gramatical words types.
     * @return shuffled user input as a SortedMap
     */
    public SortedMap<Integer, String> GetUserInput(List<String> shuffledList) {
        Scanner myReaderObj = new Scanner(System.in);
        SortedMap<Integer, String> shuffledUserInput = new TreeMap<>();

        if (shuffledList.size() > 0) {
            for (int i = 0; i < shuffledList.size(); i++) {
                if (this.vowels.contains(shuffledList.get(i).substring(1, 2))) {
                    System.out.println("Enter an " + shuffledList.get(i).substring(1) + ":");
                } else {
                    System.out.println("Enter a " + shuffledList.get(i).substring(1) + ":");
                }

                String userInput = myReaderObj.nextLine();
                var identifier = Integer.parseInt(shuffledList.get(i).substring(0, 1));
                shuffledUserInput.put(identifier, userInput);
            }
        } else {
            System.out.println("Story file does not contain tokens");
        }
        myReaderObj.close();

        return shuffledUserInput;
    }

    /**
     * Replace relevant tokens in the story text.
     *
     * @param fileContentsCopy file contents copy.
     * @param shuffledUserInput shuffled user input to insert in the final story.
     * @return final story as String
     */
    public String ReplaceTokensInTheStory(SortedMap<Integer, String> shuffledUserInput, String fileContentsCopy) {
        if (!shuffledUserInput.isEmpty()) {
            Set<Map.Entry<Integer, String>> set = shuffledUserInput.entrySet();
            Iterator<Map.Entry<Integer, String>> iterator = set.iterator();

            while(iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                int identifier = Integer.parseInt(entry.getKey().toString());
                var regex = "\\{[" + identifier + "].*?\\}";
                String value = entry.getValue().toString();

                fileContentsCopy = fileContentsCopy.replaceAll(regex, value);
            }
        } else {
            System.out.println("User Input values Map is empty");
        }

        return fileContentsCopy;
    }

    /**
     * Shuffle the token list.
     * @param tokens story tokens to shuffle.
     * @return shuffled list of tokens
     */
    public List<String> GenerateShuffledList(SortedMap<Integer, String> tokens) {
        List<String> shuffledList = new ArrayList<>();

        if (!tokens.isEmpty()) {
            Set<Map.Entry<Integer, String>> set = tokens.entrySet();
            Iterator<Map.Entry<Integer, String>> iterator = set.iterator();

            while(iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                shuffledList.add(entry.getKey().toString() + entry.getValue());
            }

            Collections.shuffle(shuffledList);
        } else {
            System.out.println("Tokens map is empty");
        }

        return shuffledList;
    }

    /**
     * Extract all tokens from the string storing the story text.
     *
     * @since var is effective since java 10.
     * @param fileContents holds file contents as a string.
     * @return tokens from the provided fileContents as SortedMap.
     */
    public SortedMap<Integer, String> ExtractTokensFromString(String fileContents) {
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        SortedMap<Integer, String> tokens = new TreeMap<>();

        if (fileContents != null && !fileContents.isEmpty()) {
            Matcher matcher = pattern.matcher(fileContents);

            while ( matcher.find()) {
                var key = matcher.group(1).split(",")[0];
                var value = matcher.group(1).split(",")[1];
                if (!tokens.containsKey(Integer.valueOf(key))) {
                    tokens.put(Integer.valueOf(key), value.trim());
                }
            }
        }
        return tokens;
    }

    /**
     * Read all file contents from specified file-name.
     *
     * @param fileName represents the name of the file.
     * @exception FileNotFoundException will be thrown if file is not found
     * @return file contents as a strings
     */
    public String ReadFile(String fileName) throws FileNotFoundException {
        StringBuilder fileContents = new StringBuilder();
        try {
            String currentDir = new java.io.File(".").getCanonicalPath();
            currentDir += "/src/main/java/" + fileName;
            File myObj = new File(currentDir);
            Scanner myReaderObj = new Scanner(myObj);

            while (myReaderObj.hasNextLine()) {
                String line = myReaderObj.nextLine();
                if (line != null && !line.isEmpty()) {
                    fileContents.append(line).append(" ");
                }
            }
            myReaderObj.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContents.toString().trim();
    }

}
