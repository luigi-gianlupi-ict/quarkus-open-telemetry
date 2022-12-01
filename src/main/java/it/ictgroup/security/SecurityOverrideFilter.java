package it.ictgroup.security;

import io.quarkus.keycloak.pep.runtime.VertxHttpFacade;
import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.core.http.HttpServerRequest;
import it.ictgroup.asset.framework.auth.HeaderConstant;
import it.ictgroup.asset.framework.auth.entity.AuthAssetResult;
import it.ictgroup.asset.framework.auth.entity.AuthDecoratedResult;
import it.ictgroup.auth.AuthResult;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.keycloak.AuthorizationContext;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.TokenVerifier;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.authorization.KeycloakAdapterPolicyEnforcer;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.representation.ServerConfiguration;
import org.keycloak.authorization.client.resource.AuthorizationResource;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Provider
public class SecurityOverrideFilter implements ContainerRequestFilter, HeaderConstant {

    final Logger LOG = Logger.getLogger(getClass());

    @Context
    ResourceInfo resourceInfo;

    @Context
    SecurityContext securityContext;

    @Context
    HttpServletRequest httpServletRequest;

    @Inject
    io.vertx.core.http.HttpServerRequest httpServerRequest;

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    AuthzClient authzClient;

    @Inject
    SecurityIdentity identity;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authToken = authzClient.authorization().authorize().getToken();
        String authToken2 = jsonWebToken.getRawToken();
        try {
            AccessToken token = TokenVerifier.create(authToken2, AccessToken.class).getToken();

            Map<String, Object> decoded = new HashMap<>();
            decoded.put(AuthDecoratedResult.USERNAME, token.getPreferredUsername().trim());
            decoded.put(AuthDecoratedResult.EMAIL, token.getEmail());
//                decoded.put(AuthDecoratedResult.GROUPS, token.getOtherClaims().get(AuthDecoratedResult.EXTRAROLES));
            decoded.put(AuthDecoratedResult.EXTRAROLES, token.getOtherClaims().get(AuthDecoratedResult.EXTRAROLES));
            decoded.put(AuthAssetResult.PARAM_GROUP, decoded.get(GROUP));
            AuthDecoratedResult d = new AuthDecoratedResult(decoded);

            final AuthorizationContext keycloakSecurityContext = (AuthorizationContext) httpServletRequest.getAttribute(AuthorizationContext.class.getName());
            //final KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) httpServletRequest.getAttribute(KeycloakSecurityContext.class.getName());
            Principal userp = securityContext.getUserPrincipal();
            if (userp != null) userp.getName();
            Method methodInvoked = resourceInfo.getResourceMethod();
            String methodCalled = methodInvoked.toGenericString();
            Class<?> resourceClass = resourceInfo.getResourceClass();

            AuthorizationResource a = authzClient.authorization();
            Configuration b = authzClient.getConfiguration();
            ServerConfiguration c = authzClient.getServerConfiguration();

        } catch (VerificationException e) {
            throw new RuntimeException(e);
        }

       /* String authToken = keycloakSecurityContext.getTokenString();
        String authId = keycloakSecurityContext.getToken().getPreferredUsername().trim();
        String realm = keycloakSecurityContext.getRealm();
        KeycloakDeployment deployment = ((RefreshableKeycloakSecurityContext) keycloakSecurityContext).getDeployment();
        PublicKey publicKey = ((RefreshableKeycloakSecurityContext) keycloakSecurityContext).getDeployment().getPublicKeyLocator().getPublicKey(realm, deployment);
        AuthResult authResult = null;*/

   /*     if (resourceClass.isAnnotationPresent(PermitAll.class) || methodInvoked.isAnnotationPresent(PermitAll.class)) {
            authResult = authKeycloakTokenBean.decodeToken(authToken, publicKey).orElseGet(() -> new AuthResult(true));
            LOG.infof(String.format("%s - PermitAll: %s", methodCalled, requestContext.toString()));
        } else if (resourceClass.isAnnotationPresent(ApiFunction.class)) {
            Path path = resourceClass.getAnnotation(Path.class);
            LOG.infof(String.format("%s - ApiFunction path: %s", methodCalled, path.value()));
            String commission = request.getHeader(COMMISSION);
            authResult = authKeycloakTokenBean.isAuthorized(authId, authToken, publicKey, commission);
        } else {
            LOG.errorf(String.format("%s %s - ACCESS UNAUTHORIZED", requestContext.getHeaders().get(PARAM_AUTH_ID),
                    methodCalled));
            requestContext.abortWith(ACCESS_UNAUTHORIZED);
            return;
        }
        decorateAuth(requestContext, authResult);*/
    }

    private void decorateAuth(ContainerRequestContext requestContext, AuthResult authResult) {
       /* if (authResult instanceof AuthDecoratedResult) {
            AuthDecoratedResult decoratedResult = (AuthDecoratedResult) authResult;
            Optional.ofNullable(decoratedResult.getUsername())
                    .ifPresent(username -> requestContext.getHeaders().add(PARAM_AUTH_ID, username));
            Optional.ofNullable(decoratedResult.getGroup())
                    .ifPresent(group -> requestContext.getHeaders().add(GROUP, group));
            Optional.ofNullable(decoratedResult.getCommission())
                    .ifPresent(commissionCode -> requestContext.getHeaders().add(COMMISSION, commissionCode));
            Optional.ofNullable(decoratedResult.getCustomer())
                    .ifPresent(customer -> requestContext.getHeaders().add(CUSTOMER, customer));
            Optional.ofNullable(decoratedResult.getProfilingGroups())
                    .filter(l -> !l.isEmpty())
                    .map(l -> String.join(",",l))
                    .ifPresent(groups -> requestContext.getHeaders().add(PROFILING_GROUPS, groups));
            LOG.infof(String.format("Auth: %s - IsAuthorized: %s - Group: %s - Commission: %s - Customer: %s",
                    requestContext.getHeaders().get(PARAM_AUTH_ID), decoratedResult.isAuthorized(), decoratedResult.getGroup(),
                    decoratedResult.getCommission(), decoratedResult.getCustomer()));
        } else {
            LOG.infof(String.format("Auth: %s - IsAuthorized: %s - Group: null - Commission: null - Customer: null",
                    requestContext.getHeaders().get(PARAM_AUTH_ID), authResult.isAuthorized()));
        }
        if (!authResult.isAuthorized()) {
            requestContext.abortWith(ACCESS_UNAUTHORIZED);
        }*/
    }
}