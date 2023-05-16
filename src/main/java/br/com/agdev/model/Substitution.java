package br.com.agdev.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Substitution {

    private String oldValue;
    private String newValue;
    private String regex;
}