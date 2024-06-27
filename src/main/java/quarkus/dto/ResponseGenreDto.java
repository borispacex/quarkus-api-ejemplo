package quarkus.dto;

import io.quarkus.hibernate.orm.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ResponseGenreDto(
        Long id,
        @ProjectedFieldName("name") String name
) {
}
