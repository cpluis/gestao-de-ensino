package repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    private static final int VERSAO = 3;
    private static final String NOME = "AppGestaoDeEnsino";

    public Banco(Context context){
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS gestaoDeEnsino ( " +
                "     id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," +
                "     nomeAluno TEXT NOT NULL ," +
                "     matricula TEXT NOT NULL ," +
                "     substitutiva TEXT NOT NULL ," +
                "     nota1 INTEGER NOT NULL ," +
                "     nota2 INTEGER NOT NULL ," +
                "     aprovado INTEGER NOT NULL ," +
                "     reprovado INTEGER NOT NULL ," +
                "     recuperacao INTEGER NOT NULL ," +
                "     notaFinal DECIMAL(4,2)," +
                "     status TEXT NOT NULL," +
                "     observacao TEXT NOT NULL" +
                ") " );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS gestaoDeEnsino");
    }
}
