package br.com.inovacaointeligentes.ondeestou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import br.com.inovacaointeligentes.ondeestou.dao.PessoaDao;
import br.com.inovacaointeligentes.ondeestou.modelo.Pessoa;

public class Formulario extends AppCompatActivity {

    private FormularioHelper helper;
//projeto Criado Por Everton Lima email:inovacaointeligentes@gmail.com
//tela inicial aonde lista todos os usuarios cadastrados aonde tem preencheFormulario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        // aluno=new Aluno();
        helper=new FormularioHelper(Formulario.this);
        Intent intent=getIntent();
        Pessoa pessoa=(Pessoa)intent.getSerializableExtra("pessoa");
        if(pessoa!=null)
        {
            helper.preencheFormulario(pessoa);
        }



    }
	//chamando estilos dos botoes do xml menu_formulario
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_formulario,menu);

        return super.onCreateOptionsMenu(menu);
    }

	//metodo para inserir usuario novo e allterar usuario existente com avisos de acordo com o evento chamado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok: {


                Pessoa pessoa=helper.getPessoa();
                PessoaDao dao=new PessoaDao(Formulario.this);



                if(pessoa.getId()==null) {
                    dao.insere(pessoa);
                    Toast.makeText(Formulario.this, "Pessoa: "+pessoa.getNome().toString() +", Salvo com Sucesso!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dao.altera(pessoa);
                    Toast.makeText(Formulario.this, "Aluno: "+pessoa.getNome().toString() +", Editado com Sucesso!!", Toast.LENGTH_SHORT).show();
                }
                dao.close();
                finish();
                break;
            }
            case R.id.menu_formulario_cancelar:{
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
