package proyecto.nequi.bootcamps.domain.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proyecto.nequi.bootcamps.domain.api.ProductServicePort;
import proyecto.nequi.bootcamps.domain.enums.TechnicalMessage;
import proyecto.nequi.bootcamps.domain.exceptions.BusinessException;
import proyecto.nequi.bootcamps.domain.exceptions.TechnicalException;
import proyecto.nequi.bootcamps.domain.model.Product;
import proyecto.nequi.bootcamps.domain.spi.ProductPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductUseCase implements ProductServicePort {

    private final ProductPersistencePort persistencePort;
    private final Logger log = LoggerFactory.getLogger(ProductUseCase.class);

    public ProductUseCase(ProductPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Mono<Product> create(Product product) {
        return persistencePort.existsByNameAndBranchId(product.name(), product.branchId())
                .flatMap(exists -> exists
                        ? Mono.<Product>error(new BusinessException(TechnicalMessage.NAME_ALREADY_EXISTS))
                        : persistencePort.save(product)
                )
                .onErrorMap(ex -> {
                    log.error("Error técnico al crear producto: {}", ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Mono<Product> updateStock(Long productId, int newStock) {
        return persistencePort.findById(productId)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ENTITY_NOT_FOUND)))
                .flatMap(existing -> persistencePort.updateStock(productId, newStock))
                .onErrorMap(ex -> {
                    log.error("Error técnico al actualizar stock del producto {}: {}", productId, ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Mono<Void> deleteById(Long productId) {
        return persistencePort.findById(productId)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ENTITY_NOT_FOUND)))
                .then(persistencePort.deleteById(productId))
                .onErrorMap(ex -> {
                    log.error("Error técnico al eliminar producto {}: {}", productId, ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Mono<Product> findById(Long productId) {
        return persistencePort.findById(productId)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ENTITY_NOT_FOUND)))
                .onErrorMap(ex -> {
                    log.error("Error técnico al buscar producto {}: {}", productId, ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }

    @Override
    public Flux<Product> findAllByBranchId(Long branchId) {
        return persistencePort.findAllByBranchId(branchId)
                .switchIfEmpty(Flux.error(new BusinessException(TechnicalMessage.LIST_EMPTY)))
                .onErrorMap(ex -> {
                    log.error("Error técnico al listar productos de sucursal {}: {}", branchId, ex.getMessage());
                    return (ex instanceof BusinessException)
                            ? ex
                            : new TechnicalException(TechnicalMessage.FAILED_TO_SAVE_ENTITY);
                });
    }
}
