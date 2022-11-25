package com.springboot.blog.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AmountResponse {
    private String categoryName;//카테고리 이름
    private List<Long> totalAmounts;

}
