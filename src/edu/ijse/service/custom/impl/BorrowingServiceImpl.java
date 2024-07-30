package edu.ijse.service.custom.impl;

import edu.ijse.dao.DaoFactory;
import edu.ijse.dao.custom.BookDao;
import edu.ijse.dao.custom.BorrowingDao;
import edu.ijse.dao.custom.MemberDao;
import edu.ijse.db.DBConnection;
import edu.ijse.dto.BorrowingDto;
import edu.ijse.entity.BookEntity;
import edu.ijse.entity.BorrowingEntity;
import edu.ijse.entity.MemberEntity;
import edu.ijse.service.custom.BorrowingService;

import java.sql.Connection;
import java.util.ArrayList;

public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingDao borrowingDao = (BorrowingDao) DaoFactory.getInstance().getDao(DaoFactory.DaoType.BORROWING);
    private final MemberDao memberDao = (MemberDao) DaoFactory.getInstance().getDao(DaoFactory.DaoType.MEMBER);
    private final BookDao bookDao = (BookDao) DaoFactory.getInstance().getDao(DaoFactory.DaoType.BOOK);

    @Override
    public String save(BorrowingDto borrowingDto) throws Exception {
        BorrowingEntity entity = getBorrowingEntity(borrowingDto);
        return borrowingDao.create(entity) ? "Success" : "Fail";
    }

    @Override
    public String update(BorrowingDto borrowingDto) throws Exception {
        BorrowingEntity entity = getBorrowingEntity(borrowingDto);
        return borrowingDao.update(entity) ? "Success" : "Fail";
    }

    @Override
    public String delete(String borrowingId) throws Exception {
        return borrowingDao.delete(borrowingId) ? "Success" : "Fail";
    }

    @Override
    public ArrayList<BorrowingDto> getAllNotReturned() throws Exception {
        ArrayList<BorrowingEntity> borrowingEntities = borrowingDao.getAll();

        if (borrowingEntities != null && !borrowingEntities.isEmpty()) {
            ArrayList<BorrowingDto> borrowingDtos = new ArrayList<>();

            for (BorrowingEntity borrowingEntity : borrowingEntities) {
                if (borrowingEntity.isReturned()) {
                    continue;
                }
                BorrowingDto dto = getBorrowingDto(borrowingEntity);


                MemberEntity memberEntity = memberDao.get(borrowingEntity.getMemberId());
                if (memberEntity != null) {
                    dto.setMemberName(memberEntity.getName());
                }

                BookEntity bookEntity = bookDao.get(borrowingEntity.getBookId());
                if (bookEntity != null) {
                    dto.setBookName(bookEntity.getTitle());
                    dto.setBookPrice(bookEntity.getUnitPrice());
                }
                borrowingDtos.add(dto);
            }
            return borrowingDtos;
        }
        return null;
    }

    @Override
    public String getLastID() throws Exception {
        return borrowingDao.getLastID();
    }

    @Override
    public String issueBook(BorrowingDto borrowingDto) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            connection.setAutoCommit(false);

            BorrowingEntity borrowingEntity = new BorrowingEntity(
                    borrowingDto.getBorrowId(),
                    borrowingDto.getBorrowDate(),
                    borrowingDto.getDueDate(),
                    borrowingDto.getMemberId(),
                    borrowingDto.getBookId(),
                    borrowingDto.getReturned()
            );
            if (borrowingDao.create(borrowingEntity)) {

                BookEntity entity = bookDao.get(borrowingDto.getBookId());
                if (entity.getQoh() > 0) {
                    entity.setQoh(entity.getQoh() - 1);
                    if (bookDao.update(entity)) {
                        connection.commit();
                        return "Success";
                    } else {
                        connection.rollback();
                        return "Book save Failure...";
                    }
                } else {
                    connection.rollback();
                    return "Book currently not available...";
                }
            } else {
                connection.rollback();
                return "Borrowing Save Error";
            }

        } catch(Exception e){
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally{
            connection.setAutoCommit(true);
        }
    }
    @Override
    public ArrayList<BorrowingDto> search(String searchText) throws Exception {
        ArrayList<BorrowingEntity> borrowingEntities = borrowingDao.search(searchText);

        if (borrowingEntities != null && !borrowingEntities.isEmpty()) {
            ArrayList<BorrowingDto> borrowingDtos = new ArrayList<>();

            for (BorrowingEntity borrowingEntity : borrowingEntities) {
                if (borrowingEntity.isReturned()) {
                    continue;
                }

                String memberName = "";
                String bookTitle = "";
                double unitPrice = 0.0;

                MemberEntity memberEntity = memberDao.get(borrowingEntity.getMemberId());
                if (memberEntity != null) {
                    memberName = memberEntity.getName();
                }

                BookEntity bookEntity = bookDao.get(borrowingEntity.getBookId());
                if (bookEntity != null) {
                    bookTitle = bookEntity.getTitle();
                    unitPrice = bookEntity.getUnitPrice();
                }

                borrowingDtos.add(new BorrowingDto(
                        borrowingEntity.getBorrowId(),
                        borrowingEntity.getBorrowDate(),
                        borrowingEntity.getDueDate(),
                        borrowingEntity.getMemberId(),
                        memberName,
                        borrowingEntity.getBookId(),
                        bookTitle,
                        unitPrice,
                        borrowingEntity.isReturned()
                ));;
            }
            return borrowingDtos;
        }
        return null;
    }

    private BorrowingEntity getBorrowingEntity(BorrowingDto borrowingDto) {
        return new BorrowingEntity(
                borrowingDto.getBorrowId(),
                borrowingDto.getBorrowDate(),
                borrowingDto.getDueDate(),
                borrowingDto.getMemberId(),
                borrowingDto.getBookId(),
                borrowingDto.getReturned()
        );
    }

    private BorrowingDto getBorrowingDto(BorrowingEntity borrowingEntity) {
        return new BorrowingDto(
                borrowingEntity.getBorrowId(),
                borrowingEntity.getBorrowDate(),
                borrowingEntity.getDueDate(),
                borrowingEntity.getMemberId(),
                "",
                borrowingEntity.getBookId(),
                "",
                null,
                borrowingEntity.isReturned()
        );
    }
}
