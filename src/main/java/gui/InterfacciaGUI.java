package gui;

import pr.backtracking.Configurazione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfacciaGUI extends JFrame {

    public InterfacciaGUI() {
        // Impostazioni JFrame
        setTitle("KenKen");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Caricamento dell'immagine di sfondo
      //  ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/kenken.jpg"));


        JButton giocaPartita = new JButton("Gioca partita");
        JButton opzioni = new JButton("Opzioni");
        JButton esci = new JButton("Esci");

        esci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Pannello centrale con GridBagLayout per centrare e dimensionare i pulsanti
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        mainPanel.add(buttonPanel, BorderLayout.CENTER);


        // Constraints per centrare e separare i pulsanti
        GridBagConstraints gbc = impostazioniTasti();

        // Imposta dimensioni specifiche per i pulsanti
        Dimension buttonSize = new Dimension(500, 75);
        giocaPartita.setPreferredSize(buttonSize);
        opzioni.setPreferredSize(buttonSize);
        esci.setPreferredSize(buttonSize);

        // Aggiunta dei pulsanti al pannello con i constraints
        buttonPanel.add(giocaPartita, gbc);
        buttonPanel.add(opzioni, gbc);
        buttonPanel.add(esci, gbc);

        giocaPartita.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    secondaPagina(mainPanel);
            }
        });

        setVisible(true);
    }

    private void secondaPagina(JPanel panel ) {
        getContentPane().remove(panel);
        validate();
        JPanel j = new JPanel(new GridBagLayout()) ;
        JButton Crea = new JButton("Crea Configurazione");
        JButton carica = new JButton("Carica da File");


        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        mainPanel.add(buttonPanel, BorderLayout.CENTER);


        Dimension buttonSize = new Dimension(500, 75);
        Crea.setPreferredSize(buttonSize);
        carica.setPreferredSize(buttonSize);

        GridBagConstraints gbc = impostazioniTasti();

        buttonPanel.add(Crea, gbc);
        buttonPanel.add(carica, gbc);

        getContentPane().add(mainPanel);
        validate();
    }

    private GridBagConstraints impostazioniTasti(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 50, 10); // Spazio tra i pulsanti
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        return gbc;
    }



    public Configurazione gestisciConfigurazione() {
        Configurazione c = new Configurazione();
        return c;
    }

    public static void main(String[] args) {
        new InterfacciaGUI();
    }
}
