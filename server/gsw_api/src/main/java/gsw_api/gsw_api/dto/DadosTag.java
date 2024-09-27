package gsw_api.gsw_api.dto;

import java.time.LocalDate;

public record DadosTag(Long id, String nome, String descricao, Boolean ativa, LocalDate dataCriacao) {}
