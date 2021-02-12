package com.bsoft.clipboard.persist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    private Long id;

    private String name;

    private String destinationUrl;
}
