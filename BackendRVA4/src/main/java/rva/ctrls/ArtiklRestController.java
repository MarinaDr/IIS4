package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Artikl;
import rva.reps.ArtiklRepository;
@RestController
@Api(tags = {"Artikl CRUD operacije"})
public class ArtiklRestController {
	@Autowired
	private ArtiklRepository artiklRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("artikl")
	@ApiOperation(value = "Vraća kolekciju svih artikala iz baze podataka")
	public Collection<Artikl> getArtikli(){
		return artiklRepository.findAll();
	}
	
	@GetMapping("artiklId/{id}")
	@ApiOperation(value = "Vraća artikl iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public Artikl getArtikl(@PathVariable("id") Integer id){
		return artiklRepository.getOne(id);
	}
	
	@GetMapping("artiklNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju svih artikala iz baze podataka koji u nazivu sadrže string prosleđen kao path varijabla")
	public Collection<Artikl> getArtiklByName(@PathVariable("naziv") String naziv){
		return artiklRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@DeleteMapping("artiklId/{id}")
	@ApiOperation(value = "Briše artikl iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public ResponseEntity<HttpStatus> deleteArtikl(@PathVariable("id") Integer id){
		if(artiklRepository.existsById(id)){
			artiklRepository.deleteById(id);
			if(id == -100)
				jdbcTemplate.execute("INSERT INTO \"artikl\"(\"id\", \"naziv\", \"proizvodjac\")VALUES(-100, 'Naziv TEST', 'Proizvodjac TEST')");
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	// insert
	@PostMapping("artikl")
	@ApiOperation(value = "Upisuje artikl u bazu podataka")
	public ResponseEntity<HttpStatus> insertArtikl(@RequestBody Artikl artikl){
		if(artiklRepository.existsById(artikl.getId()))
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
		artiklRepository.save(artikl);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	// update
	@PutMapping("artikl")
	@ApiOperation(value = "Modifikuje postojeći artikl u bazi podataka")
	public ResponseEntity<HttpStatus> updateArtikl(@RequestBody Artikl artikl){
		if(artiklRepository.existsById(artikl.getId())){
			artiklRepository.save(artikl);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
