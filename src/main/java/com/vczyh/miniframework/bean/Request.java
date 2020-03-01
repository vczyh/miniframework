package com.vczyh.miniframework.bean;

public class Request {

    private String requestPath;

    private String requestMethod;

    public Request() {
    }

    public Request(String requestPath, String requestMethod) {
        this.requestPath = requestPath;
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestPath='" + requestPath + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Request request = (Request) o;

        if (requestPath != null ? !requestPath.equals(request.requestPath) : request.requestPath != null) {
            return false;
        }
        return requestMethod != null ? requestMethod.equals(request.requestMethod) : request.requestMethod == null;
    }

    @Override
    public int hashCode() {
        int result = requestPath != null ? requestPath.hashCode() : 0;
        result = 31 * result + (requestMethod != null ? requestMethod.hashCode() : 0);
        return result;
    }
}
