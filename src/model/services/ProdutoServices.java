package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Produto;

public class ProdutoServices {

	public List<Produto> findAll(){
		List<Produto> list = new ArrayList<>();
		list.add(new Produto(1, "farinha"));
		list.add(new Produto(2, "tampa"));
		list.add(new Produto(3, "pote"));
		return list;
	}
}
