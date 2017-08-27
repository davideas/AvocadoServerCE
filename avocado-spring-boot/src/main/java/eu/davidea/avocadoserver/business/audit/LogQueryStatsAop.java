package eu.davidea.avocadoserver.business.audit;


import eu.davidea.avocadoserver.infrastructure.statistics.QueryStatsLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LogQueryStatsAop {

    private QueryStatsLogger queryStatsLogger;

    @Autowired
    public LogQueryStatsAop(QueryStatsLogger queryStatsLogger) {
        this.queryStatsLogger = queryStatsLogger;
    }

    /**
     * Logs statistics for any method with {@code @}{@link LogQueryStats}.
     */
    @Around("execution(@LogQueryStats * *(..)) && @annotation(logQueryStats)")
    public Object logDuration(ProceedingJoinPoint joinPoint, LogQueryStats logQueryStats) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(joinPoint.getSignature().getName());
        try {
            // Execute the method and get the result
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            queryStatsLogger.logQueryStats(stopWatch);
        }
    }

}