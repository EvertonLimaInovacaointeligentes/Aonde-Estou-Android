package br.com.inovacaointeligentes.ondeestou;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.inovacaointeligentes.ondeestou.dao.PessoaDao;
import br.com.inovacaointeligentes.ondeestou.modelo.Pessoa;
/**
 * Created by Everton Lima on 30/01/2018.
 tela java chamada no oncreate do android
 */
public class telaPrincipal extends AppCompatActivity {

    private ListView visao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        visao= (ListView) findViewById(R.id.lista_novo);
        visao.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Pessoa pessoa=(Pessoa)visao.getItemAtPosition(position);
                Toast.makeText(telaPrincipal.this,"Pessoa: "+pessoa.getNome()+", entrou",Toast.LENGTH_SHORT).show();
                Intent vaiParaFormulario=new Intent(telaPrincipal.this,Formulario.class);

                vaiParaFormulario.putExtra("pessoa",pessoa);
                startActivity(vaiParaFormulario);
            }
        });
        Button btNovoAluno=(Button)findViewById(R.id.lista_btNovoAluno);
        btNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro= new Intent(telaPrincipal.this,Formulario.class);
                startActivity(cadastro);
            }
        });
        registerForContextMenu(visao);

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista() {
        PessoaDao dao=new PessoaDao(this);
        List<Pessoa> alunos= dao.buscarPessoas();
        dao.close();


        ArrayAdapter<Pessoa> adapter=new ArrayAdapter<Pessoa>(this,android.R.layout.simple_list_item_1,alunos);
        visao.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
        final Pessoa pessoa=(Pessoa)visao.getItemAtPosition(info.position);
        MenuItem ligar=menu.add("Ligar para Aluno");
        Intent itentLigar=new Intent(Intent.ACTION_CALL);
        itentLigar.setData(Uri.parse("tel:"+pessoa.getTelefone()));
        ligar.setIntent(itentLigar);
        


        //enviar sms
        MenuItem sms=menu.add("Enviar SMS");
        Intent intentSMS=new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:"+pessoa.getTelefone()));
        sms.setIntent(intentSMS);

        //visitar site aluno
        MenuItem visita=menu.add("Visitar Site");
        Intent nav=new Intent(Intent.ACTION_VIEW);
        nav.setData(Uri.parse("http://"+pessoa.getEmail()));
        visita.setIntent(nav);
        //removendo pessoa
        MenuItem deletar= menu.add("Remover");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {

                PessoaDao dao=new PessoaDao(telaPrincipal.this);
                dao.removerPessoa(pessoa);
                dao.close();
                carregarLista();
                Toast.makeText(telaPrincipal.this,"Pessoa: "+pessoa.getNome()+", Removido",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
}
