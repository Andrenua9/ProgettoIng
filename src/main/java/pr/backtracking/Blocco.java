package pr.backtracking;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Blocco {
    private int dimensione;
    private String operazione;
    private int risultato;
    private Cella[] celle;


    public Blocco(){}
    public Blocco(int dimensione, String operazione,int risultato,Cella[]celle){
        this.dimensione=dimensione;
        this.operazione=operazione;
        this.risultato=risultato;
        this.celle=celle;

    }

    public int getDimensione() {
        return dimensione;
    }

    public String getOperazione() {
        return operazione;
    }

    public int getRisultato() {
        return risultato;
    }

    public Cella[] getCelle() {
        return celle;
    }

    public void setDimensione(int dimensione) {
        if(this.dimensione >4)
            throw new IllegalArgumentException("Dimensione massima blocco superata!");
        this.dimensione = dimensione;
    }

    public void setOperazione(String operazione) {
        if(operazione.equals("-")|| operazione.equals("/"))
             setDimensione(2);
        this.operazione = operazione;
    }

    public void setRisultato(int risultato) {
        this.risultato = risultato;
    }

    public void setCelle(Cella[] celle) {
        this.celle = celle;
    }

    //Due blocchi sono uguali se sono della stessa dimensione e coprono le stesse celle
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blocco blocco = (Blocco) o;
        Set<Cella> c1 = new HashSet<>(Arrays.asList(this.celle));
        for(Cella cc : blocco.celle){
            c1.remove(cc);
        }
        return c1.isEmpty();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dimensione);
        result = 31 * result + Arrays.hashCode(celle);
        return result;
    }

    @Override
    public String toString() {
        return "Blocco{" +
                "dimensione=" + dimensione +
                ", operazione='" + operazione + '\'' +
                ", risultato=" + risultato +
                ", celle=" + Arrays.toString(celle) +
                '}';
    }
}
