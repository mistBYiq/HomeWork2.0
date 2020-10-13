package org.example.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class User {

    private Long id;
    private String name;
    private String surname;
    private Date birthDate;
    private Gender gender = Gender.NOT_SELECTED;
    private Timestamp created;
    private Timestamp changed;
    private Float weight;

    public User() {
    }

    public User(Long id, String name, String surname, Date birthDate, Gender gender, Timestamp created, Timestamp changed, Float weight) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.created = created;
        this.changed = changed;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getChanged() {
        return changed;
    }

    public void setChanged(Timestamp changed) {
        this.changed = changed;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSurname(), user.getSurname()) &&
                Objects.equals(getBirthDate(), user.getBirthDate()) &&
                getGender() == user.getGender() &&
                Objects.equals(getCreated(), user.getCreated()) &&
                Objects.equals(getChanged(), user.getChanged()) &&
                Objects.equals(getWeight(), user.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getBirthDate(), getGender(), getCreated(), getChanged(), getWeight());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
