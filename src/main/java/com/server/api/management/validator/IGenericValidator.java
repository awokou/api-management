package com.server.api.management.validator;

public interface IGenericValidator<C, U> {

    /**
     *
     * @param updateRequest
     */
    void beforeUpdate(U updateRequest);

    /**
     *
     * @param createRequest
     */
    void beforeSave(C createRequest);
}
