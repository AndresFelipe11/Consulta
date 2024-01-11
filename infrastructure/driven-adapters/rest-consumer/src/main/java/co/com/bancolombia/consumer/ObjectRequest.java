package co.com.bancolombia.consumer;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ObjectRequest {

    private String codigoProducto;
    private String idCifin;
    private String motivoConsulta;
    private String numeroIdentificacion;
    private String password;
    private String tipoIdentificacion;

}
