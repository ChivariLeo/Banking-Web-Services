package leo.com.service;

import leo.com.shared.dto.AccountDto;
import leo.com.shared.dto.UserDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAccounts(String userId);
    AccountDto getAccount(String accountId);
    AccountDto updateUserAccount(String id, AccountDto accountDto);
}
