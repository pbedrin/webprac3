package ru.msu.cmc.webprac3.DAO;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac3.models.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ModelDAOTest {
    @Autowired
    private ModelDAO modelDAO;

    @Test
    void testGetModelByName() {
        Model model = modelDAO.getModelByName("Ceed");
        assertNotNull(model);
        assertEquals(model.getId(), 8);
        model = modelDAO.getModelByName("Ced");
        assertNull(model);
    }

    @Test
    void testGetAllCarsByManufacturer() {
        List<Model> models = modelDAO.getAllModelsByManufacturer("Kia");
        assertNotNull(models);
        assertEquals(3, models.size());
    }

}
