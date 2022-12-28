package by.itacademy.profiler.api.deserializers;

import by.itacademy.profiler.usecasses.dto.AuthenticationRequestDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class TrimStringToDeserializer extends JsonDeserializer<AuthenticationRequestDto> {

    @Override
    public AuthenticationRequestDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        TextNode email = (TextNode) treeNode.get("email");
        TextNode password = (TextNode) treeNode.get("password");
        return new AuthenticationRequestDto(email.asText().trim(), password.asText());
    }
}
