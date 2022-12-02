package it.ictgroup.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
/*import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;*/

//@WebFilter(urlPatterns = "/*")
public class CustomWebFilter /*extends HttpFilter*/ {

  /*  private static final long serialVersionUID = 1L;
    HttpServletRequest httpServletRequest;


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;

       // KeycloakSecurityContext keycloakSecurityContext = ((KeycloakPrincipal) httpServletRequest.getUserPrincipal()).getKeycloakSecurityContext();

        final KeycloakSecurityContext keycloakSecurityContext2 = (KeycloakSecurityContext) httpServletRequest.getAttribute(KeycloakSecurityContext.class.getName());

        final Principal userPrincipal = httpServletRequest.getUserPrincipal();

        if (userPrincipal instanceof KeycloakPrincipal) {

            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) userPrincipal;
            IDToken token = kp.getKeycloakSecurityContext().getIdToken();

            Map<String, Object> otherClaims = token.getOtherClaims();

            if (otherClaims.containsKey("YOUR_CLAIM_KEY")) {
                String yourClaim = String.valueOf(otherClaims.get("YOUR_CLAIM_KEY"));
            }
        } else {
        }
        chain.doFilter(httpServletRequest, httpServletResponse);
    }*/

}