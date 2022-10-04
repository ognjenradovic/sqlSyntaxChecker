package controller.QueryChecker;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class ErrorDescription {
    String error_code;
    String description;
    String suggestion;
    String[] error_source;

    public ErrorDescription(String error_code, String description, String suggestion) {
        this.error_code = error_code;
        this.description = description;
        this.suggestion = suggestion;
    }

    public ErrorDescription() {
    }

    public String[] getError_source() {
        return error_source;
    }

    public void setError_source(String[] error_source) {
        this.error_source = error_source;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public String toString() {
        return  error_code + "\n" + description + "\n" + suggestion;
    }
}
