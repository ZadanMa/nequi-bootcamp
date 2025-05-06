package proyecto.nequi.bootcamps.domain.api;

import proyecto.nequi.bootcamps.domain.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Casos de uso relacionados con sucursales.
 */
public interface BranchServicePort {

    /**
     * Crea una nueva sucursal.
     * @param branch datos de la sucursal (incluye franchiseId y productIds vacía o con valores)
     * @return Mono con la sucursal creada
     */
    Mono<Branch> create(Branch branch);

    /**
     * Actualiza una sucursal existente.
     * @param branch datos actualizados (incluye productIds completo)
     * @return Mono con la sucursal actualizada
     */
    Mono<Branch> update(Branch branch);

    /**
     * Elimina una sucursal por su ID.
     * @param branchId identificador de la sucursal
     * @return Mono vacío al completar
     */
    Mono<Void> deleteById(Long branchId);

    /**
     * Consulta una sucursal por su ID.
     * @param branchId identificador de la sucursal
     * @return Mono con la sucursal
     */
    Mono<Branch> findById(Long branchId);

    /**
     * Lista todas las sucursales pertenecientes a una franquicia.
     * @param franchiseId identificador de la franquicia
     * @return Flux con las sucursales encontradas
     */
    Flux<Branch> findAllByFranchiseId(Long franchiseId);
}