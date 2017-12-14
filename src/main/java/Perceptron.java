import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Perceptron {
    private ArrayList<Integer> x1 = new ArrayList<>();
    private ArrayList<Integer> x2 = new ArrayList<>();
    private ArrayList<Integer> expectedResult = new ArrayList<>();
    private double learningRate;
    private Integer dataAmount;
    private Random random = new Random();
    private int weightChangesAmount;
    private final double []weights = {(random.nextDouble() * 2) - 1,(random.nextDouble() * 2) - 1};

    public void saveGeneratedData()throws IOException {
        FileWriter fileWriter = new FileWriter("data.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i = 0; i< dataAmount; i++){
            bufferedWriter.write(x1.get(i)+" "+x2.get(i)+" "+ expectedResult.get(i)+"\n");
        }
        bufferedWriter.close();
    }

    public void saveTestResults(int testResult)throws IOException{

        FileWriter fw = new FileWriter("testResults.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(dataAmount +" "+ learningRate+" "+ testResult+ "\n");
        bw.close();
    }

    public void saveLearningResults() throws IOException{

        FileWriter fw = new FileWriter("learningResults.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(dataAmount +" "+ learningRate+" "+ weightChangesAmount + "\n");
        bw.close();
    }

    public void generateData(){
        x1.clear();
        x2.clear();
        expectedResult.clear();
        for(int i = 0; i< dataAmount; i++) {
            x1.add(random.nextInt(2));
            x2.add(random.nextInt(2));
            if (x1.get(i)==0 && x2.get(i)==0)
                expectedResult.add(0);
            else
                expectedResult.add(1);
        }
    }

    public double calculateSum(int x1, int x2, double[]weights){
        double sum = x1*weights[0]+x2*weights[1];
        return sum;
    }

    public int perceptronActivation(double sum){
        if(sum>0)
            return 1;
        else
            return 0;
    }

    public void adjustWeights(double[] weights, double error, int i){
        weights[0] += learningRate * error *  x1.get(i);
        weights[1] += learningRate * error *  x2.get(i);
    }


    public void learn(){
        int changeInWeights=1;
        while(changeInWeights != 0){
            changeInWeights=0;
            for(int i = 0; i< dataAmount; i++){
                double calculatedSum = calculateSum(x1.get(i), x2.get(i), weights);
                int result = perceptronActivation(calculatedSum);
                int error = expectedResult.get(i)-result;
                if(error!=0) {
                    adjustWeights(weights, error, i);
                    changeInWeights++;
                }
            }
            weightChangesAmount +=changeInWeights;
        }
    }

    public int test(){
        int correct=0;
        int wrong=0;
        for(int i = 0; i< dataAmount; i++){
            double calculatedSum = calculateSum(x1.get(i), x2.get(i), weights);
            int result = perceptronActivation(calculatedSum);
            int error = expectedResult.get(i)-result;
            if(error==0)
                correct++;
            else
                wrong++;
        }
        if(wrong==0)
            return 100;
        else
            return (correct / wrong) * 100;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(Double learningRate) {
        this.learningRate = learningRate;
    }

    public int getDataAmount() {
        return dataAmount;
    }

    public void setDataAmount(Integer dataAmount) {
        this.dataAmount = dataAmount;
    }
}
