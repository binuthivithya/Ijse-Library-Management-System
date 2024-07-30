package edu.ijse.service.custom.impl;

import edu.ijse.dao.DaoFactory;
import edu.ijse.dao.custom.CategoryDao;
import edu.ijse.dto.CategoryDto;
import edu.ijse.entity.CategoryEntity;
import edu.ijse.service.custom.CategoryService;

import java.util.ArrayList;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao CategoryDao = (CategoryDao) DaoFactory.getInstance().getDao(DaoFactory.DaoType.CATEGORY);

    @Override
    public String save(CategoryDto categoryDto) throws Exception {
        CategoryEntity entity = getCategoryEntity(categoryDto);
        return CategoryDao.create(entity)? "Success" : "Fail";
    }

    @Override
    public String update(CategoryDto categoryDto) throws Exception {
        CategoryEntity entity = getCategoryEntity(categoryDto);
        return CategoryDao.update(entity)? "Success" : "Fail";
    }

    @Override
    public String delete(String categoryId) throws Exception {
        return CategoryDao.delete(categoryId)? "Success" : "Fail";
    }

    @Override
    public CategoryDto getCategory(String categoryId) throws Exception {
        CategoryEntity entity = CategoryDao.get(categoryId);
        if (entity != null) {
            return getCategoryDto(entity);
        }
        return null;
    }

    @Override
    public ArrayList<CategoryDto> getAll() throws Exception {
        ArrayList<CategoryEntity> categoryEntities = CategoryDao.getAll();

        if (categoryEntities != null && !categoryEntities.isEmpty()) {
            ArrayList<CategoryDto> categoryDtos = new ArrayList<>();

            for (CategoryEntity categoryEntity : categoryEntities) {
                categoryDtos.add(getCategoryDto(categoryEntity));
            }
            return categoryDtos;
        }
        return null;
    }

    @Override
    public String getLastID() throws Exception {
        String lastID = CategoryDao.getLastID();
        if (lastID !=null){
            return lastID;
        }
        return null;
    }

    private CategoryEntity getCategoryEntity(CategoryDto categoryDto){
        return new CategoryEntity(
                categoryDto.getCatId(),
                categoryDto.getCatName(),
                categoryDto.getCatDesc()
        );
    }

    private CategoryDto getCategoryDto(CategoryEntity entity) {
        return new CategoryDto(
                entity.getCatId(),
                entity.getCatName(),
                entity.getCatDesc()
        );
    }
}
