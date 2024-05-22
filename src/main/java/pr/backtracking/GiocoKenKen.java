package pr.backtracking;

public class GiocoKenKen implements Backtracking<Cella, Integer> {

    private Configurazione c;
    private int[][] griglia;

    public GiocoKenKen(Configurazione c, int[][] griglia) {
        this.c = c;
        this.griglia = new int[c.getDimensione()][c.getDimensione()];
        for (int i = 0; i < c.getDimensione(); i++) {
            for (int j = 0; j < c.getDimensione(); j++) {
                this.griglia[i][j] = 0;
            }
        }
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
        // Controllo riga
        for (int colonna = 0; colonna < c.getDimensione(); colonna++)
            if (griglia[puntoDiScelta.getRow()][colonna] == scelta)
                return false;
        // Controllo colonna
        for (int riga = 0; riga < c.getDimensione(); riga++)
            if (griglia[riga][puntoDiScelta.getColumn()] == scelta)
                return false;
        //controllo Blocco
        boolean ver=false;
        for(Blocco b: c.getBlocchi()){
            for(Cella cc: b.getCelle()) {
                if (puntoDiScelta.equals(cc)){
                    griglia[puntoDiScelta.getRow()][puntoDiScelta.getColumn()]=scelta;
                    if(!(controllaOperazione(b,griglia,puntoDiScelta)))
                        return false;
                }
            }
        }
        return true;
    }

    private boolean ePieno(Blocco b,int [][] griglia){
        for(Cella c: b.getCelle()){
            int i = c.getRow();
            int j = c.getColumn();
            if(griglia[i][j] == 0)
                return false;
        }
        return true;
    }

    private boolean controllaOperazione(Blocco b, int [][] griglia,Cella c) {
        int ris = griglia[c.getRow()][c.getColumn()];
        System.out.println(ris);
        for (Cella cc : b.getCelle()) {
            if (!(cc.equals(c)) && griglia[cc.getRow()][cc.getColumn()] != 0) {
                System.out.println("Sto controllando il blocco"+b.toString());
                switch (b.getOperazione()) {
                    case "+":
                        ris += griglia[c.getRow()][c.getColumn()];
                        if (ris > b.getRisultato()) {
                            return false;
                        }
                        break;
                    case "-":
                        ris -= griglia[c.getRow()][c.getColumn()];
                        if (Math.abs(ris) < b.getRisultato() || Math.abs(ris) > b.getRisultato())
                            return false;
                        break;
                    case "/":
                        ris = ris / griglia[c.getRow()][c.getColumn()];
                        int ris2 = griglia[c.getRow()][c.getColumn()] / ris;
                        if (ris != b.getRisultato() || ris2 != b.getRisultato())
                            return false;
                        break;
                    case "*":
                        ris = ris * griglia[c.getRow()][c.getColumn()];
                        if (ris > b.getRisultato())
                            return false;
                        break;
                    default:
                       if(ris!=b.getRisultato())
                           return false;
                }
                if (ePieno(b, griglia) && !(ris == b.getRisultato())) {
                    return false;
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
    }

    public static void main(String[] args) {
        int dimensione = 3;
        Configurazione configurazione = new Configurazione();
        configurazione.setDimensione(dimensione);
        Blocco[] blocchi = new Blocco[5];
        Blocco b1 = new Blocco();
        b1.setDimensione(2);
        b1.setOperazione("*");
        b1.setRisultato(6);
        Cella[] celle1=new Cella[2];
        Cella c1 = new Cella(0,0);
        Cella c2 = new Cella(1,0);
        celle1[0]=c1;
        celle1[1]=c2;
        b1.setCelle(celle1);
        blocchi[0]=b1;

        Blocco b2 = new Blocco();
        b2.setDimensione(2);
        b2.setOperazione("-");
        b2.setRisultato(1);
        Cella[] celle2=new Cella[2];
        Cella c3 = new Cella(2,1);
        Cella c4 = new Cella(2,2);
        celle2[0]=c3;
        celle2[1]=c4;
        b2.setCelle(celle2);
        blocchi[1]=b2;

        Blocco b3 = new Blocco();
        b3.setDimensione(3);
        b3.setOperazione("+");
        b3.setRisultato(4);
        Cella[] celle3=new Cella[3];
        Cella c5 = new Cella(0,1);
        Cella c6 = new Cella(1,1);
        Cella c7 = new Cella(1,2);
        celle3[0]=c5;
        celle3[1]=c6;
        celle3[2]=c7;
        b3.setCelle(celle3);
        blocchi[2]=b3;


        Blocco b4 = new Blocco();
        b4.setDimensione(1);
        b4.setOperazione("");
        b4.setRisultato(1);
        Cella[] celle4=new Cella[1];
        Cella c8 = new Cella(2,0);
        celle4[0]=c8;
        b4.setCelle(celle4);
        blocchi[3]=b4;

        Blocco b5 = new Blocco();
        b5.setDimensione(1);
        b5.setOperazione("");
        b5.setRisultato(1);
        Cella[] celle5=new Cella[1];
        Cella c9 = new Cella(0,2);
        celle5[0]=c9;
        b5.setCelle(celle5);
        blocchi[4]=b5;


        configurazione.setNumeroBlocchi(5);
        configurazione.setBlocchi(blocchi);

        int[][] griglia = new int[dimensione][dimensione];

        GiocoKenKen kenKen = new GiocoKenKen(configurazione, griglia);

        kenKen.risolvi(2);
    }
}

