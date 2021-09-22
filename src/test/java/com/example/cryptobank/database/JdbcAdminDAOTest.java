package com.example.cryptobank.database;

import com.example.cryptobank.domain.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class JdbcAdminDAOTest {

    private AdminDAO adminDAOTest;

    @Autowired
    public JdbcAdminDAOTest(AdminDAO adminDAOTest) {
        this.adminDAOTest = adminDAOTest;
    }

    @Test
    void save() {
        Admin admin = new Admin("Huub", "Tienen", "salt");
        Admin actual = adminDAOTest.save(admin);
        assertThat(actual).isEqualTo(admin);
    }

    @Test
    void findByUsername() {
        Admin adminInDatabase = adminDAOTest.findByUsername("admin");
        Admin expected = new Admin("admin", "adminPassword", "9d264ec3");
        assertThat(adminInDatabase).isEqualTo(expected);
    }
}