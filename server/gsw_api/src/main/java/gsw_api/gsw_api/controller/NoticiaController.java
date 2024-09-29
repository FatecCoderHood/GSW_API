package gsw_api.gsw_api.controller;

import gsw_api.gsw_api.dto.DadosNoticia;
import gsw_api.gsw_api.dto.FiltroNoticia;
import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noticias")
@CrossOrigin(origins = "http://localhost:3000")
public class NoticiaController {

	@Autowired
	private NoticiaService noticiaService;

	@PostMapping
	public ResponseEntity<Noticia> create(@RequestBody DadosNoticia dadosNoticia) {
		Noticia noticia = noticiaService.create(dadosNoticia);
		return ResponseEntity.ok(noticia);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Noticia> findById(@PathVariable Long id) {
		return noticiaService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Noticia>> findAll() {
		List<Noticia> noticias = noticiaService.findAll();
		return ResponseEntity.ok(noticias);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Noticia> update(@PathVariable Long id, @RequestBody DadosNoticia dadosNoticia) {
		Noticia noticia = noticiaService.update(id, dadosNoticia);
		return noticia != null ? ResponseEntity.ok(noticia) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		noticiaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@PostMapping("/filtrar")
	public ResponseEntity<List<Noticia>> filtrarNoticias(@RequestBody FiltroNoticia filtro) {
		List<Noticia> noticiasFiltradas = noticiaService.filtrarNoticias(filtro);
		return ResponseEntity.ok(noticiasFiltradas);
	}
}
