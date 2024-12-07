package com.fiec.ProvaRec.models;

import jakarta.persistence.EntityManager;

public class RoupaRepositorioImpl extends GenericRepositorioImpl<Roupa, Long> {

    public RoupaRepositorioImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    Class<Roupa> getMyClass() {
        return Roupa.class;
    }
}
