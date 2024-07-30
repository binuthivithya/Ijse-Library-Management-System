package edu.ijse.service.custom;

import edu.ijse.dto.ReturnDto;
import edu.ijse.service.SuperService;

import java.util.ArrayList;

public interface ReturnService extends SuperService {
    String save(ReturnDto returnDto) throws Exception;
    ArrayList<ReturnDto> getAll() throws Exception;
    String getLastId() throws Exception;

    String returnBook(ReturnDto returnDto) throws Exception;
}
