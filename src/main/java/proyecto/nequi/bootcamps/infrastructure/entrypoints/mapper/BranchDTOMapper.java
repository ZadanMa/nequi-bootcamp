package proyecto.nequi.bootcamps.infrastructure.entrypoints.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import proyecto.nequi.bootcamps.domain.model.Branch;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.dto.BranchDTO;

@Mapper(componentModel = "spring")
public interface BranchDTOMapper {

    BranchDTO toDTO(Branch domain);

    @Mapping(target = "productIds", source = "dto.productIds")
    Branch toDomain(BranchDTO dto);
}