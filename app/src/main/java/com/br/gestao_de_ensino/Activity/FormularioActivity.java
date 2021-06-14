package com.br.gestao_de_ensino.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.br.gestao_de_ensino.R;

import model.GestaoDeEnsinoDAO;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNomeAluno;
    private EditText etMatricula;
    private EditText etNota1;
    private EditText etNota2;
    private EditText etmlObservacao;
    private EditText etNotaFinal;
    private Button btnSalvar;
    private int aprovado;
    private String tvStatus;
    private String acao;
    private GestaoDeEnsino gestaoDeEnsino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        etNomeAluno = findViewById( R.id.etNomeAluno);
        etMatricula = findViewById( R.id.etMatricula);
        etmlObservacao = findViewById(R.id.etmlObservacao);
        etNota1 = findViewById( R.id.etNota1);
        etNota2 = findViewById( R.id.etNota2);
        btnSalvar = findViewById( R.id.btnSalvar );
        acao = getIntent().getStringExtra("acao");
        if( acao.equals("editar")){
            carregarFormulario();
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

    }

    private void carregarFormulario(){
        int idGestaoDeEnsino = getIntent().getIntExtra("idGestaoDeEnsino", 0);
        if( idGestaoDeEnsino != 0) {
            gestaoDeEnsino = GestaoDeEnsinoDAO.getPetById(this, idGestaoDeEnsino);
            etNomeAluno.setText( gestaoDeEnsino.nomeAluno);
            etMatricula.setText( gestaoDeEnsino.matricula);
            etmlObservacao.setText( gestaoDeEnsino.observacao);
            etNota1.setText(   String.valueOf(gestaoDeEnsino.nota1));
            etNota2.setText(   String.valueOf(gestaoDeEnsino.nota2));
        }
    }

    @SuppressLint("ResourceType")
    private void salvar(){
        if(  etNomeAluno.getText().toString().isEmpty()|| etMatricula.getText().toString().isEmpty() || etNota1.getText().toString().isEmpty() || etNota2.getText().toString().isEmpty()) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setIcon(android.R.drawable.ic_input_delete);
            alerta.setTitle(R.string.txtAtencao_v1);
            alerta.setMessage(R.string.txtPreeTdsCampos_v1);
            alerta.setNeutralButton(R.string.txtEntendiClicAqui_v1, null);
            alerta.show();
        }else{

            if (acao.equals("novo")) {
                gestaoDeEnsino = new GestaoDeEnsino();
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setIcon(android.R.drawable.ic_input_delete);
                alerta.setTitle(R.string.txtAtencao_v1);
                Intent intent = new Intent(FormularioActivity.this, MainActivity.class);
                startActivity( intent );
            }

            gestaoDeEnsino.nomeAluno = etNomeAluno.getText().toString();
            gestaoDeEnsino.matricula = etMatricula.getText().toString();
            gestaoDeEnsino.observacao = etmlObservacao.getText().toString();;
            gestaoDeEnsino.nota1 = Integer.valueOf(etNota1.getText().toString());
            gestaoDeEnsino.nota2 = Integer.valueOf(etNota2.getText().toString());
            double valor = (gestaoDeEnsino.nota1 * 0.4) + (gestaoDeEnsino.nota2 * 0.6);

            gestaoDeEnsino.notaFinal = (double) Math.round(valor);
            String substitutiva = gestaoDeEnsino.getSubstitutiva();
            if(valor > 8){
                tvStatus = "Excelente";
            }
            if(valor >= 6 && valor <=8){
                tvStatus = "Aprovado";
            }
            if(valor < 6 && substitutiva.equals("N")){
                tvStatus = "Recuperacão";
            }
            if(valor < 6 && substitutiva.equals("S")){
                tvStatus = "Reprovado";
            }
            gestaoDeEnsino.status = tvStatus;

            if( acao.equals("editar")){
                GestaoDeEnsinoDAO.editar(gestaoDeEnsino, this);
                finish();
            }else {
                GestaoDeEnsinoDAO.inserir(gestaoDeEnsino, this);
                etNomeAluno.setText("");
                etMatricula.setText("");
                etNota1.setText("");
                etNota2.setText("");
                etmlObservacao.setText("");
            }
        }
    }
}