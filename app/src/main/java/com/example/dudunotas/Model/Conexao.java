package com.example.dudunotas.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static String nome_banco="Banco"; //Nome do seu Banco de dados(opcional)
    private static  int versao_banco=1; //Versão do seu banco de dados (não opcional, sempre deve haver uma versão para o banco).

    public Conexao(Context context){
        super(context,nome_banco,null,versao_banco);
    }


    //Método responsavel por criar o banco de dados na primeira instalação do app e executar comandos sql para
    //criação de tabelas etc.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Esse método é responsavel por executar comandos sql que são passados como parâmetros em forma de String.
        db.execSQL("create table notas(id integer primary key,titulo varchar(50),texto varchar(500))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Esse método é responsavel por verificar se a versão do seu banco foi atualizada.
        //Essa atualização deve ser feita manualmente alterando o atributo versao_banco.
        //Exemplo de uso: Adicionar uma nova coluna na em uma tabela existente.
        //Lembrando que aqui você deverfazer atualizações para aplicativos que ja foram executados.
        //Caso você modificque algo aqui, você deve mudar no método onCreate também.
        // Exe: Aqui você usa add column in table no onCreate é s escrever um coluna no comando create table.
    }
}
