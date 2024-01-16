package co.com.bancolombia.soapconsumer.tdc;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "consultaMarcacionesRequest")
public class ConsultaMarcacionesRequest {
    private String codigoProducto;
    private String idCifin;
    private String motivoConsulta;
    private String numeroIdentificacion;
    private String password;
    private String tipoIdentificacion;


    @XmlElement
    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    @XmlElement
    public String getIdCifin() {
        return idCifin;
    }

    public void setIdCifin(String idCifin) {
        this.idCifin = idCifin;
    }

    @XmlElement
    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    @XmlElement
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
}

