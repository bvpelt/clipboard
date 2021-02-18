package bsoft.com.clipboard.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Error {
    private String msg;
    private int status;
}
