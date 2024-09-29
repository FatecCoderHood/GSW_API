package gsw_api.gsw_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FiltroNoticia {
    private String titulo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
