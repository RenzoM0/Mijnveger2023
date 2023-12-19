package com.company;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class MijnBord {
    private final int LENGTE, BREEDTE, MIJNEN;
    private int MIJN = -1;
    private int[][] spelbord;
    private boolean[][] verborgen;
    private Stapel<boolean[][]> geheugen;
    private boolean isGenereerd;

    public MijnBord(int L, int B, int M){
        this.LENGTE = L;
        this.BREEDTE = B;
        this.MIJNEN = M;

        spelbord = new int[L][B];
        verborgen = new boolean[L][B];
        geheugen = new Stapel<>();
    }

    public boolean StartSpel(boolean speler) {
        verbergVakjes();
        Point zet;
        while (!dood() && !gewonnen()) {
            if (speler) {
                print();
                zet = spelerZet();
            } else {
                algoritmeZet();
                zet = algoritmeZet();
            }
            doeZet(zet);
        }
        return gewonnen();
    }
    //Methode om alle vakjes op verborgen te zetten (wordt gebruikt bij @startspel
    private void verbergVakjes(){
        for(int i = 0; i < LENGTE; i++){
            for(int j = 0; j < BREEDTE; j++){
                verborgen[i][j] = true;
            }
        }
        isGenereerd = false;
    }
    //Wanneer een mijn vak niet verborgen is = spel over
    private boolean dood(){
        boolean dood = false;
        for(int i = 0; i < LENGTE; i++){
            for(int j = 0; j < BREEDTE; j++) {
                if(!verborgen[i][j] && spelbord[i][j] == MIJN){
                    dood = true;
                }
            }
        }
        return dood;
    }
    /*Als het aantal vakken dat over is gelijk staat aan het aantal mijnen en de methode dood() false is,
    * is het spel gewonnen.
    */
    private boolean gewonnen(){
        int aantalVerborgenVakken = 0;
        for(int i = 0; i < LENGTE; i++){
            for(int j = 0; j < BREEDTE; j++) {
                if(verborgen[i][j]){
                    aantalVerborgenVakken++;
                }
            }
        }
        return aantalVerborgenVakken == MIJNEN && !dood();
    }
    /* Als er een vorige spel is , stapel.pop() uitvoeren.
    * Vakken opnieuwe verbergen
    * */
    private void undo(){
        boolean[][] vorigeSpel = geheugen.pop();
        if(vorigeSpel != null){
            for(int i = 0; i < LENGTE; i++){
                for(int j = 0; j < BREEDTE; j++) {
                    verborgen[i][j] = vorigeSpel[i][j];
                }
            }
        }
    }
    private Point spelerZet(){
        System.out.println("Geef coördinaten voor zet, geef Y" + "\n" + "Druk U voor undo" );
        Scanner input = new Scanner(System.in);
        int x,y;

        while(!input.hasNextInt()){
            if(input.next().toLowerCase().equals("u")){
                undo();
                print();
            }
        }
        x = input.nextInt();
        System.out.println("Geef X");
        y = input.nextInt();
        print();
        return new Point(x,y);
    }
    private void print(){
        System.out.printf(" ");
        // nummers x-as
        for(int i = 0; i < BREEDTE; i++){
            System.out.printf("%d", i);
        }
        System.out.printf("%n");
        for(int i = 0; i < LENGTE; i++){
            // nummers y-as
            System.out.printf("%d", i);
            for(int j = 0; j < BREEDTE; j++) {
                if(verborgen[i][j]){
                    System.out.printf("%s","X");
                } else {
                    System.out.printf("%d", spelbord[i][j]);
                }
            }
            System.out.printf("%n");
        }
    }

    private int telMijnen(Point hier){
        //Houdt aantal mijnen bijhouden
        int aantalMijnen = 0;
        /* start X/Y links/onder huidige positie
         * end X/Y rechts/boven huidige positie
         * Conditionele operator om niet buiten de grenzen van het bord te zoeken naar mijnen
         * Voorbeeld: Als hier.x = 0 is wordt startX niet -1 maar 0
         * Omgekeerd: Als hier.x = 8 is wordt endX niet 9 maar 8
         */
        int startX = (hier.x - 1 < 0) ? hier.x : hier.x - 1;
        int startY = (hier.y - 1 < 0) ? hier.y : hier.y - 1;
        int endX = (hier.x + 1 > BREEDTE - 1) ? hier.x : hier.x + 1;
        int endY = (hier.y + 1 > LENGTE - 1) ? hier.y : hier.y + 1;
        // Zoek horizontaal/verticaal/diagonaal naar mijnen
        for(int i=startX; i<=endX; i++) {
            for (int j=startY; j<=endY; j++) {
                {
                    if(spelbord[i][j] == MIJN){
                        aantalMijnen++;
                    }
                }
            }
        }

        return aantalMijnen;
    }
    /* Genereer 2 random getallen binnen de lengte & breedte
    *  Als coordinaat overeenkomt met verboden punt, opnieuw 2 random getallen genereren
    *  Aantal mijnen toekennen aan een vak als het geen MIJN is
    * */
    private void genereerBord(Point verboden){
        Random random = new Random();
        for(int i = 0; i < MIJNEN; i++){
            int x = random.nextInt(BREEDTE);
            int y = random.nextInt(LENGTE);
            while(x == verboden.x && y == verboden.y){
                 x = random.nextInt(BREEDTE);
                 y = random.nextInt(LENGTE);
            }
            spelbord[x][y] = MIJN;
        }
        for(int i = 0; i < BREEDTE; i++){
            for(int j = 0; j < LENGTE; j++) {
                if(spelbord[i][j] != MIJN) {
                    spelbord[i][j] = telMijnen(new Point(i, j));
                }
            }
        }
        isGenereerd = true;
    }
    /* Dubbele for loop om horizontaal/verticaal/diagonaal randen te onthullen.
    * Bij nieuwe 0 waarde recursief opnieuw randen onthullen
    * */
    private void onthulRanden(Point hier){
        // Kijk telMijnen
        int x = hier.x;
        int y = hier.y;

        int startX = (hier.x - 1 < 0) ? hier.x : hier.x - 1;
        int startY = (hier.y - 1 < 0) ? hier.y : hier.y - 1;
        int endX = (hier.x + 1 > BREEDTE - 1) ? hier.x : hier.x + 1;
        int endY = (hier.y + 1 > LENGTE - 1) ? hier.y : hier.y + 1;

        // Als vakje verborgen is onthul vakje en roep zichzelf aan als er 0 mijnen om het vakje zijn
        for(int i=startX; i<=endX; i++) {
            for (int j=startY; j<=endY; j++) {
                {
                    if(verborgen[i][j]){
                        verborgen[i][j] = false;
                        if(spelbord[i][j] == 0){
                            onthulRanden(new Point(i,j));
                        }
                    }

                }
            }
        }
    }

    private void doeZet(Point zet){
        if(!isGenereerd){
            genereerBord(zet);
        }
        // spel opslaan
        boolean[][] huidigspel = new boolean[LENGTE][BREEDTE];
        for(int i = 0; i < BREEDTE; i++){
            for(int j = 0; j < LENGTE; j++) {
                    huidigspel[i][j] = verborgen[i][j];
                }
            }
        geheugen.push(huidigspel);
        // vak en randen onthullen
        verborgen[zet.x][zet.y] = false;
        if (spelbord[zet.x][zet.y] == 0){
            onthulRanden(new Point(zet.x,zet.y));
        }
    }
    private Point algoritmeZet(){
        return null;
    }
}
