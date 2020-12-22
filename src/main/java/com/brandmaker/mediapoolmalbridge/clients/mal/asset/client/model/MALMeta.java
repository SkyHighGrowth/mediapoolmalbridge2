package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for fetching asset metadata from api/syncs
 */
public class MALMeta {
    @SerializedName("page")
    public Integer page;
    @SerializedName("limit")
    public Integer limit;
    @SerializedName("orderby")
    public String orderby;
    @SerializedName("direction")
    public String direction;
    @SerializedName("total")
    public Integer total;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

