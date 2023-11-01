package com.example.demo.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Coin;
import com.example.demo.repository.CoinRepository;

/*A annotation @RestController indica que a classe CoinController é um controller que receberá as
 requisições do usuário e cada método é responsével por uma pégina, controlando o que seré mostrado ao
 usuário*/
@RestController
/*A annotation @RequestMapping("/coin") indica o caminho para acessar o controller*/
@RequestMapping("/coin")
public class CoinController {

	/*O @Autowired garante a injeção de dependência automática*/
	@Autowired
	private CoinRepository rep;
	
	/*Método que permite inserir um dado no banco de dados*/
	@PostMapping() 
	public ResponseEntity post(@RequestBody Coin coin) {
		try {
			coin.setDateTime(new Timestamp(System.currentTimeMillis()));
			return new ResponseEntity<>(rep.insert(coin),HttpStatus.CREATED);
		}catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*Método que permite retornar a soma de quantias de criptomoedas do banco de dados*/
	@GetMapping() 
	public ResponseEntity getWallet() {
		try {
			return new ResponseEntity<>(rep.getAll(),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*Método que permite retornar a carteira de criptomoedas do banco de dados com base no nome*/
	@GetMapping("/{name}") 
	public ResponseEntity getWalletByName(@PathVariable String name) {
		try {
			return new ResponseEntity<>(rep.getByName(name),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*Método que permite deletar uma determinada moeda do banco de dados com base no id*/
	@DeleteMapping("/{id}") 
	public ResponseEntity deleteCoin(@PathVariable Integer id) {
		boolean response = false;
		try {
			response = rep.delete(id);
			return new ResponseEntity<>(response,HttpStatus.OK);
			
		}catch(Exception e){
			return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
		}
	}
	
	/*Método que permite atualizar um dado do banco de dados*/
	@PutMapping() 
	public ResponseEntity updateData(@RequestBody Coin coin) {
		try {
			coin.setDateTime(new Timestamp(System.currentTimeMillis()));
			return new ResponseEntity<>(rep.update(coin),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
}
