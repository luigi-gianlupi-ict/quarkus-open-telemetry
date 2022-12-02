package it.ictgroup.security;

import io.quarkus.security.identity.SecurityIdentity;
import it.ictgroup.asset.framework.auth.ApiFunction;
import it.ictgroup.asset.framework.auth.entity.AuthDecoratedResult;
import it.ictgroup.asset.framework.auth.entity.AuthResult;
import it.ictgroup.asset.framework.auth.entity.AuthTokenBean;
import it.ictgroup.asset.framework.auth.entity.HeaderConstant;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.Optional;


@Provider
public class SecurityOverrideFilter implements ContainerRequestFilter, HeaderConstant {

    final Logger LOG = Logger.getLogger(getClass());

    @Context
    ResourceInfo resourceInfo;

    @Context
    SecurityContext securityContext;

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    SecurityIdentity identity;

    @Inject
    private AuthTokenBean authTokenBean;


    @Override
    public void filter(ContainerRequestContext requestContext) {

        /* String authToken = authzClient.authorization().authorize().getToken();*/
        String authToken = jsonWebToken.getRawToken();
        String authId = identity.getPrincipal().getName();
        Method methodInvoked = resourceInfo.getResourceMethod();
        String methodCalled = methodInvoked.toGenericString();
        Class<?> resourceClass = resourceInfo.getResourceClass();
        String commission = requestContext.getHeaderString(COMMISSION);
        Path path = resourceClass.getAnnotation(Path.class);
        AuthResult authResult = authTokenBean.decodeToken(authToken).orElseGet(() -> new AuthResult(false));
        if (resourceClass.isAnnotationPresent(PermitAll.class) || methodInvoked.isAnnotationPresent(PermitAll.class)) {
            LOG.infof("%s - PermitAll: path: %s - commission %s", methodCalled, path.value(), commission);
        } else if (authResult.isAuthorized()) {
            // se è già autenticato posso fare qualcosa in più con l'annotazione ApiFunction
            if (resourceClass.isAnnotationPresent(ApiFunction.class)) {
                LOG.infof("%s - ApiFunction path: %s - commission %s", methodCalled, path.value(), commission);
                authResult = authTokenBean.isAuthorized(authId, authToken);
                if (!authResult.isAuthorized())
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } else {
            // se non è già autenticato forzo fuori
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
        decorateAuth(requestContext, authResult);
    }

    private void decorateAuth(ContainerRequestContext requestContext, AuthResult authResult) {
        if (authResult instanceof AuthDecoratedResult decoratedResult) {
            Optional.ofNullable(decoratedResult.getUsername())
                    .ifPresent(username -> requestContext.getHeaders().add(PARAM_AUTH_ID, username));
            Optional.ofNullable(decoratedResult.getGroup())
                    .ifPresent(group -> requestContext.getHeaders().add(GROUP, group));
            Optional.ofNullable(decoratedResult.getCustomer())
                    .ifPresent(customer -> requestContext.getHeaders().add(CUSTOMER, customer));
            Optional.ofNullable(decoratedResult.getProfilingGroups())
                    .filter(l -> !l.isEmpty())
                    .map(l -> String.join(",", l))
                    .ifPresent(groups -> requestContext.getHeaders().add(PROFILING_GROUPS, groups));
            LOG.infof("Auth: %s - IsAuthorized: %s - Group: %s - Customer: %s",
                    requestContext.getHeaders().get(PARAM_AUTH_ID), decoratedResult.isAuthorized(), decoratedResult.getGroup(), decoratedResult.getCustomer());
        } else {
            LOG.infof("Auth: %s - IsAuthorized: %s - Group: null - Customer: null",
                    requestContext.getHeaders().get(PARAM_AUTH_ID), authResult.isAuthorized());
        }
    }
}