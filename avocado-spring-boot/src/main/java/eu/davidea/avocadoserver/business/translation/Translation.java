package eu.davidea.avocadoserver.business.translation;

import java.io.Serializable;

import eu.davidea.avocadoserver.business.enums.EnumTranslationType;

public class Translation implements Serializable {

    private static final long serialVersionUID = -407173084396281397L;

    private Long id;
    private EnumTranslationType type;
    private String name; //English name of the translation

    public Translation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumTranslationType getType() {
        return type;
    }

    public void setType(EnumTranslationType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}