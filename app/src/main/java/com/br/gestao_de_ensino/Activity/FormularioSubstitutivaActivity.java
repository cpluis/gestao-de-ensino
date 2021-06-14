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

public class FormularioSubstitutivaActivity extends AppCompatActivity {
    private EditText etNomeAluno;
    private EditText etMatricula;
    private EditText etNota1;
    private EditText etNota2;
    private EditText etNotaFinal;
    private Button btnSalvar;
    private String tvStatus;
    private String acao;
    private GestaoDeEnsino gestaoDeEnsino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_substitutiva);
        etNomeAluno = findViewById( R.id.etNomeAluno);
        etMatricula = findViewById( R.id.etMatricula);
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
            String aluno = String.format(getString(R.string.aluno_v1));
            String matricula = String.format(getString(R.string.matricula2_v1));
            String nota1_do_aluno = String.format(getString(R.string.nota1_do_aluno2_v1));

            etNomeAluno.setText( aluno+gestaoDeEnsino.nomeAluno);
            etMatricula.setText( matricula+":   "+gestaoDeEnsino.getMatricula());
            etNota1.setText("");
            etNota1.setText(   nota1_do_aluno+":    "+String.valueOf(gestaoDeEnsino.getNota1()));
            etNota2.setText(   String.valueOf(gestaoDeEnsino.nota2));
        }
    }

    @SuppressLint("ResourceType")
    private void salvar(){
        if(  etNota2.getText().toString().isEmpty()) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setIcon(android.R.drawable.ic_input_delete);
            alerta.setTitle(R.string.txtAtencao_v1);
            alerta.setMessage(R.string.txtPreeCamposSubstitutiva_v1);
            alerta.setNeutralButton(R.string.txtEntendiClicAqui_v1, null);
            alerta.show();
        }else{

            gestaoDeEnsino.nota2 = Integer.valueOf(etNota2.getText().toString());
            double valor = (gestaoDeEnsino.nota1 * 0.4) + (gestaoDeEnsino.nota2 * 0.6);
            gestaoDeEnsino.notaFinal = Math.floor(valor);
            if(valor > 8){
                gestaoDeEnsino.setStatus("Excelente");
            }
            if(valor < 6 && gestaoDeEnsino.getSubstitutiva().equals("N")){
                gestaoDeEnsino.setStatus("Recuperacão");
                gestaoDeEnsino.setSubstitutiva("S");
            }

            if(valor < 6 && gestaoDeEnsino.getSubstitutiva().equals("S") && gestaoDeEnsino.status.equals("Recuperacão")){
                gestaoDeEnsino.setStatus("Reprovado");
            }


            if( acao.equals("editar")){
                GestaoDeEnsinoDAO.editar(gestaoDeEnsino, this);
                finish();
            }else {
                GestaoDeEnsinoDAO.inserir(gestaoDeEnsino, this);
                etNomeAluno.setText("");
                etMatricula.setText("");
                etNota1.setText("");
                etNota2.setText("");
            }

            Intent intent = new Intent(FormularioSubstitutivaActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}