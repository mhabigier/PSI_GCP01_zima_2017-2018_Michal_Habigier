package pl.mh;

import java.io.*;
import java.util.Random;
import java.util.Scanner;


public class Perceptron {
    private static final int limit = 1000000;
    private double[][] dendrites;
    private double[] synapses;
    private int[] data;
    private String[] parts;
    private File dataFileReader= new File("data.txt");
    private int dataCount;
    private double rate;

    public File getDataFileReader() {
        return dataFileReader;
    }

    public void setDataFileReader(File dataFileReader) {
        this.dataFileReader = dataFileReader;
    }

    public int getLines() throws IOException {
        return linesCounter(dataFileReader);
    }

    public int getDataCount() {
        return dataCount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public Perceptron() throws IOException {
        this.dendrites =new double[getLines()][2];
        this.synapses =new double[2];
        this.data =new int[getLines()];
    }

    public int linesCounter(File inputFile)throws IOException{
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(inputFile));
            while ((reader.readLine() != null)) ;
            return reader.getLineNumber();
        }
        catch (Exception ex) {
            return -1;
        }
        finally{
            if(reader != null)
                reader.close();
        }
    }

    public void readFromFile()throws IOException{
        try {
            Scanner read = new Scanner(dataFileReader);
            String text=read.nextLine();
            for(int i=0;i<getLines();++i){
                parts = text.split(";");
                dendrites[i][0]=(Double.parseDouble(parts[0]));
                dendrites[i][1]=(Double.parseDouble(parts[1]));
                data[i]=(Integer.parseInt(parts[2]));
                if(read.hasNextLine())text = read.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void setSynapses(){
        for(int i=0;i<2;++i){
            synapses[i]=Math.random();
        }
    }

    public double getMembranePotential(int index)
    {
        double sum=0;
        for(int i=0;i<2;++i) {
            sum += dendrites[index][i]* synapses[i];
        }
        return sum;
    }

    public int getOutput(int index)
    {
        if(getMembranePotential(index) >= 0)
            return 1;
        else return -1;
    }

    public void testLearn()throws IOException{
        readFromFile();
        setSynapses();

        double tempErr;
        int error;
        int testLimit=0;

        do {
            error=0;
            testLimit++;
            for (int i = 0; i < getLines(); ++i) {
                tempErr = data[i] - getOutput(i);

                for(int j=0;j<2;++j)
                {
                    synapses[j]+= rate * dendrites[i][j]*(data[i]-getOutput(i));
                }

                error+=tempErr;
            }
        }while (error!=0&&testLimit<limit);

        if(testLimit<limit)
        {
            System.out.println("Perceptron nauczył się po "+testLimit+" probach.");
        }else{
            System.out.println("Perceptron nie nauczył się.");
        }

//        ------------------------------------------------------------------------------------

        error = 0;

        for(int i=0;i<getLines();++i){
            System.out.println("Liczba "+ dendrites[i][0]+" < "+ dendrites[i][1]+" poprawna odpowiedz: "+ data[i]+" odpowiedz: "+getOutput(i));
            if(data[i]!=getOutput(i))error++;
        }
        System.out.println("Ilosc bledow: "+error);
    }
}