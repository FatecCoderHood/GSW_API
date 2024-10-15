package gsw_api.gsw_api.dto;

import java.time.LocalDate;

public record DadosNoticia(Long id, String titulo, String conteudo, LocalDate dataPublicacao, String autor) {
}
