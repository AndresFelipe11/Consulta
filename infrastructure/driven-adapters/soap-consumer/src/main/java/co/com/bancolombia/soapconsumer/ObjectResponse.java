package co.com.bancolombia.soapconsumer;

import co.com.bancolombia.model.inconsistenciasdto.Inconsistencias;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ObjectResponse {

    private String CATS;
    private String GMF;
    private String apellido1;
    private String apellido2;
    private String ciudadDireccion;
    private String codigoCiiu;
    private String direccion;
    private String entidadCATS;
    private String entidadGMF;
    private String estado;
    private String fecha;
    private String fechaExpedicion;
    private String genero;
    private Boolean generoInconsistencias;
    private String hora;
    private Inconsistencias inconsistencias;
    private String lugarExpedicion;
    private String nombre;
    private String nombre1;
    private String nombre2;
    private String numeroIdentificacion;
    private String respuestaConsulta;
    private String tipoIdentificacion;

}