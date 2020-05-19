package com.example.dudunotas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dudunotas.Model.AdapterNota;
import com.example.dudunotas.Model.Nota;
import com.example.dudunotas.Model.NotasDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Nota> notas = new ArrayList<Nota>();
    AdapterNota arrayAdapter;
    ListView lista_notas;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista_notas = (ListView)findViewById(R.id.listanotas);
        atualizarNotas();

        //Método que verifica quando o usuário clica em um item da lista.
        //Este método envia uma intent para classe do editor informando a posição do item selecionado. Esta posição é a mesma do arrayList.
        lista_notas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,editor.class);
                intent.putExtra("notaId",notas.get(position).getId());
                //Inicia a activity do editor
                startActivity(intent);
            }
        });


        //Esse método verifica se o usuário manteve um item da lista pressionado.
        //Caso mantenha pressionado, será mostrado um dialog perguntando se ele deseja excluir a nota selecionada.
        lista_notas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja mesmo excluir?")
                        .setNegativeButton("Não",null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                excluirNota(notas.get(position));
                            }
                        });
                adb.show();
                return true;
            }
        });

        btnAdd = (Button)findViewById(R.id.btnadicionar);
        //Inicia a activity de Escrever notas ao clicar no botão criar nota.
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,editor.class);
                startActivity(intent);
            }
        });

    }


    public void excluirNota(Nota nota){
        NotasDAO notasDAO = new NotasDAO(MainActivity.this);
        notasDAO.excluirNota(nota);
        atualizarNotas();
    }


    public void atualizarNotas(){
        NotasDAO notasDAO = new NotasDAO(MainActivity.this);

        if(notasDAO.obterNotas()!=null){
            notas = new ArrayList<Nota>(notasDAO.obterNotas());
        }

        arrayAdapter = new AdapterNota(this,notas);
        lista_notas.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarNotas();
    }

}
