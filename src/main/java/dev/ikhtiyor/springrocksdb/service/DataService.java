package dev.ikhtiyor.springrocksdb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ikhtiyor.springrocksdb.dto.DataDTO;
import dev.ikhtiyor.springrocksdb.exception.CustomException;
import dev.ikhtiyor.springrocksdb.repository.RocksdbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataService {

    private final RocksdbRepository repository;
    private final ObjectMapper objectMapper;

    public DataDTO save(DataDTO dto) {
        repository.save(dto.getKey(), dto);
        return dto;
    }

    public DataDTO get(String key) {
        Optional<Object> optional = repository.find(key);
        if (optional.isPresent()) {
            Object result = optional.get();
            return objectMapper.convertValue(result, DataDTO.class);
        }
        throw new CustomException("Data not found with this key: " + key);
    }

    public void delete(String key) {
        repository.delete(key);
    }

}
