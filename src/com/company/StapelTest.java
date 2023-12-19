package com.company;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StapelTest {

    private Stapel leegStapel = new Stapel();
    private Stapel stapel1(){
        Stapel s = new Stapel();
        s.push(1);
        return s;
    }
    private Stapel stapel2(int aantal){
        Stapel s = new Stapel();
        for(int i = 1; i <= aantal; i++){
            s.push(i);
        }
        return s;
    }
    @Test
    void push() {
        Stapel stapel0 = leegStapel;
        Stapel stapel1 = stapel1();
        Stapel stapel2 = stapel2(100);

        stapel0.push(1);
        stapel1.push(2);
        stapel2.push(2);

        assertEquals(1,stapel0.getTop().getInhoud());
        assertEquals(2,stapel1.getTop().getInhoud());
        assertEquals(2,stapel2.getTop().getInhoud());
    }
    @Test
    void pop() {
        Stapel stapel0 = leegStapel;
        Stapel stapel1 = stapel2(100);

        assertEquals(null,stapel0.pop());
        assertEquals(100,stapel1.pop());
        assertEquals(99,stapel1.getTop().getInhoud());
    }
    @Test
    void zoek() {
        Stapel stapel0 = leegStapel;
        Stapel stapel1 = stapel2(100);
        Stapel stapel2 = stapel2(5);
        Stapel stapel3 = stapel2(5);

        stapel2.push(1);
        stapel3.push(2);
        stapel3.push(3);

        assertEquals(-1,stapel0.zoek(5));
        assertEquals(2,stapel1.zoek(99));
        assertEquals(1,stapel2.zoek(1));
        assertEquals(2,stapel3.zoek(2));
    }
    @Test
    void print() {
        Stapel stapel0 = leegStapel;
        Stapel stapel1 = stapel2(100);

        System.out.println(stapel0.print());
        System.out.println(stapel1.print());
    }
}