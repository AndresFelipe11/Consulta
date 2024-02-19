package co.com.bancolombia.soapconsumer.tdc;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "requestHeader")
@XmlType(name = "requestHeader", propOrder = {
    "username",
    "password"
})

public class RequestHeader {

    @XmlElement(required = true)
    protected String username;
    protected String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = value;
    }
}
