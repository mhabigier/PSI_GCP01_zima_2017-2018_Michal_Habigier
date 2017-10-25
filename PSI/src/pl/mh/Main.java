package pl.mh;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception{
	Perceptron p = new Perceptron();
	p.setRate(0.00001);
	p.setDataCount(100);
	p.testLearn();
    }
}
