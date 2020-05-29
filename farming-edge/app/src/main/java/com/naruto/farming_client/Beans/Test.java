package com.naruto.farming_client.Beans;

import android.graphics.RectF;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.naruto.farming_client.tflite.Classifier;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity(tableName = "Test")
public class Test implements Serializable {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;
    /*
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "time")
    private Time time;
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "confidence")
    private  Float confidence;
    @ColumnInfo(name = "location")
    private RectF location;
    @ColumnInfo(name = "timeInterval")
    private Long timeInterval;

    Test(Date date,Time time,String id,String title,Float confidence,RectF location,Long timeInterval){
        this.time=time;
        this.confidence=confidence;
        this.date=date;
        this.id=id;
        this.location=location;
        this.title=title;
        this.timeInterval=timeInterval;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(RectF location) {
        this.location = location;
    }

    public void setTimeInterval(Long timeInterval) {
        this.timeInterval = timeInterval;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public Float getConfidence() {
        return confidence;
    }

    public Long getTimeInterval() {
        return timeInterval;
    }

    public RectF getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Time getTime() {
        return time;
    }*/
}
