package pl.damiankaplon;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepositoryMongo imageRepositoryMongo;
    private static final Path IMAGES_DIR = Paths.get(Paths.get("") + "local-storage");
    private static final String FILE_SYSTEM_ROOT;

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("win"))
            FILE_SYSTEM_ROOT = "";
        else
            FILE_SYSTEM_ROOT = "/";
        createLocalStorageFolder();
    }

    private static void createLocalStorageFolder() {
        try {
            Files.createDirectory(Paths.get(FILE_SYSTEM_ROOT, "local-storage"));
        } catch (FileAlreadyExistsException ex) {
            log.warn("Could not create local image storage dir. It might already exist");
        }
        catch (IOException ex) {
            log.error("IO Exception thrown while creating local image storage dir.");
            log.error(ex.getMessage());
            Arrays.stream(ex.getStackTrace()).forEach(st -> log.error(st.toString()));
        }
    }

    public Multi<File> getMultiImages(ObjectId candyId) {
        Multi<ImageEntity> multiEntities = imageRepositoryMongo.findByCandyId(candyId);
        Function<? super ImageEntity, File> toFile = (entity) -> new File(Paths.get(FILE_SYSTEM_ROOT, entity.getPath()).toString());
        return multiEntities.map(toFile);
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
                    Paths.get(FILE_SYSTEM_ROOT, entity.getPath()),
                    FileUtils.readFileToByteArray(image)
            );
            imageRepositoryMongo.persist(entity).await().indefinitely();
        }
    }

    public Uni<File> getUniImage(ObjectId candyId, Integer imageNo) {
        Uni<ImageEntity> entity = imageRepositoryMongo.findByCandyIdAndNo(candyId, imageNo);
        return entity.map(en -> new File(Paths.get(FILE_SYSTEM_ROOT, en.getPath()).toString()));
    }
}
