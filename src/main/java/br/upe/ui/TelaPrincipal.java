package br.upe.ui;

import br.upe.controller.TarefaControlador;
import br.upe.model.Tarefa;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TelaPrincipal {
    private JPanel pnlMain;
    private JTextField txtDescricaoTarefa;
    private JButton btnAdicionarTarefa;
    private JPanel pnlAdicionar;
    private JTable tblTarefas;
    private JCheckBox chkExibirFinalizadas;
    private JButton btnRepetir;
    private List<Tarefa> tarefas;
    private TarefaControlador controlador;
    private int tempo;
    private int unidade;
    private int qtdVezes;
    private Repetir repetir = new Repetir();

    public TelaPrincipal() {
        super();
        tarefas = new ArrayList<>();
        btnAdicionarTarefa.addActionListener(e -> {
            adicionarTarefa(txtDescricaoTarefa.getText(), tempo, unidade, qtdVezes);
            txtDescricaoTarefa.setText("");
            btnRepetir.setText("Repetir");
            qtdVezes = 0;
        });
        chkExibirFinalizadas.addActionListener(e -> {
            boolean selecionado = ((JCheckBox) e.getSource()).isSelected();
            controlador.exibirFinalizadas(selecionado);
        });
        btnRepetir.addActionListener(e -> {
            repetir.exibir();

            if(repetir.isRepetir()) {
                tempo = repetir.getTempo();
                unidade = repetir.getUnidade();
                qtdVezes = repetir.getQtdVezes();
            }

            if(repetir.getTexto() != null) btnRepetir.setText(repetir.getTexto());
        });
    }

    private void adicionarTarefa(String texto, int tempo, int unidade, int qtdVezes) {
        Tarefa tarefa = new Tarefa(texto, tarefas.size(), LocalDate.now());
        controlador.adicionarTarefaAtiva(tarefa);

        if(repetir.isRepetir()) {
            LocalDate data = LocalDate.now();
            for (int i = 0; i < qtdVezes; i++) {
                data = calcular(tempo, unidade, data);
                controlador.adicionarTarefaAtiva(new Tarefa(texto, tarefas.size(), data));
            }

        }
        tblTarefas.revalidate();
        tblTarefas.repaint();
    }

    private LocalDate calcular(int tempo, int unidade, LocalDate data) {
        if (unidade == 0) {
            return data.plusDays(tempo);
        } else if (unidade == 1) {
            return data.plusWeeks(tempo);
        } else if (unidade == 2) {
            return data.plusMonths(tempo);
        }
        return data.plusYears(tempo);
    }

    public JPanel getPnlMain() {
        return this.pnlMain;
    }

    private void createUIComponents() {
        controlador = new TarefaControlador();
        tblTarefas = new JTable(controlador.getTarefaTableModel());
        tblTarefas.getColumnModel().getColumn(0).setMaxWidth(20);
    }

}
