package gsw_api.gsw_api;

import model.PortalNoticia;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class GswApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GswApiApplication.class, args);
	}

}

@RestController
@CrossOrigin
@RequestMapping("/portais")
class RestGSW_APIController {
	private List<PortalNoticia> portais = new ArrayList<>();

	public RestGSW_APIController() {
		portais.addAll(List.of(
				new PortalNoticia("Globo News"),
				new PortalNoticia("UOL"),
				new PortalNoticia("Aggro"),
				new PortalNoticia("CNN")
		));
	}

	@GetMapping
	Iterable<PortalNoticia> getPortais() {
		return portais;
	}
}

// class Portal {
// 	private String id;
// 	private String name;
// 	private String url;
// 	private String parametrizacao; //json

// 	public Portal(String id, String name) {
// 		this.id = id;
// 		this.name = name;
// 	}

// 	public Portal( String name) {
// 		this(UUID.randomUUID().toString(), name);
// 	}

// 	public String getId() {
// 		return id;
// 	}

// 	public void setId(String id) {
// 		this.id = id;
// 	}

// 	public String getName() {
// 		return name;
// 	}

// 	public void setName(String name) {
// 		this.name = name;
// 	}

// 	public String getUrl() {
// 		return url;
// 	}

// 	public void setUrl(String url) {
// 		this.url = url;
// 	}

// 	public String getParametrizacao() {
// 		return parametrizacao;
// 	}

// 	public void setParametrizacao(String parametrizacao) {
// 		this.parametrizacao = parametrizacao;
// 	}
// }
