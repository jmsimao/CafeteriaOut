package br.com.josemarcelo.Cafeteria.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import br.com.josemarcelo.Cafeteria.Model.Cafe;

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
				new Cafe("Café Kona")
				));
	}
	
	@GetMapping
	Iterable<Cafe> getCafes() {
		return Cafes;
	}
	
	@GetMapping("/{id}")
	Optional<Cafe> getCafeById(@PathVariable String id) {
		for (Cafe c : this.Cafes) {
			if (c.getId().equals(id)) {
				return Optional.of(c);
			}
		} 
		return Optional.empty();
	}
	
	@PostMapping("/add/cafe")
	Cafe postCafe(@RequestBody Cafe cafe) {
		for (Cafe c: this.Cafes) {
			if (c.getNome().equals(cafe.getNome())) {
				return null;
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
		
		return (indiceCafe > -1) ?
				new ResponseEntity<>(cafe,
						HttpStatus.OK)
				: new ResponseEntity<>(postCafe(cafe),
						HttpStatus.CREATED)
				;
	}
	
	@DeleteMapping("/del/{id}") 
	void deleteCafe(@PathVariable String id) {
		this.Cafes.removeIf(c -> c.getId().equals(id));
	}
}
