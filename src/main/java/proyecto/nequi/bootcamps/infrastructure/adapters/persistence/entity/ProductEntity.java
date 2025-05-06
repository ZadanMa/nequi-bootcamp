package proyecto.nequi.bootcamps.infrastructure.adapters.persistence.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
@Data
public class ProductEntity {
    @Id
    private Long id;
    private String name;
    private int stock;
    private Long branchId;
}
