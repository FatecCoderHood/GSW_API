
package gsw_api.gsw_api.service;

import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.model.Api;
import gsw_api.gsw_api.model.PortalNoticia;
import gsw_api.gsw_api.dao.NoticiaRepository;
import gsw_api.gsw_api.dao.ApiRepository;
import gsw_api.gsw_api.dao.PortalNoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticiaFonteService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private PortalNoticiaRepository portalNoticiaRepository;

    public Noticia salvarNoticiaComFonte(Noticia noticia, Long apiId, Long portalNoticiaId) {
        if (apiId != null) {
            Api api = apiRepository.findById(apiId).orElseThrow(() -> new RuntimeException("API não encontrada"));
            noticia.setFonte("API");
            noticia.setApi(api); 
        }

        else if (portalNoticiaId != null) {
            PortalNoticia portal = portalNoticiaRepository.findById(portalNoticiaId).orElseThrow(() -> new RuntimeException("Portal não encontrado"));
            noticia.setFonte("Portal");
            noticia.setPortalNoticia(portal); 
        }

        if (noticia.getFonte() == null) {
            System.out.println("Erro: A fonte não foi definida para a notícia.");
        }

        return noticiaRepository.save(noticia);
    }
}
