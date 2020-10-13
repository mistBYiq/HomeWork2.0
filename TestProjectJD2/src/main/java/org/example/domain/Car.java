package org.example.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class Car {

    private Long id;
    private String model;
    private Integer creation_year;
    private Long user_iq;
    private Double price;
    private String color;

    public Car() {
    }

    public Car(Long id, String model, Integer creation_year, Long user_iq, Double price, String color) {
        this.id = id;
        this.model = model;
        this.creation_year = creation_year;
        this.user_iq = user_iq;
        this.price = price;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getCreation_year() {
        return creation_year;
    }

    public void setCreation_year(Integer creation_year) {
        this.creation_year = creation_year;
    }

    public Long getUser_iq() {
        return user_iq;
    }

    public void setUser_iq(Long user_iq) {
        this.user_iq = user_iq;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(getId(), car.getId()) &&
                Objects.equals(getModel(), car.getModel()) &&
                Objects.equals(getCreation_year(), car.getCreation_year()) &&
                Objects.equals(getUser_iq(), car.getUser_iq()) &&
                Objects.equals(getPrice(), car.getPrice()) &&
                Objects.equals(getColor(), car.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getModel(), getCreation_year(), getUser_iq(), getPrice(), getColor());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
