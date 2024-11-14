package com.example.humorie.consultant.search.service;

import com.example.humorie.consultant.counselor.entity.CounselingMethod;
import com.example.humorie.consultant.counselor.entity.Counselor;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class CounselorSpecification {

    public static Specification<Counselor> hasCounselingMethod(String counselingMethod) {
        return (root, query, criteriaBuilder) -> {
            if (counselingMethod == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Counselor, CounselingMethod> methods = root.join("counselingMethods");
            return criteriaBuilder.equal(methods.get("method"), counselingMethod);
        };
    }

    public static Specification<Counselor> hasGender(String gender) {
        return (root, query, criteriaBuilder) -> {
            if (gender == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("gender"), gender);
        };
    }

    public static Specification<Counselor> hasRegion(String region) {
        return (root, query, criteriaBuilder) -> {
            if (region == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("region"), region);
        };
    }

    public static Specification<Counselor> orderBy(String order) {
        return (root, query, criteriaBuilder) -> {
            if (order == null) {
                query.orderBy(criteriaBuilder.asc(root.get("id")));
                return criteriaBuilder.conjunction();
            }
            switch (order) {
                case "reviewRating_desc":
                    query.orderBy(criteriaBuilder.desc(root.get("rating")));
                    break;
                case "reviewRating_asc":
                    query.orderBy(criteriaBuilder.asc(root.get("rating")));
                    break;
                default:
                    query.orderBy(criteriaBuilder.asc(root.get("id")));
                    break;
            }
            return criteriaBuilder.conjunction();
        };
    }

}

