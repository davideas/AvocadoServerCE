package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import eu.davidea.avocadoserver.ApplicationTests;
import eu.davidea.avocadoserver.business.translation.TranslationEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.util.Assert.notNull;

@Transactional
@TestPropertySource(properties = "languageCode = fr-BE")
class TranslationRepositoryTest extends ApplicationTests {

    @Value("${languageCode}")
    String languageCode;

    @Autowired
    private TranslationRepository translationRepository;

    @Test
    @DisplayName("Create and get new Translation Entry")
    void mockTranslationEntry() {
        Long pk = translationRepository.createNewTranslation();
        notNull(pk, "Primary Key is null");

        TranslationEntry entry = mockTranslationEntry(pk);
        translationRepository.insertTranslationEntry(entry);

        TranslationEntry entryResult = translationRepository.getTranslationEntry(pk, languageCode);
        notNull(entryResult, "Translation Entry is null");
        assertEquals(pk, entryResult.getId());
        assertEquals(entry.getLanguageCode(), entryResult.getLanguageCode());
        assertEquals(entry.getName(), entryResult.getName());
        assertEquals(entry.getText(), entryResult.getText());
        notNull(entryResult.getCreDate(), "Creation date is null");
        notNull(entryResult.getModDate(), "Modified date is null");
    }

    private TranslationEntry mockTranslationEntry(Long pk) {
        TranslationEntry entry = new TranslationEntry();
        entry.setId(pk);
        entry.setLanguageCode(languageCode);
        entry.setName("Starter");
        entry.setText("Entrée");
        return entry;
    }

}