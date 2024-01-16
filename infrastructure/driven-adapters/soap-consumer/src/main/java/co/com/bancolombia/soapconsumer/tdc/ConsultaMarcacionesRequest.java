package co.com.bancolombia.soapconsumer.tdc;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "consultaMarcacionesRequest", namespace = "urn:com:bancolombia:consultamarcaciones")
public class ConsultaMarcacionesRequest {
    private String codigoProducto;
    private String idCifin;
    private String motivoConsulta;
    private String numeroIdentificacion;
    private String password;
    private String tipoIdentificacion;


    @XmlElement(namespace = "urn:com:bancolombia:consultamarcaciones")
    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    @XmlElement(namespace = "urn:com:bancolombia:consultamarcaciones")
    public String getIdCifin() {
        return idCifin;
    }

    public void setIdCifin(String idCifin) {
        this.idCifin = idCifin;
    }

    @XmlElement(namespace = "urn:com:bancolombia:consultamarcaciones")
    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    @XmlElement(namespace = "urn:com:bancolombia:consultamarcaciones")
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    @XmlElement(namespace = "urn:com:bancolombia:consultamarcaciones")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement(namespace = "urn:com:bancolombia:consultamarcaciones")
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
}

