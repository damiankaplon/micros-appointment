package pl.damiankaplon;

import io.smallrye.mutiny.Uni;

public class MockImageRepository extends ImageRepositoryMongo {
    @Override
    public Uni<ImageEntity> persist(ImageEntity entity) {
        return Uni.createFrom().item(entity);
    }
}
