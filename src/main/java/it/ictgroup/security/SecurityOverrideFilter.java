package it.ictgroup.security;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;

@Provider
public class SecurityOverrideFilter implements ContainerRequestFilter {


    @Context
    ResourceInfo resourceInfo;

    @Context
    SecurityContext security;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Principal userp = security.getUserPrincipal();
        userp.getName();
        Method methodInvoked = resourceInfo.getResourceMethod();
        String methodCalled = methodInvoked.toGenericString();
        Class<?> resourceClass = resourceInfo.getResourceClass();
    }
}