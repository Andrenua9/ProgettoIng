package gui.griglia;

import pr.backtracking.Blocco;
import pr.backtracking.Cella;
import pr.backtracking.Configurazione;
import pr.backtracking.GiocoKenKen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*

Aggiungere etichetta con il risultato e l'operazione nei blocchi, colorare i blocchi di colore diverso
Visualizzazione soluzioni (tasti next e previous)
Memento nel backtracking
Vedere che altri pattern utilizzare
Abbellire la grafica
 */

public class GiocaPartita extends JPanel {
    private Configurazione c;
    private GridLayout g;
    private JTextField[][] celle;
    private JButton abilita;
    private JButton soluzioni;
    private JButton esci;
    private int[][] grigliaCopia; //salvo ciò che inserisce l'utente

    public GiocaPartita(Configurazione c) {
        this.c = c;
        this.setLayout(new BorderLayout());

        int dimensione = c.getDimensione();
        g = new GridLayout(dimensione, dimensione);
        this.grigliaCopia = new int[dimensione][dimensione];

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(g);

        gridPanel.setOpaque(false);

        celle = new JTextField[dimensione][dimensione];

        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                celle[i][j] = new JTextField();
                gridPanel.add(celle[i][j]);


                celle[i][j].setFont(new Font(celle[i][j].getFont().getName(), Font.ROMAN_BASELINE, 30));
                celle[i][j].setHorizontalAlignment(JTextField.CENTER);

                //Salvo il contenuto delle celle in una griglia
                String text = celle[i][j].getText();
                if (!text.isEmpty()) {
                    try {
                        grigliaCopia[i][j] = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        grigliaCopia[i][j] = 0;
                    }
                } else {
                    grigliaCopia[i][j] = 0;
                }


                //Per ogni casella di testo faccio si che l'utente possa inserire solo un numero da 1 a n
                celle[i][j].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char ch = e.getKeyChar();
                        JTextField source = (JTextField) e.getSource();
                        if (!Character.isDigit(ch)) {
                            e.consume();
                        } else {
                            int digit = Character.getNumericValue(ch);
                            if (digit < 1 || digit > c.getDimensione()) {
                                e.consume();
                            } else if (!source.getText().isEmpty()) {
                                e.consume();
                            }
                        }
                    }
                });
            }
        }

        aggiungiEtichetta(gridPanel);

        this.add(gridPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        abilita = new JButton("Abilita correzione");
        soluzioni = new JButton("Visualizza soluzioni");
        esci = new JButton("Esci");

        buttonPanel.add(abilita);
        buttonPanel.add(soluzioni);
        buttonPanel.add(esci);
        add(buttonPanel, BorderLayout.SOUTH);


        abilita.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abilitaCorrezione();
                Timer timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < grigliaCopia.length; i++) {
                            for (int j = 0; j < grigliaCopia[i].length; j++) {
                                celle[i][j].setBackground(Color.white);
                            }
                        }
                        ((Timer) e.getSource()).stop();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

    }
    public void abilitaCorrezione() {
        int[][] gg = aggiornaGriglia(grigliaCopia);
        GiocoKenKen g = new GiocoKenKen(c, gg);
        for (int i = 0; i < gg.length; i++) {
            for (int j = 0; j < gg[i].length; j++) {
                if (gg[i][j] != 0) {
                    if (g.assegnabile(gg[i][j], new Cella(i, j))) {
                        celle[i][j].setBackground(Color.green);
                    } else
                        celle[i][j].setBackground(Color.red);
                }
            }
        }
    }

    private int[][] aggiornaGriglia(int[][] griglia) {
        int [][] nuovaGriglia = new int[griglia.length][griglia[0].length];
        for (int i = 0; i < griglia.length; i++) {
            for (int j = 0; j < griglia[i].length; j++) {
                String text = celle[i][j].getText();
                if (!text.isEmpty()) {
                    try {
                        nuovaGriglia[i][j] = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        nuovaGriglia[i][j] = 0;
                    }
                } else {
                    nuovaGriglia[i][j] = 0;
                }
            }
        }
        return nuovaGriglia;
    }

    private void aggiungiEtichetta(JPanel gridPanel) {
        int i = 0;
        for (Blocco b : c.getBlocchi()) {
            Cella first = b.getCelle()[0];
            JLabel vincolo = new JLabel(b.getRisultato() + " " + b.getOperazione());
            JTextField casella = celle[first.getRow()][first.getColumn()];

            vincolo.setBounds(casella.getX()+3,casella.getY()-(40-(c.getDimensione()-2)*6), casella.getWidth(), casella.getHeight());
            vincolo.setOpaque(true);
            vincolo.setBackground(new Color(255, 255, 255, 200));

            gridPanel.add(vincolo, JLayeredPane.PALETTE_LAYER);

            for (Cella c : b.getCelle()) {
                try {
                    celle[c.getRow()][c.getColumn()].setBackground(new Color(90 + i, 90 + i, 90 + i));
                } catch (IllegalArgumentException e) {
                    i = 0;
                    celle[c.getRow()][c.getColumn()].setBackground(new Color(90, 90, 90));
                }
            }
            i += 33;
        }

    }



    public static void main(String[] args) {
        // Example usage
        Configurazione config = new Configurazione();
        config.setDimensione(3); // Set desired dimension

        JFrame frame = new JFrame("Gioca Partita");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.add(new GiocaPartita(config), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
