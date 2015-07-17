package br.com.caelum.cadastro.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.DAO.CadastroAluno;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.adapters.ListaDeAlunosAdapter;
import br.com.caelum.cadastro.helper.EnviaAlunosServidor;
import br.com.caelum.cadastro.model.Aluno;


public class ListaAlunosActivity extends ActionBarActivity {


    private ListView lista;
    private Button floatingButton;
    private List<Aluno> alunos;
    public static final String ALUNO_SELECIONADO = "alunoSelecionado";
    private static final String URL_SERVIDOR = "http://www.caelum.com.br/mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        this.lista = (ListView) findViewById(R.id.lista_alunos);

        registerForContextMenu(this.lista);

        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {

                Aluno alunoParaSerAlterado = (Aluno) adapter.getItemAtPosition(posicao);

                Intent abrirForm = new Intent(ListaAlunosActivity.this, CadastroAluno.class);
                
                abrirForm.putExtra(ALUNO_SELECIONADO, alunoParaSerAlterado);
                startActivity(abrirForm);

                Toast.makeText(ListaAlunosActivity.this, "Aluno: "+ alunoParaSerAlterado.getId(),
                        Toast.LENGTH_LONG).show();
            }
        });

        this.lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {

                Log.i("Cadastro", "Longo clique na lista");
                Aluno aluno = (Aluno) adapter.getItemAtPosition(posicao);
                Toast.makeText(ListaAlunosActivity.this, "Longo clique no aluno: " + aluno,
                        Toast.LENGTH_LONG).show();

                return false;
            }
        });

        this.floatingButton = (Button) findViewById(R.id.lista_floating_button);

        this.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunosActivity.this, CadastroAluno.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.carregaLista();
    }

    private void carregaLista(){

        AlunoDAO dao = new AlunoDAO(this);
        this.alunos = dao.getLista();
        dao.close();

        ListaDeAlunosAdapter adaptador = new ListaDeAlunosAdapter(this.alunos,
                ListaAlunosActivity.this);

        this.lista.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share) {
            //Intent de compartilhar
            Intent compartilhar = new Intent(Intent.ACTION_SEND);
            compartilhar.setType("text/plain");
            compartilhar.putExtra(Intent.EXTRA_SUBJECT, "Assunto compartilhado (Teste)");
            compartilhar.putExtra(Intent.EXTRA_TEXT, "texto compartilhado (Teste)");
            startActivity(Intent.createChooser(compartilhar, "Escolha como compartilhar"));

            return true;
        }
        if(id == R.id.enviar){

            new EnviaAlunosServidor(ListaAlunosActivity.this, URL_SERVIDOR)
            .execute();
            return true;
        }
        if(id == R.id.receber){
            Intent irParaFragment = new Intent(ListaAlunosActivity.this, ProvasActivity.class);
            startActivity(irParaFragment);

        }
        if (id == R.id.mapa){
            Intent irParaMapa = new Intent(ListaAlunosActivity.this,
                    MostrarAlunosNoMapaActivity.class);
            startActivity(irParaMapa);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        //Pega o aluno selecionado
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) lista.getAdapter().getItem(info.position);

        MenuItem itemLigar = menu.add("Ligar");
        Intent ligar = new Intent(Intent.ACTION_CALL);
        ligar.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
        itemLigar.setIntent(ligar);

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent sms = new Intent(Intent.ACTION_VIEW);
        sms.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
        sms.putExtra("sms_body", "Mensagem");
        itemSMS.setIntent(sms);

        MenuItem itemMapa = menu.add("Achar no mapa");
        Intent acharMapa = new Intent(Intent.ACTION_VIEW);
        acharMapa.setData(Uri.parse("geo:0,0?z=14&q="+ Uri.encode(alunoSelecionado.getEndereco())));
        itemMapa.setIntent(acharMapa);

        MenuItem itemSite = menu.add("Navegar no site");
        Intent site = new Intent(Intent.ACTION_VIEW);
        site.setData(Uri.parse("http://"+ alunoSelecionado.getSite()));
        itemSite.setIntent(site);

        MenuItem itemDeletar = menu.add("Deletar");

        itemDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListaAlunosActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Confirmação");
                builder.setMessage("Deseja realmente deletar este item?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                        dao.deletar(alunoSelecionado);
                        dao.close();
                        carregaLista();
                    }
                });
                builder.setNegativeButton("Não", null);
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });
    }
}
