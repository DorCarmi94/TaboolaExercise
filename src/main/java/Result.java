public class Result<T> {

    private boolean isOk;
    private String error;
    private T value;

    public Result(T value)
    {
        this.isOk=true;
        this.value=value;
        error="";
    }

    public Result(String error,boolean isError)
    {
        isOk=false;
        this.error=error;
    }

    public boolean isOk()
    {
        return isOk;
    }

    public T GetValue()
    {
        if(this.isOk)
        {
            return value;
        }
        else {
            return null;
        }
    }

    public String GetError()
    {
        return error;
    }





}
