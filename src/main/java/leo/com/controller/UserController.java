package leo.com.controller;


import leo.com.exceptions.AccountServiceException;
import leo.com.exceptions.UserServiceException;
import leo.com.model.request.AccountRequestModel;
import leo.com.model.request.UserDetailsRequestModel;
import leo.com.model.response.AccountResponseModel;
import leo.com.model.response.ErrorMessages;
import leo.com.model.response.OperationStatus;
import leo.com.model.response.UserDetailsResponseModel;
import leo.com.service.AccountService;
import leo.com.service.UserService;
import leo.com.shared.dto.AccountDto;
import leo.com.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users") //localhost:8080/banking-api/users
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDetailsResponseModel getUser(@PathVariable String id) {

        UserDto userDTO = userService.getUserByUserId(id);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(userDTO,UserDetailsResponseModel.class);

    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserDetailsResponseModel createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserDetailsResponseModel returnValue ;
        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        returnValue = modelMapper.map(createdUser, UserDetailsResponseModel.class);

        return returnValue;
    }


    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserDetailsResponseModel updateUser(@PathVariable String id,
                                               @RequestBody UserDetailsRequestModel userDetails) {
        UserDetailsResponseModel returnValue = new UserDetailsResponseModel();
        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        UserDto userDTO = new UserDto();
        BeanUtils.copyProperties(userDetails, userDTO);

        UserDto updatedUser = userService.updateUser(id, userDTO);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public OperationStatus deleteUser(@PathVariable String id) {
        OperationStatus returnValue = new OperationStatus();
        returnValue.setOperationName("DELETE");

        userService.deleteUser(id);

        returnValue.setOperationResult("SUCCESS");

        return returnValue;
    }


    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserDetailsResponseModel> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserDetailsResponseModel> userList = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        for (UserDto userDto : users) {
            UserDetailsResponseModel userModel = new UserDetailsResponseModel();
            BeanUtils.copyProperties(userDto, userModel);
            userList.add(userModel);
        }

        return userList;
    }


    @GetMapping(path = "/{id}/accounts", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<AccountResponseModel> getUserAccounts(@PathVariable String id) {
        List<AccountResponseModel> returnValue = new ArrayList<>();

        List<AccountDto> accountDto = accountService.getAccounts(id);

        if (accountDto != null && !accountDto.isEmpty()) {
            Type listType = new TypeToken<List<AccountResponseModel>>() {
            }.getType();
            returnValue = new ModelMapper().map(accountDto, listType);
        }
        return returnValue;

    }


    @GetMapping(path = "/{userId}/accounts/{accountId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AccountResponseModel getUserAccount(@PathVariable String accountId) {

        AccountDto accountDto = accountService.getAccount(accountId);

        ModelMapper modelMapper = new ModelMapper();


        return modelMapper.map(accountDto, AccountResponseModel.class);
    }




    @GetMapping(path = "/email_verification",
                produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatus verifyEmailToken(@RequestParam(value = "token") String token) {
        OperationStatus returnValue = new OperationStatus();

        returnValue.setOperationName("VERIFY_EMAIL");
        boolean isVerified = userService.verifyEmailToken(token);

        if (isVerified)
            returnValue.setOperationResult("SUCCESS! YOU'RE E-MAIL HAS BEEN VERIFIED");
        else
            returnValue.setOperationResult(ErrorMessages.EMAIL_ADDRESS_NOT_VERIFIED.getErrorMessage());


        return returnValue;

    }


    @PutMapping(path = "/{userId}/accounts/{accountId}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public AccountResponseModel updateUserAccount(@PathVariable String accountId,
                                                  @RequestBody AccountRequestModel accountDetails) {
        AccountResponseModel value = new AccountResponseModel();
        if (accountDetails.getAccountName().isEmpty())
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        AccountDto accountDto = new AccountDto();

        BeanUtils.copyProperties(accountDetails, accountDto);

        AccountDto updatedAccount = accountService.updateUserAccount(accountId, accountDto);
        BeanUtils.copyProperties(updatedAccount, value);

        return value;

    }


}
