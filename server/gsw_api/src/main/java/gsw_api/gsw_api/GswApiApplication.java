package gsw_api.gsw_api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Noticia;
import model.PortalNoticia;

@SpringBootApplication
public class GswApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GswApiApplication.class, args);
	}

}

@RestController
@CrossOrigin
@RequestMapping("/gsw_api")
class RestGSW_APIController {
	private List<PortalNoticia> portais = new ArrayList<>();
	private List<Noticia> noticias = new ArrayList<>();

	public RestGSW_APIController() {
		portais.addAll(List.of(
				new PortalNoticia("Globo News"),
				new PortalNoticia("UOL"),
				new PortalNoticia("Aggro"),
				new PortalNoticia("CNN")
		));
		noticias.addAll(List.of(
            new Noticia("Agronegócio em alta", "O mercado de agronegócio está em crescimento...", LocalDate.now(), "João Silva", getPortalIdByName("Globo News")),
            new Noticia("Nova tecnologia agrícola", "Novas inovações tecnológicas para o campo...", LocalDate.now(), "Maria Santos", getPortalIdByName("UOL")),
            new Noticia("Previsão de safra recorde", "A safra de soja deste ano será recorde...", LocalDate.now(), "Carlos Oliveira", getPortalIdByName("Aggro")),
            new Noticia("Impactos climáticos", "Como o clima está afetando a produção...", LocalDate.now(), "Ana Pereira", getPortalIdByName("CNN"))
        ));
    }

    private String getPortalIdByName(String portalName) {
        Optional<PortalNoticia> portal = portais.stream()
            .filter(p -> p.getName().equals(portalName))
            .findFirst();
        return portal.map(PortalNoticia::getId).orElse(null);
    };

	@GetMapping("/portais")
	Iterable<PortalNoticia> getPortais() {
		return portais;
	}
	@GetMapping("/noticias")
	Iterable<Noticia> getNoticias() {
		return noticias;
	}
	@PostMapping("/noticias")
	public Noticia addNoticia(@RequestBody Noticia noticia) {
		noticias.add(noticia);
		return noticia;
	}
	@GetMapping("/noticias/{id}")
    public Noticia getNoticiaById(@PathVariable String id) {
        return noticias.stream()
            .filter(noticia -> noticia.getIdNoticia().equals(id))
            .findFirst()
            .orElse(null); // Você pode retornar uma exceção personalizada se preferir
    }
}
