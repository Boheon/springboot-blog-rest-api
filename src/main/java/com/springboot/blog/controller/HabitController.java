package com.springboot.blog.controller;

import com.springboot.blog.entity.habit.Habit;
import com.springboot.blog.payload.AmountResponse;
import com.springboot.blog.payload.HabitDTO;
import com.springboot.blog.payload.PeriodResponse;
import com.springboot.blog.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class HabitController {

    private HabitService habitService;

    public HabitController(HabitService habitService) {this.habitService = habitService;}

    @PostMapping("/categories/{categoryId}/members/{memberId}/habits")
    public ResponseEntity<HabitDTO> createHabit(@PathVariable(value = "categoryId") long habitCategoryId,
                                                @PathVariable(value = "memberId") long memberId,
                                                @Valid @RequestBody HabitDTO habitDTO){

        return new ResponseEntity<>(habitService.createHabit(habitCategoryId, memberId, habitDTO), HttpStatus.CREATED);
    }

    @GetMapping("/categories/{categoryId}/habits")
    public List<HabitDTO> getHabitsByHabitCategoryId(@PathVariable(value = "categoryId") Long habitCategoryId){
        return habitService.getHabitsByCategoryId(habitCategoryId);
    }

    @GetMapping("/members/{memberId}/habits")
    public List<HabitDTO> getHabitsByHabitMemberId(@PathVariable(value = "memberId") long memberId){
        return habitService.getHabitsByMemberId(memberId);
    }

    @GetMapping("/habits/{habitId}")
    public ResponseEntity<HabitDTO> getHabitById(@PathVariable(value = "habitId") Long habitId){

        HabitDTO habitDTO = habitService.getHabitById(habitId);

        return new ResponseEntity<>(habitDTO, HttpStatus.OK);
    }

    @GetMapping("categories/{categoryId}/total_amount")
    public AmountResponse getTotalAmounts(@PathVariable(value = "categoryId") Long habitCategoryId){
        return habitService.getTotalAmounts(habitCategoryId);
    }

    @GetMapping("categories/{categoryId}/total_period")
    public PeriodResponse getTotalPeriods(@PathVariable(value = "categoryId") Long habitCategoryId){
        return habitService.getTotalPeriods(habitCategoryId);
    }

    @PutMapping("/habits/{habitId}")
    public ResponseEntity<HabitDTO> updateHabit(@PathVariable(value = "habitId") Long habitId, @Valid @RequestBody HabitDTO habitDTO){
        HabitDTO updatedHabit = habitService.updateHabit(habitId, habitDTO);
        return new ResponseEntity<>(updatedHabit, HttpStatus.OK);
    }

    @PutMapping("check/success/habits/{habitId}")//is_checked를 1로
    public ResponseEntity<HabitDTO> updateCheck(@PathVariable(value = "habitId") Long habitId){
        HabitDTO updatedHabit = habitService.updateHabitCheck(habitId);
        return new ResponseEntity<>(updatedHabit, HttpStatus.OK);
    }

    @PutMapping("check/judge/habits/{habitId}")//check 성공 -> check를 false로 check 실패 -> date를 0로
    public ResponseEntity<HabitDTO> judgeHabitCheck(@PathVariable(value = "habitId") Long habitId){
        HabitDTO updatedHabit = habitService.judgeHabitCheck(habitId);
        return new ResponseEntity<>(updatedHabit, HttpStatus.OK);
    }

    @DeleteMapping("/habits/{habitId}")
    public ResponseEntity<String> deleteHabit(@PathVariable(value = "habitId") Long habitId){
        habitService.deleteHabit(habitId);
        return new ResponseEntity<>("Habit deleted successfully", HttpStatus.OK);
    }
}
