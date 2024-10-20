package gsw_api.gsw_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
            }

            DadosTag createdTag = tagService.save(dadosTag);
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
    public ResponseEntity<Tag> updateTag(@PathVariable Long id,
        @RequestParam(required = false) String nome, 
        @RequestParam(required = false) String descricao,
        @RequestParam(required = false) Boolean ativa)
        {
            Optional<Tag> optionalTag = tagRepository.findById(id);

            if (!optionalTag.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Tag existingTag = optionalTag.get();

            if (nome != null) 
                existingTag.setNome(nome);

            if (descricao != null)
                existingTag.setDescricao(descricao);

            if (ativa)
                existingTag.setAtiva(ativa);

            Tag updatedTag = tagRepository.save(existingTag);
            return ResponseEntity.ok(updatedTag);
        }
}


