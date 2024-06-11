package gui.griglia;

import pr.backtracking.Blocco;
import pr.backtracking.Cella;
import pr.backtracking.Configurazione;
import pr.backtracking.GiocoKenKen;
import pr.memento.Griglia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiocaPartita extends JPanel {
    private Configurazione c;
    private JTextField[][] celle;
    private int[][] grigliaCopia;
    private JPanel gridPanel;
    private Map<JLabel, Point> etichettePosizioni = new HashMap<>();
    private List<Griglia> soluzioni;
    private JFrame soluzioniFrame;
    private int currentSolutionIndex = 0;

    public GiocaPartita(Configurazione c) {
        this.c = c;
        this.setLayout(new BorderLayout());

        int dimensione = c.getDimensione();
        this.grigliaCopia = new int[dimensione][dimensione];

        JLayeredPane pane = new JLayeredPane();
        this.add(pane, BorderLayout.CENTER);

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridBagLayout());
        gridPanel.setBounds(0, 0, dimensione * 100, dimensione * 100); // Imposta la dimensione del pannello

        celle = new JTextField[dimensione][dimensione];

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                celle[i][j] = new JTextField();
                celle[i][j].setFont(new Font(celle[i][j].getFont().getName(), Font.PLAIN, 30));
                celle[i][j].setHorizontalAlignment(JTextField.CENTER);
                gbc.gridx = j;
                gbc.gridy = i;
                gridPanel.add(celle[i][j], gbc);


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

        aggiungiEtichetta(pane);
        coloraCelle();
        pane.add(gridPanel, JLayeredPane.DEFAULT_LAYER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton visualizzaSoluzioni = new JButton("Visualizza soluzioni");
        JButton abilitaCorrezione = new JButton("Abilita Correzione");
        JButton esci = new JButton("Esci");

        buttonPanel.add(visualizzaSoluzioni);
        buttonPanel.add(abilitaCorrezione);
        buttonPanel.add(esci);
        this.add(buttonPanel, BorderLayout.SOUTH);

        esci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        abilitaCorrezione.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abilitaCorrezione();
                Timer timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        coloraCelle();
                        ((Timer) e.getSource()).stop();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        visualizzaSoluzioni.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int scelta = JOptionPane.showConfirmDialog(null, "Ti arrendi?", "Resa", JOptionPane.YES_NO_OPTION);
                if (scelta == JOptionPane.YES_OPTION) {
                    visualizzaSoluzioni();
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(GiocaPartita.this);
                    frame.dispose();
                }
            }
        });

        // Riadatta la dimensione della finestra e delle etichette
        pane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeGrid(pane.getSize());
            }
        });

        soluzioni = calcolaSoluzioni();
    }

    // Uso questo metodo ogni volta che viene abilitata la correzione per controllare la griglia corrente
    private void aggiornaGriglia() {
        for (int i = 0; i < c.getDimensione(); i++) {
            for (int j = 0; j < c.getDimensione(); j++) {
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
            }
        }
        Griglia gg = new Griglia(grigliaCopia);
        for (Griglia soluzione : soluzioni) {
            if (gg.equals(soluzione)) {
                JOptionPane.showMessageDialog(null, "Congratulazioni! Hai vinto!", "Vittoria", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }


    private void visualizzaSoluzioni() {
        if (soluzioniFrame == null) {
            soluzioniFrame = new JFrame("Soluzioni");
            soluzioniFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            soluzioniFrame.setSize(500, 500);
            soluzioniFrame.setLayout(new BorderLayout());

            JButton next = new JButton("Next");
            JButton previous = new JButton("Previous");

            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (soluzioni != null && currentSolutionIndex < soluzioni.size() - 1) {
                        currentSolutionIndex++;
                        aggiornaGrigliaSoluzione();
                    }
                }
            });

            previous.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (soluzioni != null && currentSolutionIndex > 0) {
                        currentSolutionIndex--;
                        aggiornaGrigliaSoluzione();
                    }
                }
            });


            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(previous);
            buttonPanel.add(next);

            soluzioniFrame.add(gridPanel, BorderLayout.CENTER);
            soluzioniFrame.add(buttonPanel, BorderLayout.SOUTH);
        }
        aggiornaGrigliaSoluzione();
        soluzioniFrame.setVisible(true);
    }

    private List<Griglia> calcolaSoluzioni() {
        GiocoKenKen kenKen = new GiocoKenKen(c, grigliaCopia);
        kenKen.risolvi(c.getMaxSol()); // Devo cambiare con c.getMaxSol()
        soluzioni = kenKen.getSoluzioni();
        System.out.println(soluzioni.toString());
        return soluzioni;
    }

    private void aggiornaGrigliaSoluzione() {
        if (soluzioni != null && !soluzioni.isEmpty() && currentSolutionIndex < soluzioni.size()) {
            Griglia soluzione = soluzioni.get(currentSolutionIndex);
            for (int i = 0; i < c.getDimensione(); i++) {
                for (int j = 0; j < c.getDimensione(); j++) {
                    celle[i][j].setText(String.valueOf(soluzione.getEl(i, j)));
                    celle[i][j].setEditable(false);
                }
            }
        }
    }



    private void aggiungiEtichetta(JLayeredPane pane) {
        for (Blocco b : c.getBlocchi()) {
            Cella prima = b.getCelle()[0];
            JLabel vincolo = new JLabel(b.getOperazione() + " " + b.getRisultato());
            vincolo.setFont(new Font("Arial", Font.BOLD, 18));
            vincolo.setForeground(Color.YELLOW);
            vincolo.setVisible(true);
            etichettePosizioni.put(vincolo, new Point(prima.getRow(), prima.getColumn())); //Memorizzo la posizione dell'etichetta in una mappa
            pane.add(vincolo, JLayeredPane.PALETTE_LAYER);
        }
    }

    private void coloraCelle() {
        int i = 0;
        for (Blocco b : c.getBlocchi()) {
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

    private void posizionaEtichetta(JLabel label, int row, int col, Dimension size) {
        int dimensione = c.getDimensione();
        int cellWidth = size.width / dimensione;
        int cellHeight = size.height / dimensione;
        label.setBounds(col * cellWidth + 3, row * cellHeight, cellWidth, 20);
    }

    private void resizeGrid(Dimension size) {
        int dimensione = c.getDimensione();
        int cellWidth = size.width / dimensione;
        int cellHeight = size.height / dimensione;

        gridPanel.setBounds(0, 0, size.width, size.height);

        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                celle[i][j].setPreferredSize(new Dimension(cellWidth, cellHeight));
            }
        }

        for (Map.Entry<JLabel, Point> entry : etichettePosizioni.entrySet()) {
            JLabel label = entry.getKey();
            Point pos = entry.getValue();
            posizionaEtichetta(label, pos.x, pos.y, size);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void abilitaCorrezione() {
        aggiornaGriglia();
        Griglia gg = new Griglia(grigliaCopia);
        Griglia soluzionePiuSimile = trovaSoluzionePiuSimile(gg);
        coloraCelle(gg, soluzionePiuSimile);
    }

    private Griglia trovaSoluzionePiuSimile(Griglia gg) {
        Griglia soluzionePiuSimile = null;
        int maxSimilitudine = -1;

        for (Griglia soluzione : soluzioni) {
            int similitudine = calcolaSimilitudine(gg, soluzione);
            if (similitudine > maxSimilitudine) {
                maxSimilitudine = similitudine;
                soluzionePiuSimile = soluzione;
            }
        }

        return soluzionePiuSimile;
    }

    private int calcolaSimilitudine(Griglia gg, Griglia soluzione) {
        int similitudine = 0;
        for (int i = 0; i < c.getDimensione(); i++) {
            for (int j = 0; j < c.getDimensione(); j++) {
                if (gg.getEl(i, j) == (soluzione.getEl(i, j))) {
                    similitudine++;
                }
            }
        }
        return similitudine;
    }

    private void coloraCelle(Griglia gg, Griglia soluzione) {
        for (int i = 0; i < c.getDimensione(); i++) {
            for (int j = 0; j < c.getDimensione(); j++) {
                JTextField cella = celle[i][j];
                if (gg.getEl(i, j) != 0) {
                    if (gg.getEl(i, j) == (soluzione.getEl(i, j))) {
                        cella.setBackground(Color.GREEN);
                    } else {
                        cella.setBackground(Color.RED);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Configurazione config = new Configurazione();
        config.setDimensione(3);

        JFrame frame = new JFrame("Gioca Partita");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.add(new GiocaPartita(config), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
