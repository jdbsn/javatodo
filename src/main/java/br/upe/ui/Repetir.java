package br.upe.ui;

import javax.swing.*;
import java.awt.event.*;

public class Repetir extends JDialog {
    private JPanel contentPane;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private JTextField tempoField;
    private JComboBox comboBox;
    private JTextField qtdField;

    private int tempo;
    private int qtdVezes;
    private String texto;

    public Repetir() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvar();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cancelar();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setVisible(true);
    }

    private void salvar() {
        if(!tempoField.getText().matches("[0-9]+") || !qtdField.getText().matches("[0-9]+")) {
            btnSalvar.disable();
        } else {
            int unidade = comboBox.getSelectedIndex();
            tempo = Integer.parseInt(tempoField.getText());
            qtdVezes = Integer.parseInt(qtdField.getText());

            if(tempo == 0) tempo = 1;
            texto = "Repetir a cada " + tempo + " dia(s), " + qtdVezes + " vez(es)";

            if(unidade == 1) {
                texto = "Repetir a cada " + tempo + " semana(s), " + qtdVezes + " vez(es)";
                tempo *= 7;
            } else if(unidade == 2) {
                texto = "Repetir a cada " + tempo + " mes(es), " + qtdVezes + " vez(es)";
                tempo *= 30;
            } else if(unidade == 3) {
                texto = "Repetir a cada " + tempo + " ano(s), " + qtdVezes + " vez(es)";
                tempo *= 365;
            }
            dispose();
        }
    }

    private void cancelar() {
        // add your code here if necessary
        dispose();
    }

    public int getTempo() {
        return tempo;
    }

    public int getQtdVezes() {
        return qtdVezes;
    }

    public String getTexto() {
        return texto;
    }

}
