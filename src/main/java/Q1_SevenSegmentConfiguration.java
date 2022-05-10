import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton:
 * Class to contain all configuration needed for the operation of SevenSegment class.
 * The data is loaded from a configuration json file.
 * The class will hold the dictionaries to the expected characters in each part of the seven segment letter,
 * and a mapping from the 7 segment "on" and "off" bits to the digit it represents
 */
public class Q1_SevenSegmentConfiguration {
    final static int NUMBER_OF_DIGITS_IN_LINE=9;
    final static int NUMBER_OF_LINES_FOR_DIGIT=3;
    final static int NUMBER_OF_CHARACTERS_FOR_DIGIT_LINE=3;
    public static boolean isConfigured()
    {
        return instance!=null;
    }

    private static Q1_SevenSegmentConfiguration instance=null;
    private static Dictionary<Integer,Integer> BitmapToDigitMap;
    private static Dictionary<Integer,Character> SevenSegPositionToExpectedChar;

    public static Q1_SevenSegmentConfiguration GetInstance()
    {
        if(instance==null)
        {
            loadConfiguration();
            return instance;
        }
        else
        {
            return instance;
        }
    }

    private Q1_SevenSegmentConfiguration()
    {
        SevenSegPositionToExpectedChar=new Hashtable<>();
        BitmapToDigitMap=new Hashtable<>();
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("./conf/conf.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONObject digitsBitMap = (JSONObject) jsonObject.get("digitsBitMap");
            List<String> digitsBitMap_list= (List<String>) digitsBitMap.keySet().stream().sorted().collect(Collectors.toList());
            int digitCounter=0;

            for (int i = 0; i <digitsBitMap_list.stream().count(); i++) {
                String key = digitsBitMap_list.get(i);
                BitmapToDigitMap.put(Integer.parseInt(digitsBitMap.get(key).toString(), 2), digitCounter);
                digitCounter++;
            }



            JSONObject sevenSegLocationToCorrectChar = (JSONObject) jsonObject.get("sevenSegLocationToCorrectChar");
            digitCounter = 0;
            List<String> charsConversion_list= (List<String>) sevenSegLocationToCorrectChar.keySet().stream().sorted().collect(Collectors.toList());

            for (int i = 0; i < charsConversion_list.stream().count(); i++) {
                String nextCharStringKey = charsConversion_list.get(i);
                String nextCharString = sevenSegLocationToCorrectChar.get(nextCharStringKey).toString();
                char ch = 0;
                if (nextCharString.length() > 0) {
                    ch = nextCharString.charAt(0);
                }
                SevenSegPositionToExpectedChar.put(digitCounter, ch);
                digitCounter++;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            instance = null;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            instance = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            instance=null;
        }
    }

    private static void loadConfiguration() {
        instance=new Q1_SevenSegmentConfiguration();
    }

    /**
     * (Method description)
     * @param   i: Which line from the seven segment letter line
     * @param   j: Which character in the line
     * @return  The expected character that should be in that position of the letter, if it is "on"
     */
    public Character getSevenSegPositionToExpectedChar(int i,int j) {
        return SevenSegPositionToExpectedChar.get(i*NUMBER_OF_CHARACTERS_FOR_DIGIT_LINE+j);
    }

    /**
     * (Method description)
     * @param   digitBitMap: integer number, created from the bit map representing which segments from the
     *                     seven segments are "on"
     * @return  The Interger of the digit that this bitmap represents
     */
    public Integer getBitMapToDigitMap(int digitBitMap) {
        return BitmapToDigitMap.get(digitBitMap);
    }
}
