package proyecto.nequi.bootcamps.infrastructure.adapters.persistence;

import org.springframework.stereotype.Component;
import proyecto.nequi.bootcamps.domain.model.Product;
import proyecto.nequi.bootcamps.domain.spi.ProductPersistencePort;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.mapper.ProductMapper;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductPersistenceAdapter(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Product> save(Product product) {
        return repository.save(mapper.toEntity(product))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByNameAndBranchId(String name, Long branchId) {
        return repository.existsByNameAndBranchId(name, branchId);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Product> findAllByBranchId(Long branchId) {
        return repository.findAllByBranchId(branchId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Product> updateStock(Long id, int newStock) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setStock(newStock);
                    return repository.save(entity);
                })
                .map(mapper::toDomain);
    }
}