package com.mmall.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.exception.ParamException;
import org.apache.commons.collections4.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * BeanValidator
 *
 * @author Arsenal
 * created on 2019/7/9 2:37
 */
public class BeanValidator {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private static <T> Map<String, String> validate(T t, Class<?>... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validateResult = validator.validate(t, groups);
        if (validateResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap<String, String> errors = Maps.newLinkedHashMap();
            for (ConstraintViolation<T> violation : validateResult) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
    }

    private static Map<String, String> validateList(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        Map<String, String> errors;
        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            errors = validate(object);
        } while (errors.isEmpty());

        return errors;
    }

    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validateList(Lists.asList(first, objects));
        } else {
            return validate(first);
        }
    }

    public static void check(Object param) throws ParamException {
        Map<String, String> map = BeanValidator.validateObject(param);
        if (MapUtils.isNotEmpty(map)) {
            throw new ParamException(map.toString());
        }
    }
}
