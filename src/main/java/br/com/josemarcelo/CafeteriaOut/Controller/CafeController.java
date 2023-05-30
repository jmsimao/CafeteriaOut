package br.com.josemarcelo.CafeteriaOut.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.josemarcelo.CafeteriaOut.ErrorResponse.ErrorResponse;
import br.com.josemarcelo.CafeteriaOut.ErrorResponse.NotFoundException;
import br.com.josemarcelo.CafeteriaOut.Model.Cafe;

import br.com.josemarcelo.CafeteriaOut.ErrorResponse.FoundException;

@RestController
@RequestMapping("/cafes")
public class CafeController {

	private List<Cafe> Cafes = new ArrayList<Cafe>();
		
	public CafeController() {
		this.Cafes.addAll(List.of(new Cafe("Café Arábica"),
				new Cafe("Café Bourbon"),
				new Cafe("Café Acaiá"),
				new Cafe("Café Catuaí"),
				new Cafe("Café Robusta"),
				new Cafe("Café Geisha"),
				new Cafe("Café Kona"),
				new Cafe("Café Pelé"),
				new Cafe("Cafe da Serra")
				));
	}
	
	@GetMapping
	Iterable<Cafe> getCafes() {
		if (this.Cafes.isEmpty()) {
			throw new NotFoundException("Não existem dados na coleção!", null);
		}
		return Cafes;
	}
	
	@GetMapping("/{id}")
	Optional<Cafe> getCafeById(@PathVariable String id) {
		for (Cafe c : this.Cafes) {
			if (c.getId().equals(id)) {
				return Optional.of(c);
			}
		} 
		throw new NotFoundException("Café não existe!", "Id informado: " + id);
	}
	
	@PostMapping("/add/cafe")
	Cafe postCafe(@RequestBody Cafe cafe) {
		for (Cafe c: this.Cafes) {
			if (c.getNome().equals(cafe.getNome())) {
				throw new FoundException("Café já existe");
			}
		}
		this.Cafes.add(cafe);
		return cafe;
	}
	
	@PutMapping("/change/{id}")
	ResponseEntity<Cafe> putCafe(@PathVariable String id, @RequestBody Cafe cafe) {
		int indiceCafe = -1;
		for (Cafe c: this.Cafes) {
			if (c.getId().equals(id)) {
				indiceCafe = Cafes.indexOf(c);
				Cafes.set(indiceCafe, cafe);
			}
		}
	
		if (indiceCafe > -1) {
			return new ResponseEntity<>(cafe,
										HttpStatus.OK
										);
		}
		throw new NotFoundException("Café não existe para ser alterado!",
									"Id: " + id);
	}
	
	@DeleteMapping("/del/{id}")
	void deleteCafe(@PathVariable String id) {
		if (!this.Cafes.removeIf(c -> c.getId().equals(id))) {
			throw new NotFoundException("Café não existe!", "Id: " + id);
		}
	}
	
	@ExceptionHandler(NotFoundException.class)
	private ResponseEntity<ErrorResponse> handlerNotFoundException(NotFoundException nfe) {
		ErrorResponse errorResponse = new ErrorResponse(
									HttpStatus.NOT_FOUND.value(),
									HttpStatus.NOT_FOUND.toString(),
									nfe.getMessage(),
									nfe.getErroInfo(),
									this.getClass().toString()
									);
		return new ResponseEntity<>(errorResponse,
									HttpStatus.NOT_FOUND
									);
	}
	
	@ExceptionHandler(FoundException.class)
	private ResponseEntity<ErrorResponse> handlerFoundException(FoundException fe) {
		ErrorResponse errorResponse = new ErrorResponse(
									HttpStatus.FOUND.value(),
									HttpStatus.FOUND.toString(),
									fe.getMessage(),
									null,
									this.getClass().toString()
				);
		return new ResponseEntity<>(errorResponse,
									HttpStatus.FOUND
									);
	}
	
}
