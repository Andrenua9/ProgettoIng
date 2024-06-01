package pr.backtracking;

import pr.memento.Griglia;
import pr.memento.Memento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiocoKenKen implements Backtracking<Cella, Integer> {

    private final Configurazione c;
    private int[][] griglia;

    private List<Griglia> soluzioni;

    public GiocoKenKen(Configurazione c, int[][] griglia) {
        this.c = c;
        this.griglia = new int[c.getDimensione()][c.getDimensione()];
        for (int i = 0; i < c.getDimensione(); i++) {
            for (int j = 0; j < c.getDimensione(); j++) {
                this.griglia[i][j] = 0;
            }
        }
        soluzioni=new ArrayList<Griglia>();
    }

    @Override
    public Cella primoPuntoDiScelta() {
        return new Cella(0, 0);
    }

    @Override
    public Cella prossimoPuntoDiScelta(Cella ps) {
        int row = ps.getRow();
        int col = ps.getColumn();
        if (col == c.getDimensione() - 1) {
            return new Cella(row + 1, 0);
        } else {
            return new Cella(row, col + 1);
        }
    }

    @Override
    public Cella ultimoPuntoDiScelta() {
        return new Cella(c.getDimensione() - 1, c.getDimensione() - 1);
    }

    @Override
    public Integer primaScelta(Cella ps) {
        return 1;
    }

    @Override
    public Integer prossimaScelta(Integer integer) {
        return integer + 1;
    }

    @Override
    public Integer ultimaScelta(Cella ps) {
        return c.getDimensione();
    }

    @Override
    public boolean assegnabile(Integer scelta, Cella puntoDiScelta) {
        // Controllo riga e colonna
        if (!controllaRigheColonne(griglia, puntoDiScelta, scelta)) {
            return false;
        }

        // Controllo blocco
        for (Blocco b : c.getBlocchi()) {
            if (Arrays.asList(b.getCelle()).contains(puntoDiScelta)) {
                if (!controllaOperazione(b, griglia, puntoDiScelta, scelta)) {
                    return false;
                }
            }
        }
        //check finito,aggiorno la griglia
        griglia[puntoDiScelta.getRow()][puntoDiScelta.getColumn()] = scelta;
        return true;
    }

    private boolean controllaRigheColonne(int[][] griglia, Cella puntoDiScelta, int scelta) {
        // Controllo riga
        for (int colonna = 0; colonna < c.getDimensione(); colonna++) {
            if (griglia[puntoDiScelta.getRow()][colonna] == scelta) {
                return false;
            }
        }

        // Controllo colonna
        for (int riga = 0; riga < c.getDimensione(); riga++) {
            if (griglia[riga][puntoDiScelta.getColumn()] == scelta) {
                return false;
            }
        }
        return true;
    }

    private boolean ePieno(Blocco b, int[][] griglia) {
        for (Cella c : b.getCelle()) {
            int i = c.getRow();
            int j = c.getColumn();
            if (griglia[i][j] == 0)
                return false;
        }
        return true;
    }

    private boolean controllaOperazione(Blocco b, int[][] griglia, Cella c, int scelta) {
        int ris = scelta;
        for (Cella cc : b.getCelle()) {
            if (!cc.equals(c) && griglia[cc.getRow()][cc.getColumn()] != 0) {
                int valore = griglia[cc.getRow()][cc.getColumn()];
                if(ePieno(b,griglia) && b.getRisultato()!= ris){
                    return false;
                }
                switch (b.getOperazione()) {
                    case "+":
                        ris += valore;
                        if (ris > b.getRisultato()) {
                            return false;
                        }
                        break;
                    case "-":
                        ris = Math.abs(ris - valore);
                        if (ris != b.getRisultato()) {
                            return false;
                        }
                        break;
                    case "/":
                        if (valore != 0 && (ris % valore == 0 || valore % ris == 0)) {
                            int div1 = ris / valore;
                            int div2 = valore / ris;
                            if ((div1 != b.getRisultato() && div2 != b.getRisultato())) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                        break;
                    case "*":
                        ris *= valore;
                        if (ris > b.getRisultato()) {
                            return false;
                        }
                        break;
                    default:
                        if (ePieno(b,griglia)&&ris != b.getRisultato()) {
                            return false;
                        }
                        break;
                }
            }

        }
        return true;
    }


    @Override
    public void assegna(Integer scelta, Cella puntoDiScelta) {
        griglia[puntoDiScelta.getRow()][puntoDiScelta.getColumn()] = scelta;
    }

    @Override
    public void deassegna(Integer scelta, Cella puntoDiScelta) {
        griglia[puntoDiScelta.getRow()][puntoDiScelta.getColumn()] = 0;
    }

    @Override
    public Cella precedentePuntoDiScelta(Cella puntoDiScelta) {
        int row = puntoDiScelta.getRow();
        int col = puntoDiScelta.getColumn();
        if (col == 0) {
            if (row == 0) {
                return null;
            }
            return new Cella(row - 1, c.getDimensione() - 1);
        } else {
            return new Cella(row, col - 1);
        }
    }

    @Override
    public Integer ultimaSceltaAssegnataA(Cella puntoDiScelta) {
        return griglia[puntoDiScelta.getRow()][puntoDiScelta.getColumn()];
    }

    @Override
    public void scriviSoluzione(int nr_sol) {
        System.out.println("Soluzione numero " + nr_sol + ":");
        for (int i = 0; i < c.getDimensione(); i++) {
            for (int j = 0; j < c.getDimensione(); j++) {
                System.out.print(griglia[i][j] + " ");
            }
            System.out.println();
        }
       soluzioni.add(new Griglia(griglia));
    }

    public List<Griglia> getSoluzioni() {
        return soluzioni;
    }

    public static void main(String[] args) {
        int dimensione = 3;
        Configurazione configurazione = new Configurazione();
        configurazione.setDimensione(dimensione);

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

        int[][] griglia = new int[dimensione][dimensione];

        GiocoKenKen kenKen = new GiocoKenKen(configurazione, griglia);

        kenKen.risolvi(2);
    }
}

