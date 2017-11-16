package ml.group;

import java.util.ArrayList;

public class Node {

    // remaining Cardata in this Node
    ArrayList<CarData> nodeData;

    // is the value of the car --> leafs should classify this
    // -1 for not assigned
    int assignedClass;

    // attribute that was used for decision in parentNode
    int previousFilteredAttribute;
    // value of that attribute that was to assigned to this Node
    int filteredValue;

    // entropy of this node
    float entropy;

    ArrayList<Node> childNodes;

    // TODO use this for initialization
    /**
     * Constructor for Root-Node!
     * @param data
     * @param table
     */
    public Node(ArrayList<CarData> data, DataTranslationTable table)
    {
        this(data, table, -1, -1);
    }

    /**
     * Constructor for ChildNodes!
     * @param data
     * @param table
     * @param previousFilteredAttribute
     * @param filteredValue
     */
    public Node(ArrayList<CarData> data, DataTranslationTable table, int previousFilteredAttribute, int filteredValue)
    {
        nodeData = data;
        this.previousFilteredAttribute = previousFilteredAttribute;
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
            childDatas.get(cd.carAttributes[highestGainIndex]).add(cd);
        }

        // TODO do not create child node if it would be empty
        for(int i = 0; i < table.getNumberOfValuesForAttribute(highestGainIndex); i++) {
            if(childDatas.get(i).size() < 1)
                continue;
            result.add(new Node(childDatas.get(i), table, highestGainIndex, i));
        }
        return result;
    }

    float computeEntropyForDataList(ArrayList<CarData> data, DataTranslationTable table)
    {
        int[] classCounter = new int[table.getNumberOfClasses()];
        float result = 0;

        // TODO do stuff if list is empty
        if(data.size() <= 0) {
            System.out.println("No Data to compute Entropy with!");
            return 0;
        }

        // iterate over car Data and count each number of car-values using split-array
        for(CarData cd : data)
        {
            classCounter[cd.carValue]++;
        }

        // compute entropy
        for(int j = 0; j < classCounter.length; j++)
        {
            float pj  = (classCounter[j] / (float)data.size());
            result += (- pj * (((pj == 0)? 1 : Math.log(pj)) / Math.log(classCounter.length)));
        }

        return result;
    }

    float computeGainForAttribute(ArrayList<CarData> data, int attribute, DataTranslationTable table)
    {
        float gain = 0;

        // TODO compute entropy for current attribute
        ArrayList<ArrayList<CarData>> childDatas = new ArrayList<>();
        for(int i = 0; i < table.getNumberOfValuesForAttribute(attribute); i++)
        {
            childDatas.add(new ArrayList<>());
        }

        // sort Cardata into corresponding list depending on the value of the given attribute!
        for(CarData cd : data)
        {
            childDatas.get( cd.carAttributes[attribute] ).add(cd);
        }

        // S is the current Set, A is the Attribute we sort of, v is a Value of A
        //                                               |S_v|
        // Gain(S, A) = Entropy(S) -    SUM             ------- Entropy(S)
        //                       v element Values(A)      |S|
        gain = this.entropy;
        for(int i = 0; i < table.getNumberOfValuesForAttribute(attribute); i++)
        {
            gain -= (childDatas.get(i).size() / (float) data.size()) * computeEntropyForDataList(childDatas.get(i), table);
        }

        return gain;
    }
}
