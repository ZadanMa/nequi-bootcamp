package proyecto.nequi.bootcamps.domain.spi;

import proyecto.nequi.bootcamps.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de persistencia para franquicias.
 * Se encarga de guardar, consultar y eliminar entidades Franchise
 * sin exponer detalles de la base de datos.
 */
public interface FranchisePersistencePort {

    /**
     * Verifica si existe una franquicia con el mismo nombre.
     * @param name nombre a consultar
     * @return Mono<Boolean> true si existe, false en caso contrario
     */
    Mono<Boolean> existsByName(String name);

    /**
     * Guarda o actualiza una franquicia.
     * @param franchise entidad a persistir
     * @return Mono<Franchise> entidad con ID (y datos) actualizados
     */
    Mono<Franchise> save(Franchise franchise);

    /**
     * Busca una franquicia por su ID.
     * @param id identificador
     * @return Mono<Franchise> la entidad, o Mono.empty() si no existe
     */
    Mono<Franchise> findById(Long id);

    /**
     * Lista todas las franquicias.
     * @return Flux<Franchise> flujo de todas las entidades
     */
    Flux<Franchise> findAll();

    /**
     * Elimina una franquicia por su ID.
     * @param id identificador
     * @return Mono<Void> señal de finalización
     */
    Mono<Void> deleteById(Long id);
}