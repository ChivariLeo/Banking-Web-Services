package leo.com.service;

import leo.com.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService  extends UserDetailsService {
       UserDto createUser(UserDto user);
       UserDto getUser(String id);
       UserDto getUserByUserId(String id);
       UserDto updateUser(String id,UserDto user);
       void deleteUser(String userId);
       List<UserDto>  getUsers(int page,int limit);
       boolean verifyEmailToken(String token);
}
