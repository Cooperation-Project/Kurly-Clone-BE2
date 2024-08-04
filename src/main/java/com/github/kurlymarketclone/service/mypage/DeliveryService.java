package com.github.kurlymarketclone.service.mypage;

import com.github.kurlymarketclone.repository.delivey.Delivery;
import com.github.kurlymarketclone.repository.delivey.DeliveryRepository;
import com.github.kurlymarketclone.repository.user.User;
import com.github.kurlymarketclone.repository.user.UserRepository;
import com.github.kurlymarketclone.repository.userDetail.CustomUserDetails;
import com.github.kurlymarketclone.service.exception.NotFoundException;
import com.github.kurlymarketclone.web.dto.mypage.UserDelivery;
import com.github.kurlymarketclone.web.dto.mypage.UserDeliveryResponse;
import com.github.kurlymarketclone.web.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    public ResponseDto getAddressResult(CustomUserDetails customUserDetails) {
       Integer userId =  customUserDetails.getUserId();
       User user = userRepository.findById(userId)
               .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));
       List<Delivery> userDeliveries = deliveryRepository.findAllByUser(user);
       List<UserDelivery> userDeliveryList = userDeliveries.stream().map(UserDelivery::new).toList();
        UserDeliveryResponse userDeliveryResponse = new UserDeliveryResponse(userDeliveryList);
        return new ResponseDto(HttpStatus.OK.value(),"유저 배송지 조회 성공",userDeliveryResponse);
    }

    public ResponseDto addAddressResult(CustomUserDetails customUserDetails, UserDelivery userDelivery) {
        Integer userId =  customUserDetails.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));
        List<Delivery> deliveries = deliveryRepository.findAllByUser(user);
        List<String> addressList= deliveries.stream().map(Delivery::getDelivery).toList();
        if (addressList.contains(userDelivery.getAddress())){
            return new ResponseDto(HttpStatus.CONFLICT.value(),"이미 저장되어 있는 주소입니다.");
        }
        try{
            Delivery newDelivery = new Delivery(user,userDelivery.getAddress());
            deliveryRepository.save(newDelivery);
            return new ResponseDto(HttpStatus.OK.value(),"주소 추가가 완료되었습니다.");
        }catch (Exception e){
            return new ResponseDto(HttpStatus.BAD_REQUEST.value(),"주소 저장에 실패했습니다.");
        }

    }
}
