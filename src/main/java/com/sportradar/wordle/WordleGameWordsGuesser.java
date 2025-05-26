package com.sportradar.wordle;

import com.sportradar.wordle.constant.Constants;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WordleGameWordsGuesser {

    public static String getFeedback(String secretWord, String guess) {
        Map<Character, Long> secretWordCharCounts = secretWord.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        String[] colors = new String[secretWord.length()];

        // First pass: Identify correctly placed (GREEN) letters
        IntStream.range(0, secretWord.length())
                .forEach(i -> {
                    if (guess.charAt(i) == secretWord.charAt(i)) {
                        colors[i] = Constants.GREEN;
                        secretWordCharCounts.put(guess.charAt(i), secretWordCharCounts.get(guess.charAt(i)) - 1);
                    } else {
                        colors[i] = Constants.GRAY;
                    }
                });

        // Second pass: Identify misplaced (YELLOW) letters
        IntStream.range(0, secretWord.length())
                .filter(i -> colors[i].equals(Constants.GRAY))
                .forEach(i -> {
                    char guessedChar = guess.charAt(i);
                    if (secretWordCharCounts.getOrDefault(guessedChar, 0L) > 0) {
                        colors[i] = Constants.YELLOW;
                        secretWordCharCounts.put(guessedChar, secretWordCharCounts.get(guessedChar) - 1);
                    }
                });

        // Construct feedback
        return IntStream.range(0, secretWord.length())
                .mapToObj(i -> colors[i] + Character.toUpperCase(guess.charAt(i)) + Constants.RESET)
                .collect(Collectors.joining());
    }
}
