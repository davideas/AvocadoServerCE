package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import eu.davidea.avocadoserver.ApplicationTests;
import eu.davidea.avocadoserver.business.enums.EnumTranslationType;
import eu.davidea.avocadoserver.business.translation.Translation;
import eu.davidea.avocadoserver.business.translation.TranslationEntry;

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
    @DisplayName("Create and get new Translation")
    void testTranslation() {
        Translation translation = mockTranslation();
        Long pk = translationRepository.insertNewTranslation(translation);
        notNull(pk, "Primary Key is null");

        Translation translationResult = translationRepository.getTranslation(pk);
        assertEquals(pk, translationResult.getId());
        assertEquals(translation.getName(), translationResult.getName());
        assertEquals(translation.getType(), translationResult.getType());
    }

    @Test
    @DisplayName("Create and get new Translation Entry")
    void testTranslationEntry() {
        Translation translation = mockTranslation();
        Long pk = translationRepository.insertNewTranslation(translation);
        notNull(pk, "Primary Key is null");

        TranslationEntry entry = mockTranslationEntry(pk);
        translationRepository.insertTranslationEntry(entry);

        TranslationEntry entryResult = translationRepository.getTranslationEntry(pk, languageCode);
        notNull(entryResult, "Translation Entry is null");
        assertEquals(pk, entryResult.getId());
        assertEquals(entry.getLanguageCode(), entryResult.getLanguageCode());
        assertEquals(entry.getText(), entryResult.getText());
    }

    private Translation mockTranslation() {
        Translation translation = new Translation();
        translation.setName("Drinks");
        translation.setType(EnumTranslationType.MENU);
        return translation;
    }

    private TranslationEntry mockTranslationEntry(Long pk) {
        TranslationEntry entry = new TranslationEntry();
        entry.setId(pk);
        entry.setLanguageCode(languageCode);
        entry.setText("Entrée");
        return entry;
    }

}