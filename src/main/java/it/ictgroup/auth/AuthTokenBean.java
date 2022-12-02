package it.ictgroup.auth;


import it.ictgroup.asset.framework.auth.entity.AuthAssetResult;
import it.ictgroup.asset.framework.auth.entity.AuthDecoratedResult;
import it.ictgroup.asset.framework.auth.entity.AuthResult;
import org.keycloak.TokenVerifier;
import org.keycloak.representations.AccessToken;
import it.ictgroup.asset.framework.util.UtilsConstants;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class AuthTokenBean {
    public static final String GROUP = "group";

    Logger LOG = Logger.getLogger(getClass().getName());

    public AuthResult isAuthorized(String authId, String authToken) {
        try {
            Optional<AuthResult> decoded = decodeToken(authToken);
            if (decoded.isPresent()) {
                return _isAuthorized(decoded.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.log(Level.SEVERE, e.getMessage());
        }
        return new AuthResult(false);
    }

    private AuthResult _isAuthorized(AuthResult decoded) {
        AuthDecoratedResult auth = (AuthDecoratedResult) decoded;
        String groupPrefix = System.getProperty(UtilsConstants.GROUP_PREFIX_KEY, UtilsConstants.GROUP_PREFIX_DEFAULT);
        // se non sto passando una commessa o non ho gruppi di profilazione => autorizzo
        if (auth.getProfilingGroups() == null || auth.getProfilingGroups().isEmpty()) {
            return auth;
        }
        // se sono SuperAdmin, passo
        if(auth.getProfilingGroups().stream().anyMatch(g -> (groupPrefix + "SuperAdmin").equals(g))) {
            return auth;
        }
        return auth;
    }


    public Optional<AuthResult> decodeToken(String authToken) {
        if (authToken != null && !authToken.trim().isEmpty()) {
            try {
                AccessToken token = TokenVerifier.create(authToken, AccessToken.class).getToken();
                Map<String, Object> decoded = new HashMap<>();
                decoded.put(AuthDecoratedResult.USERNAME, token.getPreferredUsername().trim());
                decoded.put(AuthDecoratedResult.EMAIL, token.getEmail());
                decoded.put(AuthDecoratedResult.EXTRAROLES, token.getOtherClaims().get(AuthDecoratedResult.EXTRAROLES));
                decoded.put(AuthAssetResult.PARAM_GROUP, decoded.get(GROUP));
                return Optional.of(new AuthDecoratedResult(decoded));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
}

