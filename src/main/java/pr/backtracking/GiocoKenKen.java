package pr.backtracking;

public class GiocoKenKen implements Backtracking<Cella,Integer>{

    private Configurazione c;
    private int  [][] griglia;
    private int numero=1;

//se uso la cella guardo a quale blocco appartiene e uso una funzione di supporto per controllare il soddisfacimento
    //dell'operazione, controllo sulla riga e sulla colonna che non ci sia un altro valore uguale, inoltre devo
    //anche far si che l'operazione venga controllata solo quando sto posizionando l'ultimo valore del blocco
    // non ha senso controllare l'operazione quando il blocco non è pieno
    public GiocoKenKen(Configurazione c, int[][] griglia){
        this.c=c;
        this.griglia= new int[c.getDimensione()][c.getDimensione()];
        for(int i = 0; i<c.getDimensione(); i++){
            for(int j= 0; j<c.getDimensione();j++){
                this.griglia[i][j]=0;
            }
        }
    }


    @Override
    public Cella primoPuntoDiScelta() {
        return new Cella(0,0);
    }

    @Override
    public Cella prossimoPuntoDiScelta(Cella ps) {
        int i;
        if(ps.getColumn()==c.getDimensione()) {
            i= ps.getRow();
            return new Cella(i+1, 0);
        }
        else{
            i=ps.getColumn();
            return new Cella(ps.getRow(),i+1);
        }
    }

    @Override
    public Cella ultimoPuntoDiScelta() {
        return new Cella(c.getDimensione(),c.getDimensione());
    }

    @Override
    public Integer primaScelta(Cella ps) {
        return 1;
    }

    @Override
    public Integer prossimaScelta(Integer integer) {
        return ++integer;
    }

    @Override
    public Integer ultimaScelta(Cella ps) {
        return c.getDimensione();
    }

    @Override
    public boolean assegnabile(Integer scelta, Cella puntoDiScelta) {
        //controllo nella riga se ci sono valori uguali(non ha senso che guardo tutta la riga, avanti
        //sarà ancora vuota)
        for(int colonna=0; colonna<puntoDiScelta.getColumn(); colonna++)
            if(griglia[puntoDiScelta.getRow()][colonna]==scelta)
                return false;
        //controllo nella colonna se ci sono valori uguali(stesso ragionamento della colonna)
        for(int riga=0; riga<puntoDiScelta.getRow(); riga++)
            if(griglia[riga][puntoDiScelta.getColumn()]==scelta)
                return false;
        return true;

    }

    @Override
    public void assegna(Integer scelta, Cella puntoDiScelta) {
            puntoDiScelta.setNumero(scelta);
            griglia[puntoDiScelta.getRow()][puntoDiScelta.getColumn()]=scelta;
    }

    @Override
    public void deassegna(Integer scelta, Cella puntoDiScelta) {
        puntoDiScelta.setNumero(0);
        griglia[puntoDiScelta.getRow()][puntoDiScelta.getColumn()]=0;
    }

    @Override
    public Cella precedentePuntoDiScelta(Cella puntoDiScelta) {
        if(puntoDiScelta.getColumn() == 0) {
            if(puntoDiScelta.getRow() == 0) {
                return new Cella(0,0); // se siamo nella prima cella, non c'è nessun punto precedente
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
        for(int i = 0; i < c.getDimensione(); i++) {
            for(int j = 0; j < c.getDimensione(); j++) {
                System.out.print(griglia[i][j] + " ");
            }
            System.out.println();
        }
    }

        public static void main(String[] args) {
            // Dimensione della griglia (es. 4x4)
            int dimensione = 4;
            Configurazione configurazione = new Configurazione();
            configurazione.setDimensione(dimensione);
            int[][] griglia = new int[dimensione][dimensione];

            GiocoKenKen kenKen = new GiocoKenKen(configurazione, griglia);

            // Avvia la soluzione
            kenKen.risolvi(5);
        }

    }

