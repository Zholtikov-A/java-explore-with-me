package ru.practicum.ewmservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.dto.CategoryCreateDto;
import ru.practicum.ewmservice.exceptions.ValidationIdException;
import ru.practicum.ewmservice.mapper.CategoryMapper;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventService eventService;

    public CategoryDto createCategory(CategoryCreateDto category) {
        Category newCategory = categoryRepository.save(CategoryMapper.toCategory(category));
        return CategoryMapper.toCategoryDto(newCategory);
    }

    public void deleteCategory(Long catId) {
        Category category = checkCategory(catId);
        checkEvent(catId);
        categoryRepository.delete(category);
    }

    public List<CategoryDto> get(Integer from, Integer size) {
        List<Category> categoryList = categoryRepository.findAll(PageRequest.of(from, size)).getContent();
        return categoryList.stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    public CategoryDto getById(Long catId) {
        Category category = checkCategory(catId);
        return CategoryMapper.toCategoryDto(category);
    }

    public Category checkCategory(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new ValidationIdException("Category with id = \"" + catId + "\" not found"));
    }

    private void checkEvent(Long catId) {
        Event event = eventService.findEventByCategoryId(catId);
        if (event != null) {
            log.warn("ERROR! Category with id = {} is not empty", catId);
            throw new DataIntegrityViolationException("The category is not empty");
        }
    }

    public CategoryDto updateCategory(CategoryCreateDto categoryDto, Long catId) {
        Category category = checkCategory(catId);
        category.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }
}
