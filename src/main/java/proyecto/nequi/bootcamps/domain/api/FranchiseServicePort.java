package proyecto.nequi.bootcamps.domain.api;

import proyecto.nequi.bootcamps.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Casos de uso relacionados con franquicias.
 */
public interface FranchiseServicePort {

    /**
     * Crea una nueva franquicia.
     * @param franchise datos de la franquicia (incluye branchIds vacía o con valores)
     * @return Mono con la franquicia creada (con ID asignado)
     */
    Mono<Franchise> create(Franchise franchise);

    /**
     * Actualiza los datos de una franquicia existente.
     * @param franchise datos actualizados (incluye la lista completa de branchIds)
     * @return Mono con la franquicia actualizada
     */
    Mono<Franchise> update(Franchise franchise);

    /**
     * Elimina una franquicia por su ID.
     * @param franchiseId identificador de la franquicia
     * @return Mono vacío al completar
     */
    Mono<Void> deleteById(Long franchiseId);

    /**
     * Consulta una franquicia por su ID.
     * @param franchiseId identificador de la franquicia
     * @return Mono con la franquicia, o error si no existe
     */
    Mono<Franchise> findById(Long franchiseId);

    /**
     * Lista todas las franquicias.
     * @return Flux con todas las franquicias disponibles
     */
    Flux<Franchise> findAll();
}