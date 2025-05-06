package proyecto.nequi.bootcamps.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record FranchiseDTO(
        @Schema(description = "ID de la franquicia", example = "1")
        Long id,

        @Schema(description = "Nombre de la franquicia", example = "Burger King")
        String name,

        @Schema(description = "Lista de IDs de sucursales asociadas", example = "[1,2,3]")
        List<Long> branchIds
) { }