package edu.ijse.service.custom;

import edu.ijse.dto.BookDto;
import edu.ijse.service.SuperService;

import java.util.ArrayList;

public interface BookService extends SuperService {
    String save(BookDto bookDto) throws Exception;
    String update(BookDto bookDto) throws Exception;
    String delete(String bookId) throws Exception;
    BookDto getBook(String bookId) throws Exception;
    ArrayList<BookDto> getAll() throws Exception;
    String getLastID() throws Exception;
}
