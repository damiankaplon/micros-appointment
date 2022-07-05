package pl.damiankaplon;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class ImageRepositoryMongo implements ReactivePanacheMongoRepository<ImageEntity> {
    Multi<ImageEntity> findByCandyId(ObjectId candyId) {
        return stream("candyId", candyId);
    }

    Uni<ImageEntity> findByCandyIdAndNo(ObjectId candyId, Integer no) {
        return find("candyId = ?1 and no = ?2", candyId, no).firstResult();
    }
}
