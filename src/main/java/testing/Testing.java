package testing;


import org.junit.jupiter.api.DisplayName;
import pr.backtracking.Blocco;
import pr.backtracking.Cella;
import pr.backtracking.Configurazione;
import pr.backtracking.GiocoKenKen;
import org.junit.*;

import static org.junit.Assert.assertTrue;

public class Testing {

    private GiocoKenKen k;

    public GiocoKenKen creaGrigliaPerTest(){

        Configurazione configurazione = new Configurazione();
        configurazione.setDimensione(3);

        Blocco[] blocchi = new Blocco[4];
        Blocco b1 = new Blocco();
        b1.setDimensione(2);
        b1.setOperazione("/");
        b1.setRisultato(3);
        Cella[] celle1=new Cella[2];
        Cella c1 = new Cella(1,2);
        Cella c2 = new Cella(2,2);
        celle1[0]=c1;
        celle1[1]=c2;
        b1.setCelle(celle1);
        blocchi[0]=b1;

        Blocco b2 = new Blocco();
        b2.setDimensione(3);
        b2.setOperazione("+");
        b2.setRisultato(7);
        Cella[] celle2=new Cella[3];
        Cella c3 = new Cella(2,0);
        Cella c4 = new Cella(2,1);
        Cella c5 = new Cella(1,0);
        celle2[0]=c3;
        celle2[1]=c4;
        celle2[2]=c5;
        b2.setCelle(celle2);
        blocchi[1]=b2;

        Blocco b3 = new Blocco();
        b3.setDimensione(3);
        b3.setOperazione("*");
        b3.setRisultato(3);
        Cella[] celle3=new Cella[3];
        Cella c6 = new Cella(0,0);
        Cella c7 = new Cella(0,1);
        Cella c8 = new Cella(1,1);
        celle3[0]=c6;
        celle3[1]=c7;
        celle3[2]=c8;
        b3.setCelle(celle3);
        blocchi[2]=b3;


        Blocco b4 = new Blocco();
        b4.setDimensione(1);
        b4.setOperazione("");
        b4.setRisultato(2);
        Cella[] celle4=new Cella[1];
        Cella c9 = new Cella(0,2);
        celle4[0]=c9;
        b4.setCelle(celle4);
        blocchi[3]=b4;

        configurazione.setNumeroBlocchi(4);
        configurazione.setBlocchi(blocchi);
        configurazione.setMaxSol(2);

        int[][] griglia = new int[3][3];
        return new GiocoKenKen(configurazione,griglia);
    }


    @Test
    @DisplayName("Testing abilitazione")
    public void assegn() {
        k= creaGrigliaPerTest();
        //Inserisco dei numeri sulla griglia per testare l'assegnabile
        int [][] griglia = k.getGriglia();
        griglia[0][1] = 1;
        griglia[0][2]= 3;
        griglia[1][1] = 2;
        assertTrue(k.assegnabile(2,new Cella(0,3)));

    }
}
