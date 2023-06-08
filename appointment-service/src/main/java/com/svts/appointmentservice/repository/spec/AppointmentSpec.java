package com.svts.appointmentservice.repository.spec;

import com.svts.appointmentservice.controller.vm.SearchAppointmentVM;
import com.svts.appointmentservice.enums.AppointmentStatus;
import com.svts.appointmentservice.model.Appointment;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class AppointmentSpec implements Specification<Appointment> {

    private final SearchAppointmentVM searchAppointmentVM;

    @Override
    public Predicate toPredicate(@NotNull Root<Appointment> root,
                                 @NotNull CriteriaQuery<?> criteriaQuery,
                                 @NotNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (null != searchAppointmentVM.getStatus()) {
            predicates.add(criteriaBuilder.equal(root.get("status"), AppointmentStatus.values()[searchAppointmentVM.getStatus().ordinal()]));
        }

        if (null != searchAppointmentVM.getDateFrom()) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), searchAppointmentVM.getDateFrom()));
        }

        if (null != searchAppointmentVM.getDateTo()) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), searchAppointmentVM.getDateTo()));
        }

        if (!StringUtils.isEmpty(searchAppointmentVM.getName())) {
            predicates.add(criteriaBuilder.like(root.get("patient").get("name"), "%" + searchAppointmentVM.getName() + "%"));
        }

        return andTogether(predicates, criteriaBuilder);
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
