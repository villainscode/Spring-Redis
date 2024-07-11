package com.villainscode.redis.publisher;


import com.villainscode.redis.model.OrderQueue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CodeVillains
 * @description :
 */
@RestController
public class PublisherController {
    private final OrderService orderService;

    public PublisherController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/publish")
    public String publish(@RequestBody OrderQueue orderQueue) {
        orderService.publish(orderQueue);
        return "Success";
    }

}
