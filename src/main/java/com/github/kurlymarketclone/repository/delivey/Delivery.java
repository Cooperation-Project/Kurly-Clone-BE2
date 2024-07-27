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
    private String delivery;
    private Integer isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
