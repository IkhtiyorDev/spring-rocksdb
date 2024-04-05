package dev.ikhtiyor.springrocksdb.dto;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO implements Serializable {
    private String key;
    private String username;
    private String phoneNumber;
    private Object data;
}
