package proyecto.nequi.bootcamps.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
public record ProductDTO(
        @Schema(description = "ID del producto", example = "5")
        Long id,

        @Schema(description = "Nombre del producto", example = "Hamburguesa Doble")
        String name,

        @Schema(description = "Cantidad en stock", example = "120")
        int stock,

        @Schema(description = "ID de la sucursal padre", example = "10")
        Long branchId
) { }