package com.github.kurlymarketclone.repository.delivey;

import com.github.kurlymarketclone.repository.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="deliveryId")
@ToString
@Builder
@Entity
@Table(name = "delivery")
public class Delivery {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deliveryId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "delivery",length = 100)
    private String delivery;
    @Column(name = "is_default")
    private Integer isDefault;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Delivery(User user,String delivery){
        this.user=user;
        this.delivery=delivery;
        this.isDefault=0;
        this.createdAt=LocalDateTime.now();
    }
}
