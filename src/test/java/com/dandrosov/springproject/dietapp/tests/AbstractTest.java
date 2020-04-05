package com.dandrosov.springproject.dietapp.tests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void before() throws Exception {
        try (
                final Connection connection = dataSource.getConnection();
                final Statement statement = connection.createStatement()
        ) {
            statement.execute("USE dietapp_prod");
        }
    }


}
