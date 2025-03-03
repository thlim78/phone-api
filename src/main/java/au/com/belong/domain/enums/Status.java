package au.com.belong.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    ACTIVATED("Activated"),
    DEACTIVATED("Deactivated");

    @JsonValue
    private String status;

    Status(String status) { this.status = status; }

    @JsonCreator
    public static Status fromString(String value) {
        if (value == null) {
            return null;
        }

        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Cannot determine value for Status   : " + value);
    }
}
