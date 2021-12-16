package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.neo4j;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.neo4j.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNodeRepository extends Neo4jRepository<UserNode,Long> {
}
