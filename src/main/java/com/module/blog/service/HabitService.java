package com.module.blog.service;

import com.module.blog.payload.AmountResponse;
import com.module.blog.payload.HabitDTO;
import com.module.blog.payload.PeriodResponse;

import java.util.List;

public interface HabitService {
    HabitDTO createHabit(long habitCategoryId, long memberId, HabitDTO habitDto);//습관 만들기

    List<HabitDTO> getHabitsByCategoryId(long habitCategoryId);//카테고리 하위 습관 다불러오기

    List<HabitDTO> getHabitsByMemberId(long memberId);//멤버의 습관 다 불러오기

    PeriodResponse getTotalPeriods(long habitCategoryId);

    AmountResponse getTotalAmounts(long habitCategoryId);

    HabitDTO getHabitById(Long habitId);// 습관카테고리넣고 습관 아이디도 넣은거

    HabitDTO updateHabit(long habitId, HabitDTO habitRequest);//습관 수정하기

    HabitDTO updateHabitCheck(Long habitId);//습관 체크 true로 업데이트

    HabitDTO judgeHabitCheck(Long habitId);

    void deleteHabit(Long habitId);// 습관 삭제


}