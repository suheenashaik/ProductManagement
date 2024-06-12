package com.example.productManagement.publisherRegistration.repository;

import com.example.productManagement.publisherRegistration.entity.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    @Query("select u from Publisher u where u.mobileNumber=?1")
    public Publisher findByMobile(String mobileNumber);
}
