package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.CoinDTO;
import com.example.demo.entity.Coin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@EnableAutoConfiguration
public class CoinRepository {

	@Autowired
	private EntityManager entityManager;
	
	/*Método para inserir um dado no banco de dados*/
	/*Em razão do EntityManager funcionar no esquema de transação, usa-se o @Transactional em
	 todo método que houver mudança dos dados no banco, além de isso permitir diferentes operações
	 ao mesmo tempo*/
	@Transactional
	public Coin insert(Coin coin) {
		entityManager.persist(coin);
		return coin;
	}
	
	/*Método para retornar a soma das quantias do banco de dados*/
	public List<CoinDTO> getAll() {
		
		String jpql = "select new com.example.demo.dto.CoinDTO(c.name, sum(c.quantity)) from Coin c group by c.name";
		TypedQuery<CoinDTO> query = entityManager.createQuery(jpql, CoinDTO.class);
		return query.getResultList();
		
	}
	
	/*Método para retornar um dado do banco de dados com base no nome*/
	public List<Coin> getByName(String name) {
		
		String jpql = "select c from Coin c where c.name like :name";
		TypedQuery<Coin> query = entityManager.createQuery(jpql, Coin.class);
		query.setParameter("name", "%" + name + "%");
		return query.getResultList();
		
	}
	
	/*Método para deletar uma moeda no banco de dados*/
	@Transactional
	public boolean delete(Integer id) {
		
		Coin coin = entityManager.find(Coin.class, id);
		
		if(coin == null) {
			throw new RuntimeException();
		} 
		
		entityManager.remove(coin);
		return true;
		
	}

	/*Método para realizar o update de uma moeda no banco de dados*/
	@Transactional
	public Coin update(Coin coin) {
		
		entityManager.merge(coin);
		
		return coin;
	}
	
}
