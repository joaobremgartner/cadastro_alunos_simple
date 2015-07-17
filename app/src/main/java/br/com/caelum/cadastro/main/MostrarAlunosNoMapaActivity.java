package br.com.caelum.cadastro.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.fragments.MapaFragment;

/**
 * Created by joaofaro on 10/07/15.
 */
public class MostrarAlunosNoMapaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_alunos_map);

        MapaFragment mapa = new MapaFragment();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

        tx.replace(R.id.mostrar_mapa, mapa);
        tx.commit();
    }
}
