package co.com.bancolombia.model.extractdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExtractData {

    String traceTransacion;
    String documentNumber;
    String documentType;

}
