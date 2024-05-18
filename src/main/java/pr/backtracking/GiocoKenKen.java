package pr.backtracking;

public class GiocoKenKen implements Backtracking<Blocco,Cella>{

    private Configurazione c;
    private int  [][] griglia;

    public GiocoKenKen(Configurazione c, int[][] griglia){
        this.c=c;
        griglia= new int[c.getDimensione()][c.getDimensione()];
    }

    @Override
    public Blocco primoPuntoDiScelta() {
        return c.getBlocchi()[0];
    }

    @Override
    public Blocco prossimoPuntoDiScelta(Blocco ps) {
        return null;
    }

    @Override
    public Blocco ultimoPuntoDiScelta() {
        return c.getBlocchi()[c.getNumeroBlocchi()-1];
    }

    @Override
    public Cella primaScelta(Blocco ps) {
        return null;
    }

    @Override
    public Cella prossimaScelta(Cella cella) {
        return null;
    }

    @Override
    public Cella ultimaScelta(Blocco ps) {
        return null;
    }

    @Override
    public boolean assegnabile(Cella scelta, Blocco puntoDiScelta) {
        return false;
    }

    @Override
    public void assegna(Cella scelta, Blocco puntoDiScelta) {

    }

    @Override
    public void deassegna(Cella scelta, Blocco puntoDiScelta) {

    }

    @Override
    public Blocco precedentePuntoDiScelta(Blocco puntoDiScelta) {
        return null;
    }

    @Override
    public Cella ultimaSceltaAssegnataA(Blocco puntoDiScelta) {
        return null;
    }

    @Override
    public void scriviSoluzione(int nr_sol) {

    }
}
