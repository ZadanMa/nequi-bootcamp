package proyecto.nequi.bootcamps.infrastructure.adapters.persistence.mapper;

import org.mapstruct.Mapper;
import proyecto.nequi.bootcamps.domain.model.Product;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.entity.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDomain(ProductEntity entity);
    ProductEntity toEntity(Product domain);
}