package gui.griglia;

import pr.backtracking.Cella;
import pr.backtracking.Configurazione;
import pr.backtracking.GiocoKenKen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        this.grigliaCopia= new int[dimensione][dimensione];

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(g);

        celle = new JTextField[dimensione][dimensione];

        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                celle[i][j] = new JTextField();
                gridPanel.add(celle[i][j]);


                celle[i][j].setFont(new Font(celle[i][j].getFont().getName(), Font.PLAIN, 30));
                celle[i][j].setHorizontalAlignment(JTextField.CENTER);

                //Salvo il contenuto delle celle in una griglia
                String text = celle[i][j].getText();
                if (!text.isEmpty()) {
                    try {
                        grigliaCopia[i][j] = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        grigliaCopia[i][j] = 0; // or handle error as appropriate
                    }
                } else {
                    grigliaCopia[i][j] = 0; // or handle empty cell as appropriate
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


        this.add(gridPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        abilita = new JButton("Abilita correzione");
        soluzioni = new JButton("Visualizza soluzioni");
        esci = new JButton("Esci");

        buttonPanel.add(abilita);
        buttonPanel.add(soluzioni);
        buttonPanel.add(esci);

        Timer timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abilitaCorrezione(); // Chiamato quando il timer scatta
            }
        });

        abilita.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!timer.isRunning()) {
                    timer.start();
                }
            }
        });


        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void abilitaCorrezione() {
        GiocoKenKen g = new GiocoKenKen(c,grigliaCopia);
        for(int i = 0; i < grigliaCopia.length; i++) {
            for(int j = 0; j < grigliaCopia[i].length; j++) {
                if(g.assegnabile(grigliaCopia[i][j],new Cella(i,j))){
                    celle[i][j].setBackground(Color.green);
                }
                else
                    celle[i][j].setBackground(Color.red);
            }
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
