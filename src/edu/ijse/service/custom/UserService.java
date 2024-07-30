package edu.ijse.service.custom;

import edu.ijse.dto.UserDto;
import edu.ijse.service.SuperService;

public interface UserService extends SuperService {
    String save(UserDto userDto)throws Exception;
    UserDto getUser(String email)throws Exception;
}
