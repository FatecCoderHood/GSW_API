package gsw_api.gsw_api.service;


import gsw_api.gsw_api.dao.TagRepository;
import gsw_api.gsw_api.dto.DadosTag;
import gsw_api.gsw_api.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

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

    private DadosTag convertToDTO(Tag tag) {
        return new DadosTag(tag.getId(), tag.getNome(), tag.getDescricao(), tag.getAtiva(), tag.getDataCriacao());
    }
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
