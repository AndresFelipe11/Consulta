package co.com.bancolombia.model.responsedata;
import co.com.bancolombia.model.inconsistenciasdto.Inconsistencias;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ResponseData {

    String CATS;
    String GMF;
    String apellido1;
    String apellido2;
    String ciudadDireccion;
    String codigoCiiu;
    String direccion;
    String entidadCATS;
    String entidadGMF;
    String estado;
    String fecha;
    String fechaExpedicion;
    String genero;
    Boolean generoInconsistencias;
    String hora;
    Inconsistencias inconsistencias;
    String lugarExpedicion;
    String nombre;
    String nombre1;
    String nombre2;
    String numeroIdentificacion;
    String respuestaConsulta;
    String tipoIdentificacion;
}
