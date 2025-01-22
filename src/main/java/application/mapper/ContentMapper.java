package application.mapper;

import application.model.Content;
import org.springframework.stereotype.Component;

@Component
public class ContentMapper {

    public Content mapArrayToContent(String[] values) {
        Content content = new Content();

        for (int i = 0; i < values.length; i++) {
            String value = values[i];

            switch (i) {
                case 0:
                    content.setUuId(value);
                    break;
                case 1:
                    content.setUserId(value);
                    break;
                case 2:
                    content.setFullName(value);
                    break;
                case 3:
                    content.setLikes(value);
                    break;
                case 4:
                    content.setTransport(value);
                    break;
                case 5:
                    content.setAvgSpeed(Double.valueOf(value));
                    break;
                case 6:
                    value = value.replace("\\n", "");
                    content.setTopSpeed(Double.valueOf(value));
                    break;
                default:
                    break;
            }
        }
        return content;
    }

}
