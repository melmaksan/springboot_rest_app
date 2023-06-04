package com.epam.esm.certificate_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends RepresentationModel<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "order_price")
    private int price;
    @Column(name = "time")
    private LocalDateTime time;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate certificate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Order() {
    }

    public Order(int price, LocalDateTime time) {
        this.price = price;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public GiftCertificate getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificate certificate) {
        this.certificate = certificate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", time=" + time +
                ", certificate=" + certificate +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && price == order.price && Objects.equals(time, order.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, time);
    }
}
