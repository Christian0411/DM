import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        ArrayList<DataTuple> tuples = new ArrayList<DataTuple>();
        tuples.add(new DataTuple("youth", "high", "false", "fair", "false"));
        tuples.add(new DataTuple("youth", "high", "false", "excellent", "false"));
        tuples.add(new DataTuple("middle_aged", "high", "false", "fair", "true"));
        tuples.add(new DataTuple("senior", "medium", "false", "fair", "true"));
        tuples.add(new DataTuple("senior", "low", "true", "fair", "true"));
        tuples.add(new DataTuple("seinor", "low", "true", "excellent", "false"));
        tuples.add(new DataTuple("middle_aged", "low", "true", "excellent", "true"));
        tuples.add(new DataTuple("youth", "medium", "false", "fair", "false"));
        tuples.add(new DataTuple("youth", "low", "true", "fair", "true"));
        tuples.add(new DataTuple("senior", "medium", "true", "fair", "true"));
        tuples.add(new DataTuple("youth", "medium", "true", "excellent", "true"));
        tuples.add(new DataTuple("middle_aged", "medium", "false", "excellent", "true"));
        tuples.add(new DataTuple("middle_aged", "high", "true", "fair", "true"));
        tuples.add(new DataTuple("senior", "medium", "false", "excellent", "false"));



    }

    public Node generateDecisionTree(ArrayList<DataTuple> D, ArrayList<String> attribute_list) {

        String previousLabel = D.get(0).buysComputer;
        boolean isDifferentClass = false;
        for(DataTuple d : D) {
            if(d.buysComputer != previousLabel) {
                isDifferentClass = true;
            }
        }

        if(!isDifferentClass) return new Node(D.get(0).buysComputer);

        if(attribute_list.isEmpty()) {
            return new Node(majorityClass(D));
        }

        // attribute selection method

    }

    public String majorityClass(ArrayList<DataTuple> D) {
        int yes = 0;
        int no = 0;
        for(DataTuple d: D) {
            if(d.buysComputer == "true") yes++;
            else no++;
        }
        return (yes > no)? "true" : "false";
    }

    public String attributeSelection(ArrayList<DataTuple> D, ArrayList<String> attribute_list) {

    }

    public double info_class(ArrayList<DataTuple> D) {
        HashMap<String, Double> buysComputerProbablities = findProbablity(D, "buysComputer");
        double total = 0;
        for (Double prob : buysComputerProbablities.values()) {
            total += prob * (Math.log(prob) / Math.log(2));
        }
        return 0 - total;

    }

    public Double info_A(ArrayList<DataTuple> D, String attribute) {
        ArrayList<DataTuple> subset = new ArrayList<DataTuple>();
        for(DataTuple d: D) {
            if()

        double total = 0;
        for(DataTuple d : D){

        }
    }

    public HashMap<String, HashMap<String, Double>> findProbablity(ArrayList<DataTuple> D, String attribute) {
        double total = D.size();
        // hold map with all possible
        HashMap<String, Integer> occurence_map = new HashMap<String, Integer>();
            HashMap<String, HashMap<String, Double>> values =  new HashMap<String, HashMap<String, Double>>();
        for(DataTuple d: D) {

            if(!values.containsKey(d.getAttributeByName(attribute))) {
                occurence_map.put(d.getAttributeByName(attribute), 1);
            }
            else {
                occurence_map.put(d.getAttributeByName(attribute), occurence_map.get(d.getAttributeByName(attribute)) + 1);
            }
        }

        // update with probablities
        for(DataTuple d: D) {
            HashMap<String, Double> probababilityHashMap= new HashMap<String, Double>();
            probababilityHashMap.put("prob", occurence_map.get(d.getAttributeByName(attribute)) / total );
            int positive_count = 0;
            int negative_count = 0;
            for(Integer occurence: occurence_map.values()) {
                if(d.getAttributeByName("buysComputer") == "true") {

                }
            }

            values.put(d.getAttributeByName(attribute), probababilityHashMap);
        }
        return values;
    }

}

 class DataTuple {
    public String age, credit_rating, income, buysComputer, student;

    

    public DataTuple(String age, String income, String student, String credit_rating, String buysComputer){
        this.age = age;
        this.student = student;
        this.credit_rating = credit_rating;
        this.income = income;
        this.buysComputer = buysComputer;
    }

    public String getAttributeByName(String attribute) {

        if(attribute.equals("age")) return this.age;

        if(attribute.equals("student")) return this.student;

        if(attribute.equals("credit_rating")) return this.credit_rating;

        if(attribute.equals("income")) return this.income;

        if(attribute.equals("buysComputer")) return this.buysComputer;

        else return "ERROR";
    }

}

 class Node{
    String attribute;
    ArrayList<Node> children;

    public Node(String attribute){
        children = new ArrayList<Node>();
        this.attribute = attribute;
    }

     public Node(boolean attribute){
         children = new ArrayList<Node>();
         if(attribute) this.attribute = "true"; else this.attribute = "false";
     }

    public void addChild(Node node){
        children.add(node);
    }
}

