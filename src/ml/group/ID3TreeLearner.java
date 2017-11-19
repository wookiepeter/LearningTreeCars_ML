package ml.group;

import org.w3c.dom.Document;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.ArrayList;

public class ID3TreeLearner {

    DataTranslationTable table;
    ArrayList<CarData> completeDataList;

    Node root;

    public ID3TreeLearner() {
        table = new DataTranslationTable();
        completeDataList = readCarDataFromFile();
    }

    public void buildTree() throws Exception{
        //prepare a document for xml output
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
        //creating a new instance of a DOM to build a DOM tree.
        Document doc = docBuilder.newDocument();

        //main part: build tree recursively!
        root = new Node(completeDataList, table, doc);

        //TransformerFactory instance is used to create Transformer objects
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        //create String from xml tree
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result) ;
        String xmlString = sw.toString();

        //write data into xml File
        File file = new File("CarDataTree.xml");
        BufferedWriter bw = new BufferedWriter
                (new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();

        //print xml
        System.out.println("Xml file created is :\n" + xmlString);
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
