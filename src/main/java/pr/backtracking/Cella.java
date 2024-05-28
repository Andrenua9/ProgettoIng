package pr.backtracking;

import java.io.Serializable;
import java.util.Objects;

public class Cella implements Serializable {
    private static final long serialVersionUID = 123567L;
    private int row;
    private int column;

    public Cella(){};

    public Cella(int row , int column){
        if(row<0 || column<0)
            throw new IllegalArgumentException("Indice minore di 0 non valido!");
        this.row=row;
        this.column=column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }



    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cella cella = (Cella) o;
        return row == cella.row && column == cella.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Cella{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
