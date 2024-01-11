package co.com.bancolombia.consumer;

import co.com.bancolombia.model.inconsistenciasdto.Inconsistencias;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ObjectResponse {

    @JsonProperty("CATS")
    private String CATS;
    @JsonProperty("GMF")
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