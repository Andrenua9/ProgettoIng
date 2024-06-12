package pr.backtracking;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Configurazione implements Serializable {

    private static final long serialVersionUID=1L;

    private int dimensione; //le cifre si potranno disporre da 1 a dimensione(n)
    private int numeroBlocchi;
    private Blocco[] blocchi= new Blocco[numeroBlocchi];
    private int maxSol;


    public Configurazione(){};


    public int getDimensione() {
        return dimensione;
    }

    public Blocco[] getBlocchi() {
        return blocchi;
    }

    public int getNumeroBlocchi() {
        return numeroBlocchi;
    }

    public int getMaxSol() {
        return maxSol;
    }
    public void setMaxSol(int maxSol){
        this.maxSol=maxSol;
    }

    public void setDimensione(int dimensione) {
        if (dimensione < 3 || dimensione > 6) {
            throw new IllegalArgumentException("Dimensione non supportata. Deve essere tra 3 e 6.");
        }
        this.dimensione = dimensione;
    }

    public void setBlocchi(Blocco[] blocchi) {
        this.blocchi = blocchi;
        if (blocchi != null) {
            numeroBlocchi = blocchi.length;
        } else {
            numeroBlocchi = 0;
        }
    }
    public void setNumeroBlocchi(int numeroBlocchi) {
        if (numeroBlocchi < lowerBound() || numeroBlocchi > upperBound()) {
            throw new IllegalArgumentException("Numero di blocchi non valido.");
        }
        this.numeroBlocchi = numeroBlocchi;
    }

    public int lowerBound(){

        return dimensione;
    }

    public int upperBound(){

        return (dimensione*dimensione+1)/2;
    }


    public void salvaObject() throws IOException {

        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) { // Se l'utente ha cliccato su "Salva"
            String nomeFile = fileChooser.getSelectedFile().getAbsolutePath();

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFile));
            oos.writeObject(this);
            oos.close();
        }
    }

    public Configurazione ripristinaObject() throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) { // Verifica se l'utente ha selezionato un file
            String nomeFile = fileChooser.getSelectedFile().getAbsolutePath();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile));

            return (Configurazione) ois.readObject();
        } else {
            return null;
        }
    }

    }




