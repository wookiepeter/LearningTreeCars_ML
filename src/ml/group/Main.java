package ml.group;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        ID3TreeLearner treeLearner = new ID3TreeLearner();

        treeLearner.buildTree();
    }
}
