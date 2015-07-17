package br.com.caelum.cadastro.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.fragments.DetalhesProvasFragments;
import br.com.caelum.cadastro.fragments.ListaProvasFragments;
import br.com.caelum.cadastro.model.Prova;

/**
 * Created by joaofaro on 10/07/15.
 */
public class ProvasActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

        if(isTablet()){
            tx.replace(R.id.provas_lista, new ListaProvasFragments());
            tx.replace(R.id.provas_detalhes, new DetalhesProvasFragments());
        }else {
            tx.replace(R.id.provas_view, new ListaProvasFragments());

        }
        tx.commit();
    }

    private Boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void selecionaProva(Prova provaSelecionada) {

        FragmentManager manager = getSupportFragmentManager();

        if (isTablet()){
            DetalhesProvasFragments detalhesProvas = (DetalhesProvasFragments)
                    manager.findFragmentById(R.id.provas_detalhes);
            detalhesProvas.popularDados(provaSelecionada);
        } else {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable("prova", provaSelecionada);

            DetalhesProvasFragments detalhes = new DetalhesProvasFragments();
            detalhes.setArguments(argumentos);

            FragmentTransaction tx = manager.beginTransaction();
            tx.replace(R.id.provas_view, detalhes);
            tx.addToBackStack(null);
            tx.commit();
        }
    }
}
