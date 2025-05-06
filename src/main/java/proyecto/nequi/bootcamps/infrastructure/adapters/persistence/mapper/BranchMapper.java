package proyecto.nequi.bootcamps.infrastructure.adapters.persistence.mapper;

import org.mapstruct.Mapper;
import proyecto.nequi.bootcamps.domain.model.Branch;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.entity.BranchEntity;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    Branch toDomain(BranchEntity entity);
    BranchEntity toEntity(Branch domain);
}