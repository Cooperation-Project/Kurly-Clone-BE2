package com.github.kurlymarketclone.repository.delivey;

import com.github.kurlymarketclone.repository.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {
    List<Delivery> findAllByUser(User user);
}
