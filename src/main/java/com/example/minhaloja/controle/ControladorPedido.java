package com.example.minhaloja.controle;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.minhaloja.modelo.Cliente;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.modelo.Pedido;
import com.example.minhaloja.repositorios.RepositorioCliente;
import com.example.minhaloja.repositorios.RepositorioItem;
import com.example.minhaloja.repositorios.RepositorioPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorPedido{
    @Autowired
    RepositorioCliente repositorioCliente;
    @Autowired
    RepositorioItem repositorioItem;
    @Autowired
    RepositorioPedido repositorioPedido;
    
    @RequestMapping("/fazer_pedido")
    public ModelAndView fazerPedido(){ 

        Iterable<Cliente> clientes = repositorioCliente.findAll();
        Iterable<Item> itens = repositorioItem.findAll();

        ModelAndView retorno = new ModelAndView("pedido.html");
        retorno.addObject("clientes", clientes);
        retorno.addObject("itens", itens);
        return retorno;
    }

    @RequestMapping("/cadastrar_pedido")
    public ModelAndView cadastrarPedido(double valor, String itens, RedirectAttributes redirect, Cliente cliente, Date data){
        ModelAndView retorno = new ModelAndView("redirect:/fazer_pedido");
        String itensIds[] = itens.split(",");
        List<Item> itensPedido = new ArrayList<Item>();
        for (String id : itensIds) {
            Optional<Item> item = repositorioItem.findById(Long.parseLong(id));
            valor += item.get().getPreco();
            itensPedido.add(item.get());
        }
        Pedido pedido = new Pedido(cliente, itensPedido, data, valor);
        repositorioPedido.save(pedido);
        return retorno;
    }
}