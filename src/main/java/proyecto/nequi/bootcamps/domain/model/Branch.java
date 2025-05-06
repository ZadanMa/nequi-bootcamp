package proyecto.nequi.bootcamps.domain.model;

import java.util.List;

public record Branch(
        Long id,
        String name,
        Long franchiseId,
        List<Long> productIds
) { }