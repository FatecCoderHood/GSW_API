package gsw_api.gsw_api.service;

import gsw_api.gsw_api.model.Noticia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NoticiaService {
    private List<Noticia> noticias = new ArrayList<>();
    

    public NoticiaService(){
        noticias.addAll(List.of(
            new Noticia("Agronegócio em alta", "O mercado de agronegócio está em crescimento...", LocalDate.now(), "João Silva",null),
            new Noticia("Nova tecnologia agrícola", "Novas inovações tecnológicas para o campo...", LocalDate.now(), "Maria Santos", null),
            new Noticia("Previsão de safra recorde", "A safra de soja deste ano será recorde...", LocalDate.now(), "Carlos Oliveira", null),
            new Noticia("Impactos climáticos", "Como o clima está afetando a produção...", LocalDate.now(), "Ana Pereira", null)
        ));
    }

	public Iterable<Noticia> getNoticias() {
		return noticias;
	}

	public Noticia addNoticia( Noticia noticia) {
		noticias.add(noticia);
		return noticia;
	}

    public Noticia getNoticiaById( String id) {
        return noticias.stream()
            .filter(noticia -> noticia.getIdNoticia().equals(id))
            .findFirst()
            .orElse(null); // Você pode retornar uma exceção personalizada se preferir
    }
}
