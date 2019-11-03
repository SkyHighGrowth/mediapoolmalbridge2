package MediaPoolMalBridge.clients.BrandMaker.themeid.client.model;

import com.brandmaker.webservices.theme.Theme;

import java.util.ArrayList;
import java.util.List;

public class DownloadThemeIdResponse {

    private boolean status;

    private List<String> errors = new ArrayList<>();

    private List<String> warnings = new ArrayList<>();

    private Theme theme;

    public DownloadThemeIdResponse(final Theme theme) {
        this.status = true;
        this.theme = theme;
    }

    public DownloadThemeIdResponse(final boolean status, final String error) {
        this.status = status;
        errors.add(error);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
