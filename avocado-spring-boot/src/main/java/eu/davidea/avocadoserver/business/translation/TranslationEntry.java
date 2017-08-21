package eu.davidea.avocadoserver.business.translation;

import java.io.Serializable;

public class TranslationEntry implements Serializable {

    private static final long serialVersionUID = -8442999047364766717L;

    private Long id;
    private String languageCode;
    private String text;

    public TranslationEntry() {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}