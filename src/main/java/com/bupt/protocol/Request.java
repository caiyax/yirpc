package com.bupt.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class Request implements Serializable {
    private static final long serialVersionUID = 7259204908595536308L;

    private Long id; //请求id,标识一次请求
    private String className;
    private String methodName;
    private Class<?>[] parametersTypes;
    private Object[] parameters;
}
