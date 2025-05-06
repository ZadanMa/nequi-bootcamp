package proyecto.nequi.bootcamps.applications;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import proyecto.nequi.bootcamps.domain.api.BranchServicePort;
import proyecto.nequi.bootcamps.domain.api.FranchiseServicePort;
import proyecto.nequi.bootcamps.domain.api.ProductServicePort;
import proyecto.nequi.bootcamps.domain.spi.BranchPersistencePort;
import proyecto.nequi.bootcamps.domain.spi.FranchisePersistencePort;
import proyecto.nequi.bootcamps.domain.spi.ProductPersistencePort;
import proyecto.nequi.bootcamps.domain.usecase.BranchUseCase;
import proyecto.nequi.bootcamps.domain.usecase.FranchiseUseCase;
import proyecto.nequi.bootcamps.domain.usecase.ProductUseCase;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

    @Bean
    public FranchiseServicePort franchiseServicePort(FranchisePersistencePort franchisePersistencePort) {
        return new FranchiseUseCase(franchisePersistencePort);
    }

    @Bean
    public BranchServicePort branchServicePort(BranchPersistencePort branchPersistencePort) {
        return new BranchUseCase(branchPersistencePort);
    }

    @Bean
    public ProductServicePort productServicePort(ProductPersistencePort productPersistencePort) {
        return new ProductUseCase(productPersistencePort);
    }
}