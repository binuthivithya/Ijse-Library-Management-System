package edu.ijse.service.custom;

import edu.ijse.dto.CategoryDto;
import edu.ijse.service.SuperService;

import java.util.ArrayList;

public interface CategoryService extends SuperService {
    String save(CategoryDto categoryDto) throws Exception;
    String update(CategoryDto categoryDto) throws Exception;
    String delete(String categoryId) throws Exception;
    CategoryDto getCategory(String categoryId) throws Exception;
    ArrayList<CategoryDto> getAll() throws Exception;
    String getLastID() throws Exception;
}
