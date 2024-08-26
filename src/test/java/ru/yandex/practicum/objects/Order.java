package ru.yandex.practicum.objects;

import ru.yandex.practicum.constants.Color;

import java.util.List;

public class Order {
  private int id;
  private int courierId;
  private String firstName;
  private String lastName;
  private String address;
  private String metroStation;
  private String phone;
  private int rentTime;
  private String deliveryDate;
  private int track;
  private String comment;
  private List<Color> color;
  private String createdAt;
  private String updatedAt;
  private int status;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCourierId() {
    return courierId;
  }

  public void setCourierId(int courierId) {
    this.courierId = courierId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getMetroStation() {
    return metroStation;
  }

  public void setMetroStation(String metroStation) {
    this.metroStation = metroStation;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public int getRentTime() {
    return rentTime;
  }

  public void setRentTime(int rentTime) {
    this.rentTime = rentTime;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public int getTrack() {
    return track;
  }

  public void setTrack(int track) {
    this.track = track;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public List<Color> getColor() {
    return color;
  }

  public void setColor(List<Color> color) {
    this.color = color;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Order order = (Order) obj;
    return id == order.id &&
      firstName.equals(order.firstName) &&
      lastName.equals(order.lastName) &&
      track == order.track;
  }
}
