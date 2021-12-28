package com.hins.sp23.sp23mongodb.dynamicForm.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 表单模版
 * @author : chenqixuan
 * @date : 2021/12/28
 */
@Data
@Document(collection = "form_template")
public class FormTemplate {


    @Id
    private String templateId;

    private String title;

    private String description;

    private String formJson;

    private String formOptions;


}
