package com.monitoratec.tokenservice.vtswalletservice.repository;

import com.monitoratec.tokenservice.vtswalletservice.domain.model.ConfirmProvisioningModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmProvisioningRepository extends MongoRepository<ConfirmProvisioningModel,String> {
}
