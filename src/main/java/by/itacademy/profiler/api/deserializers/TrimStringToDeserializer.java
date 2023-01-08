package by.itacademy.profiler.api.deserializers;

import by.itacademy.profiler.usecasses.dto.AuthenticationRequestDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

import static java.util.Objects.isNull;

@JsonComponent
public class TrimStringToDeserializer extends JsonDeserializer<AuthenticationRequestDto> {
    private JsonNode jsonNode;

    @Override
    public AuthenticationRequestDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        jsonNode = jsonParser.getCodec().readTree(jsonParser);
        String email = (getValue("email"));
        String password = getValue("password");
        if (!isNull(email)) {
            email = email.trim();
        }
        return new AuthenticationRequestDto(email, password);
    }

    private String getValue(String s) {
        if (!isNull(jsonNode.get(s))) {
            if (!jsonNode.get(s).isNull()) {
                return jsonNode.get(s).asText();
            }
        }
        return null;
    }
}
