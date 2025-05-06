package proyecto.nequi.bootcamps.domain.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proyecto.nequi.bootcamps.domain.api.BranchServicePort;
import proyecto.nequi.bootcamps.domain.enums.TechnicalMessage;
import proyecto.nequi.bootcamps.domain.exceptions.BusinessException;
import proyecto.nequi.bootcamps.domain.exceptions.TechnicalException;
import proyecto.nequi.bootcamps.domain.model.Branch;
import proyecto.nequi.bootcamps.domain.spi.BranchPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BranchUseCase implements BranchServicePort {

    private final BranchPersistencePort persistencePort;
    private final Logger log = LoggerFactory.getLogger(BranchUseCase.class);

    public BranchUseCase(BranchPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Mono<Branch> create(Branch branch) {
        return persistencePort.existsByNameAndFranchiseId(branch.name(), branch.franchiseId())
                .flatMap(exists -> exists
                        ? Mono.<Branch>error(new BusinessException(TechnicalMessage.NAME_ALREADY_EXISTS))
                        : persistencePort.save(branch)
                )
                .onErrorMap(ex -> {
                    log.error("Error técnico al crear sucursal: {}", ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Mono<Branch> update(Branch branch) {
        return persistencePort.findById(branch.id())
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ENTITY_NOT_FOUND)))
                .flatMap(existing -> {
                    boolean nameChanged = !existing.name().equals(branch.name());
                    boolean franchiseChanged = !existing.franchiseId().equals(branch.franchiseId());
                    if (nameChanged || franchiseChanged) {
                        return persistencePort.existsByNameAndFranchiseId(branch.name(), branch.franchiseId())
                                .flatMap(dup -> dup
                                        ? Mono.<Branch>error(new BusinessException(TechnicalMessage.NAME_ALREADY_EXISTS))
                                        : persistencePort.save(branch)
                                );
                    }
                    // Sólo cambian productIds u otros campos no únicos
                    return persistencePort.save(branch);
                })
                .onErrorMap(ex -> {
                    log.error("Error técnico al actualizar sucursal: {}", ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Mono<Void> deleteById(Long branchId) {
        return persistencePort.findById(branchId)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ENTITY_NOT_FOUND)))
                .then(persistencePort.deleteById(branchId))
                .onErrorMap(ex -> {
                    log.error("Error técnico al eliminar sucursal: {}", ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Mono<Branch> findById(Long branchId) {
        return persistencePort.findById(branchId)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ENTITY_NOT_FOUND)))
                .onErrorMap(ex -> {
                    log.error("Error técnico al buscar sucursal: {}", ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Flux<Branch> findAllByFranchiseId(Long franchiseId) {
        return persistencePort.findAllByFranchiseId(franchiseId)
                .switchIfEmpty(Flux.error(new BusinessException(TechnicalMessage.LIST_EMPTY)))
                .onErrorMap(ex -> {
                    log.error("Error técnico al listar sucursales de franquicia {}: {}", franchiseId, ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }
}
