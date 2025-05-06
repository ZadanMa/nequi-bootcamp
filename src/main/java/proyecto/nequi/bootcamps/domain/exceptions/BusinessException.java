package proyecto.nequi.bootcamps.domain.exceptions;

import proyecto.nequi.bootcamps.domain.enums.TechnicalMessage;

public class BusinessException extends ProcessorException {
    public BusinessException(TechnicalMessage technicalMessage) {
        super(technicalMessage.getMessage(), technicalMessage);

    }}