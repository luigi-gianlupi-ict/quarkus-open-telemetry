package it.ictgroup.service.matrix.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@SuppressWarnings("unused")
public class DomainsValue implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final int MAX_LENGHT_DOMAIN = 30;

    public static final String WILDCARD_DOMAIN = "*";

    private String domain1;

    private String domain2;

    private String domain3;

    private String domain4;

    private String domain5;

    @Column(length = MAX_LENGHT_DOMAIN, nullable = false)
    public String getDomain1()
    {
        return domain1;
    }

    public void setDomain1(String domain1)
    {
        this.domain1 = domain1;
    }

    @Column(length = MAX_LENGHT_DOMAIN, nullable = false)
    public String getDomain2()
    {
        return domain2;
    }

    public void setDomain2(String domain2)
    {
        this.domain2 = domain2;
    }

    @Column(length = MAX_LENGHT_DOMAIN, nullable = false)
    public String getDomain3()
    {
        return domain3;
    }

    public void setDomain3(String domain3)
    {
        this.domain3 = domain3;
    }

    @Column(length = MAX_LENGHT_DOMAIN, nullable = false)
    public String getDomain4()
    {
        return domain4;
    }

    public void setDomain4(String domain4)
    {
        this.domain4 = domain4;
    }

    @Column(length = MAX_LENGHT_DOMAIN, nullable = false)
    public String getDomain5()
    {
        return domain5;
    }

    public void setDomain5(String domain5)
    {
        this.domain5 = domain5;
    }

    public String[] asArray()
    {
        return new String[]
                {
                        domain1, domain2, domain3, domain4, domain5
                };
    }

    public void defaultDomains()
    {
        domain1 = "*";
        domain2 = "*";
        domain3 = "*";
        domain4 = "*";
        domain5 = "*";
    }

    public static DomainsValue fromArray(String[] domini)
    {
        DomainsValue vd = new DomainsValue();
        vd.defaultDomains();
        String[] dominiEffettivi = (domini == null ? new String[0] : domini);

        if ((dominiEffettivi.length >= 1)
                && !StringUtils.isEmpty(dominiEffettivi[0]))
            vd.setDomain1(dominiEffettivi[0]);

        if ((dominiEffettivi.length >= 2)
                && !StringUtils.isEmpty(dominiEffettivi[1]))
            vd.setDomain2(dominiEffettivi[1]);

        if ((dominiEffettivi.length >= 3)
                && !StringUtils.isEmpty(dominiEffettivi[2]))
            vd.setDomain3(dominiEffettivi[2]);

        if ((dominiEffettivi.length >= 4)
                && !StringUtils.isEmpty(dominiEffettivi[3]))
            vd.setDomain4(dominiEffettivi[3]);

        if ((dominiEffettivi.length >= 5)
                && !StringUtils.isEmpty(dominiEffettivi[4]))
            vd.setDomain5(dominiEffettivi[4]);

        return vd;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((domain1 == null) ? 0 : domain1.hashCode());
        result = prime * result + ((domain2 == null) ? 0 : domain2.hashCode());
        result = prime * result + ((domain3 == null) ? 0 : domain3.hashCode());
        result = prime * result + ((domain4 == null) ? 0 : domain4.hashCode());
        result = prime * result + ((domain5 == null) ? 0 : domain5.hashCode());
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
        DomainsValue other = (DomainsValue) obj;
        if (domain1 == null)
        {
            if (other.domain1 != null)
                return false;
        }
        else if ( !domain1.equals(other.domain1))
            return false;
        if (domain2 == null)
        {
            if (other.domain2 != null)
                return false;
        }
        else if ( !domain2.equals(other.domain2))
            return false;
        if (domain3 == null)
        {
            if (other.domain3 != null)
                return false;
        }
        else if ( !domain3.equals(other.domain3))
            return false;
        if (domain4 == null)
        {
            if (other.domain4 != null)
                return false;
        }
        else if ( !domain4.equals(other.domain4))
            return false;
        if (domain5 == null)
        {
            return other.domain5 == null;
        }
        else return domain5.equals(other.domain5);
    }
}
