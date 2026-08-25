package ru.mariiic.impl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mariiic.impl.model.OutboxNode;
import ru.mariiic.impl.model.OutboxStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class OutboxRepository {
    private final NamedParameterJdbcTemplate  jdbcTemplate;

    private RowMapper<OutboxNode> mapper = (rs, rowNum) -> mapOutboxNode(rs);

    private OutboxNode mapOutboxNode(ResultSet rs) throws SQLException {
        return OutboxNode.builder()
                .id(rs.getObject("id", UUID.class))
                .createdAt(toInstant(rs.getTimestamp("created_at")))
                .attempts(rs.getInt("attempts"))
                .messageType(rs.getString("message_type"))
                .messagePayload(rs.getString("message_payload"))
                .status(OutboxStatus.valueOf(rs.getString("status")))
                .build();
    }

    public int save(OutboxNode node) {
        String sql = """
                insert into outbox(messageType, messagePayload, createdAt, attempts, status)
                values (:messageType, :messagePayload, :createdAt, :attempts, :status)
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("messageType", node.getMessageType());
        params.addValue("messagePayload", node.getMessagePayload());
        params.addValue("createdAt", node.getCreatedAt());
        params.addValue("attempts", node.getAttempts());
        params.addValue("status", node.getStatus());
        return jdbcTemplate.update(sql, params);
    }

    public List<OutboxNode> getBatch(int batchSize, String newStatus, List<String> statusesToProcess) {
        String sql = """
                update outbox
                set status = :status
                where id in (
                    select 
                        id
                    from outbox
                    where status in (:statuses) and attempts > 0
                    order by created_at desc 
                    limit :batchSize
                    for update skip locked
                )
                returning
                    id,
                    message_type,
                    message_payload,
                    created_at,
                    attempts,
                    status
        """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("batchSize", batchSize);
        params.addValue("statuses", statusesToProcess);
        params.addValue("status", newStatus);
        return jdbcTemplate.query(sql, params, mapper);
    }

    public void updateStatus(List<OutboxNode> nodes,  OutboxStatus status) {
        String sql = """
                update outbox
                set status = :status
                where id in (:ids)
        """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status", status);
        params.addValue("ids", nodes.stream().map(OutboxNode::getId).toList());
        jdbcTemplate.update(sql, params);
    }

    private Instant toInstant(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }
}
