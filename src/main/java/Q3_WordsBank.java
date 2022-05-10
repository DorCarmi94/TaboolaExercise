import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * (Class description)
 * Class to manage all words count.
 * A bank of words, updated simultaneously, with amount of each word appearances.
 */
public class Q3_WordsBank {

    private static Q3_WordsBank instance=new Q3_WordsBank();

    public static Q3_WordsBank GetInstance(){return instance;}
    public java.util.concurrent.ConcurrentHashMap<String, LongAdder> wordsCount;

    /**
     * (Method description)
     * * Boolean metho to check if a word already exists in the hash map
     * @param   word: the word to check its existence
     */
    public boolean WordExists(String word)
    {
        String convWord=word.toLowerCase();
        return this.wordsCount.containsKey(convWord);
    }

    /**
     * (Method description)
     * * Increment the amount of appearances of a word in the words bank.
     * If the word doesn't exist, create first apperance in the words bank.
     * If the word does exist, add 1 to the number of its appearances.
     * @param   word: the word to be added
     */
    public void IncWord(String word)
    {
        boolean shouldIncExistingWord=false;
        String convWord=word.toLowerCase();
        if(!this.wordsCount.containsKey(convWord)) {
            synchronized (this.wordsCount) {
                if (!this.wordsCount.containsKey(convWord)) {
                    wordsCount.put(convWord, new LongAdder());
                    wordsCount.get(convWord).increment();
                }
                else
                {
                    shouldIncExistingWord=true;
                }
            }
        }
        else
        {
            shouldIncExistingWord=true;
        }

        if(shouldIncExistingWord)
        {
            IncExistingWord(convWord);
        }
    }

    /**
     * (Method description)
     * * Increment the amount of an existing
     * If the word doesn't exist, create first apperance in the words bank.
     * If the word does exist, add 1 to the number of its appearances.
     * @param   word: the word to be added
     */
    private void IncExistingWord(String word)
    {
        String convWord=word.toLowerCase();
        LongAdder longAdder=wordsCount.get(convWord);
        synchronized (longAdder)
        {
            longAdder.increment();
        }
    }

    private Q3_WordsBank()
    {
        this.wordsCount=new ConcurrentHashMap<>();
    }

    /**
     * (Method description)
     * * Prints the words bank, amount of appearances counted for each word.
     */
    public void displayStatus() {
        System.out.println(GetDisplayStatusString());
    }

    /**
     * (Method description)
     * * Get the string to be printed for display status:
     * The amount of appearances for each word appeared
     * @return   string of display status
     */
    public String GetDisplayStatusString(){
        StringBuilder displayStatus = new StringBuilder();
        synchronized (wordsCount) {
            for (String word :
                    this.wordsCount.keySet()) {
                displayStatus.append(word + " : " + wordsCount.get(word) + "\n");
            }
            displayStatus.append("\n** total: " + GetTotalNumberOfWords());
        }
        return displayStatus.toString();
    }

    /**
     * (Method description)
     * * Returns the amount of appearances for a requested word.
     * If it appeared, returns the amount from the hash map
     * Else, returns zero.
     * @return   string of display status
     */
    public int GetWordAppearances(String word)
    {
        LongAdder wordCount;
        String convWord=word.toLowerCase();
        if(this.wordsCount.containsKey(convWord))
        {
            wordCount=this.wordsCount.get(convWord);
            synchronized (wordCount)
            {
                return wordCount.intValue();
            }
        }
        else {
            return 0;
        }
    }

    /**
     * (Method description)
     * * Returns the total amount of all words appearances
     * @return   int summary of all words appearances
     */
    public int GetTotalNumberOfWords()
    {
        int amountOfWords=0;
        synchronized (wordsCount)
        {
            for (String word:
                 this.wordsCount.keySet()) {
                amountOfWords+=wordsCount.get(word).intValue();
            }
        }
        return amountOfWords;
    }
}
