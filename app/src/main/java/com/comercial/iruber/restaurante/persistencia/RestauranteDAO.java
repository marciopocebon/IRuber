package com.comercial.iruber.restaurante.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.comercial.iruber.infra.EnumTipo;
import com.comercial.iruber.infra.persistencia.DbHelper;
import com.comercial.iruber.restaurante.dominio.Restaurante;
import com.comercial.iruber.usuario.persistencia.UsuarioDAO;

public class RestauranteDAO {
    private DbHelper bancoDados;
    private UsuarioDAO usuarioDAO;



    String tabela = DbHelper.TABELA_RESTAURANTE;



    public RestauranteDAO(Context context) {
        bancoDados = new DbHelper(context);
        usuarioDAO=  new UsuarioDAO(context);
    }

    public long inserirRestaurante(Restaurante restaurante){
        SQLiteDatabase bancoEscreve = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        restaurante.getUsuario().setTipo(EnumTipo.RESTAURANTE);
        long idUser= this.usuarioDAO.inserirUsuario(restaurante.getUsuario());
        restaurante.getUsuario().setId(idUser);



        long id = bancoEscreve.insert(tabela, null, values);
        bancoEscreve.close();
        return id;
    }


    public Restaurante criarRestaurante(Cursor cursor){
        String colunaId = DbHelper.RESTAURANTE_ID;
        int indexColunaId= cursor.getColumnIndex(colunaId);
        long id = cursor.getLong(indexColunaId);

        String colunaPessoaId = DbHelper.RESTAURANTE_ID_PESSOA;
        int indexColunaIdPessoa = cursor.getColumnIndex(colunaPessoaId);
        long idPessoa = cursor.getLong(indexColunaIdPessoa);




        Restaurante restaurante = new Restaurante();
        restaurante.setIdRestaurante(id);




        return restaurante;
    }

    private Restaurante criar(String query, String[] args) {
        SQLiteDatabase leitorBanco = bancoDados.getReadableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Restaurante restaurante = null;
        if (cursor.moveToNext()) {
            restaurante = criarRestaurante(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return restaurante;
    }
    public Restaurante getRestauranteByIdEmpresa(long id) {
        String query =  "SELECT * FROM cliente " +
                "WHERE idEmpresa = ?";
        String[] args = {String.valueOf(id)};
        return this.criar(query, args);
    }


}
