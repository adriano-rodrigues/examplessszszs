package com.monitoratec.tokenservice.vtswalletservice.repository;

import com.monitoratec.tokenservice.vtswalletservice.domain.model.EnrollPanModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrollPanRepository extends MongoRepository<EnrollPanModel, String> {
}
