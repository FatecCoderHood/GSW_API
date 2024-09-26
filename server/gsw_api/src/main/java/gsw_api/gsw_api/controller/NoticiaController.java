package gsw_api.gsw_api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.service.NoticiaService;

@RestController
@CrossOrigin
@RequestMapping("/noticias")
public class NoticiaController {
    private NoticiaService noticiaService = new NoticiaService();

    @GetMapping()
	Iterable<Noticia> getNoticias() {
		return noticiaService.getNoticias();
	}

	@PostMapping()
	public Noticia addNoticia(@RequestBody Noticia noticia) {
		return noticiaService.addNoticia(noticia);
	}

	@GetMapping("/{id}")
    public Noticia getNoticiaById(@PathVariable String id) {
        return noticiaService.getNoticiaById(id);
    }

}
