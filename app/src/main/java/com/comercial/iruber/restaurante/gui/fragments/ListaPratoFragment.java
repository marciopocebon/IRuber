package com.comercial.iruber.restaurante.gui.fragments;


import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.comercial.iruber.R;
import com.comercial.iruber.infra.Sessao;
import com.comercial.iruber.pedido.dominio.Pedido;
import com.comercial.iruber.restaurante.dominio.Prato;
import com.comercial.iruber.restaurante.gui.PratosAdapter;
import com.comercial.iruber.restaurante.negocio.PratoServicos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaPratoFragment extends Fragment {
    private ArrayList<Prato> pratos;
    private Button novoPrato;
    private EditText buscaPrato;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_lista_pratos, container, false);
        final RecyclerView rvPratos = (RecyclerView) inflate.findViewById(R.id.recyclerPrato);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        PratoServicos pratoServicos = new PratoServicos(getContext());
        pratos = pratoServicos.listarPratos(Sessao.getSessaoRestaurante(getContext()));
        rvPratos.setLayoutManager(linearLayoutManager);
        PratosAdapter adapter = new PratosAdapter(pratos);
        rvPratos.setAdapter(adapter);
        novoPrato = inflate.findViewById(R.id.novoPrato);
        novoPrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarPrato();
            }
        });
        buscaPrato = inflate.findViewById(R.id.buscaPrato);
        buscaPrato.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Prato> pratosad = new ArrayList<Prato>();
                int indice = 0;
                for (int i = 0; i < pratos.size(); i++) {
                    if (pratos.get(i).getNome().contains(buscaPrato.getText().toString())) {
                        pratosad.add(indice, pratos.get(i));
                        indice += 1;
                    }
                }
                RecyclerView.Adapter adapter = new PratosAdapter(pratosad);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                rvPratos.setLayoutManager(llm);
                rvPratos.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return inflate;
    }

    public void criarPrato() {
        getActivity().setTitle("Novo Prato");
        FragmentTransaction t = getFragmentManager().beginTransaction();
        Fragment mFrag = new CadastroPratoFragment();
        t.replace(R.id.listaPratos, mFrag);
        t.commit();
    }

}
