package edu.ijse.service.custom.impl;

import edu.ijse.dao.DaoFactory;
import edu.ijse.dao.custom.BookDao;
import edu.ijse.dto.BookDto;
import edu.ijse.entity.BookEntity;
import edu.ijse.service.custom.BookService;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = (BookDao) DaoFactory.getInstance().getDao(DaoFactory.DaoType.BOOK);

    @Override
    public String save(BookDto bookDto) throws Exception {
        BookEntity entity = getBookEntity(bookDto);
        return bookDao.create(entity)? "Success" : "Fail";
    }

    @Override
    public String update(BookDto bookDto) throws Exception {
        BookEntity entity = getBookEntity(bookDto);
        return bookDao.update(entity)? "Success" : "Fail";
    }

    @Override
    public String delete(String bookId) throws Exception {
        return bookDao.delete(bookId)? "Success" : "Fail";
    }

    @Override
    public BookDto getBook(String bookId) throws Exception {
        BookEntity entity = bookDao.get(bookId);
        if (entity != null) {
            return getBookDto(entity);
        }
        return null;
    }

    @Override
    public ArrayList<BookDto> getAll() throws Exception {
        ArrayList<BookEntity> bookEntities = bookDao.getAll();

        if (bookEntities != null && !bookEntities.isEmpty()) {
            ArrayList<BookDto> bookDtos = new ArrayList<>();

            for (BookEntity bookEntity : bookEntities) {
                bookDtos.add(getBookDto(bookEntity));
            }
            return bookDtos;
        }
        return null;
    }

    @Override
    public String getLastID() throws Exception {
        return bookDao.getLastID();
    }

    private BookEntity getBookEntity(BookDto bookDto){
        return new BookEntity(
                bookDto.getBookID(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getIsbn(),
                bookDto.getQoh(),
                bookDto.getUnitPrice(),
                bookDto.getCategoryID()
        );
    }

    private BookDto getBookDto(BookEntity bookEntity){
        return new BookDto(
                bookEntity.getBookID(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getIsbn(),
                bookEntity.getQoh(),
                bookEntity.getUnitPrice(),
                bookEntity.getCategoryID(),
                new Button("Remove")
        );
    }
}
