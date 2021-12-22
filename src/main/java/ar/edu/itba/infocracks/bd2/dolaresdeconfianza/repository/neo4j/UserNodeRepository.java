package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.neo4j;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.neo4j.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNodeRepository extends Neo4jRepository<UserNode,Long> {
    @Query("MATCH (user1:User),(user2:User) where user1.username = $u1 and user2.username = $u2 RETURN length(shortestPath((user1)-[*..15]-(user2)))")
    int shortestPath(String u1, String u2);

    @Query("MATCH (u1:User )-[*1..$r]-(u2:User) where u1 = $u RETURN u2")
    List<UserNode> allFriendsInRadius(String u, int r);
}
