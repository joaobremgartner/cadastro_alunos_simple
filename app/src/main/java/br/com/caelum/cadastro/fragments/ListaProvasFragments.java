package br.com.caelum.cadastro.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.main.ProvasActivity;
import br.com.caelum.cadastro.model.Prova;

/**
 * Created by joaofaro on 10/07/15.
 */
public class ListaProvasFragments extends Fragment {

    private ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View listaDeProvas = inflater.inflate(R.layout.lista_provas, container, false);
        this.lista = (ListView) listaDeProvas.findViewById(R.id.lista_provas);

        Prova prova1 = new Prova("01/01/2015", "Matemática");
        prova1.setTopicos(Arrays.asList("Algebra linear", "Calculo", "Estatística"));
        Prova prova2 = new Prova("02/01/2015", "Português");
        prova2.setTopicos(Arrays.asList("Complemento nominal", "Orações subordinadas",
                "Análise sintática"));

        List<Prova> provas = Arrays.asList(prova1, prova2);

        ArrayAdapter<Prova> adaptador = new ArrayAdapter<Prova>(getActivity(),
                android.R.layout.simple_list_item_1, provas);

        this.lista.setAdapter(adaptador);

        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Prova provaSelecionada = (Prova) parent.getItemAtPosition(position);
                ProvasActivity calendarioProvas = (ProvasActivity) getActivity();
                calendarioProvas.selecionaProva(provaSelecionada);
                
                Toast.makeText(getActivity(), "Prova selecionada: "+ provaSelecionada,
                        Toast.LENGTH_LONG).show();
            }
        });
        return listaDeProvas;
    }
}
