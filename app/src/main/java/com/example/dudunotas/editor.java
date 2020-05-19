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

import com.example.dudunotas.Model.Nota;
import com.example.dudunotas.Model.NotasDAO;

import java.util.HashSet;

public class editor extends AppCompatActivity {
EditText txtNota,txtTitulo;
Button btnsalvar;
int idNota=0;
Nota nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        txtNota = (EditText)findViewById(R.id.txtNota);
        txtTitulo = (EditText)findViewById(R.id.txtTituloNota);
        btnsalvar = (Button)findViewById(R.id.btnsalvar);

        //Habilita o botão voltar na barra de ações do app
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarNota();
            }
        });

        Intent intent = getIntent();
        idNota = intent.getIntExtra("notaId",-1);
        if(idNota!=-1){
            NotasDAO notasDAO = new NotasDAO(this);
            nota = notasDAO.obterNota(idNota);
            txtTitulo.setText(nota.getTitulo());
            txtNota.setText(nota.getTexto());
        }
        else{
            nota = new Nota();
        }

    }

    public void salvarNota(){

        //Verifica se há texto digitado na caixa de texto
        if(txtNota.getText().toString().length()>0 && txtTitulo.getText().toString().length()>0){
            NotasDAO notasDAO = new NotasDAO(editor.this);
            nota.setTexto(txtNota.getText().toString());
            nota.setTitulo(txtTitulo.getText().toString());
            if(idNota!=-1){
                notasDAO.atualizarNota(nota);
            }
            else{
                notasDAO.inserirNota(nota);
            }
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
