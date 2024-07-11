package com.villainscode.redis.subscriber;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO implements Serializable {


    private String id;
    private String userId;
    private String productName;
    private int price;
    private int qty;
}