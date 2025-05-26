package com.sportradar.wordle;

import com.sportradar.wordle.constant.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameWordsGuesserTest {

    @Test
    void testAllGreen() {
        String secret = "apple";
        String guess = "apple";
        String expected = Constants.GREEN + "A" + Constants.RESET +
                Constants.GREEN + "P" + Constants.RESET +
                Constants.GREEN + "P" + Constants.RESET +
                Constants.GREEN + "L" + Constants.RESET +
                Constants.GREEN + "E" + Constants.RESET;
        assertEquals(expected, WordleGameWordsGuesser.getFeedback(secret, guess));
    }

    @Test
    void testAllGray() {
        String secret = "apple";
        String guess = "zzzzz";
        String expected = Constants.GRAY + "Z" + Constants.RESET +
                Constants.GRAY + "Z" + Constants.RESET +
                Constants.GRAY + "Z" + Constants.RESET +
                Constants.GRAY + "Z" + Constants.RESET +
                Constants.GRAY + "Z" + Constants.RESET;
        assertEquals(expected, WordleGameWordsGuesser.getFeedback(secret, guess));
    }

    @Test
    void testSomeYellow() {
        String secret = "apple";
        String guess = "paleg";
        String expected = Constants.YELLOW + "P" + Constants.RESET +
                Constants.YELLOW  + "A" + Constants.RESET +
                Constants.YELLOW + "L" + Constants.RESET +
                Constants.YELLOW + "E" + Constants.RESET +
                Constants.GRAY   + "G" + Constants.RESET;
        assertEquals(expected, WordleGameWordsGuesser.getFeedback(secret, guess));
    }

    @Test
    void testDuplicateLetters() {
        String secret = "apple";
        String guess = "papal";
        String expected = Constants.YELLOW + "P" + Constants.RESET +
                Constants.YELLOW + "A" + Constants.RESET +
                Constants.GREEN  + "P" + Constants.RESET +
                Constants.GRAY   + "A" + Constants.RESET +
                Constants.YELLOW + "L" + Constants.RESET;
        assertEquals(expected, WordleGameWordsGuesser.getFeedback(secret, guess));
    }

    @Test
    void testMixedColors() {
        String secret = "table";
        String guess = "bleat";
        String expected = Constants.YELLOW + "B" + Constants.RESET +
                Constants.YELLOW + "L" + Constants.RESET +
                Constants.YELLOW + "E" + Constants.RESET +
                Constants.YELLOW + "A" + Constants.RESET +
                Constants.YELLOW + "T" + Constants.RESET;
        assertEquals(expected, WordleGameWordsGuesser.getFeedback(secret, guess));
    }
}
