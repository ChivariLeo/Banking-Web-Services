package leo.com.service;


import leo.com.entity.UserEntity;
import leo.com.exceptions.UserServiceException;
import leo.com.model.response.ErrorMessages;
import leo.com.repository.UserRepository;
import leo.com.shared.dto.AccountDto;
import leo.com.shared.dto.UserDto;
import leo.com.shared.dto.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils userUtils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public UserDto createUser(UserDto user) {

        if(userRepository.findByEmail(user.getEmail()) !=null)
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        for (int i = 0; i <user.getAccounts().size() ; i++) {
            AccountDto account= user.getAccounts().get(i);
            account.setUserDetails(user);
            account.setAccountId(userUtils.generateAccountId(30));
            user.getAccounts().set(i,account);
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String publicUserId=userUtils.generateUserId(30);

        userEntity.setUserId(publicUserId);
        userEntity.setEmailVerificationToken(userUtils.generateEmailVerificationToken(publicUserId));
        userEntity.setEmailVerificationStatus(false);
        UserEntity storedUserDetails = userRepository.save(userEntity);


        return modelMapper.map(storedUserDetails, UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String id) {
        UserDto returnUser = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        BeanUtils.copyProperties(userEntity, returnUser);
        return returnUser;
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {

        UserDto returnUser = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());

        UserEntity updatedUserDetails = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUserDetails, returnUser);
        return returnUser;

    }



    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());

        userRepository.delete(userEntity);

    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        if (page > 0)
            page = page - 1;

        PageRequest pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> userEntityPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = userEntityPage.getContent();

        for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }
        return returnValue;
    }

    @Override
    public boolean verifyEmailToken(String token) {
        boolean value=false;

        //find user by token
        UserEntity userEntity=userRepository.findUserByEmailVerificationToken(token);

        if(userEntity!=null) {
            boolean hasTokenExpired = Utils.hasTokenExpired(token);
            if (!hasTokenExpired) {
                userEntity.setEmailVerificationToken(null);
                userEntity.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(userEntity);
                value = true;
            }
        }
        return  value;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());


    }
}
