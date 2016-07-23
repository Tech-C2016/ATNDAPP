package com.example.teacher.atndapp.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by teacher on 2016/07/23.
 */
public class EventDto {

    private int eventID;
    private String title;
    private String description;
    private String address;
    private String url;
    private int ownerID;
    private String ownerNickname;
    private String ownerTwitterID;
    private Date startAt;
    private Date updateAt;

    public String getStartAt() {
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy/mm/dd H:mm:s");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        return format.format(startAt);
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public String getUpdateAt() {
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy/mm/dd H:mm:s");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        return format.format(updateAt);
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
    }

    public String getOwnerTwitterID() {
        return ownerTwitterID;
    }

    public void setOwnerTwitterID(String ownerTwitterID) {
        this.ownerTwitterID = ownerTwitterID;
    }
}
