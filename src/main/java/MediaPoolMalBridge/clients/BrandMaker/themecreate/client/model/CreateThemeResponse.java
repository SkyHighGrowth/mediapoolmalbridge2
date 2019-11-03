package MediaPoolMalBridge.clients.BrandMaker.themecreate.client.model;

import com.brandmaker.webservices.theme.ThemeResult;

import java.util.ArrayList;
import java.util.List;

public class CreateThemeResponse {

    private boolean status;

    private ThemeResult themeResult;

    private List<String> errors = new ArrayList<>();

    private List<String> warnings = new ArrayList<>();

    public CreateThemeResponse(final ThemeResult themeResult) {
        status = themeResult.isResult();
        this.themeResult = themeResult;
    }

    public CreateThemeResponse(final boolean status, final String error) {
        this.status = status;
        errors.add(error);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ThemeResult getThemeResult() {
        return themeResult;
    }

    public void setThemeResult(ThemeResult themeResult) {
        this.themeResult = themeResult;
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
