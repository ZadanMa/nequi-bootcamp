package proyecto.nequi.bootcamps.infrastructure.adapters.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.entity.ProductEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    Mono<Boolean> existsByNameAndBranchId(String name, Long branchId);
    Flux<ProductEntity> findAllByBranchId(Long branchId);
}