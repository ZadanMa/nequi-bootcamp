package proyecto.nequi.bootcamps.infrastructure.entrypoints.handler;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import proyecto.nequi.bootcamps.domain.api.BranchServicePort;
import proyecto.nequi.bootcamps.domain.model.Branch;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.dto.BranchDTO;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.mapper.BranchDTOMapper;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.util.APIResponse;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.util.ErrorDTO;
import proyecto.nequi.bootcamps.domain.enums.TechnicalMessage;
import proyecto.nequi.bootcamps.domain.exceptions.BusinessException;
import proyecto.nequi.bootcamps.domain.exceptions.TechnicalException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Tag(name = "Sucursales", description = "Endpoints para gestión de sucursales")
public class BranchHandler {

    private final BranchServicePort servicePort;
    private final BranchDTOMapper dtoMapper;

    public BranchHandler(BranchServicePort servicePort, BranchDTOMapper dtoMapper) {
        this.servicePort = servicePort;
        this.dtoMapper = dtoMapper;
    }

    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(BranchDTO.class)
                .flatMap(dto -> servicePort.create(dtoMapper.toDomain(dto))
                        .map(domain -> APIResponse.<BranchDTO>builder()
                                .code(TechnicalMessage.CREATED.getCode())
                                .message(TechnicalMessage.CREATED.getMessage())
                                .data(dtoMapper.toDTO(domain))
                                .build()
                        )
                )
                .flatMap(resp -> ServerResponse.status(HttpStatus.CREATED).bodyValue(resp))
                .onErrorResume(BusinessException.class, ex -> ServerResponse.badRequest().bodyValue(
                        APIResponse.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .errors(List.of(ErrorDTO.builder()
                                        .code(ex.getTechnicalMessage().getCode())
                                        .message(ex.getTechnicalMessage().getMessage())
                                        .param(null)
                                        .build()))
                                .build()
                ))
                .onErrorResume(TechnicalException.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(
                        APIResponse.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .build()
                ));
    }

    public Mono<ServerResponse> updateBranch(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(BranchDTO.class)
                .flatMap(dto -> {
                    var domainIn = dtoMapper.toDomain(dto);
                    var domainWithId = new Branch(
                            id,
                            domainIn.name(),
                            domainIn.franchiseId(),
                            domainIn.productIds()
                    );
                    return servicePort.update(domainWithId)
                            .map(updated -> APIResponse.<BranchDTO>builder()
                                    .code(TechnicalMessage.UPDATED.getCode())
                                    .message(TechnicalMessage.UPDATED.getMessage())
                                    .data(dtoMapper.toDTO(updated))
                                    .build()
                            );
                })
                .flatMap(resp -> ServerResponse.ok().bodyValue(resp))
                .onErrorResume(BusinessException.class, ex -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(
                        APIResponse.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .build()
                ))
                .onErrorResume(TechnicalException.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(
                        APIResponse.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .build()
                ));
    }

    public Mono<ServerResponse> listBranches(ServerRequest request) {
        Long franchiseId = Long.valueOf(request.pathVariable("franchiseId"));
        return servicePort.findAllByFranchiseId(franchiseId)
                .map(dtoMapper::toDTO)
                .collectList()
                .map(list -> APIResponse.<List<BranchDTO>>builder()
                        .code(TechnicalMessage.LIST_EMPTY.getCode())
                        .message(TechnicalMessage.LIST_EMPTY.getMessage())
                        .data(list)
                        .build()
                )
                .flatMap(resp -> ServerResponse.ok().bodyValue(resp))
                .onErrorResume(TechnicalException.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(
                        APIResponse.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .build()
                ));
    }

    public Mono<ServerResponse> findBranchById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return servicePort.findById(id)
                .map(domain -> APIResponse.<BranchDTO>builder()
                        .code(TechnicalMessage.FOUND.getCode())
                        .message(TechnicalMessage.FOUND.getMessage())
                        .data(dtoMapper.toDTO(domain))
                        .build()
                )
                .flatMap(resp -> ServerResponse.ok().bodyValue(resp))
                .onErrorResume(BusinessException.class, ex -> ServerResponse.status(HttpStatus.NOT_FOUND)
                        .bodyValue(APIResponse.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .build()
                        ))
                .onErrorResume(TechnicalException.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(APIResponse.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .build()
                        ));
    }
    public Mono<ServerResponse> deleteBranch(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return servicePort.deleteById(id)
                .thenReturn(APIResponse.<Void>builder()
                        .code(TechnicalMessage.DELETED.getCode())
                        .message(TechnicalMessage.DELETED.getMessage())
                        .build()
                )
                .flatMap(resp -> ServerResponse.ok().bodyValue(resp))
                .onErrorResume(BusinessException.class, ex -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(
                        APIResponse.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .build()
                ))
                .onErrorResume(TechnicalException.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(
                        APIResponse.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .build()
                ));
    }
}