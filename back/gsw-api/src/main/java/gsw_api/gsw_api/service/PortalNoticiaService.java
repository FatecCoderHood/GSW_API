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

    // Verifica duplicidade considerando nome, url e periodicidade
    private boolean isPortalDuplicado(String nome, String url, String periodicidade) {
        Optional<PortalNoticia> existingPortal = portalNoticiaRepository.findByNomeAndUrlAndPeriodicidade(nome, url, periodicidade);
        return existingPortal.isPresent();
    }

    // Método para criar um novo portal de notícia
    @Transactional
    public PortalNoticia create(DadosPortalNoticia dadosPortalNoticia) {
        if (isPortalDuplicado(dadosPortalNoticia.nome(), dadosPortalNoticia.url(), dadosPortalNoticia.periodicidade())) {
            throw new IllegalArgumentException("Portal já cadastrado com este nome, URL ou periodicidade.");
        }

        PortalNoticia portalNoticia = new PortalNoticia(
                dadosPortalNoticia.nome(),
                dadosPortalNoticia.url(),
                dadosPortalNoticia.parametrizacao(),
                dadosPortalNoticia.periodicidade()
        );
        return portalNoticiaRepository.save(portalNoticia);
    }

    // Método para buscar um portal de notícia por ID
    public Optional<PortalNoticia> findById(Long id) {
        return portalNoticiaRepository.findById(id);
    }

    // Método para buscar todos os portais de notícias
    public List<PortalNoticia> findAll() {
        return portalNoticiaRepository.findAll();
    }

    // Método para buscar todos os portais de notícias filtrando pela periodicidade
    public List<PortalNoticia> findAllByPeriodicidade(String periodicidade) {

        PortalNoticia portalExample = new PortalNoticia();
        portalExample.setPeriodicidade(periodicidade);
        Example<PortalNoticia> example = Example.of(portalExample);

        return portalNoticiaRepository.findAll(example, Sort.unsorted());
    }

    // Método para atualizar um portal de notícia
    @Transactional
    public PortalNoticia update(Long id, DadosPortalNoticia dadosPortalNoticia) {
        Optional<PortalNoticia> optionalPortalNoticia = portalNoticiaRepository.findById(id);
        if (optionalPortalNoticia.isPresent()) {
            PortalNoticia portalNoticia = optionalPortalNoticia.get();

            if (isPortalDuplicado(dadosPortalNoticia.nome(), dadosPortalNoticia.url(), dadosPortalNoticia.periodicidade())) {
                throw new IllegalArgumentException("Portal já cadastrado com este nome, URL ou periodicidade.");
            }

            portalNoticia.setNome(dadosPortalNoticia.nome());
            portalNoticia.setUrl(dadosPortalNoticia.url());
            portalNoticia.setParametrizacao(dadosPortalNoticia.parametrizacao());
            portalNoticia.setPeriodicidade(dadosPortalNoticia.periodicidade()); // Ajustado para o nome correto do método

            return portalNoticiaRepository.save(portalNoticia);
        }
        return null;
    }

    // Método para deletar um portal de notícia
    @Transactional
    public void delete(Long id) {
        portalNoticiaRepository.deleteById(id);
    }
}