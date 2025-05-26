package com.sportradar.wordle;

import com.sportradar.wordle.constant.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GuessTheWordTest {

    private GuessTheWord guessTheWord;

    @BeforeEach
    void setUp() {
        guessTheWord = new GuessTheWord();
    }

    @Test
    void testGameIntroductionPrinted() throws Exception {
        String secretWord = "apple";
        String input = "apple\n";
        provideInput(input);

        setPrivateField(guessTheWord, "randomSecretWord", secretWord);
        setPrivateField(guessTheWord, "scanner", new Scanner(System.in));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        guessTheWord.startGame();

        String output = outputStream.toString();

        assertTrue(output.contains("Welcome to Sportradar Wordle Game!"));
        assertTrue(output.contains("Guess the 5-letter secret word within " + Constants.MAX_GUESSES + " tries."));
        assertTrue(output.contains(Constants.GREEN + " GREEN " + Constants.RESET + ": Correct letter, correct position."));
        assertTrue(output.contains(Constants.YELLOW + " YELLOW " + Constants.RESET + ": Correct letter, wrong position."));
        assertTrue(output.contains(Constants.GRAY + " GRAY " + Constants.RESET + ": Letter not in the word."));
    }

    @Test
    void testStartGameCorrectGuessFirstTry() throws Exception {
        String secretWord = "apple";
        String input = "apple\n";
        provideInput(input);

        setPrivateField(guessTheWord, "randomSecretWord", secretWord);
        setPrivateField(guessTheWord, "scanner", new Scanner(System.in));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        guessTheWord.startGame();

        String output = outputStream.toString();
        assertTrue(output.contains("Congratulations! You guessed the word: apple"));
    }

    @Test
    void testStartGameInvalidThenValidGuess() throws Exception {
        String secretWord = "grape";
        String input = "abc\ngrape\n";
        provideInput(input);

        setPrivateField(guessTheWord, "randomSecretWord", secretWord);
        setPrivateField(guessTheWord, "scanner", new Scanner(System.in));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        guessTheWord.startGame();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid guess! Your guess must be exactly 5 letters."));
        assertTrue(output.contains("Congratulations! You guessed the word: grape"));
    }

    @Test
    void testStartGameMaxGuessesExceeded() throws Exception {
        String secretWord = "zebra";
        String input = String.join("\n", List.of("apple", "grape", "mango", "lemon", "peach", "berry")) + "\n";
        provideInput(input);

        setPrivateField(guessTheWord, "randomSecretWord", secretWord);
        setPrivateField(guessTheWord, "scanner", new Scanner(System.in));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        guessTheWord.startGame();

        String output = outputStream.toString();
        assertTrue(output.contains("Sorry, you ran out of guesses! The word was: zebra"));
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = GuessTheWord.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}