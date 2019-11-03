package MediaPoolMalBridge.clients.BrandMaker.themefulltree.client.model;

import com.brandmaker.webservices.theme.ThemesResult;

import java.util.ArrayList;
import java.util.List;

public class DownloadFullThemeTreeResponse {

    private boolean status;

    private ThemesResult themesResult;

    private List<String> errors = new ArrayList<>();

    private List<String> warnings = new ArrayList<>();

    public DownloadFullThemeTreeResponse(final ThemesResult themesResult) {
        this.status = true;
        this.themesResult = themesResult;
    }

    public DownloadFullThemeTreeResponse(final boolean status, final String error) {
        this.status = status;
        errors.add(error);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ThemesResult getThemesResult() {
        return themesResult;
    }

    public void setThemesResult(ThemesResult themesResult) {
        this.themesResult = themesResult;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
