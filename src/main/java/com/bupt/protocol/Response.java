package com.bupt.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {
    private static final long serialVersionUID = -6782611945534057717L;
    private Long requestId;
    private Object result;
}
