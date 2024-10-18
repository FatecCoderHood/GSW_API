package gsw_api.gsw_api.service;

import gsw_api.gsw_api.dao.PortalNoticiaRepository;
import gsw_api.gsw_api.dto.DadosPortalNoticia;
import gsw_api.gsw_api.model.PortalNoticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PortalNoticiaService {

    @Autowired
    private PortalNoticiaRepository portalNoticiaRepository;

    @Transactional
    public PortalNoticia create(DadosPortalNoticia dadosPortalNoticia) {
        PortalNoticia portalNoticia = new PortalNoticia(
                dadosPortalNoticia.nome(),
                dadosPortalNoticia.url(),
                dadosPortalNoticia.parametrizacao()
        );
        return portalNoticiaRepository.save(portalNoticia);
    }

    public Optional<PortalNoticia> findById(Long id) {
        return portalNoticiaRepository.findById(id);
    }

    public List<PortalNoticia> findAll() {
        return portalNoticiaRepository.findAll();
    }

    @Transactional
    public PortalNoticia update(Long id, DadosPortalNoticia dadosPortalNoticia) {
        Optional<PortalNoticia> optionalPortalNoticia = portalNoticiaRepository.findById(id);
        if (optionalPortalNoticia.isPresent()) {
            PortalNoticia portalNoticia = optionalPortalNoticia.get();
            portalNoticia.setNome(dadosPortalNoticia.nome());
            portalNoticia.setUrl(dadosPortalNoticia.url());
            portalNoticia.setParametrizacao(dadosPortalNoticia.parametrizacao());
            return portalNoticiaRepository.save(portalNoticia);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        portalNoticiaRepository.deleteById(id);
    }
}
