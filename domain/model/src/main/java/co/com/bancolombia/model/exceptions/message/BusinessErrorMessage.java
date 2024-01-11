package co.com.bancolombia.model.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorMessage {

    INVALID_REQUEST("SFB0000", "Invalid request"),
    INVALID_MESSAGE("SFB0001", "Invalid message length (min 207)"),
    INVALID_PAYLOAD("SFB0002", "Invalid payload (must be numeric)");

    private final String code;
    private final String message;
}

