package br.com.specmaker.apiclient.records;

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
