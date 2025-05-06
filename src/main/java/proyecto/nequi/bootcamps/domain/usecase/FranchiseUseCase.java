package proyecto.nequi.bootcamps.domain.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proyecto.nequi.bootcamps.domain.api.FranchiseServicePort;
import proyecto.nequi.bootcamps.domain.enums.TechnicalMessage;
import proyecto.nequi.bootcamps.domain.exceptions.BusinessException;
import proyecto.nequi.bootcamps.domain.exceptions.TechnicalException;
import proyecto.nequi.bootcamps.domain.model.Franchise;
import proyecto.nequi.bootcamps.domain.spi.FranchisePersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FranchiseUseCase implements FranchiseServicePort {

    private final FranchisePersistencePort persistencePort;
    private final Logger log = LoggerFactory.getLogger(FranchiseUseCase.class);

    public FranchiseUseCase(FranchisePersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Mono<Franchise> create(Franchise franchise) {
        return persistencePort.existsByName(franchise.name())
                .flatMap(exists -> exists
                        ? Mono.<Franchise>error(new BusinessException(TechnicalMessage.NAME_ALREADY_EXISTS))
                        : persistencePort.save(franchise)
                )
                .onErrorMap(ex -> {
                    log.error("Error técnico al crear franquicia: {}", ex.getMessage());
                    // Si ya es BusinessException, lo dejo pasar; si no, lo convierto
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Mono<Franchise> update(Franchise franchise) {
        return persistencePort.findById(franchise.id())
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ENTITY_NOT_FOUND)))
                .flatMap(existing -> {
                    // Si cambió el nombre, verifico unicidad
                    if (!existing.name().equals(franchise.name())) {
                        return persistencePort.existsByName(franchise.name())
                                .flatMap(nameExists -> nameExists
                                        ? Mono.<Franchise>error(new BusinessException(TechnicalMessage.NAME_ALREADY_EXISTS))
                                        : persistencePort.save(franchise)
                                );
                    }
                    // Si sólo cambian branchIds o no cambió el nombre, guardo directo
                    return persistencePort.save(franchise);
                })
                .onErrorMap(ex -> {
                    log.error("Error técnico al actualizar franquicia: {}", ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Mono<Void> deleteById(Long franchiseId) {
        return persistencePort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ENTITY_NOT_FOUND)))
                .then(persistencePort.deleteById(franchiseId))
                .onErrorMap(ex -> {
                    log.error("Error técnico al eliminar franquicia: {}", ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Mono<Franchise> findById(Long franchiseId) {
        return persistencePort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ENTITY_NOT_FOUND)))
                .onErrorMap(ex -> {
                    log.error("Error técnico al buscar franquicia: {}", ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Flux<Franchise> findAll() {
        return persistencePort.findAll()
                .switchIfEmpty(Flux.error(new BusinessException(TechnicalMessage.LIST_EMPTY)))
                .onErrorMap(ex -> {
                    log.error("Error técnico al listar franquicias: {}", ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }
}