import java.util.LinkedList;

/**
 * (Class description)
 * Class to run the words counter program.
 * Loads the words from the files and run the conversion on each, using the file words counter class.
 */
public class Q3_WordsCounter {

    private final Q3_WordsBank wordsBank;

    public Q3_WordsCounter()
    {
        wordsBank=Q3_WordsBank.GetInstance();
    }

    /**
     * (Method description)
     * * loads and runs the files received, and counts simultaneously the number of words in all files.
     * @param   fileNames: array of strings, names of files to load. Each file will be words counted,
     *                   in a separate thread, and will be run simultaneously.
     */
    public void load(String ...fileNames) throws InterruptedException {
        LinkedList<Q3_FileWordsCounter> filesWordCounter= new LinkedList<>();

        for (String fileName :
                fileNames) {
            Q3_FileWordsCounter newFileWordsCounter=new Q3_FileWordsCounter(fileName,wordsBank);
            filesWordCounter.add(newFileWordsCounter);
            newFileWordsCounter.start();
        }

        for (Q3_FileWordsCounter file:
                filesWordCounter) {
            file.join();
        }
    }

    /**
     * (Method description)
     * * main method to run the program. Receives the names of input files as params in string array,
     * prints the total number of all words in the files.
     * @param   args: array of strings, each of a file name in the ./data/ directory
     */
    public static void main(String[] args)
    {
        //long startTime=System.nanoTime();
        Q3_WordsCounter wordsCounter=new Q3_WordsCounter();
        try {
            wordsCounter.load(args);
            wordsCounter.displayStatus();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * (Method description)
     * * method used to print the result: the total number of all words in the loaded files.
     */
    private void displayStatus() {
        wordsBank.displayStatus();
    }
}


