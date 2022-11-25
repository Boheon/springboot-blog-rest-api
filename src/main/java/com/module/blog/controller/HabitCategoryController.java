package com.module.blog.controller;



import com.module.blog.payload.HabitCategoryDTO;
import com.module.blog.payload.HabitCategoryResponse;
import com.module.blog.service.HabitCategoryService;
import com.module.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class HabitCategoryController {

    private HabitCategoryService habitCategoryService;

    public HabitCategoryController(HabitCategoryService habitCategoryService) {
        this.habitCategoryService = habitCategoryService;
    }

    //create habitCategory rest api
    @PostMapping
    public ResponseEntity<HabitCategoryDTO> createHabitCategory(@Valid @RequestBody HabitCategoryDTO habitCategoryDTO){
        return new ResponseEntity<>(habitCategoryService.createHabitCategory(habitCategoryDTO), HttpStatus.CREATED);
    }

    //get all habit categories rest api
    @GetMapping
    public HabitCategoryResponse getAllHabitCategories(
            @RequestParam(value = "pageNO", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return habitCategoryService.getAllHabitCategories(pageNo, pageSize, sortBy, sortDir);
    }

    //get habit category by id
    @GetMapping("/{habitCategoryID}")
    public ResponseEntity<HabitCategoryDTO> getHabitCategoryByID(@PathVariable(name = "habitCategoryID") long id){
        return ResponseEntity.ok(habitCategoryService.getHabitCategoryByID(id));
    }

    @PutMapping("/{habitCategoryId}")
    public ResponseEntity<HabitCategoryDTO> updateHabitCategory(@Valid @RequestBody HabitCategoryDTO habitCategoryDTO,
                                                                @PathVariable(name = "habitCategoryId") long id){

        HabitCategoryDTO habitCategoryResponse = habitCategoryService.updateHabitCategory(habitCategoryDTO, id);

        return new ResponseEntity<>(habitCategoryResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{habitCategoryID}")
    public ResponseEntity<String> deleteHabitCategory(@PathVariable(name = "habitCategoryID") long id){

        habitCategoryService.deleteHabitCategoryByID(id);

        return new ResponseEntity<>("Habit Category Entity deleted successfully", HttpStatus.OK);
    }
}
