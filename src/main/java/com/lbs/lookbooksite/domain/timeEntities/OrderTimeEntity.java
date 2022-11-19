package com.lbs.lookbooksite.domain.timeEntities;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class OrderTimeEntity {

    // 등록일
    @CreatedDate
    private LocalDateTime orderDate;

    // 수정일
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
