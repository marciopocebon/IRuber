package com.comercial.iruber.infra.persistencia;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.comercial.iruber.cliente.persistencia.ContratoCliente;
import com.comercial.iruber.pedido.dominio.persistencia.ContratoItemPedido;
import com.comercial.iruber.pedido.dominio.persistencia.ContratoPedido;
import com.comercial.iruber.restaurante.persistencia.ContratoEntregador;
import com.comercial.iruber.restaurante.persistencia.ContratoIngrediente;
import com.comercial.iruber.restaurante.persistencia.ContratoIngredientePrato;
import com.comercial.iruber.restaurante.persistencia.ContratoPrato;
import com.comercial.iruber.restaurante.persistencia.ContratoRestaurante;
import com.comercial.iruber.usuario.persistencia.ContratoEndereco;
import com.comercial.iruber.usuario.persistencia.ContratoUsuario;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, Contrato.DATABASE_NAME, null, Contrato.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContratoEntregador.SQL_CREATE_TABLE_ENTREGADOR);
        db.execSQL(ContratoUsuario.SQL_CREATE_TABLE_USUARIO);
        db.execSQL(ContratoCliente.SQL_CREATE_TABLE_CLIENTE);
        db.execSQL(ContratoRestaurante.SQL_CREATE_TABLE_RESTAURANTE);
        db.execSQL(ContratoIngrediente.SQL_CREATE_TABLE_INGREDIENTE);
        db.execSQL(ContratoPrato.SQL_CREATE_TABLE_PRATO);
        db.execSQL(ContratoPedido.SQL_CREATE_TABLE_PEDIDO);
        db.execSQL(ContratoItemPedido.SQL_CREATE_TABLE_ITEM_PEDIDO);
        db.execSQL(ContratoIngredientePrato.SQL_CREATE_TABLE_INGREDIENTE_PRATO);
        db.execSQL(ContratoEndereco.SQL_CREATE_TABLE_ENDERECO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContratoUsuario.SQL_DELETE_USUARIOS);
        db.execSQL(ContratoCliente.SQL_DELETE_ADDRESS);
        db.execSQL(ContratoEntregador.SQL_DELETE_ENTREGADOR);
        db.execSQL(ContratoRestaurante.SQL_DELETE_RESTAURANTE);
        db.execSQL(ContratoIngrediente.SQL_DELETE_INGREDIENTE);
        db.execSQL(ContratoPrato.SQL_DELETE_PRATO);
        db.execSQL(ContratoPedido.SQL_DELETE_PEDIDO);
        db.execSQL(ContratoItemPedido.SQL_DELETE_ITEM_PEDIDO);
        db.execSQL(ContratoIngredientePrato.SQL_DELETE_INGREDIENTE_PRATO);
        db.execSQL(ContratoEndereco.SQL_DELETE_TABLE);
        this.onCreate(db);
    }
}


