package br.com.caelum.cadastro.DAO;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.helper.FormularioHelper;
import br.com.caelum.cadastro.model.Aluno;


public class CadastroAluno extends ActionBarActivity {

    private Button salvarForm;
    private FormularioHelper helper;
    private Aluno pegarAluno;
    private String localDaFoto;
    private static final int FOTO_OK = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        this.helper = new FormularioHelper(this);
        salvarForm = (Button) findViewById(R.id.botao_salvar);

        pegarAluno = (Aluno) this.getIntent().getSerializableExtra("alunoSelecionado");

        if(pegarAluno != null){
            this.helper.colocaAlunoNoFormulario(pegarAluno);
        }

        Button botaoFoto = this.helper.getBotaoFoto();
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                localDaFoto = getExternalFilesDir(null) + "/" +
                        System.currentTimeMillis() + ".jpg";

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(localDaFoto)));
                startActivityForResult(camera, FOTO_OK);
            }
        });



        salvarForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroAluno.this);
                builder.setTitle("Confirmação");
                builder.setMessage("Confirma cadastro do aluno?");

                //botão SIM
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Aluno aluno = helper.getAluno();
                        AlunoDAO dao = new AlunoDAO(CadastroAluno.this);

                        if (pegarAluno != null && helper.temNome()) {
                            salvarForm.setText("Alterar aluno");
                            aluno.setId(pegarAluno.getId());
                            dao.altera(aluno);
                            finish();
                        }else if(!helper.temNome()){
                            helper.mostrarErro();
                        }else {
                            dao.insere(aluno);
                            Toast.makeText(CadastroAluno.this, "Cadastro efetuado com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        dao.close();
                    }
                });

                //Botão NÃO
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_aluno, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_form_ok:

                Aluno aluno = helper.getAluno();
                AlunoDAO dao = new AlunoDAO(CadastroAluno.this);
                Toast.makeText(CadastroAluno.this, "Aluno: "+ aluno.getId(),
                        Toast.LENGTH_SHORT).show();

                dao.close();
                finish();
                return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pegarAluno != null) {
            salvarForm.setText("Alterar aluno");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FOTO_OK){
            if(resultCode == RESULT_OK){
                this.helper.carregaImagem(this.localDaFoto);
            } else {
                this.localDaFoto = null;
            }
        }
    }
}
