package proyecto.nequi.bootcamps.infrastructure.adapters.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.entity.BranchEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository extends ReactiveCrudRepository<BranchEntity, Long> {
    Mono<Boolean> existsByNameAndFranchiseId(String name, Long franchiseId);
    Flux<BranchEntity> findAllByFranchiseId(Long franchiseId);
}