package com.fiec.ProvaRec.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public abstract class GenericRepositorioImpl<T, ID> {

    protected EntityManager entityManager;

    public GenericRepositorioImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    abstract Class<T> getMyClass();

    public T criar(T t) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(t);
        transaction.commit();
        return t;
    }

    public List<T> ler() {
        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM " + getMyClass().getName() + " t", getMyClass());
        return query.getResultList();
    }

    public T lerPorId(ID id) {
        return entityManager.find(getMyClass(), id);
    }

    public void atualiza(T t, ID id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        T existingEntity = entityManager.find(getMyClass(), id);
        if (existingEntity != null) {
            entityManager.merge(t);
        }
        transaction.commit();
    }

    public void deleta(ID id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        T existingEntity = entityManager.find(getMyClass(), id);
        if (existingEntity != null) {
            entityManager.remove(existingEntity);
        }
        transaction.commit();
    }
}
