package ra.rest_apidemo.mapper;

import ra.rest_apidemo.dto.response.UserDTOResponse;
import ra.rest_apidemo.model.User;

public class UserMapper implements GenericMapper<User,User, UserDTOResponse>{
    @Override
    public User mapperRequestToEntity(User user) {
        return null;
    }

    @Override
    public UserDTOResponse mapperEntityToResponse(User user) {
        return new UserDTOResponse(user.getId(), user.getEmail(), user.isStatus());
    }
}
