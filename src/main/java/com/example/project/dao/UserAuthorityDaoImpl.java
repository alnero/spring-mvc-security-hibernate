package com.example.project.dao;

import com.example.project.model.UserAuthority;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserAuthorityDaoImpl implements UserAuthorityDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAuthority> listUserAuthorities() {
        TypedQuery<UserAuthority> query = entityManager.createQuery("from UserAuthority", UserAuthority.class);
        return query.getResultList();
    }

    @Override
    public UserAuthority getById(long id) {
        return entityManager.find(UserAuthority.class, id);
    }

    @Override
    public UserAuthority getUserAuthorityByName(String authority) {
        UserAuthority userAuthority = entityManager.createQuery("SELECT uA from UserAuthority uA WHERE uA.authority = :authority", UserAuthority.class).
                setParameter("authority", authority).getSingleResult();
        return userAuthority;
    }

    @Override
    public void add(UserAuthority userAuthority) {
        entityManager.persist(userAuthority);
    }

    @Override
    public void edit(UserAuthority userAuthority) {
        entityManager.merge(userAuthority);
    }

    @Override
    public void delete(UserAuthority userAuthority) {
        entityManager.remove(entityManager.contains(userAuthority) ? userAuthority : entityManager.merge(userAuthority));
    }
}
