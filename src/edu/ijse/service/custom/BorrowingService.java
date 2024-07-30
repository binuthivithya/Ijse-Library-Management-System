package edu.ijse.service.custom;

import edu.ijse.dto.BorrowingDto;
import edu.ijse.service.SuperService;

import java.util.ArrayList;

public interface BorrowingService extends SuperService {
    String save(BorrowingDto borrowingDto) throws Exception;
    String update(BorrowingDto borrowingDto) throws Exception;
    String delete(String borrowingId) throws Exception;
    ArrayList<BorrowingDto> getAllNotReturned() throws Exception;
    String getLastID() throws Exception;
    ArrayList<BorrowingDto> search(String searchText) throws Exception;
    String issueBook(BorrowingDto borrowingDto) throws Exception;
}
