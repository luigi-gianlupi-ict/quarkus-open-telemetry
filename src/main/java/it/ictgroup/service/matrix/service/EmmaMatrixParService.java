package it.ictgroup.service.matrix.service;

import it.ictgroup.auth.PaginatedListWrapper;
import it.ictgroup.service.matrix.entity.DomainsValue;
import it.ictgroup.service.matrix.entity.KeyRowConfGlobal;
import it.ictgroup.service.matrix.entity.MatrixParAppl;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@RequestScoped
@SuppressWarnings("unused,unchecked")
public class EmmaMatrixParService {

    @Inject
    EntityManager entityManager;

    final org.jboss.logging.Logger LOG = Logger.getLogger(getClass());

    public PaginatedListWrapper<MatrixParAppl> list(Integer page,
                                                    String sortFields,
                                                    String sortDirections) {
        PaginatedListWrapper<MatrixParAppl> paginatedListWrapper = new PaginatedListWrapper<>();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(50);
        return find(paginatedListWrapper);
    }

    private PaginatedListWrapper<MatrixParAppl> find(PaginatedListWrapper<MatrixParAppl> wrapper) {
        wrapper.setTotalResults(count());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(find(start,
                wrapper.getPageSize(),
                wrapper.getSortFields(),
                wrapper.getSortDirections()));
        return wrapper;
    }

    private Integer count() {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM MatrixParAppl m");
        return ((Long) query.getSingleResult()).intValue();
    }

    private List<MatrixParAppl> find(int start, Integer pageSize, String sortFields, String sortDirections) {
        Query query = entityManager.createQuery("SELECT m FROM MatrixParAppl m ORDER BY " + sortFields + " " + sortDirections);
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Optional<MatrixParAppl> getValue(String key, String d1, String d2,
                             String d3, String d4, String d5) {
        KeyRowConfGlobal id = getId(key, d1, d2, d3, d4, d5);
        MatrixParAppl matrixParAppl = getMatrixParAppl(id);
        return Optional.ofNullable(matrixParAppl);
    }

    public Optional<MatrixParAppl> getValue(String key) {
        return getValue(key, "*", "*", "*", "*", "*");
    }

    private KeyRowConfGlobal getId(String key, String d1, String d2, String d3, String d4, String d5) {
        KeyRowConfGlobal id = new KeyRowConfGlobal();
        DomainsValue domainsValue = new DomainsValue();
        domainsValue.setDomain1(d1);
        domainsValue.setDomain2(d2);
        domainsValue.setDomain3(d3);
        domainsValue.setDomain4(d4);
        domainsValue.setDomain5(d5);
        id.setKey(key);
        id.setDomain(domainsValue);
        return id;
    }

    private MatrixParAppl getMatrixParAppl(KeyRowConfGlobal id) {
        return entityManager.find(MatrixParAppl.class, id);
    }
}
