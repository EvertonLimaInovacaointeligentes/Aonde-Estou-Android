package br.com.inovacaointeligentes.ondeestou;

        import android.widget.EditText;
        import android.widget.RatingBar;

        import br.com.inovacaointeligentes.ondeestou.modelo.Pessoa;

/**
 * Created by edwin on 25/07/2017.
 */

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoTelefone;
    private final EditText campoSenha;
    private final EditText campoEmail;


    private Pessoa pessoa;

    public FormularioHelper(Formulario activity){
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEmail=(EditText)activity.findViewById(R.id.formulario_email);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoSenha = (EditText) activity.findViewById(R.id.formulario_senha);


        pessoa =new Pessoa();
    }

    public Pessoa getPessoa() {

        pessoa.setNome(campoNome.getText().toString());
        pessoa.setEmail(campoEmail.getText().toString());
        pessoa.setTelefone(campoTelefone.getText().toString());
        pessoa.setSenha(campoSenha.getText().toString());



        return pessoa;
    }

    public void preencheFormulario(Pessoa pessoa) {
        campoNome.setText(pessoa.getNome());
        campoEmail.setText(pessoa.getEmail());
        campoTelefone.setText(pessoa.getTelefone());
        campoSenha.setText(pessoa.getSenha());


        this.pessoa=pessoa;
    }
}
