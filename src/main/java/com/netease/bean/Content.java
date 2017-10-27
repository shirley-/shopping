package com.netease.bean;


/**
 * 内容类
 */
public class Content {
    private int id;
    private int price;//现在价格
    private String title;
    private String image;
    private String summary;
    private String detail;
    private int isSell;
    private int isBuy;
    private int buyPrice;//购买时价格
    private int buyNum;
    private int saleNum;
    private String buyTime;
    private int total;


    public Content() {
    }

    @Override
    public String toString() {
        return "Content{" + "id=" + id + ", price=" + price + ", prePrice=" + ", title='" + title + '\'' + ", image='" +image + '\'' + ", summary='" + summary + '\'' + ", detail="  + ", isSell=" + isSell + ", isBuy=" + isBuy + ", buyPrice=" + buyPrice + ", buyNum=" + buyNum + ", saleNum=" + saleNum + ", buyTime='" + buyTime + '\'' + ", total=" + total + '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getIsSell() {
        return isSell;
    }

    public void setIsSell(int isSell) {
        this.isSell = isSell;
    }

    public int getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(int isBuy) {
        this.isBuy = isBuy;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
