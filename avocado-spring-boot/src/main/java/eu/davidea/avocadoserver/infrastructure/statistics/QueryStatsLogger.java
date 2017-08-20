package eu.davidea.avocadoserver.infrastructure.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author Davide
 * @since 07/08/2017
 */
@Component
public class QueryStatsLogger {

    private static final Logger QUERY_STATS_LOGGER = LoggerFactory.getLogger("QUERY-STATS");

    /**
     * Logs statistics related to a query.
     * Query statistics can be gathered with StopWatch.
     *
     * @param stopWatch the stopwatch used for gathering statistics
     */
    public void logQueryStats(StopWatch stopWatch) {
        QUERY_STATS_LOGGER.trace(stopWatch.prettyPrint());
    }

}