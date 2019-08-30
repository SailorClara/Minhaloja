package com.example.minhaloja.controle;

import java.util.Optional;

import javax.validation.Valid;

import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.repositorios.RepositorioItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorItem{
    
    @Autowired
    RepositorioItem repositorioItem;

    @RequestMapping("/formulario_item")
    public ModelAndView formularioItem(Item item){
        ModelAndView retorno = new ModelAndView("cadastroItens.html");
        //retorno.addObject("item", item);
        return retorno;
    }

    @RequestMapping("/novo_item")
    public ModelAndView novoItem(@Valid Item item, BindingResult bindingResult, RedirectAttributes redirect){
        ModelAndView retorno; 
        if(bindingResult.hasErrors()){
            redirect.addFlashAttribute("item",item);
            retorno = new ModelAndView("cadastroItens.html");
            return retorno;
        }
        repositorioItem.save(item);
         retorno = new ModelAndView("index.html");
        return retorno;
    }

    @RequestMapping("/listar_itens")
    public ModelAndView listaItem(){
        Iterable<Item> itens = repositorioItem.findAll();
        ModelAndView retorno = new ModelAndView("listarItens.html");
        retorno.addObject("itens", itens);
        return retorno;
    }

    @RequestMapping("/atualizar_item/{itemId}")
    public ModelAndView atualizarItem(@PathVariable ("itemId") Long itemId){
        Optional<Item> opcao = repositorioItem.findById(itemId);
        ModelAndView retorno = new ModelAndView("cadastroItens.html");
        if(opcao.isPresent()){
            Item item = opcao.get();
            retorno.addObject("item",item);
        }
        return retorno;
    }

    @RequestMapping("/deletar_item/{itemId}")
    public ModelAndView deletarItem(@PathVariable ("itemId") Long itemId, RedirectAttributes redirect){
        Optional<Item> opcao = repositorioItem.findById(itemId);
        ModelAndView retorno = new ModelAndView("redirect:/listar_itens" );
        if(opcao.isPresent()){
            Item item = opcao.get();
            repositorioItem.deleteById(item.getId());
        }
        return retorno;
    }
}