package proyecto.nequi.bootcamps.infrastructure.entrypoints.mapper;

import org.mapstruct.Mapper;
import proyecto.nequi.bootcamps.domain.model.Product;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.dto.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper {

    ProductDTO toDTO(Product domain);

    Product toDomain(ProductDTO dto);
}