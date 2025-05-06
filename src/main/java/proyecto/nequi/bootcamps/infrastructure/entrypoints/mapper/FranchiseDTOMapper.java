package proyecto.nequi.bootcamps.infrastructure.entrypoints.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import proyecto.nequi.bootcamps.domain.model.Franchise;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.dto.FranchiseDTO;

@Mapper(componentModel = "spring")
public interface FranchiseDTOMapper {

    FranchiseDTO toDTO(Franchise domain);

    @Mapping(target = "branchIds", source = "dto.branchIds")
    Franchise toDomain(FranchiseDTO dto);
}