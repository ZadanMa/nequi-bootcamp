
package proyecto.nequi.bootcamps.domain.enums;

import lombok.Getter;

@Getter
public enum TechnicalMessage {
    NAME_REQUIRED("FR-400", "El nombre es obligatorio"),
    ENTITY_NOT_FOUND("FR-404", "Entidad no encontrada"),
    FOUND("FR-200", "Entidad encontrada"),
    FAILED_TO_SAVE_ENTITY("FR-500", "Error al guardar la entidad"),
    NAME_ALREADY_EXISTS("FR-409", "El nombre ya existe"),
    CREATED("FR-201", "Creado exitosamente"),
    UPDATED("FR-200", "Actualizado exitosamente"),
    DELETED("FR-200", "Eliminado exitosamente"),
    LIST_EMPTY("FR-204", "No hay registros disponibles");

    private final String code;
    private final String message;

    TechnicalMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}