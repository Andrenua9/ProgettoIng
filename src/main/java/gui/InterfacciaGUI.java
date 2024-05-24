package gui;
import javax.swing.*;

public class InterfacciaGUI extends JFrame {

    public InterfacciaGUI() {
        setTitle("La Mia Finestra con JPanel");

        // Impostare la dimensione della finestra
        setSize(400, 300);

        // Impostare l'operazione di chiusura della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creare un pannello (JPanel)
        JPanel pannello = new JPanel();

        // Aggiungere un pulsante al pannello
        JButton giocaParita = new JButton("Gioca partita");
        pannello.add(giocaParita);
        giocaParita.setBounds(10,15,20,50);
        JButton opzioni = new JButton("Opzioni");
        pannello.add(opzioni);
        JButton esci = new JButton("Esci");
        pannello.add(esci);


        // Aggiungere il pannello al frame
        add(pannello);
    }

    public static void main(String[] args) {
        // Creare un'istanza della finestra
        InterfacciaGUI finestra = new InterfacciaGUI();

        // Rendere visibile la finestra
        finestra.setVisible(true);
    }
}
