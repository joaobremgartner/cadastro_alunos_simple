package br.com.caelum.cadastro.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.model.Aluno;

/**
 * Created by joaofaro on 07/07/15.
 */
public class AlunoDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "CadastroCaelum";
    private  static final String TABELA = "aluno";
    private static final int VERSAO = 66;
    private List<Aluno> lista;

    public AlunoDAO(Context contexto) {
        super(contexto, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABELA + "(id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, telefone TEXT, " +
                "endereco TEXT, site TEXT, " +
                "nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TEXT;";
        db.execSQL(sql);
    }

    public void insere(Aluno aluno){

        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getEndereco());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoDaFoto());

        //Imprime o Id do aluno cadastrado no Logcat
        Long i = getWritableDatabase().insert(TABELA, null, values);
        Log.i("Cadastro", "Aluno cadastrado: " + i);
    }

    public List<Aluno> getLista() {
        this.lista = new ArrayList<Aluno>();
        String sql = "SELECT * FROM " + TABELA;

        Cursor c = null;
        try {
            c = getReadableDatabase().rawQuery(sql, null);

            while (c.moveToNext()) {
                Aluno aluno = new Aluno();

                aluno.setId(c.getLong(c.getColumnIndex("id")));
                aluno.setNome(c.getString(c.getColumnIndex("nome")));
                aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
                aluno.setSite(c.getString(c.getColumnIndex("site")));
                aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
                aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
                aluno.setCaminhoDaFoto(c.getString(c.getColumnIndex("caminhoFoto")));

                lista.add(aluno);
            }


        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return lista;
    }

    public void deletar(Aluno aluno){

        getWritableDatabase().delete(TABELA, "id=?",
                new String[] {aluno.getId().toString()});

    }

    public void altera(Aluno aluno){

        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("telefone", aluno.getTelefone());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoDaFoto());

        getWritableDatabase().update(TABELA, values, "id=?",
                new String[]{aluno.getId().toString()});
    }
}
