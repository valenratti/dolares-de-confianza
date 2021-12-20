package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.neo4j;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.neo4j.UserNode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNodeRepository extends Neo4jRepository<UserNode,Long> {
    @Query(value = "MATCH (u1:User { username:$u1 }),(u2:User { username:$u2 })\n" +
            "RETURN length(shortestPath((u1)-[*]-(u2)))")
    int shortestPath(String u1, String u2);

    @Query(value = "MATCH (u1:User { username:$u })-[*..$r]-(u2:User)\n" +
            "RETURN u2")
    List<UserNode> allFriendsInRadius(String u, int r);
}
