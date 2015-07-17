package br.com.caelum.cadastro.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.util.WebClient;

/**
 * Created by joaofaro on 09/07/15.
 */
public class EnviaAlunosServidor extends AsyncTask <Object, Object, String> {

    private final Context contexto;
    private String url;
    private ProgressDialog dialog;

    public EnviaAlunosServidor(Context contexto, String url) {
        this.contexto = contexto;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {

        this.dialog = new ProgressDialog(contexto);
        this.dialog.setTitle("Aguarde");
        this.dialog.setMessage("Enviando dados para o servidor");
        this.dialog.show();
    }

    @Override
    protected String doInBackground(Object[] params) {

        AlunoDAO dao = new AlunoDAO(this.contexto);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        String json = new AlunoConverter().toJSON(alunos);
        String resposta = new WebClient(this.url).post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resultado) {
        Toast.makeText(contexto, resultado, Toast.LENGTH_LONG).show();
        this.dialog.dismiss();
    }
}
