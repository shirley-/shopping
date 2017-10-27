package com.netease.bean;

/**
 * 上传图片结果类
 */
public class UploadReturnResult {
    private int code;
    private String message;
    private String result;

    @Override
    public String toString() {
        return "UploadReturnResult{" + "code=" + code + ", message='" + message + '\'' + ", result='" + result + '\'' + '}';
    }

    public UploadReturnResult() {
        this.code=300;
        this.message="error";
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
