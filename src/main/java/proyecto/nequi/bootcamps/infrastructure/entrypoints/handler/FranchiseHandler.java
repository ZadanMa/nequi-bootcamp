package proyecto.nequi.bootcamps.infrastructure.entrypoints.handler;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import proyecto.nequi.bootcamps.domain.api.FranchiseServicePort;
import proyecto.nequi.bootcamps.domain.model.Franchise;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.dto.FranchiseDTO;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.mapper.FranchiseDTOMapper;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.util.APIResponse;
import proyecto.nequi.bootcamps.infrastructure.entrypoints.util.ErrorDTO;
import proyecto.nequi.bootcamps.domain.enums.TechnicalMessage;
import proyecto.nequi.bootcamps.domain.exceptions.BusinessException;
import proyecto.nequi.bootcamps.domain.exceptions.TechnicalException;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Tag(name = "Franquicias", description = "Endpoints para gestión de franquicias")
public class FranchiseHandler {

    private final FranchiseServicePort servicePort;
    private final FranchiseDTOMapper dtoMapper;

    public FranchiseHandler(FranchiseServicePort servicePort, FranchiseDTOMapper dtoMapper) {
        this.servicePort = servicePort;
        this.dtoMapper = dtoMapper;
    }

    public Mono<ServerResponse> registerFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseDTO.class)
                .flatMap(dto -> servicePort.create(dtoMapper.toDomain(dto))
                        .map(domain -> APIResponse.<FranchiseDTO>builder()
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

    public Mono<ServerResponse> findFranchiseById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return servicePort.findById(id)
                .map(domain -> APIResponse.<FranchiseDTO>builder()
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
    public Mono<ServerResponse> updateFranchise(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(FranchiseDTO.class)
                .flatMap(dto -> {
                    var domainIn = dtoMapper.toDomain(dto);
                    var domainWithId = new Franchise(
                            id,
                            domainIn.name(),
                            domainIn.branchIds()
                    );
                    return servicePort.update(domainWithId)
                            .map(updated -> APIResponse.<FranchiseDTO>builder()
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

    public Mono<ServerResponse> findAllFranchises(ServerRequest request) {
        return servicePort.findAll()
                .map(domain -> dtoMapper.toDTO(domain))
                .collectList()
                .map(list -> APIResponse.<List<FranchiseDTO>>builder()
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

    public Mono<ServerResponse> deleteFranchise(ServerRequest request) {
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
