import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Q1_SevenSegmentTest extends TestCase {

    private Q1_SevenSegment q1_sevenSegment;
    public void setUp() throws Exception {
        super.setUp();
        q1_sevenSegment=new Q1_SevenSegment();
    }

    public void tearDown() throws Exception {
    }

    public void testGetDigitFrom7SegmentLine_WrongValues() {
        assertTrue(Q1_SevenSegmentConfiguration.isConfigured());
        Result result=q1_sevenSegment.GetDigitFrom7SegmentLine(new String[0]);
        assertFalse(result.isOk());
        result= q1_sevenSegment.GetDigitFrom7SegmentLine(null);
        assertFalse(result.isOk());
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(new String[5]);
        assertFalse(result.isOk());

        String[] eight_zeros=new String[]{
                " _  _  _  _  _  _  _  _ ",
                "| || || || || || || || |",
                "|_||_||_||_||_||_||_||_|"
        };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(eight_zeros);
        assertFalse(result.isOk());
    }

    public void testGetDigitFrom7SegmentLine_CorrectValues() {
        assertTrue(Q1_SevenSegmentConfiguration.isConfigured());
        String[] zeros=new String[]{
                " _  _  _  _  _  _  _  _  _ ",
                "| || || || || || || || || |",
                "|_||_||_||_||_||_||_||_||_|"
        };

        Result result=q1_sevenSegment.GetDigitFrom7SegmentLine(zeros);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("000000000"));

        String[] ones=new String[]
                {
                  "                           ",
                  "  |  |  |  |  |  |  |  |  |",
                  "  |  |  |  |  |  |  |  |  |"
                };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(ones);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("111111111"));

        String[] twos=new String[]
                {
                        " _  _  _  _  _  _  _  _  _ ",
                        " _| _| _| _| _| _| _| _| _|",
                        "|_ |_ |_ |_ |_ |_ |_ |_ |_ "
                };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(twos);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("222222222"));

        String[] threes=new String[]
                {
                        " _  _  _  _  _  _  _  _  _ ",
                        " _| _| _| _| _| _| _| _| _|",
                        " _| _| _| _| _| _| _| _| _|"
                };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(threes);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("333333333"));

        String[] fours=new String[]
                {
                        "                           ",
                        "|_||_||_||_||_||_||_||_||_|",
                        "  |  |  |  |  |  |  |  |  |"
                };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(fours);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("444444444"));

        String[] fives=new String[]
                {
                        " _  _  _  _  _  _  _  _  _ ",
                        "|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
                        " _| _| _| _| _| _| _| _| _|"
                };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(fives);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("555555555"));

        String[] sixes=new String[]
                {
                        " _  _  _  _  _  _  _  _  _ ",
                        "|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
                        "|_||_||_||_||_||_||_||_||_|"
                };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(sixes);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("666666666"));

        String[] sevens=new String[]
                {
                        " _  _  _  _  _  _  _  _  _ ",
                        "  |  |  |  |  |  |  |  |  |",
                        "  |  |  |  |  |  |  |  |  |"
                };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(sevens);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("777777777"));

        String[] eights=new String[]
                {
                        " _  _  _  _  _  _  _  _  _ ",
                        "|_||_||_||_||_||_||_||_||_|",
                        "|_||_||_||_||_||_||_||_||_|"
                };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(eights);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("888888888"));

        String[] nines=new String[]
                {
                        " _  _  _  _  _  _  _  _  _ ",
                        "|_||_||_||_||_||_||_||_||_|",
                        " _| _| _| _| _| _| _| _| _|"
                };
        result=q1_sevenSegment.GetDigitFrom7SegmentLine(nines);
        assertTrue(result.isOk());
        assertTrue(result.GetValue().equals("999999999"));
    }


    public void testMain_AllNumbersCorrect() {
        String[] args=new String[]{"input_Q1a.txt"};
        Q1_SevenSegment.main(args);
        BufferedReader reader_myoutput;
        BufferedReader reader_originalOutput;
        try{
            String outputname=Q1_SevenSegment.GetOutputFilename(args);
            reader_myoutput=new BufferedReader(new FileReader(outputname));
            reader_originalOutput=new BufferedReader(new FileReader("./output/original/output_Q1a.txt"));

            String myLine=reader_myoutput.readLine();
            String originalLine=reader_originalOutput.readLine();
            boolean checkingEqualLines=true;
            while (checkingEqualLines && myLine!=null && originalLine!=null)
            {
                if(!myLine.equals(originalLine))
                {
                    checkingEqualLines=false;
                }
                else
                {
                    myLine=reader_myoutput.readLine();
                    originalLine=reader_originalOutput.readLine();
                }
            }

            if(myLine!=null || originalLine!=null)
            {
                fail("Number of lines don't match");
            }

            assertTrue(checkingEqualLines);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }

    }

    public void testMain_SomenumbersIncorrect() {
        String[] args=new String[]{"input_Q1b.txt"};
        Q1_SevenSegment.main(args);
        BufferedReader reader_myoutput;
        BufferedReader reader_originalOutput;
        try{
            String outputname=Q1_SevenSegment.GetOutputFilename(args);
            reader_myoutput=new BufferedReader(new FileReader(outputname));
            reader_originalOutput=new BufferedReader(new FileReader("./output/original/output_Q1b.txt"));

            String myLine=reader_myoutput.readLine();
            String originalLine=reader_originalOutput.readLine();
            boolean checkingEqualLines=true;
            while (checkingEqualLines && myLine!=null && originalLine!=null)
            {
                if(!myLine.equals(originalLine))
                {
                    checkingEqualLines=false;
                }
                else
                {
                    myLine=reader_myoutput.readLine();
                    originalLine=reader_originalOutput.readLine();
                }
            }

            if(myLine!=null || originalLine!=null)
            {
                fail("Number of lines don't match");
            }
            assertTrue(checkingEqualLines);
            reader_myoutput.close();
            reader_originalOutput.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }

    }

}