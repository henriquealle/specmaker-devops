package br.com.specmaker.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public record QueryRecord(
        String id,
        String name,
        String path,
        boolean hasChildren,
        boolean isFolder,
        List<QueryRecord> children
) {
}
