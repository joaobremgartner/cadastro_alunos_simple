package br.com.caelum.cadastro.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.util.RoundImage;

/**
 * Created by joaofaro on 08/07/15.
 */
public class ListaDeAlunosAdapter extends BaseAdapter {

    private List<Aluno> alunos;
    private Activity activity;

    public ListaDeAlunosAdapter(List<Aluno> alunos, Activity activity) {
        this.alunos = alunos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        //posicao na lista
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        //posicao no id do aluno
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
        Aluno aluno = this.alunos.get(posicao);
        Bitmap foto;

        //Retornando os itens da lista
        TextView txtNome = (TextView) view.findViewById(R.id.item_nome);
        TextView txtSite = (TextView) view.findViewById(R.id.item_site);
        TextView txtTel = (TextView) view.findViewById(R.id.item_telefone);
        txtNome.setText(aluno.getNome());

        if(txtTel != null && txtSite != null){
            txtSite.setText(aluno.getSite());
            txtTel.setText(aluno.getTelefone());
        }
        //Recuperando a imagem
        if (aluno.getCaminhoDaFoto() != null){
            foto = BitmapFactory.decodeFile(aluno.getCaminhoDaFoto());
        } else {
            foto = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_no_image);
        }
        //Trantando a imagem
        foto = RoundImage.getRoundBitmap(foto);
        foto = Bitmap.createScaledBitmap(foto, 200, 200, true);

        //Agora...buscando a imagem
        ImageView img = (ImageView) view.findViewById(R.id.item_foto);
        img.setImageBitmap(foto);

        return view;
    }
}
