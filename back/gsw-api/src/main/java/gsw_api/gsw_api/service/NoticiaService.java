package gsw_api.gsw_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashSet;

import gsw_api.gsw_api.dao.TagRepository;
import gsw_api.gsw_api.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gsw_api.gsw_api.dao.NoticiaRepository;
import gsw_api.gsw_api.dto.DadosNoticia;
import gsw_api.gsw_api.dto.DadosTag;
import gsw_api.gsw_api.dto.FiltroNoticia;
import gsw_api.gsw_api.model.Noticia;
import jakarta.persistence.criteria.JoinType;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagService tagService;

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

            //processar novas tags
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
        return noticiaRepository.findAll((Specification<Noticia>) (root, query, criteriaBuilder) -> {
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
                spec = spec.and((root1, query1, criteriaBuilder1) ->{
                    var join = root1.join("tags", JoinType.INNER);
                    return join.get("nome").in(filtro.getTags());
                });
            }
            return spec.toPredicate(root, query, criteriaBuilder);
        });
    }


    public List<Noticia> buscarNoticiasPorTermo(String termo) {
        List<String> sinonimos = sinonimoService.buscarSinonimos(termo);
        sinonimos.add(termo); // Adiciona o termo original

        return noticiaRepository.findByTags_NomeIn(sinonimos);
    }

    public List<DadosTag> associateTags(Long noticiaId, List<String> tagNames)
    {
        //TODO: Stop the flow if noticia was not found
        Noticia noticia = noticiaRepository.findById(noticiaId)
            .orElseThrow(() -> new RuntimeException("Notícia não encontrada"));
        
        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames)
        {
            Tag tag = tagRepository.findByNome(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                
            tags.add(tag);
        }

        noticia.getTags().addAll(tags);

        Noticia savedNoticia = noticiaRepository.save(noticia);

        List<DadosTag> savedTagNames = null;
        
        if (savedNoticia != null && savedNoticia.getId() != null)
            savedTagNames = noticia.getTags().stream().map(tagService::convertToDTO).collect(Collectors.toList());

        return savedTagNames;
    }

    public boolean unassociateTags(Long noticiaId, Long tagId)
    {
        //TODO: Stop the flow if noticia was not found
        Noticia noticia = noticiaRepository.findById(noticiaId)
        .orElseThrow(() -> new RuntimeException("Notícia não encontrada"));
        
        //TODO: Stop the flow if tag was not found
        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(() -> new RuntimeException("Tag não encontrada"));
        
        noticia.getTags().remove(tag);

        Noticia savedNoticia = noticiaRepository.save(noticia);

        return (savedNoticia != null && savedNoticia.getId() != null);
    }
}
