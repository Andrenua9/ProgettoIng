package gui;

import gui.griglia.GiocaPartita;
import pr.backtracking.Configurazione;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InterfacciaGUI extends JFrame {

    public InterfacciaGUI() {
        // Impostazioni JFrame
        setTitle("KenKen");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        JLabel titleLabel = new JLabel("KENKEN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 56));
        titleLabel.setForeground(Color.BLUE); // Colore del testo
        titleLabel.setBackground(Color.WHITE);
        titleLabel.setOpaque(true);

        // Caricamento dell'immagine di sfondo
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/unnamed.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);
        backgroundLabel.setLayout(new BorderLayout());

        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        JButton giocaPartita = new JButton("Gioca partita");
        JButton esci = new JButton("Esci");


        esci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        backgroundLabel.add(mainPanel);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setTasto(esci,false,buttonPanel);
        setTasto(giocaPartita,true,buttonPanel);

        giocaPartita.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                secondaPagina(mainPanel);
            }
        });

        setVisible(true);
    }


//Funzione per gestire le caratteristiche dei pulsanti
    private void setTasto(JButton b,boolean leftRight,JPanel panel){
        Dimension buttonSize = new Dimension(200, 50);
        b.setPreferredSize(buttonSize);

        Color borderColor = new Color(0,0,255,150);
        b.setBorder(new LineBorder(borderColor,6));
        if(leftRight){
            JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            leftButtonPanel.setOpaque(false);
            leftButtonPanel.add(b);
            panel.add(leftButtonPanel, BorderLayout.WEST);
        }
        else{
            JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            rightButtonPanel.setOpaque(false);
            rightButtonPanel.add(b);
            panel.add(rightButtonPanel, BorderLayout.EAST);
        }
    }



    private void secondaPagina(JPanel panel) {
        getContentPane().remove(panel);
        validate();

        JButton crea = new JButton("Crea Configurazione");
        JButton carica = new JButton("Carica da File");

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false); // Trasparenza
        add(mainPanel);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false); // Trasparenza
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        crea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CreaConfig().setVisible(true);
            }
        });

        // Carica configurazione da file
        carica.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Configurazione c = new Configurazione();
                try {
                    c = (Configurazione) c.ripristinaObject();
                    JFrame frame = new JFrame("Gioca Partita");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setLayout(new BorderLayout());
                    frame.add(new GiocaPartita(c), BorderLayout.CENTER);
                    frame.setVisible(true);
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Errore durante il caricamento del file.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setTasto(crea,true,buttonPanel);
        setTasto(carica,false,buttonPanel);

        getContentPane().add(mainPanel);
        validate();
    }

    public static void main(String[] args) {
        new InterfacciaGUI();
    }
}
