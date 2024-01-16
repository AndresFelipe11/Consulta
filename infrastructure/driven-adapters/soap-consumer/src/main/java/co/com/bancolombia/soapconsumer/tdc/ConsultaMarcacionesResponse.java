package co.com.bancolombia.soapconsumer.tdc;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "consultaMarcacionesResponse", namespace = "urn:com:bancolombia:consultamarcaciones")
public class ConsultaMarcacionesResponse {
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
    private Inconsistencias inconsistencias;
    private boolean generoInconsistencias;
    private String hora;
    private String lugarExpedicion;
    private String nombre;
    private String nombre1;
    private String nombre2;
    private String numeroIdentificacion;
    private String respuestaConsulta;
    private String tipoIdentificacion;



    @XmlElement
    public String getCATS() {
        return CATS;
    }

    public void setCATS(String CATS) {
        this.CATS = CATS;
    }

    @XmlElement
    public String getGMF() {
        return GMF;
    }

    public void setGMF(String GMF) {
        this.GMF = GMF;
    }

    @XmlElement
    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    @XmlElement
    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    @XmlElement
    public String getCiudadDireccion() {
        return ciudadDireccion;
    }

    public void setCiudadDireccion(String ciudadDireccion) {
        this.ciudadDireccion = ciudadDireccion;
    }

    @XmlElement
    public String getCodigoCiiu() {
        return codigoCiiu;
    }

    public void setCodigoCiiu(String codigoCiiu) {
        this.codigoCiiu = codigoCiiu;
    }

    @XmlElement
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlElement
    public String getEntidadCATS() {
        return entidadCATS;
    }

    public void setEntidadCATS(String entidadCATS) {
        this.entidadCATS = entidadCATS;
    }

    @XmlElement
    public String getEntidadGMF() {
        return entidadGMF;
    }

public void setEntidadGMF(String entidadGMF) {
        this.entidadGMF = entidadGMF;
    }

    @XmlElement
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlElement
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @XmlElement
    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(String fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    @XmlElement
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @XmlElement
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @XmlElement
    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    @XmlElement
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement
    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    @XmlElement
    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    @XmlElement
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    @XmlElement
    public String getRespuestaConsulta() {
        return respuestaConsulta;
    }

    public void setRespuestaConsulta(String respuestaConsulta) {
        this.respuestaConsulta = respuestaConsulta;
    }

    @XmlElement
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public boolean isGeneroInconsistencias() {
        return generoInconsistencias;
    }

    public void setGeneroInconsistencias(boolean generoInconsistencias) {
        this.generoInconsistencias = generoInconsistencias;
    }

    @XmlElement
    public Inconsistencias getInconsistencias() {
        return inconsistencias;
    }

    public void setInconsistencias(Inconsistencias inconsistencias) {
        this.inconsistencias = inconsistencias;
    }
}
