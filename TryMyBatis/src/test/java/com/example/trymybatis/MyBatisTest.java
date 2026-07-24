package com.example.trymybatis;

import com.example.trymybatis.entity.Address;
import com.example.trymybatis.entity.Article;
import com.example.trymybatis.entity.People;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyBatisTest {
    @Test
    void testMapping() throws IOException {
        String resource = "com/example/trymybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {

            String init_script = "com/example/trymybatis/init-db.sql";
            Reader init_script_stream = Resources.getResourceAsReader(init_script);
            ScriptRunner scriptRunner = new ScriptRunner(session.getConnection());
            scriptRunner.runScript(init_script_stream);

            People people = new People(null,"kent", 100, null, null);
            session.insert("com.example.trymybatis.insertPeople", people);
            session.commit();

            People people2 = session.selectOne("com.example.trymybatis.selectPeople", new HashMap<String, Object>() {{
                put("id", people.getId());
            }});
            // Or do this:
            People people3 = session.selectOne("com.example.trymybatis.selectPeople", people);

            Assertions.assertInstanceOf(People.class, people2);
            Assertions.assertEquals(people.getId(), people2.getId());
            Assertions.assertEquals(people.getName(), people2.getName());
            Assertions.assertEquals(people.getAge(), people2.getAge());
            Assertions.assertEquals(people2.getId(), people3.getId());

            // Insert multiple people.
            List<People> peopleList = createPeopleList();
            session.insert("com.example.trymybatis.insertPeopleList", peopleList);

            // batch save address an article for peoples.
            List<Address> addressList = new ArrayList<>();
            List<Article> articleList = new ArrayList<>();
            peopleList.forEach(people1 -> {
                Address address1 = people1.getAddress();
                address1.setPeopleId(people1.getId());
                addressList.add(address1);

                people1.getArticles().forEach(article1 -> {
                    article1.setPeopleId(people1.getId());
                    articleList.add(article1);
                });
            });
            session.insert("com.example.trymybatis.insertPeopleAddressList", addressList);
            session.insert("com.example.trymybatis.insertPeopleArticleList", articleList);

            session.commit();

            session.update("com.example.trymybatis.dropPeoples");
        }
    }

    private static @NonNull List<People> createPeopleList() {
        List<People> peopleList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            People people1 = new People();
            people1.setAge(20 + i);
            people1.setName("name" + i);
            peopleList.add(people1);

            Address address1 = new Address();
            address1.setCity("city" + i);
            people1.setAddress(address1);

            List<Article> personalArticleList = new ArrayList<>();
            for (int j = 1; j <= 10; j++) {
                Article article1 = new Article();
                article1.setTitle("title" + i);
                personalArticleList.add(article1);
                people1.setArticles(personalArticleList);
            }
        }
        return peopleList;
    }
}
