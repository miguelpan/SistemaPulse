package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoServices {
	
	private ProdutoDao dao  = DaoFactory.createProdutoDao();

	public List<Produto> findAll(){
		return dao.findAll();
	}
	
	
	public void saveOrUpdate(Produto obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Produto obj) {
		dao.deleteById(obj.getId());
	}
}
