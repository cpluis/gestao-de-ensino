package com.br.gestao_de_ensino.Activity;

import androidx.appcompat.app.AppCompatActivity;

public class GestaoDeEnsino extends AppCompatActivity {

    public int id;
    public String nomeAluno;
    public String matricula;
    public String observacao;
    public String substitutiva = "N";
    public int nota1;
    public int nota2;
    public int aprovado;
    public int reprovado;
    public int recuperacao;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String data;
    public Double notaFinal;
    public String status;

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getAprovado() {
        return aprovado;
    }

    public void setAprovado(int aprovado) {
        this.aprovado = aprovado;
    }

    public int getReprovado() {
        return reprovado;
    }

    public void setReprovado(int reprovado) {
        this.reprovado = reprovado;
    }

    public int getRecuperacao() {
        return recuperacao;
    }

    public void setRecuperacao(int recuperacao) {
        this.recuperacao = recuperacao;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getSubstitutiva() {
        return substitutiva;
    }

    public void setSubstitutiva(String substitutiva) {
        this.substitutiva = substitutiva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public int getNota1() {
        return nota1;
    }

    public void setNota1(int nota1) {
        this.nota1 = nota1;
    }

    public int getNota2() {
        return nota2;
    }

    public void setNota2(int nota2) {
        this.nota2 = nota2;
    }

    public GestaoDeEnsino() {

    }

    public GestaoDeEnsino(int id, String nomeAluno, String data,String observacao,int recuperacao, int aprovado, int reprovado, Double notaFinal, String status, int nota1, int nota2, String substitutiva, String matricula) {
        this.id = id;
        this.nomeAluno = nomeAluno;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.aprovado = aprovado;
        this.reprovado = reprovado;
        this.recuperacao = recuperacao;
        this.data = data;
        this.notaFinal = notaFinal;
        this.matricula = matricula;
        this.observacao = observacao;
        this.substitutiva = substitutiva;
        this.status = status;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {

        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "GestaoDeEnsino{" +
                "id=" + id +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", matricula=" + matricula +
                ", nota1=" + nota1 +
                ", nota2=" + nota2 +
                '}';
    }
}
