package com.custom.hermes.mode;

/**
 * Created by: Ysw on 2020/2/5.
 */
public class RequestBean {
    private String className;
    private String resultClassName;
    private String requestObject;
    private String methodName;
    private RequestParameter[] requestParameters;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getResultClassName() {
        return resultClassName;
    }

    public void setResultClassName(String resultClassName) {
        this.resultClassName = resultClassName;
    }

    public String getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(String requestObject) {
        this.requestObject = requestObject;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public RequestParameter[] getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(RequestParameter[] requestParameters) {
        this.requestParameters = requestParameters;
    }
}
