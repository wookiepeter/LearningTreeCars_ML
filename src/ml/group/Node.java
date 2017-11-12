package ml.group;

import java.util.ArrayList;

public class Node {

    ArrayList<CarData> nodeData;

    // -1 for not assigned
    int assignedClass;

    int filteredAttribute;
    int filteredValue;

    float entropy;

    ArrayList<Node> childNodes;

    public Node(ArrayList<CarData> data, DataTranslationTable table)
    {
        nodeData = data;

        // TODO choose attribute for next Split and split!!

        float[] entropies = new float[table.getNumberOfAttributes()];
        for(int i = 0; i < entropies.length; i++)
        {
            entropies[i] = computeEntropy(i, table);
        }
    }

    float computeEntropy(int attribute, DataTranslationTable table)
    {
        float result = 0;
        int[] classSplitForAttribute = new int[table.getNumberOfClasses()];
        for(int i = 0; i < classSplitForAttribute.length; i++)
            classSplitForAttribute[i] = 0;

        // TODO compute entropy for current attribute


        return result;
    }
}
