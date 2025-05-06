package proyecto.nequi.bootcamps.domain.api;

import proyecto.nequi.bootcamps.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseServicePort {
    Mono<Franchise> create(Franchise franchise);

    Mono<Franchise> update(Franchise franchise);

    Mono<Void> deleteById(Long franchiseId);

    Mono<Franchise> findById(Long franchiseId);

    Flux<Franchise> findAll();
}