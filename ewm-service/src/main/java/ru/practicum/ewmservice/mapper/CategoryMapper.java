package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.dto.NewCategoryDto;
import ru.practicum.ewmservice.model.Category;

@UtilityClass
public class CategoryMapper {
    public static Category toCategory(NewCategoryDto category) {
        return Category.builder()
                .name(category.getName())
                .build();
    }

    public static CategoryDto toCategoryDto(Category newCategory) {
        return CategoryDto.builder()
                .id(newCategory.getId())
                .name(newCategory.getName())
                .build();
    }
}
