package by.itacademy.profiler.api.deserializers;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class TrimStringsDeserializer extends SimpleModule {

    public TrimStringsDeserializer() {
        addDeserializer(String.class, new StdScalarDeserializer<>(String.class) {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return jsonParser.getValueAsString().trim();
            }
        });
    }
}
