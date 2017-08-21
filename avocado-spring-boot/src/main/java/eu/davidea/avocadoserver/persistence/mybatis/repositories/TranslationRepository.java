package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import eu.davidea.avocadoserver.business.audit.LogQueryStats;
import eu.davidea.avocadoserver.business.translation.Translation;
import eu.davidea.avocadoserver.business.translation.TranslationEntry;
import eu.davidea.avocadoserver.persistence.mybatis.mappers.TranslationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @LogQueryStats
    public Long insertNewTranslation(Translation translation) {
        mapper.insertNewTranslation(translation);
        Long pk = mapper.getLastInsertId();
        translation.setId(pk);
        return pk;
    }

    @LogQueryStats
    public Translation getTranslation(Long id) {
        return mapper.getTranslation(id);
    }

    @LogQueryStats
    public void insertTranslationEntry(TranslationEntry entry) {
        mapper.insertTranslationEntry(entry);
    }

    @LogQueryStats
    public TranslationEntry getTranslationEntry(Long id, String languageCode) {
        return mapper.getTranslationEntry(id, languageCode);
    }

    @LogQueryStats
    public List<TranslationEntry> getTranslationEntriesForId(Long id) {
        return mapper.getTranslationEntriesForId(id);
    }

    @LogQueryStats
    public int updateTranslationEntry(TranslationEntry entry) {
        return mapper.updateTranslationEntry(entry.getId(), entry.getLanguageCode(), entry.getText());
    }

}