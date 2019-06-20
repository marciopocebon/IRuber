package com.comercial.iruber.cliente.gui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.comercial.iruber.R;
import com.comercial.iruber.cliente.gui.MapsActivity;
import com.comercial.iruber.cliente.gui.RestaurantesAdapter;
import com.comercial.iruber.infra.EnumFiltro;
import com.comercial.iruber.infra.Sessao;
import com.comercial.iruber.restaurante.dominio.Restaurante;
import com.comercial.iruber.restaurante.negocio.RestauranteServicos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaRestauranteFragment extends Fragment {
    private List<Restaurante> restaurantes;
    private EditText buscaRestaurante;
    private EnumFiltro tipoFiltro;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate = inflater.inflate(R.layout.fragment_lista_restaurante, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            tipoFiltro = (EnumFiltro) bundle.get("TipoFiltro");
        } else {
            tipoFiltro = EnumFiltro.SEM_FILTRO;
        }
        final RecyclerView rvRestaurante = (RecyclerView) inflate.findViewById(R.id.recyclerRestaurante);
        Button btnMapa = inflate.findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        TextView filtrar = inflate.findViewById(R.id.filtrarRestaurantes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RestauranteServicos restauranteServicos = new RestauranteServicos(getContext());
        restaurantes = ordenarLista((ArrayList<Restaurante>) restauranteServicos.listarRestaurantes());
        rvRestaurante.setLayoutManager(linearLayoutManager);
        RestaurantesAdapter adapter = new RestaurantesAdapter(restaurantes);
        adapter.setOnItemClickListener(new RestaurantesAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("restaurante", restaurantes.get(position));

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                RestauranteCardapioFragment restauranteCardapioFragment = new RestauranteCardapioFragment();
                restauranteCardapioFragment.setArguments(bundle);
                transaction.replace(R.id.frameCliente, restauranteCardapioFragment);
                transaction.commit();
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
        rvRestaurante.setAdapter(adapter);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("TipoFiltro", tipoFiltro);

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                RestauranteFiltroFragment restauranteFiltroFragment = new RestauranteFiltroFragment();
                restauranteFiltroFragment.setArguments(bundle);
                transaction.replace(R.id.frameCliente, restauranteFiltroFragment);
                transaction.commit();
            }
        });
        buscaRestaurante = inflate.findViewById(R.id.buscaRestaurante);
        buscaRestaurante.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //precisa existir para utilização
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarRestaurante(rvRestaurante);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //precisa existir para utilização
            }
        });
        return inflate;
    }

    private void buscarRestaurante(RecyclerView rvRestaurantes){
        ArrayList<Restaurante> restaurantesad = new ArrayList<>();
        int indice = 0;
        for (int i = 0; i < restaurantes.size(); i++) {
            if (restaurantes.get(i).getNome().contains(buscaRestaurante.getText().toString())) {
                restaurantesad.add(indice, restaurantes.get(i));
                indice += 1;
            }
        }
        RecyclerView.Adapter adapter = new RestaurantesAdapter(restaurantesad);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvRestaurantes.setLayoutManager(llm);
        rvRestaurantes.setAdapter(adapter);
    }

    private ArrayList<Restaurante> ordenarLista(ArrayList<Restaurante> restaurantes){
        RestauranteServicos restauranteServicos = new RestauranteServicos((getContext()));
        switch (tipoFiltro){
            case SEM_FILTRO:
                restaurantes = (ArrayList<Restaurante>) restauranteServicos.listarRestaurantes();
                break;
            case NOME:
                restaurantes = ordenarListaPorNome((ArrayList<Restaurante>) restauranteServicos.listarRestaurantes());
                break;
            case AVALIACAO:
                restaurantes = ordenarListaPorAvaliacao((ArrayList<Restaurante>) restauranteServicos.listarRestaurantes());
                break;
            default:
                restaurantes = (ArrayList<Restaurante>) restauranteServicos.listarRestaurantes();
                break;
        }
        return restaurantes;

    }

    private ArrayList<Restaurante> ordenarListaPorNome(ArrayList<Restaurante> restaurantes) {
        ArrayList<Restaurante> result = new ArrayList<Restaurante>();
        ArrayList<Restaurante> lista = restaurantes;
        ArrayList<String> nomes = new ArrayList<String>();

        for(Restaurante p: restaurantes){
            nomes.add(p.getNome());
        }

        Collections.sort(nomes);
        for(String s : nomes){
            for(Restaurante p : lista){
                if(s.equals(p.getNome())){
                    result.add(p);
                    p.setNome("");
                }
            }
        }
        return result;
    }

    private ArrayList<Restaurante> ordenarListaPorAvaliacao(ArrayList<Restaurante> restaurantes) {
        ArrayList<Restaurante> result = new ArrayList<Restaurante>();
        ArrayList<Restaurante> lista = restaurantes;
        ArrayList<Double> notas = new ArrayList<Double>();

        for(Restaurante p: restaurantes){
            notas.add(p.getNota());
        }

        Collections.sort(notas);
        for(Double d : notas){
            for(Restaurante r : lista){
                if(d.equals(r.getNota())){
                    result.add(r);
                    r.setNota(-1);
                }
            }
        }
        return result;
    }
}
