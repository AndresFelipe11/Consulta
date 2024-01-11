package co.com.bancolombia.model.inconsistenciasdto;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Inconsistencias {
    String codigo;
    String descripcion;
}

