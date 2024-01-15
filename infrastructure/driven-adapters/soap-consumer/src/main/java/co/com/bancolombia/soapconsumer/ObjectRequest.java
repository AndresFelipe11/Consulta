package co.com.bancolombia.soapconsumer;

import lombok.*;

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
