package com.netease.bean;

/**
 * post提交结果类1
 */
public class ReturnResult {
    private int code;//200 success
    private String message;
    private boolean result;

    @Override
    public String toString() {
        return "ReturnResult{" + "code=" + code + ", message='" + message + '\'' + ", result=" + result + '}';
    }

    public ReturnResult() {
        this.code = 300;
        this.message="error";
        this.result=false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
