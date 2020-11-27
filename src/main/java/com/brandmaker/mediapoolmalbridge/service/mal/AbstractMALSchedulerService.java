package com.brandmaker.mediapoolmalbridge.service.mal;

import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public abstract class AbstractMALSchedulerService extends AbstractSchedulerService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm:ss");

    protected String getMidnightMalLookInThePast()
    {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .minusDays( appConfig.getMalLookInThePastDays() )
                .withHour( 0 )
                .withMinute( 0 )
                .withSecond( 0 )
                .format(DATE_TIME_FORMATTER);
    }
}
