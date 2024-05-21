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
        if (puntoDiScelta.getColumn() == 0) {
            if (puntoDiScelta.getRow() == 0) {
                return new Cella(0, 0);
            } else {
                return new Cella(puntoDiScelta.getRow() - 1, c.getDimensione() - 1);
            }
        } else {
            return new Cella(puntoDiScelta.getRow(), puntoDiScelta.getColumn() - 1);
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
        int dimensione = 4;
        Configurazione configurazione = new Configurazione();
        configurazione.setDimensione(dimensione);
        int[][] griglia = new int[dimensione][dimensione];

        GiocoKenKen kenKen = new GiocoKenKen(configurazione, griglia);

        kenKen.risolvi(5);
    }
}

