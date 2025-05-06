package proyecto.nequi.bootcamps.infrastructure.adapters.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.entity.FranchiseEntity;
import reactor.core.publisher.Mono;

public interface FranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {
    Mono<Boolean> existsByName(String name);
}