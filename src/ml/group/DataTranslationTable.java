package ml.group;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class DataTranslationTable {

    String[] attributeNames;
    String[] carValues;

    HashMap<Integer, String[]> attributeValueMap;

    public DataTranslationTable()
    {
//        carValues = new String[] {"unacc", "acc", "good", "vgood"};
        attributeNames = new String[]{"buying", "maint", "doors", "persons", "lug_boot", "safety"};

        attributeValueMap = new HashMap<>();
        attributeValueMap.put(0, new String[]{"vhigh", "high", "med", "low"});
        attributeValueMap.put(1, new String[]{"vhigh", "high", "med", "low"});
        attributeValueMap.put(2, new String[]{"2", "3", "4", "5more"});
        attributeValueMap.put(3, new String[]{"2", "4", "more"});
        attributeValueMap.put(4, new String[]{"small", "med", "big"});
        attributeValueMap.put(5, new String[]{"low", "med", "high"});
    }

    public CarData createCarDataForString(String input)
    {
        input = input.trim();
        String[] valueStrings = input.split(",");
        int[] attributeValues = new int[6];
        for(int i = 0; i < attributeValues.length; i++)
        {
            attributeValues[i] = findIndexInStringArray(valueStrings[i], attributeValueMap.get(i));
            if(attributeValues[i] < 0)
                System.out.println("Did not find value in attributes");
        }
        return new CarData( findIndexInStringArray(valueStrings[valueStrings.length-1], carValues),attributeValues);
    }

    int findIndexInStringArray(String s, String[] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            if(s == arr[i]) return i;
        }
        return -1;
    }

    int getNumberOfClasses()
    {
        return carValues.length;
    }

    int getNumberOfAttributes()
    {
        return attributeValueMap.size();
    }

    int getNumberOfValuesForAttribute(int attribute)
    {
        return attributeValueMap.get(attribute).length;
    }
}
