package proyecto.nequi.bootcamps.domain.model;

import java.util.List;

public record Franchise(
        Long id,
        String name,
        List<Long> branchIds
) { }