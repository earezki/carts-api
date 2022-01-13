package com.earezki.carts.restapi;

import com.earezki.carts.application.*;
import com.earezki.carts.domain.CartCapacityExceedException;
import com.earezki.carts.toolkit.ConstraintViolatedException;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.NoCache;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/carts")
@ApplicationScoped
class CartsResource {

    private static final int INSUFFICIENT_STORAGE = 507;

    @Inject
    CartApplicationService cartApplicationService;

    @Inject
    CartQueryService cartQueryService;

    @Inject
    JsonWebToken jwt;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> createCart(@Valid CreateCartCommand request) {
        String tenantId = jwt.getClaim(Claims.azp);

        request.setTenantId(tenantId);

        return cartApplicationService.createCart(request)
                .map(id -> Response.created(URI.create("/carts/" + id)).build());
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> addItem(@PathParam("id") String cartId,
                                 @Valid AddCartItemCommand request) {
        String tenantId = jwt.getClaim(Claims.azp);

        request.setTenantId(tenantId);
        request.setCartId(cartId);

        return cartApplicationService.addCartItem(request)
                .map(id -> Response.noContent().build());
    }

    @GET
    @NoCache
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<CartData> getCart(String id) {
        String tenantId = jwt.getClaim(Claims.azp);
        return cartQueryService.cartOfId(tenantId, id);
    }

    @ServerExceptionMapper
    public RestResponse<String> noSuchElementException(NoSuchElementException e) {
        return RestResponse.status(
                NOT_FOUND,
                e.getMessage()
        );
    }

    @ServerExceptionMapper
    public RestResponse<List<String>> constraintViolatedException(ConstraintViolatedException e) {
        return RestResponse.status(
                BAD_REQUEST,
                e.getErrors()
        );
    }

    @ServerExceptionMapper
    public RestResponse<List<String>> constraintViolatedException(ConstraintViolationException e) {
        return RestResponse.status(
                BAD_REQUEST,
                e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .toList()
        );
    }

    @ServerExceptionMapper
    public RestResponse<String> cartCapacityExceedException(CartCapacityExceedException e) {
        return RestResponse.status(
                INSUFFICIENT_STORAGE,
                e.getMessage()
        );
    }

}