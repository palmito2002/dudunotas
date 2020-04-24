package com.example.dudunotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;

public class editor extends AppCompatActivity {
EditText txtNota;
Button btnsalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        txtNota = (EditText)findViewById(R.id.txtNota);
        btnsalvar = (Button)findViewById(R.id.btnsalvar);

        //Habilita o botão voltar na barra de ações do app
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarNota();
            }
        });

        //Recebe a intent enviada pela outra activity com a posição do item selecionado na lista.
        //Caso seja diferente de -1, significa que o usuario esta querendo modificar uma nota ja existente.
        //Então, ele pega o texto do item da posicão informada no array de notas e coloca na caixa de texto.
        Intent intent = getIntent();
        final int notaId = intent.getIntExtra("notaId",-1);
        if(notaId!=-1){
            txtNota.setText(MainActivity.notas.get(notaId));
        }

    }

    public void salvarNota(){
        //Recebe a intent enviada pela outra activity com a posição do item selecionado na lista.
        Intent intent = getIntent();
        final int notaId = intent.getIntExtra("notaId",-1);

        //Verifica se há texto digitado na caixa de texto
        if(txtNota.getText().toString().length()>0){

            //Verifica se o notaId(Posição do item selecionado) é diferente de -1.
            //Caso seja diferente de -1, significa que o usuario esta querendo modificar uma nota ja existente.
            //Caso contrário, significa que ele está criando uma nova nota.
            if(notaId!=-1){
                MainActivity.notas.set(notaId,txtNota.getText().toString());
                MainActivity.arrayAdapter.notifyDataSetInvalidated();
            }
            else{
                MainActivity.notas.add(txtNota.getText().toString());
                MainActivity.arrayAdapter.notifyDataSetInvalidated();
            }


            //Carrega o SharedPreferences do app
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.dudunotas", Context.MODE_PRIVATE);
            //Cria um HashSet para salvar o arrayList de notas
            HashSet<String> set = new HashSet<>(MainActivity.notas);
            //Salva o HashSet no sharedPreferences
            sharedPreferences.edit().putStringSet("notas",set).apply();
            //Finaliza a activity
            finish();

        }
    }


    //Ao clicar no botão voltar, ele verifica se há texto para ser salvo.
    //Utiliza o mesmo método do botão salvar.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        salvarNota();
    }

    // Faz com que o botão voltar da toolbar funcione igual ao do celular
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}
