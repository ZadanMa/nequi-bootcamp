package proyecto.nequi.bootcamps.domain.spi;

import proyecto.nequi.bootcamps.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de persistencia para productos.
 * Encapsula el acceso a datos de Product.
 */
public interface ProductPersistencePort {

    /**
     * Verifica si existe un producto con el mismo nombre en la sucursal dada.
     * @param name nombre del producto
     * @param branchId ID de la sucursal padre
     * @return Mono<Boolean> true si existe, false en caso contrario
     */
    Mono<Boolean> existsByNameAndBranchId(String name, Long branchId);

    /**
     * Guarda o actualiza un producto.
     * @param product entidad a persistir
     * @return Mono<Product> entidad resultante con ID actualizado
     */
    Mono<Product> save(Product product);

    /**
     * Busca un producto por su ID.
     * @param id identificador
     * @return Mono<Product> la entidad, o Mono.empty() si no existe
     */
    Mono<Product> findById(Long id);

    /**
     * Lista todos los productos de una sucursal.
     * @param branchId ID de la sucursal padre
     * @return Flux<Product> flujo de productos
     */
    Flux<Product> findAllByBranchId(Long branchId);

    /**
     * Elimina un producto por su ID.
     * @param id identificador
     * @return Mono<Void> señal de finalización
     */
    Mono<Void> deleteById(Long id);

    /**
     * Actualiza solo el stock de un producto.
     * @param id ID del producto
     * @param newStock nuevo valor de stock
     * @return Mono<Product> producto con stock actualizado
     */
    Mono<Product> updateStock(Long id, int newStock);
}