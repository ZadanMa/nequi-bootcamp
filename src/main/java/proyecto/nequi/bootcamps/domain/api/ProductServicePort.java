package proyecto.nequi.bootcamps.domain.api;

import proyecto.nequi.bootcamps.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Casos de uso relacionados con productos.
 */
public interface ProductServicePort {

    /**
     * Crea un nuevo producto.
     * @param product datos del producto (incluye branchId)
     * @return Mono con el producto creado
     */
    Mono<Product> create(Product product);

    /**
     * Actualiza el stock de un producto.
     * @param productId identificador del producto
     * @param newStock nuevo valor de stock
     * @return Mono con el producto actualizado
     */
    Mono<Product> updateStock(Long productId, int newStock);

    /**
     * Elimina un producto por su ID.
     * @param productId identificador del producto
     * @return Mono vacío al completar
     */
    Mono<Void> deleteById(Long productId);

    /**
     * Consulta un producto por su ID.
     * @param productId identificador del producto
     * @return Mono con el producto
     */
    Mono<Product> findById(Long productId);

    /**
     * Lista todos los productos de una sucursal.
     * @param branchId identificador de la sucursal
     * @return Flux con los productos encontrados
     */
    Flux<Product> findAllByBranchId(Long branchId);
}
