package com.br.gestao_de_ensino.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.gestao_de_ensino.Activity.util.ConfigInternet;
import com.br.gestao_de_ensino.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    RequestQueue requestQueue ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        initUI();
        acao = getIntent().getStringExtra("acao");
        if( acao.equals("editar")){
            carregarFormulario();
        }
        requestQueue = Volley.newRequestQueue(this);
        btnSalvar.setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View v) {salvar();}});}

    private void initUI(){
        etNomeAluno = findViewById(R.id.etNomeAluno);
        etMatricula = findViewById(R.id.etMatricula);
        etmlObservacao = findViewById(R.id.etmlObservacao);
        etNota1 = findViewById(R.id.etNota1);
        etNota2 = findViewById(R.id.etNota2);
        btnSalvar = findViewById(R.id.btnSalvar);

    }

    private void postBanco(final String nome, final String matricula,
                           final String nota1, final String nota2,
                           final String nota_final,
                           final String observacao

                            ){
        GestaoDeEnsino gestaoDeEnsino = new GestaoDeEnsino();
        final String substitutiva = gestaoDeEnsino.getSubstitutiva();
        ConfigInternet configInternet = new ConfigInternet();
        StringRequest stringRequest = new StringRequest(

                Request.Method.POST,
                configInternet.metodoPost(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FormularioActivity.this,"Aluno Criado Com sucesso!!",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nome",nome);
                params.put("matricula",matricula);
                params.put("nota1",nota1);
                params.put("nota2",nota2);
                params.put("nota_final",nota_final);
                params.put("observacao",observacao);
                params.put("substitutiva",substitutiva);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void carregarFormulario(){
        int idGestaoDeEnsino = getIntent().getIntExtra("idGestaoDeEnsino", 0);
        if( idGestaoDeEnsino != 0) {
            gestaoDeEnsino = GestaoDeEnsinoDAO.getAlunoById(this, idGestaoDeEnsino);
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
                Double notaFinal_2 = 3.06;
                String nome = etNomeAluno.getText().toString().trim();
                String matricula = etMatricula.getText().toString();
                String observacao = etmlObservacao.getText().toString();
                String nota1 = etNota1.getText().toString();
                String nota2 = etNota2.getText().toString();
                String notaFinal = String.valueOf(notaFinal_2);

                postBanco(nome, matricula,  nota1, nota2, notaFinal, observacao);

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
            gestaoDeEnsino.data = retornaData();
            double valor = (gestaoDeEnsino.nota1 * 0.4) + (gestaoDeEnsino.nota2 * 0.6);
            gestaoDeEnsino.notaFinal = (double) Math.round(valor);

            if(gestaoDeEnsino.notaFinal < 6 ){
                gestaoDeEnsino.setStatus("Recuperacão");
                gestaoDeEnsino.setSubstitutiva("precisa");
            }
            if(gestaoDeEnsino.notaFinal >= 6 && valor <=8){
                gestaoDeEnsino.status = "Aprovado";
                gestaoDeEnsino.setSubstitutiva("Não precisou");
            }

            if(gestaoDeEnsino.notaFinal >= 8){
                gestaoDeEnsino.status = "Excelente";
                gestaoDeEnsino.setSubstitutiva("Não precisou");
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
                etmlObservacao.setText("");
            }
        }
    }

    public String retornaData(){
        Date hoje = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");
        String data = new String(df.format(hoje));
        return data;
    }
}