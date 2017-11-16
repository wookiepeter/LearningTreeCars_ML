package ml.group;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ID3TreeLearner {

    DataTranslationTable table;

    ArrayList<CarData> completeDataList;

    Node root;

    public ID3TreeLearner() {
        table = new DataTranslationTable();
        completeDataList = readCarDataFromFile();

        // builds tree recursively!
        root = new Node(completeDataList, table);

    }

    public void buildTree()
    {

    }

    public ArrayList<CarData> readCarDataFromFile()
    {
        ArrayList<CarData> result = new ArrayList<>();
        try {
            File file = new File("car.c45-names");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // stringBuffer.append(line);
                // stringBuffer.append("\n");
                if(line.length() > 0){
                    result.add(table.createCarDataForString(line));
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
