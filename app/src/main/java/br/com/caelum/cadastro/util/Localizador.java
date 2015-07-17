package br.com.caelum.cadastro.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by joaofaro on 10/07/15.
 */
public class Localizador {

    private Geocoder geocoder;
    private Context context;

    public Localizador(Context ctx) {
        this.geocoder = new Geocoder(ctx);
    }

    public LatLng getCoordenada(String endereco){
        try {
            List<Address> listaEnderecos = this.geocoder.getFromLocationName(endereco, 1);
            if (!listaEnderecos.isEmpty()){
                Address address = listaEnderecos.get(0);
                return new LatLng(address.getLatitude(), address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
