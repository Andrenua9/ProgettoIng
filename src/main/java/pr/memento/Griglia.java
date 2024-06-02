package pr.memento;

public class Griglia implements Originator {
    private int[][] griglia;

    public Griglia(int dimensione) {
        griglia = new int[dimensione][dimensione];
    }

    public Griglia(int[][] griglia) {
        this.griglia = copiaGriglia(griglia);
    }

    public int getEl(int i,int j){
        return griglia[i][j];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Griglia other = (Griglia) obj;
        for (int i = 0; i < griglia.length; i++) {
            for (int j = 0; j < griglia[0].length; j++) {
                if (griglia[i][j] != other.griglia[i][j]) return false;
            }
        }
        return true;
    }


    @Override
    public Memento getMemento() {
        return new GrigliaMemento(getGriglia());
    }

    @Override
    public void setMemento(Memento m) {
        if (m instanceof GrigliaMemento) {
            this.griglia = ((GrigliaMemento) m).griglia();
        }
    }

    public int[][] getGriglia() {
        return copiaGriglia(griglia);
    }

    private int[][] copiaGriglia(int[][] originale) {
        int[][] copia = new int[originale.length][originale[0].length];
        for (int i = 0; i < originale.length; i++) {
            System.arraycopy(originale[i], 0, copia[i], 0, originale[i].length);
        }
        return copia;
    }

    private record GrigliaMemento(int[][] griglia) implements Memento {
        private GrigliaMemento(int[][] griglia) {
            this.griglia = copiaGriglia(griglia);
        }

        private static int[][] copiaGriglia(int[][] originale) {
            int[][] copia = new int[originale.length][originale[0].length];
            for (int i = 0; i < originale.length; i++) {
                System.arraycopy(originale[i], 0, copia[i], 0, originale[i].length);
            }
            return copia;
        }
    }
}
