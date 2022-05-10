import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import static org.junit.Assert.*;

public class Q3_WordsBankTest {

    Q3_WordsBank wordsBank;
    @Before
    public void setUp() throws Exception {
        wordsBank=Q3_WordsBank.GetInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_wordExists() {
        this.wordsBank.IncWord("hello");
        assertTrue(wordsBank.WordExists("hello"));
        assertFalse(wordsBank.WordExists("NotAWordInEnglish"));
    }

    @Test
    public void test_getWordAppearances() {
        int amount=wordsBank.GetWordAppearances("hello");
        this.wordsBank.IncWord("hello");
        assertEquals(amount+1,wordsBank.GetWordAppearances("hello"));
    }

    @Test
    public void test_getTotalNumberOfWords() {
        int amount=wordsBank.GetTotalNumberOfWords();
        this.wordsBank.IncWord("hello");
        this.wordsBank.IncWord("hello");
        this.wordsBank.IncWord("hello");
        assertEquals(amount+3,wordsBank.GetTotalNumberOfWords());
    }

    @Test
    public void test_incWord() {

        try {
            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    wordsBank.IncWord("Goodbye");
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    wordsBank.IncWord("Goodbye");
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();
        }
        catch (Exception ex)
        {
            fail(ex.getMessage());
        }


    }







}