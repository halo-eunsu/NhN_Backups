package com.nhnacademy.shoppingmall.common.util;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

public class DbUtils {
    private DbUtils() {
        throw new IllegalStateException("유틸리티 클래스입니다.");
    }

    private static final DataSource DATASOURCE;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();

        try {
            basicDataSource.setDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // todo#1-1 IP, 데이터베이스, 사용자 이름, 비밀번호 설정
        basicDataSource.setUrl("jdbc:mysql://{ip}/{database}");
        basicDataSource.setUsername("{username}");
        basicDataSource.setPassword("{password}");

        // todo#1-2 초기 크기, 최대 총 연결 수, 최대 유휴 연결 수, 최소 유휴 연결 수를 모두 5로 설정
        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(5);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(5);

        // todo#1-3 유효성 검사 쿼리 설정
        basicDataSource.setValidationQuery("SELECT 1");

        basicDataSource.setMaxWait(Duration.ofSeconds(2));

        // todo#1-4 `DATASOURCE`를 적절하게 초기화
        DATASOURCE = basicDataSource;
    }

    public static DataSource getDataSource() {
        return DATASOURCE;
    }
}
