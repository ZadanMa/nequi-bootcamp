package proyecto.nequi.bootcamps.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record BranchDTO(
        @Schema(description = "ID de la sucursal", example = "10")
        Long id,

        @Schema(description = "Nombre de la sucursal", example = "Centro")
        String name,

        @Schema(description = "ID de la franquicia padre", example = "1")
        Long franchiseId,

        @Schema(description = "Lista de IDs de productos disponibles", example = "[5,6,7]")
        List<Long> productIds
) { }