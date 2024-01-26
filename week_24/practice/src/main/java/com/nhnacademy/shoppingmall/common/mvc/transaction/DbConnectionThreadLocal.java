package com.nhnacademy.shoppingmall.common.mvc.transaction;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class DbConnectionThreadLocal {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(()->false);

    public static void initialize() {
        try {
            // connection pool에서 connectionThreadLocal에 connection을 할당합니다.
            Connection connection = com.nhnacademy.shoppingmall.common.util.DbUtils.getDataSource().getConnection();
            connectionThreadLocal.set(connection);

            // connection의 Isolation level을 READ_COMMITED로 설정합니다.
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            // auto commit 을 false로 설정합니다.
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            log.error("Error initializing database connection", e);
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }

    public static void setSqlError(boolean sqlError) {
        sqlErrorThreadLocal.set(sqlError);
    }

    public static boolean getSqlError() {
        return sqlErrorThreadLocal.get();
    }

    public static void reset() {
        try {
            Connection connection = connectionThreadLocal.get();
            if (connection != null) {
                // getSqlError() 에러가 존재하면 rollback 합니다.
                if (getSqlError()) {
                    connection.rollback();
                } else {
                    // getSqlError() 에러가 존재하지 않다면 commit 합니다.
                    connection.commit();
                }

                // 사용이 완료된 connection은 close를 호출하여 connection pool에 반환합니다.
                connection.close();
            }
        } catch (SQLException e) {
            log.error("Error resetting database connection", e);
            throw new RuntimeException(e);
        } finally {
            // 현재 사용하고 있는 connection을 재사용할 수 없도록 connectionThreadLocal을 초기화 합니다.
            connectionThreadLocal.remove();
            sqlErrorThreadLocal.set(false);
        }
    }
}
