package eu.davidea.avocadoserver.business.translation;

import eu.davidea.avocadoserver.business.audit.AuditableEntity;

import java.io.Serializable;
import java.util.Date;

public class TranslationEntry implements AuditableEntity, Serializable {

    private static final long serialVersionUID = -8442999047364766717L;

    private Long id;
    private String languageCode;
    private Date creDate;
    private Date modDate;
    private String name;
    private String text;

    public TranslationEntry() {
        creDate = modDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public Date getCreDate() {
        return creDate;
    }

    @Override
    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    @Override
    public Date getModDate() {
        return modDate;
    }

    @Override
    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}