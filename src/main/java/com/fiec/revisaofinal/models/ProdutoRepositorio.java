package com.fiec.revisaofinal.models;

import jakarta.persistence.EntityManager;

public class ProdutoRepositorio extends GenericRepositorioImpl<Produto, String> {

    public ProdutoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    Class<Produto> getMyClass() {
        return Produto.class;
    }
}
