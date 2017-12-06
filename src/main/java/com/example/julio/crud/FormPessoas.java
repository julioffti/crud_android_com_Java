package com.example.julio.crud;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julio.crud.banco.PessoaBanco;
import com.example.julio.crud.modelo.Pessoa;

public class FormPessoas extends AppCompatActivity {
    EditText editNome, editTelefone, editIdade, editEndereco;
    Button btnOpcao;
    Pessoa pessoa, outPessoa;
    PessoaBanco pessoaBanco;
    Button btnLigar;
    long retornoDB;

    public void alerta(String titulo, String mensagem) //ALERT PARA MENSAGEM NA TELA
    {
        AlertDialog.Builder alerta = new AlertDialog.Builder(FormPessoas.this);

        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setNeutralButton("OK", null);
        alerta.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pessoas);

        Intent i = getIntent();
        outPessoa = (Pessoa) i.getSerializableExtra("pessoa-enviada");
        pessoa = new Pessoa();
        pessoaBanco = new PessoaBanco(FormPessoas.this);

        editNome = (EditText) findViewById(R.id.editNome);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        editEndereco = (EditText) findViewById(R.id.editEndereco);
        editIdade = (EditText) findViewById(R.id.editIdade);
        btnOpcao = (Button) findViewById(R.id.btnOpcao);
        btnLigar = (Button) findViewById(R.id.btnLigar);

        //mostrar se é um novo cadastro
        if(outPessoa != null){
            btnOpcao.setText("Alterar");
            editNome.setText(outPessoa.getNome());
            editIdade.setText(outPessoa.getIdade()+"");
            editEndereco.setText(outPessoa.getEndereco());
            editTelefone.setText(outPessoa.getTelefone());

            pessoa.setId(outPessoa.getId());
        }else{
            btnOpcao.setText("Salvar");
        }



        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String numero = editTelefone.getText().toString();
                Uri uri = Uri.parse("tel:" + numero);

                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                if (ActivityCompat.checkSelfPermission(FormPessoas.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(FormPessoas.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent);
            }
        });



        btnOpcao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                pessoa.setNome(editNome.getText().toString());
                pessoa.setIdade(Integer.parseInt(editIdade.getText().toString()));
                pessoa.setEndereco(editEndereco.getText().toString());
                pessoa.setTelefone(editTelefone.getText().toString());

                if( //CASO ALGO NÃO SEJA PREENCHIDO
                        editNome.getText().length() == 0 ||
                                editTelefone.getText().length() == 0   ||
                                editEndereco.getText().length() == 0  ||
                                editIdade.getText().length() == 0 ) {
                    alerta("Atenção!", "É necessario que todos os dados sejam preenchidos");
                    return;
                }

                if (btnOpcao.getText().toString().equals("Salvar")){
                    retornoDB = pessoaBanco.salvarPessoa(pessoa);
                    pessoaBanco.close();
                    if (retornoDB == -1){
                        alert("Erro ao cadastrar");
                    }else {
                        alert("Cadastro realizado com sucesso");
                    }
                }else{
                    retornoDB = pessoaBanco.alterarPessoa(pessoa);
                    pessoaBanco.close();
                    if(retornoDB==-1){
                        alert("Erro ao alterar");
                    }else{
                        alert("Atualização realizada com sucesso");
                    }
                };
                finish();
            }
        });
    }
    private void alert(String s){
        Toast.makeText(this, s , Toast.LENGTH_SHORT).show();
    }
}
