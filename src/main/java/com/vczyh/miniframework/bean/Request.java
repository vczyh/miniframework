package com.vczyh.miniframework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Request {

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求路径
     */
    private String requestPath;

    public Request (String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Request request = (Request) o;
//
//        return new EqualsBuilder()
//                .append(requestMethod, request.requestMethod)
//                .append(requestPath, request.requestPath)
//                .isEquals();
//    }

//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(17, 37)
//                .append(requestMethod)
//                .append(requestPath)
//                .toHashCode();
//    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestMethod='" + requestMethod + '\'' +
                ", requestPath='" + requestPath + '\'' +
                '}';
    }

    //    @Override
//    public boolean equals(Object obj) {
//        Request request = (Request) obj;
//        System.out.println("1");
//        if (requestMethod.equals(request.getRequestMethod()) && requestPath.equals(request.getRequestPath())) {
//            System.out.println("2");
//            return true;
//        }
//        System.out.println("3");
//        return false;
//    }
}
