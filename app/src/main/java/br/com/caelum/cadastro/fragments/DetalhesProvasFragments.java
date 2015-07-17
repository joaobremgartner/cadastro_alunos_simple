package br.com.caelum.cadastro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.model.Prova;

/**
 * Created by joaofaro on 10/07/15.
 */
public class DetalhesProvasFragments extends Fragment {

    private Prova prova;

    private TextView materia;
    private TextView data;
    private ListView topicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View detalhesProvas = inflater.inflate(R.layout.fragment_detalhes_provas, container, false);

        //Setando o argumento criado na activity principal
        if (getArguments() != null){
            this.prova = (Prova) getArguments().getSerializable("prova");
        }

        buscaComponentes(detalhesProvas);
        popularDados(prova);

        return detalhesProvas;
    }

    private void buscaComponentes(View layout){
        this.materia = (TextView) layout.findViewById(R.id.detalhe_prova_materia);
        this.data = (TextView) layout.findViewById(R.id.detalhe_prova_data);
        this.topicos = (ListView) layout.findViewById(R.id.detalhe_prova_topicos);
    }

    public void popularDados(Prova prova){
        if(prova != null){
            this.materia.setText(prova.getMateria());
            this.data.setText(prova.getData());

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, prova.getTopicos());

            this.topicos.setAdapter(adaptador);
        }
    }
}
