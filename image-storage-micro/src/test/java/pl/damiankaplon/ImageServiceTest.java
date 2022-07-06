package pl.damiankaplon;

import com.google.common.io.Files;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ImageServiceTest {

    ImageService imageService = new ImageService(new MockImageRepository());
    static File testFile;

    @BeforeAll
    public static void setUp() throws IOException {
        Path path = Paths.get("", "local-storage");
        testFile = new File(String.valueOf(Paths.get(path.toString(), "testFile.png")));
        Files.write(new byte[]{1, 1, 1, 1, 12, 12, 12, 12}, testFile);
    }

    @Test
    public void testFileUploadToLocalStorageWithoutException() {
        //GIVEN
        ImageForm form = new ImageForm();
        form.setImage1(testFile);
        form.setImage2(testFile);
        //WHEN & THEN
        Assertions.assertDoesNotThrow(() -> imageService.upload(new ObjectId("507f1f77bcf86cd799439011"), form));
    }

    @Test
    public void testFilesAreProperlySavedInLocalStorageFolder() throws IOException {
        //GIVEN
        File directory = new File(
                String.valueOf(Paths.get("", "local-storage"))
        );
        int fileCount = 0;
        if (directory.list() != null)
            fileCount = directory.list().length;
        ImageForm form = new ImageForm();
        form.setImage1(testFile);
        form.setImage2(testFile);
        //WHEN
        imageService.upload(new ObjectId("518f2f88bcf86cd799439022"), form);
        //THEN
        int fileCountAfterUpload = directory.list().length;
        Assertions.assertEquals(fileCount + 2, fileCountAfterUpload);
    }

    @Test
    public void testReturnsAllImagesOfCertainCandyId() throws IOException {
        //GIVEN
        ImageForm form = new ImageForm();
        form.setImage1(testFile);
        form.setImage2(testFile);
        imageService.upload(new ObjectId("997f1f77bcf86cd799439099"), form);
        //WHEN
        Multi<File> images = imageService.getMultiImages(new ObjectId("997f1f77bcf86cd799439099"));
        Uni<List<File>> imagesList = images.collect().asList();
        //THEN
        Assertions.assertEquals(2, imagesList.await().atMost(Duration.of(3, ChronoUnit.SECONDS)).size());
    }

}