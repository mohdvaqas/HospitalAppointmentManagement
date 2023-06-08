package com.svts.appointmentservice.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "HOSPITAL-SERVICE")
public interface PatientFeignClient {

}
