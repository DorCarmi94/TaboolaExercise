import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * (Class description)
 * Runner to run a word counting process for a file.
 * The class reads all the text from a file,
 * and runs a words counting process for the whole text
 */
public class Q3_FileWordsCounter extends Thread {
    private String BasicFileExtension="./data/";
    private String FileName;
    private Q3_WordsBank WordsBank;

    public Q3_FileWordsCounter(String fileName, Q3_WordsBank wordsBank) {
        this.FileName = fileName;
        this.WordsBank = wordsBank;
    }

    /**
     * (Method description)
     * * Use the run method to assign a thread to this task.
     * The task will be to read all words from the file, and add the words amounts to the words bank.
     */
    public void run() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(BasicFileExtension+ FileName));
            String line=reader.readLine();
            while (line!=null)
            {
                String[] words=line.split(" ");
                for (String word:
                        words) {
                    WordsBank.IncWord(word);
                }
                line=reader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

