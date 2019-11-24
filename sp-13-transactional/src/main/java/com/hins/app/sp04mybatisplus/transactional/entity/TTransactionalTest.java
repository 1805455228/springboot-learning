package com.hins.app.sp04mybatisplus.transactional.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hins.app.sp04mybatisplus.common.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * springbootäº‹åŠ¡æµ‹è¯•
 * </p>
 *
 * @author jobob
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_transactional_test")
public class TTransactionalTest extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private LocalDateTime createDate;


}
