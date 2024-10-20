package gsw_api.gsw_api.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltroNoticia {
    private String titulo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<String> tags;
}
