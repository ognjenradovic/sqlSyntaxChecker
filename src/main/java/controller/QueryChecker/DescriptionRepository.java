package controller.QueryChecker;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DescriptionRepository {


    List<ErrorDescription> errorDescriptions;

    public DescriptionRepository() {
        errorDescriptions=new ArrayList<>();
        this.mapErrors();
    }
    public void mapErrors(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            errorDescriptions = Arrays.asList(mapper.readValue(Paths.get("errorDescriptions.json").toFile(), ErrorDescription[].class));
           // errorDescriptions.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ErrorDescription> getErrorDescriptions() {
        return errorDescriptions;
    }

    public void setErrorDescriptions(List<ErrorDescription> errorDescriptions) {
        this.errorDescriptions = errorDescriptions;
    }
}
