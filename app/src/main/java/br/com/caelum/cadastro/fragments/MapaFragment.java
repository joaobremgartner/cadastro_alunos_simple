package br.com.caelum.cadastro.fragments;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.util.Localizador;

/**
 * Created by joaofaro on 10/07/15.
 */
public class MapaFragment extends SupportMapFragment {

    @Override
    public void onResume() {
        super.onResume();

        Localizador localizador = new Localizador(getActivity());

        LatLng local = localizador.getCoordenada("Rua vergueiro 3185 Vila Mariana");

        this.centralizandoLocalizacao(local);

        AlunoDAO dao = new AlunoDAO(getActivity());
        List<Aluno> alunos = dao.getLista();

        for(Aluno aluno : alunos){
            aluno.getEndereco();
            aluno.getNome();

            LatLng localAluno = localizador.getCoordenada(aluno.getEndereco());

            MarkerOptions marcador = new MarkerOptions();
            marcador.title(aluno.getNome());
            marcador.position(localAluno);

            getMap().addMarker(marcador);
        }
    }

    private void centralizandoLocalizacao(LatLng local) {
        GoogleMap googleMap = getMap();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 11));

    }
}
