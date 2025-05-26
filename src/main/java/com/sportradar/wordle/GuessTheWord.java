package com.sportradar.wordle;

import com.sportradar.wordle.constant.Constants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.sportradar.wordle.constant.Constants.WORD_LENGTH;

@Component
public class GuessTheWord implements CommandLineRunner {
    private String randomSecretWord;
    private Scanner scanner;

    @Override
    public void run(String... args) {
        this.scanner = new Scanner(System.in);
        loadRandomSecretWord();
        startGame();
    }

    void startGame() {
        gameIntroduction();

        for (int guessCount = 1; guessCount <= Constants.MAX_GUESSES; guessCount++) {
            System.out.println("\nGuess " + guessCount + "/" + Constants.MAX_GUESSES + ":");
            String guess = getUserGuess();

            String feedback = WordleGameWordsGuesser.getFeedback(randomSecretWord, guess);
            System.out.println(feedback);

            if (guess.equalsIgnoreCase(randomSecretWord)) {
                System.out.println("\nCongratulations! You guessed the word: " + randomSecretWord);
                return;
            }
        }
        System.out.println("\nSorry, you ran out of guesses! The word was: " + randomSecretWord);
    }

    private void gameIntroduction() {
        String intro = "Welcome to Sportradar Wordle Game!\n" +
                "Guess the 5-letter secret word within " +
                Constants.MAX_GUESSES +
                " tries.\n\n" +
                "Feedback will be given using colors:\n" +
                Constants.GREEN + " GREEN " + Constants.RESET +
                ": Correct letter, correct position.\n" +
                Constants.YELLOW + " YELLOW " + Constants.RESET +
                ": Correct letter, wrong position.\n" +
                Constants.GRAY + " GRAY " + Constants.RESET +
                ": Letter not in the word.\n\n" +
                "Good luck with your guesses!";
        System.out.println(intro);
    }

    private void loadRandomSecretWord() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(Constants.TEXT_FILE)) {
            if (is == null) {
                throw new RuntimeException("Resource not found: " + Constants.TEXT_FILE);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                List<String> wordList = reader.lines()
                        .map(String::toLowerCase)
                        .filter(word -> word.length() == WORD_LENGTH)
                        .toList();

                if (wordList.isEmpty()) {
                    throw new IllegalArgumentException("Word list cannot be empty or contain no " + WORD_LENGTH + "-letter words.");
                }
                randomSecretWord = wordList.get(new Random().nextInt(wordList.size()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading word list: " + e.getMessage(), e);
        }
    }

    private String getUserGuess() {
        while (true) {
            System.out.print("Enter your 5-letter guess: ");
            String guess = scanner.nextLine().toLowerCase();
            if (guess.length() != WORD_LENGTH) {
                System.out.println("Invalid guess! Your guess must be exactly 5 letters.");
            } else {
                return guess;
            }
        }
    }
}