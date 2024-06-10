package pr.memento;

import java.util.Arrays;

public class Griglia implements Originator {
    private int[][] griglia;

    public Griglia(int dimensione) {
        griglia = new int[dimensione][dimensione];
    }

    public Griglia(int[][] griglia) {
        this.griglia = copiaGriglia(griglia);
    }

    public int getEl(int i, int j) {
        return griglia[i][j];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Griglia other = (Griglia) obj;
        return Arrays.deepEquals(this.griglia, other.griglia);
    }

    @Override
    public Memento getMemento() {
        return new GrigliaMemento(getGriglia());
    }

    @Override
    public void setMemento(Memento m) {
        if (m instanceof GrigliaMemento) {
            this.griglia = copiaGriglia(((GrigliaMemento) m).griglia());
        }
    }

    public int[][] getGriglia() {
        return copiaGriglia(griglia);
    }

    private int[][] copiaGriglia(int[][] originale) {
        int[][] copia = new int[originale.length][];
        for (int i = 0; i < originale.length; i++) {
            copia[i] = Arrays.copyOf(originale[i], originale[i].length);
        }
        return copia;
    }

    @Override
    public String toString() {
        return "Griglia{" +
                "griglia=" + Arrays.deepToString(griglia) +
                '}';
    }

    private static class GrigliaMemento implements Memento {
        private final int[][] griglia;

        private GrigliaMemento(int[][] griglia) {
            this.griglia = copiaGriglia(griglia);
        }

        public int[][] griglia() {
            return copiaGriglia(griglia);
        }

        private static int[][] copiaGriglia(int[][] originale) {
            int[][] copia = new int[originale.length][];
            for (int i = 0; i < originale.length; i++) {
                copia[i] = Arrays.copyOf(originale[i], originale[i].length);
            }
            return copia;
        }
    }
}
