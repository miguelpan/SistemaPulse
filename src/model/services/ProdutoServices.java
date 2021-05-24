package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoServices {
	
	private ProdutoDao dao  = DaoFactory.createProdutoDao();

	public List<Produto> findAll(){
		return dao.findAll();
	}
}
