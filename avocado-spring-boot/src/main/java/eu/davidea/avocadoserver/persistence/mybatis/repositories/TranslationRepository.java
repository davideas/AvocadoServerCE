package eu.davidea.avocadoserver.persistence.mybatis.repositories;

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

    public long createNewTranslation() {
        mapper.newTranslationPk();
        return mapper.getLastInsertId();
    }

    public void insertTranslationEntry(TranslationEntry entry) {
        mapper.insertTranslationEntry(entry);
    }

    public TranslationEntry getTranslationEntry(Long id, String languageCode) {
        return mapper.getTranslationEntry(id, languageCode);
    }

    public List<TranslationEntry> getTranslationsForId(Long id) {
        return mapper.getTranslationsForId(id);
    }

}