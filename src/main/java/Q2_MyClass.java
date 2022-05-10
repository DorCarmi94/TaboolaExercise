import java.util.Date;
import java.util.List;

public class Q2_MyClass {
    private Date time;
    String name;
    private List<Long> numbers;
    private List<String> strings;

    public Q2_MyClass(Date time, String name,List<Long> numbers,List<String> strings)
    {
        this.time=time;
        this.name=name;
        this.numbers=numbers;
        this.strings=strings;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof Q2_MyClass)
        {
            return name.equals(((Q2_MyClass)obj).name);
        }
        return false;
    }

    public String toString()
    {
        String out=name;
        for(long item: numbers)
        {
            out+= " "+ item;
        }
        return out;
    }
    public void removeString(String str)
    {
        for (int i = 0; i < strings.size(); i++) {
            if(strings.get(i).equals(str))
            {
                strings.remove(i);
            }
        }
    }

    public boolean containsNumber(long number)
    {
        for(long num:numbers)
        {
            if(num==number)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isHistoric()
    {
        return time.before(new Date());
    }
}
