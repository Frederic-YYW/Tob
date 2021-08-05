package com.hw.tobcore.bean.vo;

import lombok.Data;

import java.util.List;

@Data
public class ImportExcelVO {
    private String fileName;
    private List<String> ids;
}
