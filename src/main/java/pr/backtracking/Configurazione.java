package pr.backtracking;

import java.util.Scanner;

public class Configurazione {

    private int dimensione; //le cifre si potranno disporre da 1 a dimensione(n)
    private int numeroBlocchi;
    private Blocco[] blocchi= new Blocco[numeroBlocchi];


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

    public void setDimensione(int dimensione) {
        if (dimensione < 3 || dimensione > 6) {
            throw new IllegalArgumentException("Dimensione non supportata. Deve essere tra 3 e 6.");
        }
        this.dimensione = dimensione;
    }

    public void setBlocchi(Blocco[] blocchi) {
        this.blocchi = blocchi;
        // Aggiorna il numero di blocchi ogni volta che l'array viene modificato
        if (blocchi != null) {
            numeroBlocchi = blocchi.length;
        } else {
            numeroBlocchi = 0;
        }
    }
    public void setNumeroBlocchi(int numeroBlocchi) {
        if (numeroBlocchi < upperBound() || numeroBlocchi > lowerBound()) {
            throw new IllegalArgumentException("Numero di blocchi non valido.");
        }
        this.numeroBlocchi = numeroBlocchi;
    }

    public int upperBound(){
        return dimensione;
    }
    public int lowerBound(){
        return (dimensione*dimensione+1)/2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput) {
            try {
                Configurazione config = new Configurazione();

                System.out.print("Inserisci la dimensione della griglia (3-6): ");
                int dimensione = scanner.nextInt();
                config.setDimensione(dimensione);

                System.out.print("Inserisci il numero di blocchi: ");
                int numeroBlocchi = scanner.nextInt();
                config.setNumeroBlocchi(numeroBlocchi);

                Blocco[] blocchi = new Blocco[numeroBlocchi];
                for (int i = 0; i < numeroBlocchi; i++) {
                    System.out.println("Blocco " + (i + 1) + ":");
                    System.out.print("  - Dimensione: ");
                    int bloccoDimensione = scanner.nextInt();

                    System.out.print("  - Operazione (+, -, *, /): ");
                    String operazione = scanner.next();

                    System.out.print("  - Risultato: ");
                    int risultato = scanner.nextInt();

                    Cella[] celle = new Cella[bloccoDimensione];
                    for (int j = 0; j < bloccoDimensione; j++) {
                        System.out.print("    - Cella " + (j + 1) + " (formato 'riga,colonna'): ");
                        String cellaCoords = scanner.next();
                        String[] cellaParts = cellaCoords.split(",");
                        int riga = Integer.parseInt(cellaParts[0]);
                        int colonna = Integer.parseInt(cellaParts[1]);
                        celle[j] = new Cella(riga, colonna);
                    }

                    blocchi[i] = new Blocco(bloccoDimensione, operazione, risultato, celle);
                }

                config.setBlocchi(blocchi);

                validInput = true;
                System.out.println("Configurazione creata con successo!");

            } catch (IllegalArgumentException e) {
                System.out.println("Errore: " + e.getMessage());
                System.out.println("Per favore, reinserisci i valori.");
                scanner.nextLine(); // Pulire l'input buffer
                continue; // Ritorna all'inizio del ciclo while
            } catch (Exception e) {
                System.out.println("Errore imprevisto: " + e.getMessage());
                scanner.nextLine(); // Pulire l'input buffer
                continue; // Ritorna all'inizio del ciclo while
            }
        }

        scanner.close();
    }
    }


