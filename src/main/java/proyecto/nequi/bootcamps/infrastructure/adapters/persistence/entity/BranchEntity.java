package proyecto.nequi.bootcamps.infrastructure.adapters.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("branch")
@Data
public class BranchEntity {
    @Id
    private Long id;
    private String name;
    private Long franchiseId;
    private List<Long> productIds;
}
