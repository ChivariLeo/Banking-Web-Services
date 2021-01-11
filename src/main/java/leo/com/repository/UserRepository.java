package leo.com.repository;

import leo.com.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository  //query DB methods
public interface UserRepository  extends PagingAndSortingRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
    UserEntity findUserByEmailVerificationToken(String token);

}
