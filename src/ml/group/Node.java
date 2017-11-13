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


    // TODO make constructor for root node!
    public Node(ArrayList<CarData> data, DataTranslationTable table, int filteredAttribute, int filteredValue)
    {
        nodeData = data;
        this.filteredAttribute = filteredAttribute;
        this.filteredValue = filteredValue;

        entropy = computeEntropyForDataList(nodeData, table);

        // TODO if necessary --> make this a leaf!

        float[] gains = new float[table.getNumberOfAttributes()];
        int highestGainIndex = -1;
        float highestGain = -1;
        for(int i = 0; i < gains.length; i++)
        {
            gains[i] = computeGainForAttribute(i, table);
            if(gains[i] > highestGain)
            {
                highestGain = gains[i];
                highestGainIndex = i;
            }
        }
        if(highestGain < 0 || highestGainIndex < 0)
            System.out.println("Stuff is not working yet");

        childNodes = new ArrayList<Node>();
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
            childNodes.add(new Node(childDatas.get(i), table, highestGainIndex, i));
        }
    }

    float computeEntropyForDataList(ArrayList<CarData> data, DataTranslationTable table)
    {
        int[] split = new int[table.getNumberOfClasses()];
        float result = 0;

        if(data.size() <= 0)
            System.out.println("No Data to compute Entropy with!");

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

    float computeGainForAttribute(int attribute, DataTranslationTable table)
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

        for(CarData cd : nodeData)
        {
            childDatas.get(cd.carAttributes[attribute]).add(cd);
        }
/*
        for(int i = 0; i < table.getNumberOfValuesForAttribute(attribute); i++)
        {
            childEntropies[i] = computeEntropyForDataList(childDatas.get(i), table);

            // TODO delete when this works!!
            /*int[] split = new int[table.getNumberOfClasses()];
            for(CarData cd : childDatas.get(i))
            {
                split[cd.carValue]++;
            }
            childEntropies[i] = 0;
            for(int j = 0; j < table.getNumberOfClasses(); j++)
            {
                float pj  = (split[j] / (float)childDatas.get(j).size());
                childEntropies[i] += - pj * (Math.log(pj) / Math.log(table.getNumberOfClasses()));
            }
        }
*/
        gain = entropy;
        for(int i = 0; i < table.getNumberOfValuesForAttribute(attribute); i++)
        {
            gain -= (childDatas.get(i).size() / (float) nodeData.size()) * computeEntropyForDataList(childDatas.get(i), table);
        }

        return gain;
    }
}
