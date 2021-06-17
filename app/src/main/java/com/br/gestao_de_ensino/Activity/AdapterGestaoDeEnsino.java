package com.br.gestao_de_ensino.Activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.br.gestao_de_ensino.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class AdapterGestaoDeEnsino  extends BaseAdapter {

    private List<GestaoDeEnsino> gestaoDeEnsinoList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterGestaoDeEnsino(Context context, List<GestaoDeEnsino> listaGestaoDeEnsinos){
        this.gestaoDeEnsinoList = listaGestaoDeEnsinos;
        this.context = context;
        this.inflater = LayoutInflater.from( context );
    }

    @Override
    public int getCount() {
        return gestaoDeEnsinoList.size();
    }

    @Override
    public Object getItem(int i) {
        return gestaoDeEnsinoList.get( i );
    }

    @Override
    public long getItemId(int i) {
        return gestaoDeEnsinoList.get(i).id;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ItemSuporte item;

        if( convertView == null){
            convertView = inflater.inflate(R.layout.layout_lista, null);
            item = new ItemSuporte();
            item.tvNomeAluno = convertView.findViewById(R.id.tvListaNomeAluno);
            item.tvMatricula = convertView.findViewById(R.id.tvListaMatricula);
            item.tvNota1 = convertView.findViewById(R.id.tvListaNota1);
            item.tvNota2 = convertView.findViewById(R.id.tvListaNota2);
            item.tvStatus = convertView.findViewById(R.id.tvListaStatus);
            item.tvNotaFinal = convertView.findViewById(R.id.tvListaNotaFinal);
            item.tvSunstitutiva = convertView.findViewById(R.id.tvListaSunstitutiva);
            item.tvObservacao = convertView.findViewById(R.id.etmlObservacao);
            item.tvData = convertView.findViewById(R.id.tvListaData);
            item.layout = convertView.findViewById(R.id.llFundoLista);
            convertView.setTag( item );
        }else {
            item = (ItemSuporte) convertView.getTag();
        }

        GestaoDeEnsino gestaoDeEnsino = gestaoDeEnsinoList.get(i);

        item.tvNomeAluno.setText(  gestaoDeEnsino.nomeAluno.toUpperCase());
        item.tvMatricula.setText(    gestaoDeEnsino.matricula);
        item.tvSunstitutiva.setText(    gestaoDeEnsino.substitutiva);
        item.tvNota1.setText(   String.valueOf(gestaoDeEnsino.nota1));
        item.tvNota2.setText(  String.valueOf( gestaoDeEnsino.nota2 ));
        item.tvStatus.setText(  gestaoDeEnsino.status);
        item.tvObservacao.setText(  gestaoDeEnsino.observacao);
        item.tvData.setText("ljlj");
        item.tvNotaFinal.setText(  String.valueOf( gestaoDeEnsino.notaFinal));

        if( i % 2 == 0 ){
            item.layout.setBackgroundColor(Color.rgb(230, 230, 230));
        }else {
            item.layout.setBackgroundColor( Color.WHITE );
        }
        return convertView;
    }

    private class ItemSuporte{
        TextView tvNomeAluno, tvMatricula, tvNota1, tvNota2,  tvStatus,tvNotaFinal, tvObservacao, tvData, tvSunstitutiva;
        RelativeLayout layout;
    }
}
