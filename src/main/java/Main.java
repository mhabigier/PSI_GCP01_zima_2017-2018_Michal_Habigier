import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        Perceptron perceptron = new Perceptron();
        perceptron.setDataAmount(1000);
        perceptron.setLearningRate(0.005);
        perceptron.generateData();
        perceptron.saveGeneratedData();
        perceptron.learn();
        perceptron.saveLearningResults();
        perceptron.saveTestResults(perceptron.test());
    }
}
