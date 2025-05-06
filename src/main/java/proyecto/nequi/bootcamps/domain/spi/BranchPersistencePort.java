package proyecto.nequi.bootcamps.domain.spi;

import proyecto.nequi.bootcamps.domain.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de persistencia para sucursales.
 * Gestiona operaciones CRUD de Branch sin detalles de la capa de datos.
 */
public interface BranchPersistencePort {

    /**
     * Verifica si existe una sucursal con el mismo nombre en la franquicia dada.
     * @param name nombre de la sucursal
     * @param franchiseId ID de la franquicia padre
     * @return Mono<Boolean> true si existe, false en caso contrario
     */
    Mono<Boolean> existsByNameAndFranchiseId(String name, Long franchiseId);

    /**
     * Guarda o actualiza una sucursal.
     * @param branch entidad a persistir
     * @return Mono<Branch> entidad resultante con ID actualizado
     */
    Mono<Branch> save(Branch branch);

    /**
     * Busca una sucursal por su ID.
     * @param id identificador
     * @return Mono<Branch> la entidad, o Mono.empty() si no existe
     */
    Mono<Branch> findById(Long id);

    /**
     * Lista todas las sucursales de una franquicia.
     * @param franchiseId ID de la franquicia padre
     * @return Flux<Branch> flujo de sucursales
     */
    Flux<Branch> findAllByFranchiseId(Long franchiseId);

    /**
     * Elimina una sucursal por su ID.
     * @param id identificador
     * @return Mono<Void> señal de finalización
     */
    Mono<Void> deleteById(Long id);
}