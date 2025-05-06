package proyecto.nequi.bootcamps.infrastructure.adapters.persistence;

import org.springframework.stereotype.Component;
import proyecto.nequi.bootcamps.domain.model.Franchise;
import proyecto.nequi.bootcamps.domain.spi.FranchisePersistencePort;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.mapper.FranchiseMapper;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.repository.FranchiseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FranchisePersistenceAdapter implements FranchisePersistencePort {

    private final FranchiseRepository repository;
    private final FranchiseMapper mapper;

    public FranchisePersistenceAdapter(FranchiseRepository repository, FranchiseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(mapper.toEntity(franchise))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Mono<Franchise> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}