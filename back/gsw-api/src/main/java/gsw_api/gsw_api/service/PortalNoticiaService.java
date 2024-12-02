package gsw_api.gsw_api.service;

import gsw_api.gsw_api.dao.PortalNoticiaRepository;
import gsw_api.gsw_api.dto.DadosPortalNoticia;
import gsw_api.gsw_api.model.PortalNoticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PortalNoticiaService {

    @Autowired
    private PortalNoticiaRepository portalNoticiaRepository;

    private boolean isPortalDuplicado(String nome, String url, String periodicidade, String tipo) {
        Optional<PortalNoticia> existingPortal = portalNoticiaRepository.findByNomeAndUrlAndPeriodicidade(nome, url, periodicidade);
        return existingPortal.isPresent();
    }

    @Transactional
    public PortalNoticia create(DadosPortalNoticia dadosPortalNoticia) {
        if (isPortalDuplicado(dadosPortalNoticia.nome(), dadosPortalNoticia.url(), dadosPortalNoticia.periodicidade(), dadosPortalNoticia.tipo())) {
            throw new IllegalArgumentException("Portal já cadastrado com este nome, URL, periodicidade ou tipo.");
        }

        PortalNoticia portalNoticia = new PortalNoticia(
                dadosPortalNoticia.nome(),
                dadosPortalNoticia.url(),
                dadosPortalNoticia.parametrizacao(),
                dadosPortalNoticia.periodicidade(),
                dadosPortalNoticia.tipo() 
        );
        return portalNoticiaRepository.save(portalNoticia);
    }

    public Optional<PortalNoticia> findById(Long id) {
        return portalNoticiaRepository.findById(id);
    }

    public List<PortalNoticia> findAll() {
        return portalNoticiaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<PortalNoticia> findAllByPeriodicidade(String periodicidade) {
        PortalNoticia portalExample = new PortalNoticia();
        portalExample.setPeriodicidade(periodicidade);
        Example<PortalNoticia> example = Example.of(portalExample);
        return portalNoticiaRepository.findAll(example, Sort.unsorted());
    }

    @Transactional
    public PortalNoticia update(Long id, DadosPortalNoticia dadosPortalNoticia) {
        Optional<PortalNoticia> optionalPortalNoticia = portalNoticiaRepository.findById(id);
        if (optionalPortalNoticia.isPresent()) {
            PortalNoticia portalNoticia = optionalPortalNoticia.get();

            if (isPortalDuplicado(dadosPortalNoticia.nome(), dadosPortalNoticia.url(), dadosPortalNoticia.periodicidade(), dadosPortalNoticia.tipo())) {
                throw new IllegalArgumentException("Portal já cadastrado com este nome, URL, periodicidade ou tipo.");
            }

            portalNoticia.setNome(dadosPortalNoticia.nome());
            portalNoticia.setUrl(dadosPortalNoticia.url());
            portalNoticia.setParametrizacao(dadosPortalNoticia.parametrizacao());
            portalNoticia.setPeriodicidade(dadosPortalNoticia.periodicidade());
            portalNoticia.setTipo(dadosPortalNoticia.tipo()); 

            return portalNoticiaRepository.save(portalNoticia);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        portalNoticiaRepository.deleteById(id);
    }
}
