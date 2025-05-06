package proyecto.nequi.bootcamps.infrastructure.adapters.persistence;

import org.springframework.stereotype.Component;
import proyecto.nequi.bootcamps.domain.model.Branch;
import proyecto.nequi.bootcamps.domain.spi.BranchPersistencePort;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.mapper.BranchMapper;
import proyecto.nequi.bootcamps.infrastructure.adapters.persistence.repository.BranchRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class BranchPersistenceAdapter implements BranchPersistencePort {

    private final BranchRepository repository;
    private final BranchMapper mapper;

    public BranchPersistenceAdapter(BranchRepository repository, BranchMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Branch> save(Branch branch) {
        return repository.save(mapper.toEntity(branch))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByNameAndFranchiseId(String name, Long franchiseId) {
        return repository.existsByNameAndFranchiseId(name, franchiseId);
    }

    @Override
    public Mono<Branch> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Branch> findAllByFranchiseId(Long franchiseId) {
        return repository.findAllByFranchiseId(franchiseId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

}
