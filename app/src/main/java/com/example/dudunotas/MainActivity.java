package com.example.dudunotas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notas = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;
    ListView lista_notas;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Carrega o sharedPreferences do app e busca as notas salvas em um HashSet.
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.dudunotas", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notas", null);

        //Verifica se o hashSet não é nulo, e então, passa ele para o o arrayList.
        if (set!=null){
            notas = new ArrayList<String>(set);
        }

        //Criar o array_adapter, passando como parâmetro o contexto,o estilo que vai ser exibido o item da lista e també, o arrayList que será utilizado.
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notas);
        lista_notas = (ListView)findViewById(R.id.listanotas);
        //Informa o array_adapter para o listView
        lista_notas.setAdapter(arrayAdapter);


        //Método que verifica quando o usuário clica em um item da lista.
        //Este método envia uma intent para classe do editor informando a posição do item selecionado. Esta posição é a mesma do arrayList.
        lista_notas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,editor.class);
                intent.putExtra("notaId",position);
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
                                //Caso u usuário tenha escolhido excluir, o item será removido do arrayList, atualizado o listView e també,
                                //serão atualizadas as sharedPreferences.

                                notas.remove(position);
                                arrayAdapter.notifyDataSetInvalidated();

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.dudunotas", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(notas);
                                sharedPreferences.edit().putStringSet("notas",set).apply();

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

}
