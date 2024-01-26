package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        // SQL Injection이 발생하지 않도록 수정된 코드
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT " + "user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at " +
                "FROM users " +
                "WHERE user_id=? AND user_password=?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);

            try (ResultSet rs = psmt.executeQuery()) {
                // select문을 실행했을 때 executeQuery() 사용
                // 나머지 update delete insert 사용시 executeUpdate() 사용
                if (rs.next()) {
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error("Database error", e);
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at " +
                "FROM users " +
                "WHERE user_id=?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error("Database error", e);
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO users " +
                "(user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, user.getUserId());
            psmt.setString(2, user.getUserName());
            psmt.setString(3, user.getUserPassword());
            psmt.setString(4, user.getUserBirth());
            psmt.setString(5, user.getUserAuth().toString());
            psmt.setInt(6, user.getUserPoint());
            psmt.setTimestamp(7, user.getCreatedAt() != null ? Timestamp.valueOf(user.getCreatedAt()) : null);
            psmt.setTimestamp(8, user.getLatestLoginAt() != null ? Timestamp.valueOf(user.getLatestLoginAt()) : null);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Database error", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId); // PreparedStatement를 사용하여 SQL Injection 방지

            return psmt.executeUpdate(); // 삭제된 행의 수를 반환
        } catch (SQLException e) {
            log.error("Database error", e);
            throw new RuntimeException(e); // 예외 발생 시, 로그를 남기고 RuntimeException을 던져 처리 흐름을 중단
        }
    }


    @Override
    public int update(User user) {
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE users SET " +
                "user_name = ?, " +
                "user_password = ?, " +
                "user_birth = ?, " +
                "user_auth = ?, " +
                "user_point = ?, " +
                "created_at = ?, " +
                "latest_login_at = ? " +
                "WHERE user_id = ?";

        log.debug("sql : {}", sql);


        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, user.getUserName());
            psmt.setString(2, user.getUserPassword());
            psmt.setString(3, user.getUserBirth());
            psmt.setString(4, user.getUserAuth().toString());
            psmt.setInt(5, user.getUserPoint());
            psmt.setTimestamp(6, user.getCreatedAt() != null ? Timestamp.valueOf(user.getCreatedAt()) : null);
            psmt.setTimestamp(7, user.getLatestLoginAt() != null ? Timestamp.valueOf(user.getLatestLoginAt()) : null);
            psmt.setString(8, user.getUserId());

            return psmt.executeUpdate(); // 업데이트된 행의 수를 반환
        } catch (SQLException e) {
            log.error("Database error", e);
            throw new RuntimeException(e); // 예외 발생 시, 로그를 남기고 RuntimeException을 던져 처리 흐름을 중단
        }
    }


    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE users SET latest_login_at = ? WHERE user_id = ?";

        log.debug("sql : {}", sql);

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            if (findById(userId).equals(Optional.empty()) && Objects.nonNull(latestLoginAt)) {
                return 0;
            } else {
                psmt.setTimestamp(1, Timestamp.valueOf(latestLoginAt));
                psmt.setString(2, userId);

                return psmt.executeUpdate();
            }
        } catch (SQLException e) {
            log.error("{} / {}", e.getSQLState(), e.getMessage());
            throw new RuntimeException(e); // 예외 발생 시, 로그를 남기고 RuntimeException을 던져 처리 흐름을 중단
        }
    }


    @Override
    public int countByUserId(String userId) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";

        log.debug("sql : {}", sql); // 쿼리 로깅

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId); // PreparedStatement를 사용하여 SQL Injection 방지

            try (ResultSet rs = psmt.executeQuery()) { // 쿼리 실행
                if (rs.next()) {
                    return rs.getInt(1); // 첫 번째 컬럼의 값을 반환
                }
            }
        } catch (SQLException e) {
            log.error("{} / {}", e.getSQLState(), e.getMessage()); // 예외 발생 시, 로깅
            throw new RuntimeException(e); // RuntimeException을 던져 상위 코드에서 처리할 수 있도록 함
        }
        return 0; // 예외 발생 시 또는 결과가 없을 경우 0 반환
    }


}
