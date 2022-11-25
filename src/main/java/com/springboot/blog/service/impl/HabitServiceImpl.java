package com.springboot.blog.service.impl;

import com.springboot.blog.entity.habit.Habit;
import com.springboot.blog.entity.habit.HabitCategory;
import com.springboot.blog.entity.member.Member;
import com.springboot.blog.entity.member.MemberRepository;
import com.springboot.blog.exception.AppApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.AmountResponse;
import com.springboot.blog.payload.HabitDTO;
import com.springboot.blog.payload.PeriodResponse;
import com.springboot.blog.repository.HabitCategoryRepository;
import com.springboot.blog.repository.HabitRepository;
import com.springboot.blog.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EnableAsync
@Service
public class HabitServiceImpl implements HabitService {
    private HabitRepository habitRepository;
    private HabitCategoryRepository habitCategoryRepository;
    private MemberRepository memberRepository;

    public HabitServiceImpl(HabitRepository habitRepository, HabitCategoryRepository habitCategoryRepository, MemberRepository memberRepository){
        this.habitRepository = habitRepository;
        this.habitCategoryRepository = habitCategoryRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public HabitDTO createHabit(long habitCategoryID, long memberId, HabitDTO habitDTO) {
        Habit habit = mapToEntity(habitDTO);

        //retrieve Habit Category entity by id
        HabitCategory habitCategory = habitCategoryRepository.findById(habitCategoryID).orElseThrow(() -> new ResourceNotFoundException("HabitCategory", "habit category id", habitCategoryID));

        //retrieve Member entity by id
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Member", "member Id", memberId));

        //set member, habitCategory to habit entity
        habit.updateHabit(habitCategory, member);

        //habit entity to DB
        Habit newHabit = habitRepository.save(habit);

        return mapToDTO(newHabit);
    }

    @Override
    public List<HabitDTO> getHabitsByCategoryId(long habitCategoryId) {
        //retrieve habits by category ID
        List<Habit> habits = habitRepository.findByHabitCategoryId(habitCategoryId);

        //convert list of habit entities to list of habit dto
        return habits.stream().map(habit -> mapToDTO(habit)).collect(Collectors.toList());
    }

    @Override
    public List<HabitDTO> getHabitsByMemberId(long memberId) {
        //retrieve habits by member Id
        List<Habit> habits = habitRepository.findByMemberId(memberId);

        //convert list of habit entities to list of habit dto
        return habits.stream().map(habit -> mapToDTO(habit)).collect(Collectors.toList());
    }

    @Override
    public PeriodResponse getTotalPeriods(long habitCategoryId) {
        //retrieve habits by category ID
        List<Habit> habits = habitRepository.findByHabitCategoryId(habitCategoryId);
        HabitCategory habitCategory = habitCategoryRepository.findById(habitCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit Category", "HabitCategory Id", habitCategoryId));

        String categoryName = habitCategory.getHabitCategoryName();
        List<Long> totalPeriods = habits.stream().map(habit -> habit.getTotalPeriod()).collect(Collectors.toList());

        PeriodResponse periodResponse = PeriodResponse.builder()
                .categoryName(categoryName)
                .totalPeriods(totalPeriods)
                .build();

        return periodResponse;
    }

    @Override
    public AmountResponse getTotalAmounts(long habitCategoryId) {
        //retrieve habits by category ID
        List<Habit> habits = habitRepository.findByHabitCategoryId(habitCategoryId);
        HabitCategory habitCategory = habitCategoryRepository.findById(habitCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit Category", "HabitCategory Id", habitCategoryId));

        String categoryName = habitCategory.getHabitCategoryName();
        List<Long> totalAmounts = habits.stream().map(habit -> habit.getTotalAmount()).collect(Collectors.toList());

        AmountResponse amountResponse = AmountResponse.builder()
                .categoryName(categoryName)
                .totalAmounts(totalAmounts)
                .build();

        return amountResponse;
    }


    @Override
    public HabitDTO getHabitById(Long habitId) {

        //retrieve habit by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        return mapToDTO(habit);
    }

    @Override
    public HabitDTO updateHabit( long habitId, HabitDTO habitRequest) {

        //retrieve habit by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        habit.updateHabitBody(habitRequest);

        Habit updatedHabit = habitRepository.save(habit);
        return mapToDTO(updatedHabit);
    }

    @Override
    public HabitDTO updateHabitCheck(Long habitId) {
        //retrieve habit entity by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        habit.updateCheck(); // 습관 체크하기 true로

        Habit updatedHabit = habitRepository.save(habit);
        return mapToDTO(updatedHabit);
    }

    @Override
    public HabitDTO judgeHabitCheck(Long habitId) {//check값을 기반으로 계산
        //retrieve habit entity by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        if(!habit.getIsChecked()){
            habit.updateHabitDate();
            //habitdate 초기화 및 카운트를 0로 만든다
        }
        else {
            habit.returnCheck();
            //check를 false로 만든다
        }

        Habit updatedHabit = habitRepository.save(habit);
        return mapToDTO(updatedHabit);

    }


    @Override
    public void deleteHabit(Long habitId) {

        //retrieve habit entity by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        habitRepository.delete(habit);
    }

    private HabitDTO mapToDTO(Habit habit){
        HabitDTO habitDTO = HabitDTO.builder()
                .id(habit.getHabitID())
                .name(habit.getHabitName())
                .check(habit.getIsChecked())
                .amount(habit.getHabitAmount())
                .period(habit.getHabitPeriod())
                .count(habit.getHabitCount())
                .date(habit.getHabitDate())
                .location(habit.getPictureLocation())
                .build();
        return habitDTO;
    }

    private Habit mapToEntity(HabitDTO habitDTO){
        Habit habit = Habit.builder()
                .habitID(habitDTO.getId())
                .habitName(habitDTO.getName())
                .isChecked(habitDTO.getCheck())
                .habitAmount(habitDTO.getAmount())
                .habitPeriod(habitDTO.getPeriod())
                .habitCount(habitDTO.getCount())
                .habitDate(habitDTO.getDate())
                .pictureLocation(habitDTO.getLocation())
                .build();
        return habit;
    }
}
