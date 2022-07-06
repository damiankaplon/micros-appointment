package pl.damiankaplon;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MockImageRepository extends ImageRepositoryMongo {

    Set<ImageEntity> entities = new HashSet<>();
    @Override
    public Uni<ImageEntity> persist(ImageEntity entity) {
        entities.add(entity);
        return Uni.createFrom().item(entity);
    }

    @Override
    Multi<ImageEntity> findByCandyId(ObjectId candyId) {
        return Multi.createFrom().items(entities.stream().filter(imageEntity -> imageEntity.getCandyId().equals(candyId)));
    }

    @Override
    Uni<ImageEntity> findByCandyIdAndNo(ObjectId candyId, Integer no) {
        return Uni.createFrom().item(
                entities.stream()
                        .filter(entity -> entity.getCandyId().equals(candyId) && entity.getNo() == no)
                        .collect(Collectors.toList())
                        .get(0)
        );
    }
}
