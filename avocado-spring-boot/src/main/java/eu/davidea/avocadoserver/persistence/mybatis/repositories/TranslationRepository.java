package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import eu.davidea.avocadoserver.business.translation.Translation;
import eu.davidea.avocadoserver.business.translation.TranslationEntry;
import eu.davidea.avocadoserver.persistence.mybatis.mappers.TranslationMapper;

/**
 * @author Davide
 * @since 17/08/2016
 */
@Repository("translationRepository")
public class TranslationRepository {

    private final TranslationMapper mapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    TranslationRepository(TranslationMapper mapper) {
        this.mapper = mapper;
    }

    public Long insertNewTranslation(Translation translation) {
        mapper.insertNewTranslation(translation);
        Long pk = mapper.getLastInsertId();
        translation.setId(pk);
        return pk;
    }

    public Translation getTranslation(Long id) {
        return mapper.getTranslation(id);
    }

    public void insertTranslationEntry(TranslationEntry entry) {
        mapper.insertTranslationEntry(entry);
    }

    public TranslationEntry getTranslationEntry(Long id, String languageCode) {
        return mapper.getTranslationEntry(id, languageCode);
    }

    public List<TranslationEntry> getTranslationEntriesForId(Long id) {
        return mapper.getTranslationEntriesForId(id);
    }

    public int updateTranslationEntry(TranslationEntry entry) {
        return mapper.updateTranslationEntry(entry.getId(), entry.getLanguageCode(), entry.getText());
    }

}