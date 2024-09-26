package gsw_api.gsw_api.service;

import gsw_api.gsw_api.model.PortalNoticia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PortalNoticiaService {
    private List<PortalNoticia> portais = new ArrayList<>();

    public PortalNoticiaService() {
        portais.addAll(List.of(
				new PortalNoticia("Globo News", "globonews.com","globonewsjson"),
				new PortalNoticia("UOL","uol.com","uoljson"),
				new PortalNoticia("Aggro","aggro.com","aggrojson"),
				new PortalNoticia("CNN","cnn.com","cnnjson")
		));
    }

    public Iterable<PortalNoticia> getPortais() {
		return portais;
	}

    public PortalNoticia addPortalNoticia( PortalNoticia portalNoticia) {
		portais.add(portalNoticia);
		return portalNoticia;
	}

    public PortalNoticia getPortalNoticiaById( String id) {
        return portais.stream()
            .filter(portalNoticia -> portalNoticia.getId().equals(id))
            .findFirst()
            .orElse(null); // Você pode retornar uma exceção personalizada se preferir
    }

    public String getPortalIdByName(String portalName) {
        Optional<PortalNoticia> portal = portais.stream()
            .filter(p -> p.getName().equals(portalName))
            .findFirst();
        return portal.map(PortalNoticia::getId).orElse(null);
    };
}
