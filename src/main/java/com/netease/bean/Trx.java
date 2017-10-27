package com.netease.bean;

/**
 * 商品购买交易类
 */
public class Trx {
    private int id;
    private Content content;//购买商品
    private Person person;//买家
    private int price;
    private String time;
    private int num;

    public Trx() {
    }

    @Override
    public String toString() {
        return "Trx{" + "id=" + id + ", content=" + content + ", person=" + person + ", price=" + price + ", time='" + time + '\'' + ", num=" + num + '}';
    }

    public Trx(Content content, Person person, int price, String time, int num) {
        this.content = content;
        this.person = person;
        this.price = price;
        this.time = time;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
