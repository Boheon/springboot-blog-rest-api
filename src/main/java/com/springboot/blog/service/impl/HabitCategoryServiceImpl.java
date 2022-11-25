package com.springboot.blog.service.impl;

import com.springboot.blog.entity.habit.HabitCategory;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.HabitCategoryDTO;
import com.springboot.blog.payload.HabitCategoryResponse;
import com.springboot.blog.repository.HabitCategoryRepository;
import com.springboot.blog.service.HabitCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitCategoryServiceImpl implements HabitCategoryService {

    private HabitCategoryRepository habitCategoryRepository;


    public HabitCategoryServiceImpl(HabitCategoryRepository habitCategoryRepository) {
        this.habitCategoryRepository = habitCategoryRepository;
    }


    @Override
    public HabitCategoryDTO createHabitCategory(HabitCategoryDTO habitCategoryDTO) {
        //convert DTO to Entity
        HabitCategory habitCategory = mapToEntity(habitCategoryDTO);
        //System.out.println("habit category 결과 : "+ "(" + habitCategory.getId() + "," + habitCategory.getHabitCategoryName()+ ")");
        HabitCategory newHabitCategory = habitCategoryRepository.save(habitCategory);
        //System.out.println("new habit category 결과 : "+ "(" + newHabitCategory.getId() + "," + newHabitCategory.getHabitCategoryName()+ ")");
        //convert Entity to DTO
        HabitCategoryDTO habitCategoryResponse = mapToDTO(newHabitCategory);
        return habitCategoryResponse;
    }

    @Override
    public HabitCategoryResponse getAllHabitCategories(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<HabitCategory> habitCategories = habitCategoryRepository.findAll(pageable);

        //get content for page object
        List<HabitCategory> listOfHabitCategories = habitCategories.getContent();

        List<HabitCategoryDTO> content = listOfHabitCategories.stream().map(habitCategory -> mapToDTO(habitCategory)).collect(Collectors.toList());

        HabitCategoryResponse habitCategoryResponse = HabitCategoryResponse.builder()
                .content(content)
                .pageNo(habitCategories.getNumber())
                .pageSize(habitCategories.getSize())
                .totalElements(habitCategories.getTotalElements())
                .totalPages(habitCategories.getTotalPages())
                .last(habitCategories.isLast())
                .build();

        return habitCategoryResponse;
    }

    @Override
    public HabitCategoryDTO getHabitCategoryByID(long habitCategoryID) {
        HabitCategory habitCategory = habitCategoryRepository.findById(habitCategoryID).orElseThrow(() -> new ResourceNotFoundException("Habit Category", "habit Category ID", habitCategoryID));
        return mapToDTO(habitCategory);
    }

    @Override
    public HabitCategoryDTO updateHabitCategory(HabitCategoryDTO habitCategoryDTO, long id) {
        //get Habit Category by id from the db
        HabitCategory habitCategory = habitCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Habit Category", "habit category ID", id));
        habitCategory.updateHabitCategory(habitCategoryDTO);
        HabitCategory updatedHabitCategory = habitCategoryRepository.save(habitCategory);
        return mapToDTO(updatedHabitCategory);
    }


    @Override
    public void deleteHabitCategoryByID(long id) {
        //get habit category by id from the database
        HabitCategory habitCategory = habitCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Habit Category", "Habit Category ID", id));
        habitCategoryRepository.delete(habitCategory);
    }

    //convert Entity into DTO
    private HabitCategoryDTO mapToDTO(HabitCategory habitCategory){

        HabitCategoryDTO habitCategoryDTO = HabitCategoryDTO.builder()
                .id(habitCategory.getId())
                .name(habitCategory.getHabitCategoryName())
                .build();
        return habitCategoryDTO;
    }

    private HabitCategory mapToEntity(HabitCategoryDTO habitCategoryDTO){
        HabitCategory habitCategory = HabitCategory.builder()
                .id(habitCategoryDTO.getId())
                .habitCategoryName(habitCategoryDTO.getName())
                .build();


        return habitCategory;
    }
}
