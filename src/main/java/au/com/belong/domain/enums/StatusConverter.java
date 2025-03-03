package au.com.belong.domain.enums;

import org.springframework.core.convert.converter.Converter;

public class StatusConverter implements Converter<String,Status> {
    @Override
    public Status convert(String value) {
        return Status.fromString(value);
    }
}
