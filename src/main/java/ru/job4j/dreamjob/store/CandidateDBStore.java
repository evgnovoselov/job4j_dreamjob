package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@ThreadSafe
public class CandidateDBStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateDBStore.class.getSimpleName());
    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Collection<Candidate> findAll() {
        Collection<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getBytes("photo"),
                            it.getString("name"),
                            it.getString("description"),
                            new City(it.getInt("city_id"), null),
                            it.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error CandidateDBStore.findAll");
        }
        return candidates;
    }

    public Candidate findById(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    candidate = new Candidate(it.getInt("id"),
                            it.getBytes("photo"),
                            it.getString("name"),
                            it.getString("description"),
                            new City(it.getInt("city_id"), null),
                            it.getTimestamp("created").toLocalDateTime());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error CandidateDBStore.findById id = {}", id);
        }
        return candidate;
    }

    public void add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate(photo, name, description, city_id, created) VALUES (?,?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setBytes(1, candidate.getPhoto());
            ps.setString(2, candidate.getName());
            ps.setString(3, candidate.getDescription());
            ps.setInt(4, candidate.getCity().getId());
            ps.setTimestamp(5, Timestamp.valueOf(candidate.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error CandidateDBStore.add id = {}", candidate.getId());
        }
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidate SET photo = ?, name = ?, description = ?, city_id = ?, created = ? WHERE id = ?"
             )) {
            ps.setBytes(1, candidate.getPhoto());
            ps.setString(2, candidate.getName());
            ps.setString(3, candidate.getDescription());
            ps.setInt(4, candidate.getCity().getId());
            ps.setTimestamp(5, Timestamp.valueOf(candidate.getCreated()));
            ps.setInt(6, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOGGER.error("Error CandidateDBStore.update id = {}", candidate.getId());
        }
    }
}
