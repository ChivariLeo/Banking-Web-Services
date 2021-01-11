package leo.com.repository;

import leo.com.entity.AccountEntity;
import leo.com.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity,Long> {

    List<AccountEntity> findAllByUserDetails(UserEntity userEntity);
    AccountEntity findByAccountId(String accountId);
}
