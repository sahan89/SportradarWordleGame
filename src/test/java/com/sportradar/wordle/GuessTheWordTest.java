package com.sportradar.wordle;

import com.sportradar.wordle.constant.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GuessTheWordTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;
    private ByteArrayInputStream inputStreamSimulator;

    private GuessTheWord guessTheWord;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        guessTheWord = new GuessTheWord();
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        if (inputStreamSimulator != null) {
            inputStreamSimulator.close();
        }
        Files.deleteIfExists(Paths.get("test_words.txt"));
    }

    private void provideInput(String data) {
        inputStreamSimulator = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStreamSimulator);
        ReflectionTestUtils.setField(guessTheWord, "scanner", new Scanner(System.in));
    }

    private String getOutput() {
        return outputStreamCaptor.toString().trim();
    }

    @Test
    @DisplayName("Should successfully guess the word on the first attempt")
    void shouldWinGameOnFirstAttempt() {
        String secretWord = "APPLE";
        ReflectionTestUtils.setField(guessTheWord, "randomSecretWord", secretWord);
        provideInput("apple\n");
        guessTheWord.startGame();

        String output = getOutput();
        assertTrue(output.contains("APPLE"));
        assertTrue(output.contains("Congratulations! You guessed the word: APPLE"));
        assertFalse(output.contains("Sorry, you ran out of guesses!"));
    }

    @Test
    @DisplayName("Should successfully guess the word after multiple attempts")
    void shouldWinGameAfterMultipleAttempts() {
        String secretWord = "WATER";
        ReflectionTestUtils.setField(guessTheWord, "randomSecretWord", secretWord);

        provideInput("brick\nsetup\nwater\n");

        guessTheWord.startGame();

        String output = getOutput();
        assertTrue(output.contains("Guess 1/" + Constants.MAX_GUESSES));
        assertTrue(output.contains("Guess 2/" + Constants.MAX_GUESSES));
        assertTrue(output.contains("Guess 3/" + Constants.MAX_GUESSES));
        assertTrue(output.contains("Congratulations! You guessed the word: WATER"));
        assertFalse(output.contains("Sorry, you ran out of guesses!"));
    }

    @Test
    @DisplayName("Should lose the game after all guesses are exhausted")
    void shouldLoseGameAfterMaxGuesses() {
        String secretWord = "APPLE";
        ReflectionTestUtils.setField(guessTheWord, "randomSecretWord", secretWord);

        provideInput("brick\nsetup\nworld\nbless\nlight\n");

        guessTheWord.startGame();

        String output = getOutput();
        assertTrue(output.contains("Sorry, you ran out of guesses! The word was: APPLE"));
        assertFalse(output.contains("Congratulations!"));
        assertTrue(output.contains("Guess " + Constants.MAX_GUESSES + "/" + Constants.MAX_GUESSES));
    }

    @Test
    @DisplayName("Should handle invalid guess length repeatedly until a valid 5-letter guess is provided")
    void shouldHandleInvalidGuessLength() {
        String secretWord = "HELLO";
        ReflectionTestUtils.setField(guessTheWord, "randomSecretWord", secretWord);

        provideInput("hi\nlongword\nhello\n");

        guessTheWord.startGame();

        String output = getOutput();
        assertTrue(output.contains("Invalid guess! Your guess must be exactly 5 letters."));
        assertTrue(output.contains("Congratulations! You guessed the word: HELLO"));
    }

    @Test
    @DisplayName("Game introduction should be printed correctly")
    void gameIntroductionShouldBePrinted() {
        ReflectionTestUtils.invokeMethod(guessTheWord, "gameIntroduction");

        String expectedIntroPart = "Welcome to Sportradar Wordle Game!";
        String output = getOutput();
        assertTrue(output.contains(expectedIntroPart));
        assertTrue(output.contains("Guess the 5-letter secret word within " + Constants.MAX_GUESSES + " tries."));
        assertTrue(output.contains(Constants.GREEN + " GREEN " + Constants.RESET + ": Correct letter, correct position."));
    }
}