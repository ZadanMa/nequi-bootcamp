package proyecto.nequi.bootcamps.domain.model;

public record Product(
        Long id,
        String name,
        int stock,
        Long branchId
) { }