package co.com.bancolombia.soapconsumer.tdc;

import jakarta.xml.bind.annotation.XmlElement;

public class Inconsistencias {
    private String codigo;
    private String descripcion;


    @XmlElement
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlElement
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}