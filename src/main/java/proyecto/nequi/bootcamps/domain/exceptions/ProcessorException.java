package proyecto.nequi.bootcamps.domain.exceptions;

import lombok.Getter;
import proyecto.nequi.bootcamps.domain.enums.TechnicalMessage;


@Getter
public class ProcessorException extends RuntimeException {
    private final TechnicalMessage technicalMessage;

    public ProcessorException(String message, TechnicalMessage technicalMessage) {
        super(message);
        this.technicalMessage = technicalMessage;
    }
}