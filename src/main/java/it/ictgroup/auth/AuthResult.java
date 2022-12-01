package it.ictgroup.auth;

public class AuthResult {

    // TODO MOVE

    private boolean authorized = false;

    public AuthResult(boolean var1) {
        this.authorized = var1;
    }

    public boolean isAuthorized() {
        return this.authorized;
    }

    public void setAuthorized(boolean var1) {
        this.authorized = var1;
    }
}
