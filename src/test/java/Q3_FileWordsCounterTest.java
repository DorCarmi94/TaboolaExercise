import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.OrderWith;

public class Q3_FileWordsCounterTest extends TestCase {

    Q3_FileWordsCounter fileWordsCounter;
    Q3_FileWordsCounter wrong_fileWordsCounter;
    Q3_WordsBank wordsBank;
    public void setUp() throws Exception {
        super.setUp();
        wordsBank=Q3_WordsBank.GetInstance();
        fileWordsCounter=new Q3_FileWordsCounter("input_Q3a.txt",wordsBank);
        wrong_fileWordsCounter=new Q3_FileWordsCounter("not_a_file_name.txt",wordsBank);
    }

    public void tearDown() throws Exception {
    }

    @Test
    public void testRun_a_wrongFileName()
    {
        wrong_fileWordsCounter.run();
        String status=wordsBank.GetDisplayStatusString();
        assertEquals(0,wordsBank.GetTotalNumberOfWords());
    }

    public void testRun_b_firstBasicTest() {
        fileWordsCounter.run();
        String status=wordsBank.GetDisplayStatusString();
        assertEquals(3,status.split("\n").length);
        assertEquals("aa : 200",status.split("\n")[0]);
        assertEquals(200,wordsBank.GetWordAppearances("aa"));
    }



}