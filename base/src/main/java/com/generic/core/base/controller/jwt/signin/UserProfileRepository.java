package com.generic.core.base.controller.jwt.signin;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource
public interface UserProfileRepository extends CrudRepository<UserProfileEntity, UUID> {
    boolean existsByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
    long countByUsernameStartingWith(String username);
    boolean existsByUuidAndStatus(UUID uuid, String status);
    UserProfileEntity findByUuid(UUID uuid);
    Optional<UserProfileEntity> findByUsername(String username);
    Page<UserProfileEntity> findAllByCompanyId(UUID uuid, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update UserProfileEntity v set v.companyName = :companyName where v.companyId = :id")
    void updateCompanyNameById(String companyName, UUID id);
}
