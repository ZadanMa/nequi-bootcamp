package proyecto.nequi.bootcamps.infrastructure.entrypoints.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.handler.FranchiseHandler;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.handler.BranchHandler;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.handler.ProductHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

@Configuration
public class ApiRouter {

    @Bean
    public RouterFunction<?> apiRoutes(
            FranchiseHandler franchiseHandler,
            BranchHandler branchHandler,
            ProductHandler productHandler
    ) {
        return nest(path("/api"),
                nest(path("/franchise"),
                        route(POST(""), franchiseHandler::registerFranchise)
                                .andRoute(GET(""), franchiseHandler::findAllFranchises)
                                .andRoute(GET("/{id}"), franchiseHandler::findFranchiseById)
                                .andRoute(PUT("/{id}"), franchiseHandler::updateFranchise)
                                .andRoute(DELETE("/{id}"), franchiseHandler::deleteFranchise)
                )
                        .andNest(path("/branch"),
                                route(POST(""), branchHandler::createBranch)
                                        .andRoute(GET("/franchise/{franchiseId}"), branchHandler::listBranches)
                                        .andRoute(GET("/{id}"), branchHandler::findBranchById)
                                        .andRoute(PUT("/{id}"), branchHandler::updateBranch)
                                        .andRoute(DELETE("/{id}"), branchHandler::deleteBranch)
                        )
                        .andNest(path("/product"),
                                route(POST(""), productHandler::createProduct)
                                        .andRoute(GET("/branch/{branchId}"), productHandler::listProducts)
                                        .andRoute(GET("/{id}"), productHandler::findProductById)
                                        .andRoute(PUT("/{id}"), productHandler::updateProductStock)
                                        .andRoute(DELETE("/{id}"), productHandler::deleteProduct)
                        )
        );
    }
}
