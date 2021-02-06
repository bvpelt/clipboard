package bsoft.com.clipboard.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BadRequest {
    private String errorReason;
}
