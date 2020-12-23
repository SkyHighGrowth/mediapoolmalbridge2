package com.brandmaker.mediapoolmalbridge.service.mal;

import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public abstract class AbstractMALSchedulerService extends AbstractSchedulerService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm:ss");

    /**
     * Get midnight look in the past days
     *
     * @return formatted date
     */
    protected String getMidnightMalLookInThePast() {
        return getFormattedDate(appConfig.getAppConfigData().getMalLookInThePastDays());
    }

    /**
     * For the unavailable assets we are going 3 times less days in past because there is no paging for that request
     *
     * @return formatted date
     */
    protected String getMidnightMalLookInThePastDeleted() {
        int malLookInThePastDays = appConfig.getAppConfigData().getMalLookInThePastDays() > 3 ? appConfig.getAppConfigData().getMalLookInThePastDays() / 3 : 3;
        return getFormattedDate(malLookInThePastDays);
    }

    private String getFormattedDate(int malLookInThePastDays) {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .minusDays(malLookInThePastDays)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .format(DATE_TIME_FORMATTER);
    }
}
