package dev.ikhtiyor.springrocksdb.repository;

import dev.ikhtiyor.springrocksdb.exception.CustomException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Slf4j
@Repository
public class RocksdbRepository {

    @Value("${rocksdb.base.path}")
    private String BASE_PATH;

    private final static String FILE_NAME = "data-rocksdb";

    RocksDB rocksDB;

    // execute after the application starts.
    @PostConstruct
    void initialize() {
        RocksDB.loadLibrary();
        final Options options = new Options();
        options.setCreateIfMissing(true);
        File baseDir = new File(BASE_PATH, FILE_NAME);
        try {
            Files.createDirectories(baseDir.getParentFile().toPath());
            Files.createDirectories(baseDir.getAbsoluteFile().toPath());
            rocksDB = RocksDB.open(options, baseDir.getAbsolutePath());
            log.info("RocksDB initialized");
        } catch (IOException | RocksDBException e) {
            log.error("Error initializing RocksDB. Exception: '{}', message: '{}'", e.getCause(), e.getMessage(), e);
            throw new CustomException(e.getMessage());
        }
    }

    public synchronized void save(String key, Object value) {
        log.info("saving value '{}' with key '{}'", value, key);
        try {
            rocksDB.put(key.getBytes(), SerializationUtils.serialize(value));
        } catch (RocksDBException e) {
            log.error("Error saving entry. Cause: '{}', message: '{}'", e.getCause(), e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    public synchronized Optional<Object> find(String key) {
        Object value = null;
        try {
            byte[] bytes = rocksDB.get(key.getBytes());
            if (bytes != null) value = SerializationUtils.deserialize(bytes);
        } catch (RocksDBException e) {
            log.error(
                    "Error retrieving the entry with key: {}, cause: {}, message: {}",
                    key,
                    e.getCause(),
                    e.getMessage()
            );
            throw new CustomException(e.getMessage());
        }
        log.info("finding key '{}' returns '{}'", key, value);
        return value != null ? Optional.of(value) : Optional.empty();
    }

    public synchronized void delete(String key) {
        log.info("deleting key '{}'", key);
        try {
            rocksDB.delete(key.getBytes());
        } catch (RocksDBException e) {
            log.error("Error deleting entry, cause: '{}', message: '{}'", e.getCause(), e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }
}
