package proyecto.nequi.bootcamps.infrastructure.adapters.persistence.mapper;

import org.mapstruct.Mapper;
import proyecto.nequi.bootcamps.domain.model.Franchise;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.entity.FranchiseEntity;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {
    Franchise toDomain(FranchiseEntity entity);
    FranchiseEntity toEntity(Franchise domain);
}