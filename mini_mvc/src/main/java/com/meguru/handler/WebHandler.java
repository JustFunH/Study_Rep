package com.meguru.handler;

import com.meguru.annotation.ResponseBody;
import com.meguru.model.ModelAndView;

import java.lang.reflect.Method;

public class WebHandler {
    private final Object controllerBean;

    private final Method method;

    private final ResultType resultType;

    public WebHandler(Object controllerBean, Method method) {
        this.controllerBean = controllerBean;
        this.method = method;
        this.resultType = resultType(method);
    }

    private ResultType resultType(Method method) {
        if (method.isAnnotationPresent(ResponseBody.class))
            return ResultType.JSON;
        if (method.getReturnType() == ModelAndView.class)
            return ResultType.LOCAL;
        return ResultType.HTML;
    }

    public Object getControllerBean() {
        return controllerBean;
    }

    public Method getMethod() {
        return method;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public enum ResultType {
        JSON, HTML, LOCAL
    }
}
