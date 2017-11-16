package ml.group;

import java.util.ArrayList;
import java.util.List;

public class Node {

    ArrayList<CarData> nodeData;

    // -1 for not assigned
    int assignedClass;

    int filteredAttribute;
    int filteredValue;

    float entropy;

    ArrayList<Node> childNodes;


    /**
     * Constructor for ChildNodes!
     * @param data
     * @param table
     * @param filteredAttribute
     * @param filteredValue
     */
    public Node(ArrayList<CarData> data, DataTranslationTable table, int filteredAttribute, int filteredValue)
    {
        nodeData = data;
        this.filteredAttribute = filteredAttribute;
        this.filteredValue = filteredValue;

        entropy = computeEntropyForDataList(nodeData, table);

        // make this a leaf!
        if(entropy == 0)
        {
            if(nodeData.size() > 0)
                assignedClass = nodeData.get(0).carValue;
            System.out.println("reached a leaf! This leaf has the value " + assignedClass);
            return;
        }

        int highestGainIndex = findChildWithHighestGain(nodeData, table);

        if(highestGainIndex < 0)
            System.out.println("Stuff is not working yet");

        childNodes = splitNodeIntoChildren(highestGainIndex, table);
    }

    // TODO use this for initialization
    /**
     * Constructor for Root-Node!
     * @param data
     * @param table
     */
    public Node(ArrayList<CarData> data, DataTranslationTable table)
    {
        nodeData = data;

        entropy = computeEntropyForDataList(nodeData, table);

        // make this a leaf!
        if(entropy == 0)
        {
            if(nodeData.size() > 0)
                assignedClass = nodeData.get(0).carValue;
            System.out.println("reached a leaf! This leaf has the value " + assignedClass);
            return;
        }

        int highestGainIndex = findChildWithHighestGain(nodeData, table);

        filteredAttribute = highestGainIndex;
        // because we are Root;
        filteredValue = -1;

        if(highestGainIndex < 0)
            System.out.println("Stuff is not working yet");

        childNodes = splitNodeIntoChildren(highestGainIndex, table);

    }

    int findChildWithHighestGain(ArrayList<CarData> data, DataTranslationTable table)
    {
        float[] gains = new float[table.getNumberOfAttributes()];
        int highestGainIndex = -1;
        float highestGain = -1;
        for(int i = 0; i < gains.length; i++)
        {
            gains[i] = computeGainForAttribute(data, i, table);
            if(gains[i] > highestGain)
            {
                highestGain = gains[i];
                highestGainIndex = i;
            }
        }
        return highestGainIndex;
    }

    ArrayList<Node> splitNodeIntoChildren(int highestGainIndex, DataTranslationTable table)
    {
        ArrayList<Node> result = new ArrayList<Node>();
        ArrayList<ArrayList<CarData>> childDatas = new ArrayList<>();
        for(int i = 0; i < table.getNumberOfValuesForAttribute(highestGainIndex); i++)
        {
            childDatas.add(new ArrayList<>());
        }
        for(CarData cd : nodeData)
        {
            childDatas.get(cd.carValue).add(cd);
        }

        for(int i = 0; i < table.getNumberOfValuesForAttribute(highestGainIndex); i++) {
            result.add(new Node(childDatas.get(i), table, highestGainIndex, i));
        }
        return result;
    }

    float computeEntropyForDataList(ArrayList<CarData> data, DataTranslationTable table)
    {
        int[] split = new int[table.getNumberOfClasses()];
        float result = 0;

        if(data.size() <= 0) {
            System.out.println("No Data to compute Entropy with!");
            return 0;
        }
        for(CarData cd : data)
        {
            split[cd.carValue]++;
        }

        for(int j = 0; j < table.getNumberOfClasses(); j++)
        {
            float pj  = (split[j] / (float)data.size());
            result += (- pj * (Math.log(pj) / Math.log(table.getNumberOfClasses())));
        }

        return result;
    }

    float computeGainForAttribute(ArrayList<CarData> data, int attribute, DataTranslationTable table)
    {
        float gain = 0;
        int[] classSplitForAttribute = new int[table.getNumberOfClasses()];

        float[] childEntropies = new float[table.getNumberOfValuesForAttribute(attribute)];

        for(int i = 0; i < classSplitForAttribute.length; i++)
            classSplitForAttribute[i] = 0;

        // TODO compute entropy for current attribute
        ArrayList<ArrayList<CarData>> childDatas = new ArrayList<>();
        for(int i = 0; i < table.getNumberOfValuesForAttribute(attribute); i++)
        {
            childDatas.add(new ArrayList<>());
        }

        for(CarData cd : data)
        {
            childDatas.get(cd.carAttributes[attribute]).add(cd);
        }

        gain = entropy;
        for(int i = 0; i < table.getNumberOfValuesForAttribute(attribute); i++)
        {
            gain -= (childDatas.get(i).size() / (float) data.size()) * computeEntropyForDataList(childDatas.get(i), table);
        }

        return gain;
    }
}
