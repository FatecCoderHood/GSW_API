package gsw_api.gsw_api.controller;

import gsw_api.gsw_api.dto.DadosPortalNoticia;
import gsw_api.gsw_api.model.PortalNoticia;
import gsw_api.gsw_api.service.PortalNoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portais")
public class PortalNoticiaController {

	@Autowired
	private PortalNoticiaService portalNoticiaService;

	@PostMapping
	public ResponseEntity<PortalNoticia> create(@RequestBody DadosPortalNoticia dados) {
		PortalNoticia portal = portalNoticiaService.create(dados);
		return ResponseEntity.ok(portal);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PortalNoticia> findById(@PathVariable Long id) {
		return portalNoticiaService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<PortalNoticia>> findAll() {
		List<PortalNoticia> portais = portalNoticiaService.findAll();
		return ResponseEntity.ok(portais);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PortalNoticia> update(@PathVariable Long id, @RequestBody DadosPortalNoticia dados) {
		PortalNoticia portal = portalNoticiaService.update(id, dados);
		return portal != null ? ResponseEntity.ok(portal) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		portalNoticiaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
