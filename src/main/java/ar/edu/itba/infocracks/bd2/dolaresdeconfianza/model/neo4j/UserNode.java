package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.neo4j;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;


@Node("User")
@Getter
@Setter
@ToString
public class UserNode {
    @Id
    private final long id;

    @Property("username")
    private final String username;

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @ToString.Exclude
    @Relationship(type = "FRIEND_OF")
    private Set<UserNode> friends;

    public UserNode(long id, String username) {
        this.id = id;
        this.username = username;
    }
}
