package com.monitoratec.tokenservice.vtswalletservice.repository;

import com.monitoratec.tokenservice.vtswalletservice.domain.model.DeviceEnrollmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrollDeviceRepository extends MongoRepository<DeviceEnrollmentModel,String> {
}
