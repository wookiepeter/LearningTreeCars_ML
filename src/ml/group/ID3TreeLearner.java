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
    }

    public void buildTree()
    {
        // builds tree recursively!
        root = new Node(completeDataList, table);
    }

    public ArrayList<CarData> readCarDataFromFile()
    {
        ArrayList<CarData> result = new ArrayList<>();
        try {
            File.listRoots();
            File file = new File("data/car.data");
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
