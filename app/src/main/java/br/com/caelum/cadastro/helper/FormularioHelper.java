package br.com.caelum.cadastro.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.caelum.cadastro.DAO.CadastroAluno;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.model.Aluno;

/**
 * Created by joaofaro on 07/07/15.
 */
public class FormularioHelper {

    private EditText campoNome;
    private EditText campoEndereco;
    private EditText campoSite;
    private EditText campoTelefone;
    private RatingBar campoNota;
    private ImageView foto;
    private Button botaoFoto;

    private Aluno aluno;

    public FormularioHelper(CadastroAluno activity) {

        this.campoNome = (EditText) activity.findViewById(R.id.nomeEdit);
        this.campoEndereco = (EditText) activity.findViewById(R.id.enderecoEdit);
        this.campoSite = (EditText) activity.findViewById(R.id.siteEdit);
        this.campoTelefone = (EditText) activity.findViewById(R.id.telEdit);
        this.campoNota = (RatingBar) activity.findViewById(R.id.rating);
        this.foto = (ImageView) activity.findViewById(R.id.formulario_imagem);
        this.botaoFoto = (Button) activity.findViewById(R.id.formulario_botaoTirarFoto);

        this.aluno = new Aluno();
    }

    public Aluno getAluno(){
        this.aluno.setNome(this.campoNome.getText().toString());
        this.aluno.setEndereco(this.campoEndereco.getText().toString());
        this.aluno.setTelefone(this.campoTelefone.getText().toString());
        this.aluno.setSite(this.campoSite.getText().toString());
        this.aluno.setNota(Double.valueOf(campoNota.getProgress()));
        this.aluno.setCaminhoDaFoto((String) this.foto.getTag());

        return aluno;
    }

    public boolean temNome(){
        return !this.campoNome.getText().toString().isEmpty();
    }

    public void mostrarErro(){
        this.campoNome.setError("O campo 'nome' precisa ser preenchido!");
    }

    public void colocaAlunoNoFormulario(Aluno aluno){

        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoSite.setText(aluno.getSite());
        campoTelefone.setText(aluno.getTelefone());
        campoNota.setProgress(aluno.getNota().intValue());

        this.aluno = aluno;

        if (aluno.getCaminhoDaFoto() != null){
            carregaImagem(aluno.getCaminhoDaFoto());
        }
    }

    public Button getBotaoFoto() {
        return botaoFoto;
    }

    public void carregaImagem(String localDaFoto) {
        Bitmap fotoDoAluno = BitmapFactory.decodeFile(localDaFoto);
        Bitmap imagemReduzida = Bitmap.createScaledBitmap(fotoDoAluno, fotoDoAluno.getWidth(),
                300, true);
        this.foto.setImageBitmap(imagemReduzida);
        this.foto.setTag(localDaFoto);
        this.foto.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
