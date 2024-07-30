package edu.ijse.service.custom.impl;

import edu.ijse.dao.DaoFactory;
import edu.ijse.dao.custom.UserDao;
import edu.ijse.dto.UserDto;
import edu.ijse.entity.UserEntity;
import edu.ijse.service.custom.UserService;

public class UserServiceImpl implements UserService {

    private UserDao UserDao = (UserDao) DaoFactory.getInstance().getDao(DaoFactory.DaoType.USER);

    @Override
    public String save(UserDto userDto) throws Exception {
        UserEntity entity = getUserEntity(userDto);
        return UserDao.create(entity)? "Success" : "Fail";
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity entity = UserDao.get(email);
        if (entity!= null) {
            return getUserDto(entity);
        }
        return null;
    }

    private UserEntity getUserEntity(UserDto userDto) {
        return new UserEntity(
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPassword()
        );
    }

    private UserDto getUserDto(UserEntity entity) {
        return new UserDto(
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPassword()
        );
    }
}
