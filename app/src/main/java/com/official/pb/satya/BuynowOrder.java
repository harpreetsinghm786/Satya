package com.official.pb.satya;

import java.util.Date;
import java.util.List;

public class BuynowOrder {

    String itemname,normalprice,grandtotal,quantity,username,givenphonenumber,extraphonenumber,orderid,itemmainurl,userpicurl,address,deliverymode,paymentmode,orderstatus;
    Date orderdate;
    int deliverycharges,servicetax;
    List<Addons> addons;

    public BuynowOrder(){

    }


    public BuynowOrder(String itemname, String normalprice, String grandtotal, String quantity, String username, String givenphonenumber, String extraphonenumber, int deliverycharges, String s, int servicetax, String orderid, String itemmainurl, String userpicurl, String address, String deliverymode, List<Addons> addons, String paymentmode, String orderstatus, Date orderdate) {
        this.itemname = itemname;
        this.normalprice = normalprice;
        this.grandtotal = grandtotal;
        this.quantity = quantity;
        this.username = username;
        this.orderdate=orderdate;
        this.orderstatus=orderstatus;
        this.givenphonenumber = givenphonenumber;
        this.extraphonenumber = extraphonenumber;
        this.deliverycharges = deliverycharges;
        this.servicetax = servicetax;
        this.orderid = orderid;
        this.itemmainurl = itemmainurl;
        this.userpicurl = userpicurl;
        this.address = address;
        this.deliverymode = deliverymode;
        this.addons = addons;
        this.paymentmode=paymentmode;
    }



    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getNormalprice() {
        return normalprice;
    }

    public void setNormalprice(String normalprice) {
        this.normalprice = normalprice;
    }

    public String getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(String grandtotal) {
        this.grandtotal = grandtotal;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGivenphonenumber() {
        return givenphonenumber;
    }

    public void setGivenphonenumber(String givenphonenumber) {
        this.givenphonenumber = givenphonenumber;
    }

    public String getExtraphonenumber() {
        return extraphonenumber;
    }

    public void setExtraphonenumber(String extraphonenumber) {
        this.extraphonenumber = extraphonenumber;
    }

    public int getDeliverycharges() {
        return deliverycharges;
    }

    public void setDeliverycharges(int deliverycharges) {
        this.deliverycharges = deliverycharges;
    }

    public int getServicetax() {
        return servicetax;
    }

    public void setServicetax(int servicetax) {
        this.servicetax = servicetax;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getitemmainurl() {
        return itemmainurl;
    }

    public void setItemmainurl(String itemmainurl) {
        this.itemmainurl = itemmainurl;
    }

    public String getUserpicurl() {
        return userpicurl;
    }

    public void setUserpicurl(String userpicurl) {
        this.userpicurl = userpicurl;
    }

    public List<Addons> getAddons() {
        return addons;
    }

    public void setAddons(List<Addons>addons) {
        this.addons = addons;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliverymode() {
        return deliverymode;
    }

    public void setDeliverymode(String deliverymode) {
        this.deliverymode = deliverymode;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }


}
