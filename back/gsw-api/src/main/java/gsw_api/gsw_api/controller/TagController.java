package gsw_api.gsw_api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import gsw_api.gsw_api.dao.TagRepository;
import gsw_api.gsw_api.dto.DadosTag;
import gsw_api.gsw_api.model.Tag;
import gsw_api.gsw_api.service.TagService;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "http://localhost:3000")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public List<DadosTag> getAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosTag> getTagById(@PathVariable Long id) {
        DadosTag dadosTag = tagService.findById(id);
        return ResponseEntity.ok(dadosTag);
    }

    @PostMapping
    public ResponseEntity<DadosTag> createTag(@RequestBody DadosTag dadosTag) {
        try {
            if (dadosTag.nome() == null || dadosTag.nome().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            if (tagRepository.existsByNome(dadosTag.nome())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); 
            }

            Tag tag = new Tag(
                dadosTag.nome(),
                dadosTag.descricao(),
                dadosTag.ativa(),
                dadosTag.dataCriacao(),
                dadosTag.sinonimo1(),
                dadosTag.sinonimo2()
            );

            Tag savedTag = tagRepository.save(tag);

            DadosTag createdTag = new DadosTag(
                savedTag.getId(),
                savedTag.getNome(),
                savedTag.getDescricao(),
                savedTag.getAtiva(),
                savedTag.getDataCriacao(),
                savedTag.getCor(),
                savedTag.getSinonimo1(),
                savedTag.getSinonimo2()
            );

            // Retornar a tag criada com status CREATED (201)
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody DadosTag dadosTag) {
        Optional<Tag> optionalTag = tagRepository.findById(id);

        if (!optionalTag.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Tag existingTag = optionalTag.get();

        // Atualiza os campos se os valores não forem nulos
        if (dadosTag.nome() != null)
            existingTag.setNome(dadosTag.nome());

        if (dadosTag.descricao() != null)
            existingTag.setDescricao(dadosTag.descricao());

        if (dadosTag.ativa() != null)
            existingTag.setAtiva(dadosTag.ativa());

        if (dadosTag.cor() != null)
            existingTag.setCor(dadosTag.cor());

        if (dadosTag.sinonimo1() != null)
            existingTag.setSinonimo1(dadosTag.sinonimo1());

        if (dadosTag.sinonimo2() != null)
            existingTag.setSinonimo2(dadosTag.sinonimo2());

        Tag updatedTag = tagRepository.save(existingTag);

        return ResponseEntity.ok(updatedTag);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<DadosTag>> filterTags(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean ativa,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) LocalDate dataCriacao,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> tagPage = tagService.filterTags(nome, ativa, descricao, dataCriacao, pageable);

        Page<DadosTag> dadosTagPage = tagPage.map(tag -> new DadosTag(tag.getId(), tag.getNome(), tag.getDescricao(), tag.getAtiva(), tag.getDataCriacao(), tag.getCor(), tag.getSinonimo1(), tag.getSinonimo2()));

        return ResponseEntity.ok(dadosTagPage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DadosTag>> searchTags(@RequestParam String termo) {
        List<DadosTag> tagsRelacionadas = tagService.findTagsByTerm(termo);
        return ResponseEntity.ok(tagsRelacionadas);
    }
}
