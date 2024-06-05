package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.repo.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;
    private ImageService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ImageService(imageRepository);
    }

    @Test
    void findByPartId() {
        // given
        int partId = 1;
        List<Image> images = new ArrayList<>(List.of(
                new Image(1, "name", "type", "filePath", partId)
        ));

        given(imageRepository.findByPartId(anyInt()))
                .willReturn(images);

        // when
        List<Image> res = underTest.findByPartId(partId);

        // then
        assertThat(res).isEqualTo(images);
    }
}