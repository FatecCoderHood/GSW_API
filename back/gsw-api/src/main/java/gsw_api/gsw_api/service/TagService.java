package gsw_api.gsw_api.service;

import gsw_api.gsw_api.dao.TagRepository;
import gsw_api.gsw_api.dto.DadosTag;
import gsw_api.gsw_api.model.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SinonimoService sinonimoService;

    

    public List<DadosTag> findAll() {
        return tagRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DadosTag findById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag não encontrada"));
        return convertToDTO(tag);
    }

    public DadosTag save(DadosTag dadosTag) {
        Tag tag = new Tag(dadosTag.nome(), dadosTag.descricao(), dadosTag.ativa(), LocalDate.now());
        Tag savedTag = tagRepository.save(tag);
        return convertToDTO(savedTag);
    }

    public DadosTag convertToDTO(Tag tag) {
        return new DadosTag(tag.getId(), tag.getNome(), tag.getDescricao(), tag.getAtiva(), tag.getDataCriacao(), tag.getCor());
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    public Page<Tag> filterTags(String nome, Boolean ativa, String descricao, LocalDate dataCriacao, Pageable pageable) {
        return tagRepository.findAll(createSpecification(nome, ativa, descricao, dataCriacao), pageable);
    }

    private Specification<Tag> createSpecification(String nome, Boolean ativa, String descricao, LocalDate dataCriacao) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (nome != null && !nome.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
            }

            if (ativa != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("ativa"), ativa));
            }

            if (descricao != null && !descricao.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("descricao"), "%" + descricao + "%"));
            }

            if (dataCriacao != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dataCriacao"), dataCriacao));
            }

            return predicate;
        };
    }

        // Novo método para buscar tags usando sinônimos
        public List<DadosTag> findTagsByTerm(String termo) {
            List<String> sinonimos = sinonimoService.buscarSinonimos(termo);
            sinonimos.add(termo); // Inclui o termo original
    
            return tagRepository.findByNomeIn(sinonimos).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
}
