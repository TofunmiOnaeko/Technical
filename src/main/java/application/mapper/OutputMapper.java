package application.mapper;

import application.model.Content;
import application.model.Output;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class OutputMapper {

    public ArrayList<Output> mapToOutput(ArrayList<Content> contentList) {
        ArrayList<Output> outputList = new ArrayList<>();

        contentList.stream().forEach(content -> {
            Output output = new Output(content.getFullName(), content.getTransport(), content.getTopSpeed());
            outputList.add(output);
        });

        return outputList;
    }

}
