package com.brandmaker.mediapoolmalbridge.service.brandmaker.theme.download;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.BMTheme;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.themefulltree.client.BMDownloadFullThemeTreeClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.themefulltree.client.model.DownloadFullThemeTreeResponse;
import com.brandmaker.mediapoolmalbridge.model.brandmaker.theme.BMThemes;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service which downloads themes form MAL server
 */
@Service
public class BMDownloadThemeUniqueThreadService extends AbstractBMUniqueThreadService {

    private final BMDownloadFullThemeTreeClient downloadThemeIdClient;

    private final BMThemes bmThemes;

    public BMDownloadThemeUniqueThreadService(final BMDownloadFullThemeTreeClient downloadThemeIdClient,
                                              final BMThemes bmThemes) {
        this.downloadThemeIdClient = downloadThemeIdClient;
        this.bmThemes = bmThemes;
    }

    @Override
    protected void run() {
        final BMTheme bmTheme = new BMTheme();
        bmTheme.setThemeId(301);
        final DownloadFullThemeTreeResponse response = downloadThemeIdClient.downloadFullThemeTree(bmTheme);
        if (!response.isStatus() )
        {
            reportErrorOnResponse(String.valueOf(bmTheme.getThemeId()), response);
        }
        if( response.getThemesResult() == null) {
            final String message = String.format("Download full theme tree resulted in empty themes list theme [%s]", GSON.toJson(bmTheme));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.WARNING, getClass().getName(), null,  message, ReportTo.BM, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        bmThemes.update(response.getThemesResult());
    }
}
