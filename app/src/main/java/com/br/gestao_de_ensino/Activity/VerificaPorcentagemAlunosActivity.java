package com.br.gestao_de_ensino.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.br.gestao_de_ensino.R;

import java.util.List;

import model.GestaoDeEnsinoDAO;

public class VerificaPorcentagemAlunosActivity extends AppCompatActivity {

    private ListView lvAlunoRecuperacao;
    private AdapterGestaoDeEnsino adapter;
    private Button btnQtdRecuperacao;
    private List<GestaoDeEnsino> listaGestaoDeEnsinos;
    private  Boolean verificaSeTem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifica_porcentagem_alunos_recuperacao);

        Toolbar toolbar = findViewById(R.id.toolbar);
        lvAlunoRecuperacao = findViewById(R.id.lvAlunoRecuperacao);
        btnQtdRecuperacao = findViewById(R.id.btnQtdRecuperacao);

        carregarAluno();
        configurarListView();

    }

    private void configurarListView() {
        lvAlunoRecuperacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                GestaoDeEnsino gestaoDeEnsinoSelecionado = listaGestaoDeEnsinos.get(position);
                Intent intent = new Intent(VerificaPorcentagemAlunosActivity.this, FormularioSubstitutivaActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idGestaoDeEnsino", gestaoDeEnsinoSelecionado.id);
                startActivity(intent);
            }
        });

        lvAlunoRecuperacao.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                GestaoDeEnsino gestaoDeEnsinoSelecionado = listaGestaoDeEnsinos.get(position);
                excluirAluno(gestaoDeEnsinoSelecionado);
                return true;
            }
        });

    }

    private void excluirAluno(GestaoDeEnsino gestaoDeEnsino) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setIcon(android.R.drawable.ic_input_delete);
        alerta.setTitle(R.string.txtAtencao_v1);
        alerta.setMessage(R.string.txtConfirmaExclusao_v1);
        alerta.setNeutralButton(R.string.txtCancela_v1, null);
        alerta.setPositiveButton(R.string.txtSim_v1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GestaoDeEnsinoDAO.excluir(gestaoDeEnsino.id, VerificaPorcentagemAlunosActivity.this);
                carregarAluno();
            }
        });
        alerta.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarAluno();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void carregarAluno() {
        listaGestaoDeEnsinos = GestaoDeEnsinoDAO.getRecuperacao(this);
        adapter = new AdapterGestaoDeEnsino(this, listaGestaoDeEnsinos);
        lvAlunoRecuperacao.setAdapter(adapter);
    }
}