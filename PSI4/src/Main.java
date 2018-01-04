

public class Main {
        static int numberOfInputs = 64 + 1;					//ilość wejść
        static double learningRate = 0.1;					//współczynnik uczenia się
        static double forgettingRate = learningRate / 5.0;	//współczynnik zapominania
        static int numberOfEmoticon = 4;						//liczba emotikonów
        static int numberOfNeurons = 5;						//liczba neuronów

        public static void main ( String[] args ) {

            int winner;
            Hebb[] hebbMethods = new Hebb[numberOfNeurons];
            for ( int i = 0; i < numberOfNeurons; i++ )
                hebbMethods[i] = new Hebb( numberOfInputs );
            System.out.println("\n");
//
            for (int i = 0; i < numberOfEmoticon; i++ ) {
                winner = testHebb(hebbMethods, Emoticon.emoticonToLearn[i] );
                System.out.println( "Zwycięzca przed uczeniem = " + winner );
            }

            int ages = learn(hebbMethods);
            System.out.println("\n");
//
            for (int i = 0; i < numberOfEmoticon; i++ ) {
                winner = testHebb(hebbMethods, Emoticon.emoticonToLearn[i] );
                System.out.println( "Zwycięzca po uczeniu = " + winner );
            }

            System.out.println( "\n\nIlość epok = " + ages );
            System.out.println("\n");
//
            for (int i = 0; i < numberOfEmoticon; i++ ) {
                winner = testHebb(hebbMethods, Emoticon.EmoticonToTest[i] );
                System.out.println( "Zwycięzca po testowaniu = " + winner );
            }

        }


        //uczenie neuronów
        public static int learn ( Hebb[] hebbMethods) {

            int counter = 0;
            int limit = 1000;
            int[] winners = new int[numberOfNeurons];
            for ( int i = 0; i < numberOfNeurons; i++ )
                winners[i] = - 1;

            while ( ! isUnique( winners ) ) {

                for ( int j = 0; j < numberOfNeurons; j++ ) {

                    //uczenie neuronów każdej emotikony
                    for (int k = 0; k < numberOfEmoticon; k++ )
                        hebbMethods[j].learnMethod( Emoticon.emoticonToLearn[k], learningRate, forgettingRate, Hebb.hebbUnforgetting);

                    //tesotowanie sieci
                    for (int l = 0; l < numberOfEmoticon; l++ )
                        winners[l] = testHebb(hebbMethods, Emoticon.emoticonToLearn[l] );
                }

                if ( ++ counter == limit )
                    break;
            }

            return counter;
        }

        //funkcja pomocnicza w procesie uczenie
        //zwraca true jeśli każdy element w tablicy jest unikalny
        public static boolean isUnique ( int[] winners ) {

            for ( int i = 0; i < numberOfNeurons; i++ )
                for ( int j = 0; j < numberOfNeurons; j++ )
                    if ( i != j )
                        if ( winners[i] == winners[j] )
                            return false;

            return true;
        }

        //zwraca wartość zwycięzkiego neuronu dla podanej emotikony
        public static int testHebb (Hebb[] hebbMethods, double[] emoticon ) {

            double max = hebbMethods[0].neuronOutput(emoticon);
            int winner = 0;

            for ( int i = 1; i < numberOfNeurons; i++ ) {
                if ( hebbMethods[i].neuronOutput(emoticon) > max ) {
                    max = hebbMethods[i].neuronOutput(emoticon);
                    winner = i;
                }
            }

            return winner;
        }
    }

