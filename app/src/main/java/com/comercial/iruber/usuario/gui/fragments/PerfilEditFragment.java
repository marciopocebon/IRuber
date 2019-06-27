package com.comercial.iruber.usuario.gui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.comercial.iruber.R;
import com.comercial.iruber.cliente.dominio.Cliente;
import com.comercial.iruber.infra.EnumTipo;
import com.comercial.iruber.infra.Sessao;
import com.comercial.iruber.restaurante.dominio.Entregador;
import com.comercial.iruber.restaurante.dominio.Restaurante;
import com.comercial.iruber.restaurante.gui.fragments.CadastroIngredienteFragment;
import com.comercial.iruber.usuario.dominio.Usuario;
import com.comercial.iruber.usuario.negocio.ServicoUsuario;

public class PerfilEditFragment extends Fragment {
    protected String campoAlterar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_perfil_edit, container, false);
        Button cancelar = inflate.findViewById(R.id.cancelarAlterar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfil();
            }
        });
        final Usuario usuario = Sessao.getSessao(getContext());
        Restaurante restaurante = new Restaurante();
        Cliente cliente = new Cliente();
        Entregador entregador = new Entregador();
        if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
            restaurante = Sessao.getSessaoRestaurante(getContext());
        } else if (usuario.getTipo() == EnumTipo.CLIENTE) {
            cliente = Sessao.getSessaoCliente(getContext());
        } else {
            entregador = Sessao.getSessaoEntregador(getContext());
        }
        EditText alterarCampo = inflate.findViewById(R.id.alterarPerfilEdit);
        TextView alterarCampoView = inflate.findViewById(R.id.textViewAlterar);
        switch (campoAlterar) {
            case "email":
                alterarCampoView.setText("E-mail");
                alterarCampo.setText(usuario.getEmail());
                ServicoUsuario servicoUsuario = new ServicoUsuario(getContext());
                servicoUsuario.updateUsuario(usuario);
                break;
            case "nome":
                alterarCampoView.setText("Nome Completo");
                if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
                    alterarCampo.setText(restaurante.getNome());
                } else if (usuario.getTipo() == EnumTipo.CLIENTE) {
                    alterarCampo.setText(cliente.getNome());
                } else if (usuario.getTipo() == EnumTipo.ENTREGADOR) {
                    alterarCampo.setText(entregador.getNome());
                }
                break;
            case "documento":
                if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
                    alterarCampoView.setText("Cnpj");
                    alterarCampo.setText(restaurante.getCnpj());
                } else {
                    alterarCampoView.setText("Cpf");
                    alterarCampo.setText(cliente.getCpf());
                }
                break;
            case "telefone":
                alterarCampoView.setText("Telefone");
                if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
                    alterarCampo.setText(restaurante.getTelefone());
                } else if (usuario.getTipo() == EnumTipo.ENTREGADOR) {
                    alterarCampo.setText(entregador.getTelefone());
                }
                break;
            case "senha":
                alterarCampoView.setText("Senha");
                break;
            case "endereco":
                alterarCampoView.setText("Endereço");
                if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
                    alterarCampo.setText(restaurante.getEndereco().getBairro());
                } else if (usuario.getTipo() == EnumTipo.CLIENTE) {
                    alterarCampo.setText(cliente.getEndereco().getBairro());
                }
                break;
        }
        Button alterarPerfil = inflate.findViewById(R.id.alterarPerfil);
        final Restaurante finalRestaurante = restaurante;
        final Cliente finalCliente = cliente;
        final Entregador finalEntregador = entregador;
        alterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText campo = inflate.findViewById(R.id.alterarPerfilEdit);
                String alterar = campo.getText().toString();
                ServicoUsuario servicoUsuario = new ServicoUsuario(getContext());
                switch (campoAlterar) {
                    case "email":
                        usuario.setEmail(alterar);
                        servicoUsuario.updateUsuario(usuario);
                        break;
                    case "nome":
                        if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
                            finalRestaurante.setNome(alterar);
                        } else if (usuario.getTipo() == EnumTipo.CLIENTE) {
                            finalCliente.setNome(alterar);
                        } else if (usuario.getTipo() == EnumTipo.ENTREGADOR) {
                            finalEntregador.setNome(alterar);
                        }
                        break;
                    case "senha":
                        usuario.setSenha(alterar);
                        servicoUsuario.updateUsuario(usuario);
                        break;
                    case "documento":
                        if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
                            finalRestaurante.setCnpj(alterar);
                        } else {
                            finalCliente.setCpf(alterar);
                        }
                        break;
                    case "telefone":
                        if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
                            finalRestaurante.setTelefone(alterar);
                        } else if (usuario.getTipo() == EnumTipo.ENTREGADOR) {
                            finalEntregador.setTelefone(alterar);
                        } else {
                            finalCliente.setTelefone(alterar);
                        }
                        break;
                    case "endereco":
                        EditText cidade = inflate.findViewById(R.id.cidadeAlterar);
                        EditText bairro = inflate.findViewById(R.id.bairroAlterar);
                        EditText rua = inflate.findViewById(R.id.ruaAlterar);
                        EditText numero = inflate.findViewById(R.id.numeroAlterar);
                        EditText cep = inflate.findViewById(R.id.cepAlterar);
                        if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
                            finalRestaurante.getEndereco().setCidade(cidade.getText().toString());
                            finalRestaurante.getEndereco().setBairro(bairro.getText().toString());
                            finalRestaurante.getEndereco().setRua(rua.getText().toString());
                            finalRestaurante.getEndereco().setNumero(numero.getText().toString());
                            finalRestaurante.getEndereco().setCep(cep.getText().toString());
                        } else if (usuario.getTipo() == EnumTipo.CLIENTE) {
                            finalCliente.getEndereco().setCidade(cidade.getText().toString());
                            finalCliente.getEndereco().setBairro(bairro.getText().toString());
                            finalCliente.getEndereco().setRua(rua.getText().toString());
                            finalCliente.getEndereco().setNumero(numero.getText().toString());
                            finalCliente.getEndereco().setCep(cep.getText().toString());
                        }
                        break;
                }
                Sessao sessao = new Sessao();
                sessao.editSessao(usuario, getContext());
                if (usuario.getTipo() == EnumTipo.RESTAURANTE) {
                    sessao.editSessaoRestaurante(finalRestaurante,getContext());
                } else if (usuario.getTipo() == EnumTipo.ENTREGADOR) {
                    sessao.editSessaoEntregador(finalEntregador, getContext());
                }else {
                    sessao.editSessaoCliente(finalCliente, getContext());
                }
            }
        });
        return inflate;
    }

    public void abrirPerfil() {
        getActivity().setTitle("Perfil");
        Fragment mFrag = new PerfilUsuarioFragment();
        FragmentTransaction t = getFragmentManager().beginTransaction();
        if (Sessao.getSessao(getContext()).getTipo().getDescricao().equals(EnumTipo.RESTAURANTE.getDescricao())) {
            t.replace(R.id.frameRestaurante, mFrag);
        } else if (Sessao.getSessao(getContext()).getTipo().getDescricao().equals(EnumTipo.CLIENTE.getDescricao())) {
            t.replace(R.id.frameCliente, mFrag);
        } else {
            t.replace(R.id.placeHolder, mFrag);
        }
        t.addToBackStack(null);
        t.commit();
    }

}
