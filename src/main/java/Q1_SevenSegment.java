import java.io.*;

/**
 * (Class description)
 * Class to control and convert the lines of the seven segment letters, to convert it to regular decimal digits
 */
public class Q1_SevenSegment {

    private final Q1_SevenSegmentConfiguration configuration;

    public Q1_SevenSegment()
    {
        configuration=Q1_SevenSegmentConfiguration.GetInstance();
    }

    /**
     * (Method description)
     * * Converts one line of seven segment letters to decimal number, represented in string
     * @param   digitsLine: string lines, representing one line of seven segment digits.
     *                    Should be according to the configuration information
     * @return  Result with a string, representing the output decimal number, converted from the seven segment line.
     *          Returns error if something didn't work
     */
    public Result<String> GetDigitFrom7SegmentLine(String[] digitsLine)
    {
        if(!Q1_SevenSegmentConfiguration.isConfigured())
        {
            return new Result<>("Error while reading configuration",true);
        }
        if(digitsLine ==null || digitsLine.length<=0)
        {
            return new Result<>("Wrong line array input", true);
        }

        if(digitsLine.length%Q1_SevenSegmentConfiguration.NUMBER_OF_LINES_FOR_DIGIT!=0)
        {
            return new Result<>("Wrong partition of the lines",true);
        }

        if(digitsLine[0].length()<Q1_SevenSegmentConfiguration.NUMBER_OF_DIGITS_IN_LINE*Q1_SevenSegmentConfiguration.NUMBER_OF_CHARACTERS_FOR_DIGIT_LINE)
        {
            return new Result<>("Wrong length of line",true);
        }

        try {

            String[] digitlines = new String[Q1_SevenSegmentConfiguration.NUMBER_OF_LINES_FOR_DIGIT];
            StringBuilder convertedNumber = new StringBuilder();
            boolean isWrongSyntax = false;
            for (int i = 0; i < Q1_SevenSegmentConfiguration.NUMBER_OF_DIGITS_IN_LINE; i++) {
                for (int j = 0; j < Q1_SevenSegmentConfiguration.NUMBER_OF_LINES_FOR_DIGIT; j++) {
                    digitlines[j] = digitsLine[j].substring(i * Q1_SevenSegmentConfiguration.NUMBER_OF_CHARACTERS_FOR_DIGIT_LINE, i * Q1_SevenSegmentConfiguration.NUMBER_OF_CHARACTERS_FOR_DIGIT_LINE
                            + Q1_SevenSegmentConfiguration.NUMBER_OF_CHARACTERS_FOR_DIGIT_LINE);
                }
                Result<String> resultDigit = this.ConvertOneDigit(digitlines);
                if (!resultDigit.isOk()) {
                    convertedNumber.append("?");
                    isWrongSyntax = true;
                } else {
                    convertedNumber.append(resultDigit.GetValue());
                }
            }

            if (isWrongSyntax) {
                convertedNumber.append(" ILLEGAL");
            }

            return new Result<>(convertedNumber.toString());
        }
        catch (Exception ex)
        {
            return new Result<>(ex.getMessage(),true);
        }
    }

    /**
     * (Method description)
     * Converts one seven segment letter to decimal digit string
     * @param   currDigit: string lines, representing one seven segment letter
     * @return  Result with a string, representing the output decimal digit, converted from the seven segment letter
     *          Returns error if something didn't work
     */
    private Result<String> ConvertOneDigit(String[] currDigit) {
        int digitBitMap=0x00;
        for (int i = 0; i <currDigit.length; i++) {
            for (int j = 0; j < currDigit[i].length(); j++) {
                if(currDigit[i].charAt(j)!=' ')
                {

                    if(configuration.getSevenSegPositionToExpectedChar(i,j)!=null  &&
                            currDigit[i].charAt(j)==configuration.getSevenSegPositionToExpectedChar(i,j))
                    {
                        digitBitMap|=0x01<<(i*Q1_SevenSegmentConfiguration.NUMBER_OF_CHARACTERS_FOR_DIGIT_LINE+j);
                    }
                    else {
                        return new Result<>("Wrong digit syntax",true);
                    }
                }

            }
        }

        if(configuration.getBitMapToDigitMap(digitBitMap)==null)
        {
            return new Result<>("Something went wrong",true);
        }

        Integer toRet=configuration.getBitMapToDigitMap(digitBitMap);
        if(toRet!=null)
        {
            return new Result<>(toRet.toString());
        }
        else
        {
            return new Result<>("Wrong digit syntax",true);
        }
    }

    /**
     * (Method description)
     * main method to run, to convert input file with lines of seven segment letters to decimal digits string.
     * @param   args: arg[0] should contain the name of the input data file, inside the "data" directory
     */
    public static void main(String[] args)
    {
        Q1_SevenSegment mySevenSegment= new Q1_SevenSegment();
        FileWriter myWriter;
        BufferedReader reader;
        try{
            myWriter=new FileWriter(GetOutputFilename(args));
            reader=new BufferedReader(new FileReader(GetInputFilename(args)));
            boolean shouldEndReadingLines=false;
            while(!shouldEndReadingLines)
            {
                String[] linesOfDigits= new String[Q1_SevenSegmentConfiguration.NUMBER_OF_LINES_FOR_DIGIT];
                for (int i = 0; i < Q1_SevenSegmentConfiguration.NUMBER_OF_LINES_FOR_DIGIT && !shouldEndReadingLines; i++) {
                    String line=reader.readLine();
                    if(line==null)
                    {
                        shouldEndReadingLines=true;
                    }
                    else {
                        linesOfDigits[i] =line;
                    }
                }
                ReadAnotherSeparateEmptyLine(reader);
                if(!shouldEndReadingLines) {
                    Result<String> convertedNumber=mySevenSegment.GetDigitFrom7SegmentLine(linesOfDigits);
                    if(convertedNumber.isOk())
                    {
                        myWriter.write(convertedNumber.GetValue()+"\n");
                    }
                    else {
                        System.out.println(convertedNumber.GetError());
                    }

                }

            }
            myWriter.close();
            reader.close();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * (Method description)
     * Read the empty line between each seven segment lines
     * @param   reader: the open file reader
     */
    private static void ReadAnotherSeparateEmptyLine(BufferedReader reader) {
        try {
            reader.readLine();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * (Method description)
     * Simple conversion from input file name to output file name
     * @param   args: arg[0] should contain input file name
     * @return output file name string
     */
    public static String GetOutputFilename(String[] args)
    {
        StringBuilder outputfileName=new StringBuilder("./output/output");
        if(args.length>0)
        {
            String toAppend="_"+args[0].split("_")[1];
            outputfileName.append(toAppend);
        }
        else {
            outputfileName.append(".txt");
        }
        return outputfileName.toString();
    }

    /**
     * (Method description)
     * Simple conversion from input file name to the full path to input file
     * @param   args: arg[0] should contain input file name
     * @return full path to input file, string
     */
    public static String GetInputFilename(String[] args)
    {
        StringBuilder inputfileName=new StringBuilder("./data/");
        if(args.length>0) {
            inputfileName.append(args[0]);
        }
        else
        {
            inputfileName.append("input_Q1a.txt");
        }
        return inputfileName.toString();
    }

}