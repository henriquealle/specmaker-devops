package br.com.specmaker.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkItemImage {

    private String fileName;
    private int width;
    private int height;
    private byte[] imgFile;
}
