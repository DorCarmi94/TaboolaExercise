import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class Q3_WordsCounterTest {

    Q3_WordsBank wordsBank;
    Q3_WordsCounter wordsCounter;

    @Before
    public void setUp() throws Exception {
        wordsCounter = new Q3_WordsCounter();
        wordsBank = Q3_WordsBank.GetInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_main_sameWord() {
        Q3_WordsCounter.main(new String[]{
                "input_Q3a.txt",
                "input_Q3b.txt",
                "input_Q3c.txt",
                "input_Q3d.txt",
                "input_Q3e.txt",
                "input_Q3f.txt",
                "input_Q3g.txt",
                "input_Q3h.txt",
                "input_Q3i.txt",
                "input_Q3j.txt"
        });

        assertEquals(10 * 200, wordsBank.GetWordAppearances("aa"));
        assertEquals(10 * 200, wordsBank.GetTotalNumberOfWords());
    }

    @Test
    public void test_main_FromExercise() {
        Q3_WordsCounter.main(new String[]{
                "input_Q3_fromEx_1.txt",
                "input_Q3_fromEx_2.txt",
                "input_Q3_fromEx_3.txt",
        });

        assertEquals(1, wordsBank.GetWordAppearances("and"));
        assertEquals(3, wordsBank.GetWordAppearances("file"));
        assertEquals(1, wordsBank.GetWordAppearances("first"));
        assertEquals(3, wordsBank.GetWordAppearances("is"));
        assertEquals(1, wordsBank.GetWordAppearances("one"));
        assertEquals(1, wordsBank.GetWordAppearances("second"));
        assertEquals(3, wordsBank.GetWordAppearances("the"));
        assertEquals(1, wordsBank.GetWordAppearances("third"));
        assertEquals(3, wordsBank.GetWordAppearances("this"));

        assertEquals(17, wordsBank.GetTotalNumberOfWords());
    }

    @Test
    public void test_main_FromWiki() {
        Q3_WordsCounter.main(new String[]{"input_Q3_fromWiki_Deep.txt"});


        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("./output/expected_Q3_output.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONObject wordsFreqObject=(JSONObject)jsonObject.get("wordsFrequency");
            List<String> words_list= (List<String>) wordsFreqObject.keySet().stream().sorted().collect(Collectors.toList());
            long totalAmount=0;

            for (String word:
                    words_list) {
                assertEquals(word, wordsFreqObject.get(word), (long) wordsBank.GetWordAppearances(word));
                totalAmount += (long) wordsFreqObject.get(word);
            }
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }
}