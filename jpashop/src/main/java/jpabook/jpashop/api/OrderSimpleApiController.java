package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    xToOne(ManyToOne, OneToOne)
    Order
    Order -> Member
    Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        // FORCE_LAZY_LOADING을 끄고 원하는 것만 골라서 출력하는 방법
        for (Order order : all) {
            //          프록시.LAZY 강제 초기화
            order.getMember().getName();
            order.getDelivery().getAddress();
        }

        return all;
    }

}
