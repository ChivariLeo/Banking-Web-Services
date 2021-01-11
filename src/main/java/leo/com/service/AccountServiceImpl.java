package leo.com.service;

import leo.com.entity.AccountEntity;
import leo.com.entity.UserEntity;
import leo.com.exceptions.AccountServiceException;
import leo.com.model.response.ErrorMessages;
import leo.com.repository.AccountRepository;
import leo.com.repository.UserRepository;
import leo.com.shared.dto.AccountDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountDto> getAccounts(String userId) {
        List<AccountDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);

        Iterable<AccountEntity> accounts = accountRepository.findAllByUserDetails(userEntity);
        for (AccountEntity account : accounts) {
            returnValue.add(modelMapper.map(account, AccountDto.class));
        }


        return returnValue;
    }

    @Override
    public AccountDto getAccount(String accountId) {

        AccountDto returnValue = null;

        AccountEntity accountEntity = accountRepository.findByAccountId(accountId);

        if (accountEntity != null)
            returnValue = new ModelMapper().map(accountEntity, AccountDto.class);


        return returnValue;
    }

    @Override
    public AccountDto updateUserAccount(String id, AccountDto accountDto) {

        AccountDto value = new AccountDto();
        AccountEntity accountEntity = accountRepository.findByAccountId(id);

        if (accountEntity == null)
            throw new AccountServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        accountEntity.setBalance(accountDto.getBalance());

        accountEntity.setInterest(accountDto.getInterest());

        AccountEntity updatedAccount = accountRepository.save(accountEntity);
        BeanUtils.copyProperties(updatedAccount, value);

        return value;

    }
}
