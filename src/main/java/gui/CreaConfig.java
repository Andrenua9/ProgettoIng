package gui;

import gui.griglia.GiocaPartita;
import pr.backtracking.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class CreaConfig extends JFrame {
    private JTextField dimensioneField;
    private JTextField numeroBlocchiField;
    private JTextField soluzioniMassimeField;
    private JButton aggiungiBloccoButton;
    private JButton salvaButton;
    private JButton giocaButton;
    private Configurazione c;

    private Blocco[] blocchi;
    private int bloccoIndex;

    public CreaConfig() {
        setTitle("Configurazione");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel dimensioneLabel = new JLabel("Dimensione della griglia (3-6):");
        dimensioneField = new JTextField();
        JLabel numeroBlocchiLabel = new JLabel("Numero di blocchi:");
        numeroBlocchiField = new JTextField();
        JLabel soluzioniMassimeLabel = new JLabel("Soluzioni massime:");
        soluzioniMassimeField = new JTextField();
        JLabel bloccoLabel = new JLabel("Aggiungi blocco:");
        aggiungiBloccoButton = new JButton("Aggiungi");

        salvaButton = new JButton("Salva");
        giocaButton = new JButton("Gioca");

        panel.add(dimensioneLabel);
        panel.add(dimensioneField);
        panel.add(numeroBlocchiLabel);
        panel.add(numeroBlocchiField);
        panel.add(soluzioniMassimeLabel);
        panel.add(soluzioniMassimeField);
        panel.add(bloccoLabel);
        panel.add(aggiungiBloccoButton);
        panel.add(salvaButton);
        panel.add(giocaButton);

        blocchi = null;

        aggiungiBloccoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiBlocco();
            }
        });

        giocaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GiocaPartita(c).setVisible(true);
            }
        });

        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    salvaConfigurazione();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(panel);
    }

    private void aggiungiBlocco() {
        try {
            if (blocchi == null) {
                int numeroBlocchi = Integer.parseInt(numeroBlocchiField.getText());
                if (numeroBlocchi < 1) {
                    throw new IllegalArgumentException("Il numero di blocchi deve essere almeno 1.");
                }
                blocchi = new Blocco[numeroBlocchi];
                bloccoIndex = 0;
            }

            if (bloccoIndex >= blocchi.length) {
                JOptionPane.showMessageDialog(this, "Hai già aggiunto il numero massimo di blocchi.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int dimensioneBlocco = Integer.parseInt(JOptionPane.showInputDialog("Inserisci la dimensione del blocco:"));
            if (dimensioneBlocco < 1 || dimensioneBlocco > 4) {
                throw new IllegalArgumentException("La dimensione del blocco deve essere compresa tra 1 e 4.");
            }

            String operazione = JOptionPane.showInputDialog("Inserisci l'operazione (+, -, *, /):");
            if (!operazione.equals("+") && !operazione.equals("-") && !operazione.equals("*") && !operazione.equals("/")) {
                throw new IllegalArgumentException("Operazione non valida. Utilizzare solo '+', '-', '*' o '/'.");
            }

            int risultato = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il risultato:"));

            // Creazione delle celle del blocco
            ArrayList<Cella> celle = new ArrayList<>();
            for (int i = 0; i < dimensioneBlocco; i++) {
                int riga = Integer.parseInt(JOptionPane.showInputDialog("Inserisci la riga della cella " + (i + 1) + ":"));
                int colonna = Integer.parseInt(JOptionPane.showInputDialog("Inserisci la colonna della cella " + (i + 1) + ":"));
                celle.add(new Cella(riga, colonna));
            }

            // Creazione del blocco e aggiunta all'array
            blocchi[bloccoIndex++] = new Blocco(dimensioneBlocco, operazione, risultato, celle.toArray(new Cella[0]));

            JOptionPane.showMessageDialog(this, "Blocco aggiunto con successo.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Errore: Inserisci un numero valido per la dimensione del blocco e il risultato.", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvaConfigurazione() throws IOException {
        try {
            int dimensione = Integer.parseInt(dimensioneField.getText());
            if (dimensione < 3 || dimensione > 6) {
                throw new IllegalArgumentException("Dimensione non supportata. Deve essere tra 3 e 6.");
            }

            int numeroBlocchi = Integer.parseInt(numeroBlocchiField.getText());
            if (numeroBlocchi < 1) {
                throw new IllegalArgumentException("Il numero di blocchi deve essere almeno 1.");
            }

            int soluzioniMassime = Integer.parseInt(soluzioniMassimeField.getText());

            c = new Configurazione();
            c.setDimensione(dimensione);
            c.setNumeroBlocchi(numeroBlocchi);
            c.setBlocchi(blocchi);

            c.salvaObject();

            JOptionPane.showMessageDialog(this, "Configurazione salvata con successo.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Errore: Inserisci un numero valido per la dimensione della griglia, il numero di blocchi e le soluzioni massime.", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio della configurazione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CreaConfig().setVisible(true);
            }
        });
    }
}
