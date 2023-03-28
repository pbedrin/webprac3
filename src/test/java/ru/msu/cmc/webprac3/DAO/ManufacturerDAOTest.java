package ru.msu.cmc.webprac3.DAO;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac3.models.Manufacturer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ManufacturerDAOTest {
    @Autowired
    private ManufacturerDAO manufacturerDAO;

    @Test
    void testGetManufacturerByName() {
        Manufacturer manufacturer = manufacturerDAO.getManufacturerByName("Kia");
        assertNotNull(manufacturer);
        assertEquals(manufacturer.getManufacturer(), "Kia");
    }

    @Test
    void testAddDelete() {
        Manufacturer manufacturerToAdd = new Manufacturer(null, "Manf5");
        manufacturerDAO.addEntity(manufacturerToAdd);
        manufacturerToAdd = manufacturerDAO.getManufacturerByName("Manf5");
        assertNotNull(manufacturerToAdd);

        manufacturerDAO.delete(manufacturerToAdd);
        manufacturerToAdd = manufacturerDAO.getManufacturerByName("Manf5");
        assertNull(manufacturerToAdd);

        manufacturerToAdd = new Manufacturer(null, "Manf6");
        manufacturerDAO.addEntity(manufacturerToAdd);
        manufacturerToAdd = manufacturerDAO.getManufacturerByName("Manf6");
        assertNotNull(manufacturerToAdd);

        manufacturerDAO.deleteById(manufacturerToAdd.getId());
        manufacturerToAdd = manufacturerDAO.getManufacturerByName("Manf6");
        assertNull(manufacturerToAdd);
    }
}