/*
 *
 */

package me.melvins.labs.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mels
 */
public class ResponseVO {

    @JsonProperty(value = "Item", index = 1)
    private String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

}
