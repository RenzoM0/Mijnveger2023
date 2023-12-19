package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    public static void menu(){
        Scanner s = new Scanner(System.in);

        System.out.println("Welkom" + "\n" + "Wil je het spelerspel (1) of het algoritmespel (2) starten?");
        int keuze = s.nextInt();
        if (keuze == 1){
            System.out.println("Spelerspel wordt gestart");
            MijnBord spelbord = new MijnBord(9,9,10);
            if(spelbord.StartSpel(true)){
                System.out.println("Gewonnen");
            } else {
                System.out.println("Verloren");
            }
        } else if(keuze == 2){
            System.out.println("Hoe vaak moet het algoritmespel gespeeld worden?");
            algoritmeSpel(s.nextInt());
        }
    }
    public static int algoritmeSpel(int aantal){
        return -1;
    }
}
