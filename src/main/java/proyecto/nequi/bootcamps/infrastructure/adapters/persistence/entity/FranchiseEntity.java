package proyecto.nequi.bootcamps.infrastructure.adapters.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table("franchise")
public class FranchiseEntity {
    @Id
    private Long id;
    private String name;
    private List<Long> branchIds;
}
