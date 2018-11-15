import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<DataTuple> tuples = new ArrayList<DataTuple>();
        tuples.add(new DataTuple("youth", "high", "false", "fair", "false"));
        tuples.add(new DataTuple("youth", "high", "false", "excellent", "false"));
        tuples.add(new DataTuple("middle_aged", "high", "false", "fair", "true"));
        tuples.add(new DataTuple("senior", "medium", "false", "fair", "true"));
        tuples.add(new DataTuple("senior", "low", "true", "fair", "true"));
        tuples.add(new DataTuple("senior", "low", "true", "excellent", "false"));
        tuples.add(new DataTuple("middle_aged", "low", "true", "excellent", "true"));
        tuples.add(new DataTuple("youth", "medium", "false", "fair", "false"));
        tuples.add(new DataTuple("youth", "low", "true", "fair", "true"));
        tuples.add(new DataTuple("senior", "medium", "true", "fair", "true"));
        tuples.add(new DataTuple("youth", "medium", "true", "excellent", "true"));
        tuples.add(new DataTuple("middle_aged", "medium", "false", "excellent", "true"));
        tuples.add(new DataTuple("middle_aged", "high", "true", "fair", "true"));
        tuples.add(new DataTuple("senior", "medium", "false", "excellent", "false"));

        ArrayList<String> attributeList = new ArrayList<String>();
        attributeList.add("age");
        attributeList.add("income");
        attributeList.add("student");
        attributeList.add("credit_rating");


        Node root = generateDecisionTree(tuples, attributeList);
        printDecisionTree(root, 0);
        System.out.println("------------------------------------------------------");
        Scanner scan = new Scanner(System.in);
        System.out.println("What is your age? (youth, middle_aged, senior)");
        String age = scan.nextLine();
        System.out.println("What is your income? (high, medium, low)");
        String income = scan.nextLine();
        System.out.println("Are you a student? (true, false)");
        String student = scan.nextLine();
        System.out.println("What is your credit rating? (fair, excellent)");
        String credit_rating = scan.nextLine();

        DataTuple userTuple = new DataTuple(age,income,student,credit_rating);
        System.out.println("User tuple:\n (" + age + ", " + income + ", " + student + ", " + credit_rating + ")");

        boolean buysComputer = traverseDecisionTree(userTuple, root);
        if(buysComputer) System.out.println("You will buy a computer");
        else System.out.println("You will NOT buy a computer");
    }

    public static boolean traverseDecisionTree(DataTuple userTuple, Node n) {
        //System.out.println(n.attribute);
        if(n.children.isEmpty())
        {
            //System.out.println(n.attribute);
            return n.attribute.equals("true");
        }
        for(Node child : n.children)
        {
            String attribute = userTuple.getAttributeByName(n.attribute);
            if(child.branchName.equals(attribute))
            {
                return traverseDecisionTree(userTuple, child);
            }

        }
        return false;
    }




    public static void printDecisionTree(Node N, int times)
    {

            ArrayList<Node> children = N.children;
            if(N.branchName != null) {
                System.out.println(tabs(times) + N.branchName + "\n" + tabs(times + 1) + N.attribute);
            } else {
                System.out.println(tabs(times + 1) + N.attribute);
            }

            for (Node child : children) {
                printDecisionTree(child, times + 2);
        }
    }
    public static String tabs(int times){
        String tabs = "";
        for(int i =0; i < times; i++){
            tabs += "---";
        }
        return tabs;
    }
    public static Node generateDecisionTree(ArrayList<DataTuple> D, ArrayList<String> attributeList) {

        String previousLabel = D.get(0).buysComputer;
        boolean isDifferentClass = false;

        for (DataTuple d : D) {
            if (d.buysComputer != previousLabel) {
                isDifferentClass = true;
            }
        }

        if (!isDifferentClass) return new Node(D.get(0).buysComputer);

        if (attributeList.isEmpty()) {
            return new Node(majorityClass(D));
        }

        String majorityClass = majorityClass(D);

        // attribute selection method
        Node N = new Node(attributeSelectionMethod(D, attributeList));
        attributeList.remove(N.attribute);
        ArrayList<String> attrValues = new ArrayList<String>();
        for(DataTuple d : D)
        {
            if (!attrValues.contains(d.getAttributeByName(N.attribute)))
            {
                attrValues.add(d.getAttributeByName(N.attribute));
            }
        }
        for(String attrValue : attrValues)
        {
            ArrayList<DataTuple> Dj = new ArrayList<DataTuple>();
            for(DataTuple d : D)
            {
                if (d.getAttributeByName(N.attribute).equals(attrValue))
                {
                    Dj.add(d);

                }
            }
            if (Dj.isEmpty())
            {
                Node n = new Node(majorityClass);
                n.setBranchName(attrValue);

                N.addChild(n);
            } else {
                Node n = generateDecisionTree(Dj, attributeList);
                n.setBranchName(attrValue);
                N.addChild(n);
            }
        }
        return N;

    }

    public static String attributeSelectionMethod(ArrayList<DataTuple> D, ArrayList<String> list)
    {
        String criterion = "";
        double highest = Integer.MIN_VALUE;
        for(String attribute : list){
            AttributeStat s = new AttributeStat(attribute, D);

            double current = getInfoD((D)) - getAttributeInfoGain(s.getPostiveFreq(), s.getPercentage());
            if(current > highest) {
                highest = current;
                criterion = attribute;
            };
        }
        return criterion;
    }

    public static double getInfoD(ArrayList<DataTuple> D) {
        double freq = 0.0;
        for(DataTuple d: D) {
            if(d.getAttributeByName("buysComputer").equals("true")) {
                freq++;
            }
        }
        freq = freq / D.size();
        double comp = 1 - freq;
        return -freq*logBase2(freq) - comp*logBase2(comp);
    }





    public static double getAttributeInfoGain(HashMap<String, Double> positiveFreq, HashMap<String, Double> percentage){
        double sum = 0;
        for(Map.Entry<String, Double> x : percentage.entrySet()) {
            double freq = positiveFreq.get(x.getKey());
            double complementFreq = 1 - freq;
            double perc = x.getValue();
            sum += (perc*(-freq*logBase2(freq)-(complementFreq * logBase2(complementFreq))));
        }

        return sum;
    }

    public static double logBase2(double value){
        if(value == 0) {
            return 0;
        }
        else {
            return Math.log(value) / Math.log(2);
        }
    }




    public static String majorityClass(ArrayList<DataTuple> D) {
        int yes = 0;
        int no = 0;
        for (DataTuple d : D) {
            if (d.buysComputer == "true") yes++;
            else no++;
        }
        return (yes > no) ? "true" : "false";
    }


}
class Node {
    String attribute;
    String branchName;
    ArrayList<Node> children;

    public Node(String attribute) {
        children = new ArrayList<Node>();
        this.attribute = attribute;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void setBranchName(String name){
        this.branchName = name;
    }

    public String toString(){
        return this.attribute;
    }

}
class DataTuple {

    public String age, credit_rating, income, buysComputer, student;

    public DataTuple(String age, String income, String student, String credit_rating, String buysComputer) {
        this.age = age;
        this.student = student;
        this.credit_rating = credit_rating;
        this.income = income;
        this.buysComputer = buysComputer;
    }
    public DataTuple(String age, String income, String student, String credit_rating ) {
        this.age = age;
        this.student = student;
        this.credit_rating = credit_rating;
        this.income = income;
    }

    public String getAttributeByName(String attribute) {

        if (attribute.equals("age")) return this.age;

        if (attribute.equals("student")) return this.student;

        if (attribute.equals("credit_rating")) return this.credit_rating;

        if (attribute.equals("income")) return this.income;

        if (attribute.equals("buysComputer")) return this.buysComputer;

        else return "ERROR";
    }

}



class AttributeStat {

    String name;
    ArrayList<DataTuple> D;
    HashMap<String, Integer> frequency_map;

    public AttributeStat(String name, ArrayList<DataTuple> D) {
        this.name = name;
        this.D = D;
        this.frequency_map = new HashMap<String, Integer>();
        for(DataTuple d : this.D) {
            if(!this.frequency_map.containsKey(d.getAttributeByName(name))) {
                this.frequency_map.put(d.getAttributeByName(name), 1);
            }
            else {
                this.frequency_map.put(d.getAttributeByName(name), this.frequency_map.get(d.getAttributeByName(name)) + 1);
            }
        }
    }

    public HashMap<String, Double> getPercentage() {
        HashMap<String, Double> result = new HashMap<String, Double>();
        for (Map.Entry<String, Integer> x : this.frequency_map.entrySet()) {
            double percentage = (double) x.getValue() / this.D.size();
            result.put(x.getKey(), percentage);
        }
        return result;
    }

    public HashMap<String, Double> getPostiveFreq() {
        HashMap<String, Double> result = new HashMap<String, Double>();
        for(Map.Entry<String, Integer> x : this.frequency_map.entrySet()) {
            double count = 0.0;
            for(DataTuple d : this.D) {
                if(d.getAttributeByName(this.name).equals(x.getKey())) {
                    if(d.getAttributeByName("buysComputer").equals("true")) {
                        count++;
                    }
                }
            }
            result.put(x.getKey(), count / x.getValue());
        }
        return result;
    }

    public HashMap<String, Double> getNegativeFreq() {
        HashMap<String, Double> result = new HashMap<String, Double>();
        for(Map.Entry<String, Integer> x : this.frequency_map.entrySet()) {
            double count = 0.0;
            for(DataTuple d : this.D) {
                if(d.getAttributeByName(this.name).equals(x.getKey())) {
                    if(d.getAttributeByName("buysComputer").equals("false")) {
                        count++;
                    }
                }
            }
            result.put(x.getKey(), count / x.getValue());
        }
        return result;
    }

}

