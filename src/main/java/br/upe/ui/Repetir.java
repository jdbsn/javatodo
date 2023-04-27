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
    private JButton btnLimpar;
    private int tempo = 1;
    private int unidade = 0;
    private int qtdVezes = 1;
    private String texto;
    private boolean repetir = false;

    public Repetir() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);

        btnSalvar.addActionListener(e -> salvar());

        btnCancelar.addActionListener(e -> cancelar());

        btnLimpar.addActionListener(e -> limpar());

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

    }

    public void exibir() {
        tempoField.setText(Integer.toString(this.tempo));
        qtdField.setText(Integer.toString(this.qtdVezes));
        comboBox.setSelectedIndex(unidade);

        this.pack();
        this.setVisible(true);
    }

    private void salvar() {
        if(!tempoField.getText().matches("^0*?[1-9]\\d*$") || !qtdField.getText().matches("^0*?[1-9]\\d*$")) {
            btnSalvar.disable();
        } else {
            unidade = comboBox.getSelectedIndex();
            tempo = Integer.parseInt(tempoField.getText());
            qtdVezes = Integer.parseInt(qtdField.getText());
            texto = "Repetir a cada " + tempo;

            if(unidade == 0) {
                texto += tempo == 1  ? " dia, " : " dias, ";
            } else if(unidade == 1) {
                texto += tempo == 1  ? " semana, " : " semanas, ";
            } else if(unidade == 2) {
                texto += tempo == 1  ? " mês, " : " meses, ";
            } else {
                texto += tempo == 1  ?  " ano, " : " anos, ";
            }
            texto += qtdVezes == 1 ? qtdVezes + " vez" : qtdVezes + " vezes";
            repetir = true;
            dispose();
        }
    }

    private void cancelar() {
        dispose();
    }

    private void limpar() {
        setPadrao();
        dispose();
    }

    public void setPadrao() {
        this.tempo = 1;
        this.unidade = 0;
        this.qtdVezes = 1;
        this.repetir = false;
        this.texto = "Repetir";
    }

    public int getTempo() { return tempo; }

    public int getUnidade() { return unidade; }

    public int getQtdVezes() { return qtdVezes; }

    public String getTexto() { return texto; }

    public boolean isRepetir() { return repetir; }

}
