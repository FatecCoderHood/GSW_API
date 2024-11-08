package gsw_api.gsw_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import gsw_api.gsw_api.dao.TagRepository;
import gsw_api.gsw_api.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gsw_api.gsw_api.dao.NoticiaRepository;
import gsw_api.gsw_api.dto.DadosNoticia;
import gsw_api.gsw_api.dto.FiltroNoticia;
import gsw_api.gsw_api.model.Noticia;
import jakarta.persistence.criteria.JoinType;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    private TagRepository tagRepository;

    private SinonimoService sinonimoService;

    @Autowired
    public void setSinonimoService(SinonimoService sinonimoService){
        this.sinonimoService = sinonimoService;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public Noticia create(DadosNoticia dadosNoticia) {
        Optional<Noticia> existingNoticia = noticiaRepository.findByTituloAndConteudo(dadosNoticia.titulo(), dadosNoticia.conteudo());
        if (existingNoticia.isPresent()) {
            return existingNoticia.get();  
        }

        Noticia noticia = new Noticia();
        noticia.setTitulo(dadosNoticia.titulo());
        noticia.setConteudo(dadosNoticia.conteudo());
        noticia.setDataPublicacao(dadosNoticia.dataPublicacao());
        noticia.setAutor(dadosNoticia.autor());

        for (String termo : noticia.getConteudo().split(" ")) {
            List<String> sinonimos = sinonimoService.buscarSinonimos(termo);
            for (String sinonimo : sinonimos) {
                Tag tag = buscarOuCriarTag(sinonimo);
                noticia.getTags().add(tag);
            }
        }

        return noticiaRepository.save(noticia);
    }

    private Tag buscarOuCriarTag(String nome) {
        return tagRepository.findByNome(nome).orElseGet(() -> {
            Tag novaTag = new Tag(nome, null, true, LocalDate.now());
            return tagRepository.save(novaTag);
        });
    }

    @Transactional
    public List<Noticia> createAll(List<Noticia> noticias) {
        List<Noticia> resultado = new ArrayList<>();
        DadosNoticia dadoNoticia;
         for (Noticia n : noticias)
         {
            dadoNoticia = new DadosNoticia(n.getId(), n.getTitulo(), n.getConteudo(), null, n.getAutor());
            resultado.add(create(dadoNoticia));
         }
        return resultado;
    }

    public Optional<Noticia> findById(Long id) {
        return noticiaRepository.findById(id);
    }

    public List<Noticia> findAll() {
        return noticiaRepository.findAll();
    }

    @Transactional
    public Noticia update(Long id, DadosNoticia dadosNoticia) {
        Optional<Noticia> optionalNoticia = noticiaRepository.findById(id);
        if (optionalNoticia.isPresent()) {
            Noticia noticia = optionalNoticia.get();
            noticia.setTitulo(dadosNoticia.titulo());
            noticia.setConteudo(dadosNoticia.conteudo());
            noticia.setDataPublicacao(dadosNoticia.dataPublicacao());
            noticia.setAutor(dadosNoticia.autor());

            noticia.getTags().clear();

         
            for (String termo : noticia.getConteudo().split(" ")) {
                List<String> sinonimos = sinonimoService.buscarSinonimos(termo);
                for (String sinonimo : sinonimos) {
                    Tag tag = buscarOuCriarTag(sinonimo);
                    noticia.getTags().add(tag);
                }
            }

            return noticiaRepository.save(noticia);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        noticiaRepository.deleteById(id);
    }

    public List<Noticia> filtrarNoticias(FiltroNoticia filtro) {
        List<Noticia> noticiasFiltradas = noticiaRepository.findAll((Specification<Noticia>) (root, query, criteriaBuilder) -> {
            Specification<Noticia> spec = Specification.where(null);

            if (filtro.getTitulo() != null && !filtro.getTitulo().isEmpty()) {
                spec = spec.and((root1, query1, criteriaBuilder1) -> 
                        criteriaBuilder1.like(root1.get("titulo"), "%" + filtro.getTitulo() + "%"));
            }
            if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) -> 
                        criteriaBuilder1.between(root1.get("dataPublicacao"), filtro.getDataInicio(), filtro.getDataFim()));
            }
            if (filtro.getTags() != null && !filtro.getTags().isEmpty()) {
                spec = spec.and((root1, query1, criteriaBuilder1) -> {
                    var join = root1.join("tags", JoinType.INNER);
                    return join.get("nome").in(filtro.getTags());
                });
            }
            return spec.toPredicate(root, query, criteriaBuilder);
        });

        return noticiasFiltradas.stream()
            .distinct() 
            .toList();
    }

    public List<Noticia> buscarNoticiasPorTermo(String termo) {
        List<String> sinonimos = sinonimoService.buscarSinonimos(termo);
        sinonimos.add(termo); 

        return noticiaRepository.findByTags_NomeIn(sinonimos).stream()
            .distinct()  
            .toList();
    }
}

