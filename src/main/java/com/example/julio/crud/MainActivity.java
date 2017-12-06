package com.example.julio.crud;

import android.content.Intent;
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

import com.example.julio.crud.banco.PessoaBanco;
import com.example.julio.crud.modelo.Pessoa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listaPessoas;
    Button btnNovoCadastro;
    Pessoa pessoa;
    PessoaBanco pessoaBanco;
    ArrayList<Pessoa> arrayListPessoa;
    ArrayAdapter<Pessoa> arrayAdapterPessoa;
    Button btnSobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaPessoas = (ListView) findViewById(R.id.listaPessoas);
        registerForContextMenu(listaPessoas);

        btnNovoCadastro = (Button) findViewById(R.id.btnNovoCadastro);
        btnSobre = (Button) findViewById(R.id.btnSobre);

        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FormPessoas.class);
                startActivity(i);
            }
        });

        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SobreActivity.class);
                startActivity(i);
            }
        });


        listaPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
             Pessoa pessoaEnviada = (Pessoa) arrayAdapterPessoa.getItem(position);

             Intent i = new Intent (MainActivity.this, FormPessoas.class);
             i.putExtra("pessoa-enviada",pessoaEnviada);
             startActivity(i);
         }

        });

        listaPessoas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pessoa = arrayAdapterPessoa.getItem(position);
                return false;
            }
        });
    }


    public void popularLista(){
        pessoaBanco= new PessoaBanco(MainActivity.this);

        arrayListPessoa = pessoaBanco.selectAllPessoa();
        pessoaBanco.close();

        if (listaPessoas != null){
            arrayAdapterPessoa = new ArrayAdapter<Pessoa>(MainActivity.this,
                    android.R.layout.simple_list_item_1, arrayListPessoa);
            listaPessoas.setAdapter(arrayAdapterPessoa);
        }
    }

    protected void onResume() {
        super.onResume();
        popularLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Delete Registro");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick (MenuItem item){
                long retornoDB;
                pessoaBanco = new PessoaBanco(MainActivity.this);
                retornoDB = pessoaBanco.excluirPessoa(pessoa);
                pessoaBanco.close();

                if (retornoDB == -1){
                    alert ("Erro de exclusão");
                }else {
                    alert("Registro excluído com sucesso");
                }
                popularLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
