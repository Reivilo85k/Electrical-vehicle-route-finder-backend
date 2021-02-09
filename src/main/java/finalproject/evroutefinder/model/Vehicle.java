package finalproject.evroutefinder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "capacity")
    private Integer capacity; // battery, kWh

    @Column(name = "ev_range")
    private Integer range; // km per loading

    @Column(name = "consumption")
    private Float consumption; // km per 1 kWh (range/capacity)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    // @Column(name = "battery_status")
    // private int status; // battery_status

    // capacity in km = status * consumption;
}
