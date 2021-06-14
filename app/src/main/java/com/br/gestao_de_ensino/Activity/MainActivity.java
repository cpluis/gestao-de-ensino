package com.br.gestao_de_ensino.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.br.gestao_de_ensino.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import loginFirebase.LoginActivity;
import model.GestaoDeEnsinoDAO;

public class MainActivity extends AppCompatActivity {

    private ListView lvAluno;
    private RelativeLayout llFundoLista;
    private AdapterGestaoDeEnsino adapter;
    private List<GestaoDeEnsino> listaGestaoDeEnsinos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btVerificaRecuperacao = findViewById(R.id.btVerificaRecuperacao);
        Button btnQtdRecuperacao = findViewById(R.id.btnQtdRecuperacao);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                intent.putExtra("acao", "novo");
                startActivity(intent);
            }
        });

        btVerificaRecuperacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(verificaSeTemAlgo()){
                    naoHaRecuperacao();
                }else{
                    Intent intent = new Intent(MainActivity.this, VerificaPorcentagemAlunosActivity.class);
                    startActivity(intent);

                }
            }
        });

        btnQtdRecuperacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicadores();
            }
        });

        lvAluno = findViewById(R.id.lvAluno);
        carregarAluno();
        configurarListView();
    }

    private Boolean verificaSeTemAlgo() {
        listaGestaoDeEnsinos = GestaoDeEnsinoDAO.getRecuperacao(this);
        return listaGestaoDeEnsinos.isEmpty();
    }

    private void configurarListView() {

        lvAluno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                GestaoDeEnsino gestaoDeEnsinoSelecionado = listaGestaoDeEnsinos.get(position);

                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idGestaoDeEnsino", gestaoDeEnsinoSelecionado.id);
                startActivity(intent);
            }
        });

        lvAluno.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                GestaoDeEnsino gestaoDeEnsinoSelecionado = listaGestaoDeEnsinos.get(position);
                excluirAluno(gestaoDeEnsinoSelecionado);
                return true;
            }
        });

    }

    private void naoHaRecuperacao(){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
        String txtRecuperacao = String.format(getString(R.string.naoharecuperacao_v1));

        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.activity_vazio, null);
        alertadd.setView(view);
        alertadd.setNeutralButton(R.string.Aqui_v1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
            }
        });
        alertadd.show();
    }

    private void indicadores(){
        int quantidadeRecuperacao = GestaoDeEnsinoDAO.getRecuperacao(this).size();;
        int quantidadeAprovado = GestaoDeEnsinoDAO.getAprovado(this).size();
        int quantidadeExcelente = GestaoDeEnsinoDAO.getExcelente(this).size();
        int quantidadeReprovado = GestaoDeEnsinoDAO.getReprovado(this).size();
        AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
        String txtRecuperacao = String.format(getString(R.string.txtRecuperacao_v1));
        String txtAprovados = String.format(getString(R.string.txtAprovados_v1));
        String txtReprovado = String.format(getString(R.string.txtReprovado_v1));
        String txtExcelente = String.format(getString(R.string.txtExcelente_v1));
        String resulatdo =txtExcelente+":       "+quantidadeExcelente+"      " +
                        ""+txtAprovados+":    "+quantidadeAprovado+"\n"
                        +txtRecuperacao+":  "+quantidadeRecuperacao+"" +
                        "     "+txtReprovado+":    "+quantidadeReprovado;

        alertadd.setTitle(resulatdo);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.alert, null);
        alertadd.setView(view);
        alertadd.setNeutralButton(R.string.Aqui_v1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
            }
        });
        alertadd.show();
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
                GestaoDeEnsinoDAO.excluir(gestaoDeEnsino.id, MainActivity.this);
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
        listaGestaoDeEnsinos = GestaoDeEnsinoDAO.getAluno(this);
        adapter = new AdapterGestaoDeEnsino(this, listaGestaoDeEnsinos);
        lvAluno.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.toString().equals( getResources().getString(R.string.action_sair) ) ){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}