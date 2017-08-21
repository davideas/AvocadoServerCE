package eu.davidea.avocadoserver.business.translation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import eu.davidea.avocadoserver.business.restaurant.GetRestaurantUseCase;
import eu.davidea.avocadoserver.infrastructure.statistics.QueryStatsLogger;
import eu.davidea.avocadoserver.persistence.mybatis.repositories.TranslationRepository;

/**
 * @author Davide
 * @since 07/08/2017
 */
@Service
public class GetTranslationUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetRestaurantUseCase.class);

    private TranslationRepository translationRepository;
    private QueryStatsLogger queryStatsLogger;

    @Autowired
    public GetTranslationUseCase(TranslationRepository translationRepository, QueryStatsLogger queryStatsLogger) {
        this.translationRepository = translationRepository;
        this.queryStatsLogger = queryStatsLogger;
    }

    public List<TranslationEntry> getTranslationsForId(Long translationId) {
        return translationRepository.getTranslationEntriesForId(translationId);
    }

}
