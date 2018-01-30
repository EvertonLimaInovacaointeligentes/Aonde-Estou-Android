package br.com.inovacaointeligentes.ondeestou.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.inovacaointeligentes.ondeestou.modelo.Pessoa;

/**
 * Created by edwin on 24/07/2017.
 */

public class PessoaDao extends SQLiteOpenHelper {
    public PessoaDao(Context context) {
        super(context, "OndeEstouMVP", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table pessoa(id integer primary key,nome text,email text,senha text,telefone text);";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS pessoa";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();
        //criando conent value, objeto aonde vai salvar os dados para a coluna aonde vai cada elemento do objeto pessoa correspodente
        ContentValues dados = getContentValuesPessoa(pessoa);
        //inserindo o cantentvalue carregado com o objeto pessoas
        db.insert("pessoa",null,dados);

    }

    @NonNull
    private ContentValues getContentValuesPessoa(Pessoa pessoa) {
        ContentValues dados=new ContentValues();
        dados.put("nome",pessoa.getNome());
        dados.put("email",pessoa.getEmail());
        dados.put("senha",pessoa.getSenha());
        dados.put("telefone",pessoa.getTelefone());


        return dados;
    }

    public List<Pessoa> buscarPessoas() {
        String sql="select * from pessoa;";
        SQLiteDatabase db=getReadableDatabase();
        Cursor c= db.rawQuery(sql,null);
        List<Pessoa>listaPessoas=new ArrayList<Pessoa>();

        while(c.moveToNext())
        {
            Pessoa PessoaTemp=new Pessoa();
            PessoaTemp.setId(c.getLong(c.getColumnIndex("id")));
            PessoaTemp.setNome(c.getString(c.getColumnIndex("nome")));
            PessoaTemp.setEmail(c.getString(c.getColumnIndex("email")));
            PessoaTemp.setSenha(c.getString(c.getColumnIndex("senha")));
            PessoaTemp.setTelefone(c.getString(c.getColumnIndex("telefone")));
            listaPessoas.add(PessoaTemp);
        }
        c.close();
        return listaPessoas;
    }

    public void removerPessoa(Pessoa pessoa) {
        SQLiteDatabase db=getWritableDatabase();
        String[]paramts={pessoa.getId().toString()};
        db.delete("pessoa","id = ?",paramts);


    }

    public Pessoa buscarPessoa(Pessoa pessoaabucsar)
    {
        String sql="select * from pessoa;";
        SQLiteDatabase db=getReadableDatabase();
        Cursor c= db.rawQuery(sql,null);
        List<Pessoa>listaPessoas=new ArrayList<Pessoa>();
        Pessoa pessoaCarregado=new Pessoa();
        while(c.moveToNext())
        {
            Pessoa PessoaTemp=new Pessoa();

            PessoaTemp.setId(c.getLong(c.getColumnIndex("id")));
            PessoaTemp.setNome(c.getString(c.getColumnIndex("nome")));
            PessoaTemp.setEmail(c.getString(c.getColumnIndex("email")));
            PessoaTemp.setSenha(c.getString(c.getColumnIndex("senha")));
            PessoaTemp.setTelefone(c.getString(c.getColumnIndex("celular")));

            if((PessoaTemp.getNome()==pessoaabucsar.getNome())&&(PessoaTemp.getTelefone()==pessoaabucsar.getTelefone())&&(PessoaTemp.getSenha()==pessoaabucsar.getSenha())&&(PessoaTemp.getEmail()==pessoaabucsar.getEmail()))
            {
                pessoaabucsar=PessoaTemp;
                break;
            }
            else{
                PessoaTemp=null;
                pessoaabucsar=null;
            }
        }
        c.close();
        return pessoaabucsar;

    }


    public void altera(Pessoa pessoa) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues dados = getContentValuesPessoa(pessoa);
        String[] parms={pessoa.getId().toString()};
        db.update("pessoa",dados,"id = ?",parms);
    }

}
