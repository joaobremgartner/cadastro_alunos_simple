package br.com.caelum.cadastro.converter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.caelum.cadastro.model.Aluno;

/**
 * Created by joaofaro on 08/07/15.
 */
public class AlunoConverter {

    private JSONStringer arquivoJson;

    public String toJSON(List<Aluno> lista){
        try {
            arquivoJson = new JSONStringer();
            arquivoJson.object().key("list").array().object().key("aluno").array();
            
            for(Aluno aluno : lista){
                arquivoJson.object().key("nome").value(aluno.getNome())
                .key("telefone").value(aluno.getTelefone())
                .key("endereco").value(aluno.getEndereco())
                .key("site").value(aluno.getSite())
                .key("nota").value(aluno.getNota())
                .key("id").value(aluno.getId())
                .endObject();
            }
            arquivoJson.endArray().endObject().endArray().endObject();

        } catch (JSONException e){
            Log.e("JSON", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return arquivoJson.toString();
    }
}
