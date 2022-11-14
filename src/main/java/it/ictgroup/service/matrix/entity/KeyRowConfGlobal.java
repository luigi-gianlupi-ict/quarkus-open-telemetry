package it.ictgroup.service.matrix.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author Riccardo Merolla
 *         Created on 23/10/14.
 */
@Embeddable
@SuppressWarnings("unused")
public class KeyRowConfGlobal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    public static final int MAX_LENGTH_KEY = 200;

    private String key;

    private DomainsValue domain;

    @Column(name = "matrix_key", length = MAX_LENGTH_KEY, nullable = false)
    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    @Embedded
    public DomainsValue getDomain()
    {
        return domain;
    }

    public void setDomain(DomainsValue domain)
    {
        this.domain = domain;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((domain == null) ? 0 : domain.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyRowConfGlobal other = (KeyRowConfGlobal) obj;
        if (key == null)
        {
            if (other.key != null)
                return false;
        }
        else if ( !key.equals(other.key))
            return false;
        if (domain == null)
        {
            return other.domain == null;
        }
        else return domain.equals(other.domain);
    }
}
