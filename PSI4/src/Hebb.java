import java.util.Random;

public class Hebb {
    private int inputs;
    private double[] wages;
    public static boolean hebbForgetting = true;
    public static boolean hebbUnforgetting = false;

    public Hebb(int inputs) {
        this.inputs = inputs;
        wages = new double[inputs];

        for (int i = 0; i < inputs; i++ ) {
            wages[i] = new Random().nextDouble(); //losowanie początkowych wag
        }

        normalizeWeights();
    }

    private double activationFunction(double y_p ) {
        return ( 1.0 / ( 1 + Math.pow( Math.E, - y_p ) ) ); //funkcja unipolarna sigmoidalna
    }

    //zwraca sumę iloczynu wag i sygnałów wejściowych
    private double sumMethod(double[] x ) {
        double y_p = 0.0;
        for (int i = 0; i < inputs; i++ ) {
            y_p += x[i] * wages[i];
        }

        return y_p;
    }

    public double learnMethod(double[] x, double lr, double fr, boolean isTeacher ) {
        double y_p = activationFunction( sumMethod( x ) );

        for (int i = 0; i < inputs; i++ )
            if ( isTeacher ) wages[i] = ( 1 - fr ) * wages[i] + lr * x[i] * y_p; //ze współczynnikiem zapominania
            else wages[i] += lr * x[i] * y_p; //bez współczynnika zapominania

        normalizeWeights();

        return activationFunction( sumMethod( x ) );
    }

    public double neuronOutput ( double[] x ) {
        return activationFunction( sumMethod( x ) );
    }

    private void normalizeWeights () {
        double dl = 0.0;
        for (double wage : wages) dl += Math.pow(wage, 2);

        dl = Math.sqrt( dl );

        for (int i = 0; i < wages.length; i++ )
            if ( wages[i] > 0 && dl != 0 )
                wages[i] = wages[i] / dl;
    }


}


