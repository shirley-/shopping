package com.netease.bean;

/**
 * 上传图片类
 */
public class Image {
    private int id;//PK
    private String name;
    private byte[] bytes;//Blob

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
