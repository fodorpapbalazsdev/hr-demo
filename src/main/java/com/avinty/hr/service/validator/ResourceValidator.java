package com.avinty.hr.service.validator;

import com.avinty.hr.exception.ResourceException;

public interface ResourceValidator<T> {
    boolean validate(T t) throws ResourceException;
}
