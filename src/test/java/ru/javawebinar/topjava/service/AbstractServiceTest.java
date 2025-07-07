package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.Profiles;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public abstract class AbstractServiceTest {
    private static final Logger log = getLogger("result");

    private static final Map<String, Map<String, StringBuilder>> testResults = new LinkedHashMap<>();

    @Autowired
    private Environment environment;

    @Rule
    public final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String testType = AbstractServiceTest.this.getClass().getSimpleName();
            String profile = Profiles.getActiveDbProfile();
            StringBuilder results = testResults
                    .computeIfAbsent(testType, k -> new LinkedHashMap<>())
                    .computeIfAbsent(profile, k -> new StringBuilder());
            String result = String.format("\n%-31s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    @AfterClass
    public static void printResult() {
        StringBuilder finalSummary = new StringBuilder();
        testResults.forEach((testType, profileResults) -> finalSummary.append(buildSummary(testType, profileResults)));
        log.info(finalSummary.toString());
        testResults.clear();
    }

    private static String buildSummary(String testType, Map<String, StringBuilder> profileResults) {
        StringJoiner summary = new StringJoiner("\n", "\n", "");
        summary.add("=======================================");
        summary.add("Test Type: " + testType);
        profileResults.forEach((profile, results) -> {
            summary.add("---------------------------------------");
            summary.add("Profile: " + profile);
            summary.add("Test                       Duration, ms");
            summary.add("---------------------------------------");
            summary.add(results.toString().trim());
            summary.add("---------------------------------------");
        });
        return summary.toString();
    }

}