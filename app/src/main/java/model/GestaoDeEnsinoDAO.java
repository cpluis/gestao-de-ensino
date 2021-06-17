package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.br.gestao_de_ensino.Activity.GestaoDeEnsino;

import java.util.ArrayList;
import java.util.List;

import repository.Banco;

public class GestaoDeEnsinoDAO {


    public static void inserir(GestaoDeEnsino gestaoDeEnsino, Context context){
        ContentValues valores = new ContentValues();
        valores.put("nomeAluno", gestaoDeEnsino.nomeAluno);
        valores.put("matricula", gestaoDeEnsino.matricula );
        valores.put("substitutiva", gestaoDeEnsino.substitutiva);
        valores.put("status", gestaoDeEnsino.status);
        valores.put("nota1", gestaoDeEnsino.nota1);
        valores.put("nota2", gestaoDeEnsino.nota2);
        valores.put("aprovado", gestaoDeEnsino.aprovado);
        valores.put("reprovado", gestaoDeEnsino.reprovado);
        valores.put("observacao", gestaoDeEnsino.observacao);
        valores.put("recuperacao", gestaoDeEnsino.recuperacao);
        valores.put("notaFinal", gestaoDeEnsino.notaFinal);

        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();

        db.insert("gestaoDeEnsino", null, valores);
    }

    public static void editar(GestaoDeEnsino gestaoDeEnsino, Context context){
        ContentValues valores = new ContentValues();
        valores.put("nomeAluno", gestaoDeEnsino.nomeAluno);
        valores.put("matricula", gestaoDeEnsino.matricula );
        valores.put("substitutiva", gestaoDeEnsino.substitutiva);
        valores.put("status", gestaoDeEnsino.status);
        valores.put("nota1", gestaoDeEnsino.nota1);
        valores.put("nota2", gestaoDeEnsino.nota2);
        valores.put("aprovado", gestaoDeEnsino.aprovado);
        valores.put("observacao", gestaoDeEnsino.observacao);
        valores.put("reprovado", gestaoDeEnsino.reprovado);
        valores.put("recuperacao", gestaoDeEnsino.recuperacao);
        valores.put("notaFinal", gestaoDeEnsino.notaFinal);

        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();

        db.update("gestaoDeEnsino", valores, " id = " + gestaoDeEnsino.id , null );
    }

    public static void editarSubstitutiva(GestaoDeEnsino gestaoDeEnsino, Context context){
        ContentValues valores = new ContentValues();

        valores.put("nomeAluno", gestaoDeEnsino.nomeAluno);
        valores.put("matricula", gestaoDeEnsino.matricula );
        valores.put("substitutiva", gestaoDeEnsino.substitutiva);
        valores.put("status", gestaoDeEnsino.status);
        valores.put("nota1", gestaoDeEnsino.nota1);
        valores.put("nota2", gestaoDeEnsino.nota2);
        valores.put("aprovado", gestaoDeEnsino.aprovado);
        valores.put("reprovado", gestaoDeEnsino.reprovado);
        valores.put("observacao", gestaoDeEnsino.observacao);
        valores.put("recuperacao", gestaoDeEnsino.recuperacao);
        valores.put("notaFinal", gestaoDeEnsino.notaFinal);

        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();

        db.update("gestaoDeEnsino", valores, " id = " + gestaoDeEnsino.id , null );
    }

    public static void excluir(int id, Context context){
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete("gestaoDeEnsino", " id = " + id , null);
    }

    public static List<GestaoDeEnsino> getAluno(Context context){
        List<GestaoDeEnsino> lista = new ArrayList<>();
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nomeAluno, matricula, substitutiva, nota1, nota2, aprovado, reprovado, recuperacao, notaFinal, status,observacao FROM gestaoDeEnsino ORDER BY nomeAluno", null );
        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do{
                GestaoDeEnsino gestaoDeEnsino = new GestaoDeEnsino();
                gestaoDeEnsino.id = cursor.getInt( 0);
                gestaoDeEnsino.nomeAluno = cursor.getString(1);
                gestaoDeEnsino.matricula = cursor.getString(2);
                gestaoDeEnsino.substitutiva = cursor.getString(3);
                gestaoDeEnsino.nota1 = cursor.getInt(4);
                gestaoDeEnsino.nota2 = cursor.getInt(5);
                gestaoDeEnsino.aprovado = cursor.getInt(6);
                gestaoDeEnsino.reprovado = cursor.getInt(7);
                gestaoDeEnsino.recuperacao = cursor.getInt(8);
                gestaoDeEnsino.notaFinal = cursor.getDouble(9);
                gestaoDeEnsino.status = cursor.getString(10);
                gestaoDeEnsino.observacao = cursor.getString(11);
                lista.add(gestaoDeEnsino);
            }while( cursor.moveToNext() );
        }
        return lista;
    }


    public static List<GestaoDeEnsino> getRecuperacao(Context context){
        List<GestaoDeEnsino> lista = new ArrayList<>();
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nomeAluno, matricula, substitutiva, nota1, nota2, aprovado, reprovado, recuperacao, notaFinal, status, observacao FROM gestaoDeEnsino WHERE status = 'Recuperacão'" , null );
        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do{
                GestaoDeEnsino gestaoDeEnsino = new GestaoDeEnsino();

                gestaoDeEnsino.id = cursor.getInt( 0);
                gestaoDeEnsino.nomeAluno = cursor.getString(1);
                gestaoDeEnsino.matricula = cursor.getString(2);
                gestaoDeEnsino.substitutiva = cursor.getString(3);
                gestaoDeEnsino.nota1 = cursor.getInt(4);
                gestaoDeEnsino.nota2 = cursor.getInt(5);
                gestaoDeEnsino.aprovado = cursor.getInt(6);
                gestaoDeEnsino.reprovado = cursor.getInt(7);
                gestaoDeEnsino.recuperacao = cursor.getInt(8);
                gestaoDeEnsino.notaFinal = cursor.getDouble(9);
                gestaoDeEnsino.status = cursor.getString(10);
                gestaoDeEnsino.observacao = cursor.getString(11);
                lista.add(gestaoDeEnsino);
            }while( cursor.moveToNext() );
        }
        return lista;
    }

    public static List<GestaoDeEnsino> getExcelente(Context context){
        List<GestaoDeEnsino> lista = new ArrayList<>();
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nomeAluno, matricula, substitutiva, nota1, nota2, aprovado, reprovado, recuperacao, notaFinal, status, observacao FROM gestaoDeEnsino WHERE status = 'Excelente'" , null );
        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do{
                GestaoDeEnsino gestaoDeEnsino = new GestaoDeEnsino();

                gestaoDeEnsino.id = cursor.getInt( 0);
                gestaoDeEnsino.nomeAluno = cursor.getString(1);
                gestaoDeEnsino.matricula = cursor.getString(2);
                gestaoDeEnsino.substitutiva = cursor.getString(3);
                gestaoDeEnsino.nota1 = cursor.getInt(4);
                gestaoDeEnsino.nota2 = cursor.getInt(5);
                gestaoDeEnsino.aprovado = cursor.getInt(6);
                gestaoDeEnsino.reprovado = cursor.getInt(7);
                gestaoDeEnsino.recuperacao = cursor.getInt(8);
                gestaoDeEnsino.notaFinal = cursor.getDouble(9);
                gestaoDeEnsino.status = cursor.getString(10);
                gestaoDeEnsino.observacao = cursor.getString(11);
                lista.add(gestaoDeEnsino);
            }while( cursor.moveToNext() );
        }
        return lista;
    }

    public static List<GestaoDeEnsino> getReprovado(Context context){
        List<GestaoDeEnsino> lista = new ArrayList<>();
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nomeAluno, matricula, substitutiva, nota1, nota2, aprovado, reprovado, recuperacao, notaFinal, status, observacao FROM gestaoDeEnsino WHERE status = 'Reprovado'" , null );
        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do{
                GestaoDeEnsino gestaoDeEnsino = new GestaoDeEnsino();

                gestaoDeEnsino.id = cursor.getInt( 0);
                gestaoDeEnsino.nomeAluno = cursor.getString(1);
                gestaoDeEnsino.matricula = cursor.getString(2);
                gestaoDeEnsino.substitutiva = cursor.getString(3);
                gestaoDeEnsino.nota1 = cursor.getInt(4);
                gestaoDeEnsino.nota2 = cursor.getInt(5);
                gestaoDeEnsino.aprovado = cursor.getInt(6);
                gestaoDeEnsino.reprovado = cursor.getInt(7);
                gestaoDeEnsino.recuperacao = cursor.getInt(8);
                gestaoDeEnsino.notaFinal = cursor.getDouble(9);
                gestaoDeEnsino.status = cursor.getString(10);
                gestaoDeEnsino.observacao = cursor.getString(11);
                lista.add(gestaoDeEnsino);
            }while( cursor.moveToNext() );
        }
        return lista;
    }

    public static List<GestaoDeEnsino> getAprovado(Context context){
        List<GestaoDeEnsino> lista = new ArrayList<>();
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nomeAluno, matricula, substitutiva, nota1, nota2, aprovado, reprovado, recuperacao, notaFinal, status, observacao FROM gestaoDeEnsino WHERE status = 'Aprovado'" , null );
        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do{
                GestaoDeEnsino gestaoDeEnsino = new GestaoDeEnsino();
                gestaoDeEnsino.id = cursor.getInt( 0);
                gestaoDeEnsino.nomeAluno = cursor.getString(1);
                gestaoDeEnsino.matricula = cursor.getString(2);
                gestaoDeEnsino.substitutiva = cursor.getString(3);
                gestaoDeEnsino.nota1 = cursor.getInt(4);
                gestaoDeEnsino.nota2 = cursor.getInt(5);
                gestaoDeEnsino.aprovado = cursor.getInt(6);
                gestaoDeEnsino.reprovado = cursor.getInt(7);
                gestaoDeEnsino.recuperacao = cursor.getInt(8);
                gestaoDeEnsino.notaFinal = cursor.getDouble(9);
                gestaoDeEnsino.status = cursor.getString(10);
                gestaoDeEnsino.observacao = cursor.getString(11);
                lista.add(gestaoDeEnsino);
            }while( cursor.moveToNext() );
        }
        return lista;
    }

    public static GestaoDeEnsino getAlunoById(Context context, int id) {
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nomeAluno, matricula, substitutiva, nota1, nota2, aprovado, reprovado, recuperacao, notaFinal, status, observacao   FROM gestaoDeEnsino WHERE id = " + id, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            GestaoDeEnsino gestaoDeEnsino = new GestaoDeEnsino();
            gestaoDeEnsino.id = cursor.getInt( 0);
            gestaoDeEnsino.nomeAluno = cursor.getString(1);
            gestaoDeEnsino.matricula = cursor.getString(2);
            gestaoDeEnsino.substitutiva = cursor.getString(3);
            gestaoDeEnsino.nota1 = cursor.getInt(4);
            gestaoDeEnsino.nota2 = cursor.getInt(5);
            gestaoDeEnsino.aprovado = cursor.getInt(6);
            gestaoDeEnsino.reprovado = cursor.getInt(7);
            gestaoDeEnsino.recuperacao = cursor.getInt(8);
            gestaoDeEnsino.notaFinal = cursor.getDouble(9);
            gestaoDeEnsino.status = cursor.getString(10);
            gestaoDeEnsino.observacao = cursor.getString(11);
            return gestaoDeEnsino;
        } else {
            return null;
        }
    }
}
