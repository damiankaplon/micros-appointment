package pl.damiankaplon;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepositoryMongo imageRepositoryMongo;
    private static final Path IMAGES_DIR = Paths.get(Paths.get("") + "local-storage");

    public Multi<File> getMultiImages(ObjectId id) {
        Set<File> images = new HashSet<>();
        File image = new File(Paths.get(String.valueOf(IMAGES_DIR), "test.png").toString());
        images.add(image);
        return Multi.createFrom()
                .items(images.stream());
    }

    @Transactional(rollbackOn = Exception.class)
    public void upload(ObjectId candyId, ImageForm upload) throws IOException {
        List<File> images = upload.getImages();
        for(int i=0; i<images.size(); i++) {
            File image = images.get(i);
            if(image == null)
                continue;
            ImageEntity entity = new ImageEntity();
            entity.setCandyId(candyId);
            entity.setNo(i);
            entity.setPath(
                    Paths.get(
                            String.valueOf(IMAGES_DIR),
                            candyId + "img" + i + ".png"
                    ).toString()
            );
            Files.write(
                    Paths.get(entity.getPath()),
                    FileUtils.readFileToByteArray(image)
            );
            imageRepositoryMongo.persist(entity).await().indefinitely();
        }
    }

    public Uni<File> getUniImage(ObjectId objectId, ObjectId objectId1) {
        return null;
    }
}