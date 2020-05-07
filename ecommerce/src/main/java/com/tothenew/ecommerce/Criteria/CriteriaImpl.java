package com.tothenew.ecommerce.Criteria;

import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
@Repository
public class CriteriaImpl implements Criteria{
    @PersistenceContext
    private EntityManager entityManager;

    public CriteriaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public User findById(Long id) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();

        CriteriaQuery<User> criteriaQuery=criteriaBuilder.createQuery(User.class);

        Root<User> userRoot=criteriaQuery.from(User.class);
        Predicate idPredicate = criteriaBuilder.equal(userRoot.get("id"), id);
        System.out.println("id ......."+id);
        criteriaQuery.where(idPredicate);

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        System.out.println(query.getSingleResult().getId());

        return query.getSingleResult();
//        return Optional.empty();
    }

    @Override
    public Customer findByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        Predicate predicate = builder.equal(root.get("email"), email);
        //logger.info(String.valueOf(query.where(predicate)));

        query.where(predicate);

        TypedQuery<Customer> query1 = entityManager.createQuery(query);
        //List<Customer> customers = entityManager.createQuery(query).getResultList();
        System.out.println("//////////////////////////////////" + query1.getSingleResult());
        //logger.info(String.valueOf(query.where(predicate)));

        return query1.getSingleResult();

        // return query.;

        //return query.select(root.get("email"));


    }
}
