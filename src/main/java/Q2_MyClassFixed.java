import java.time.LocalDateTime;
import java.util.HashSet;

public class Q2_MyClassFixed {
    private LocalDateTime time;
    private String name;

    //@CHANGE: replace use of numbers list with use of HashSet to locate and delete items faster
    private HashSet<Long> numbers;

    //@CHANGE: replace use of strings list with use of HashSet to locate and delete items faster
    private HashSet<String> strings;

    public Q2_MyClassFixed(LocalDateTime time, String name, HashSet<Long> numbers, HashSet<String> strings)
    {
        this.time=time;
        this.name=name;
        this.numbers=numbers;
        this.strings=strings;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof Q2_MyClassFixed)
        {
            return name.equals(((Q2_MyClassFixed)obj).name);
        }
        return false;
    }

    //@CHANGE: The use of string builder is much better for memory use. Instead of saving a new string every time
    //because string is immutable, we use a class that knows how to aggregate strings and create string from all of it.
    public String toString()
    {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(name);
        for(long item: numbers)
        {
            stringBuilder.append( " "+ item);
        }
        return stringBuilder.toString();
    }

    //@CHANGE: Instead of locating the item in the list and then remove it, just use the remove method of the hash set.
    public void removeString(String str)
    {
        if(this.strings.contains(str)) {
            this.strings.remove(str);
        }
    }
    //@CHANGE: Instead of locating the item in the list, just use the contains method of the hash set.
    public boolean containsNumber(long number)
    {
        return this.numbers.contains(number);
    }
    //@CHANGE: Use of LocalDateTime instead of Date class, because the previous use wasn't correct,
    //the new Date created didn't hold now's date, and the comparison was wrong.
    public boolean isHistoric()
    {
        return time.isBefore(LocalDateTime.now());
    }
}
