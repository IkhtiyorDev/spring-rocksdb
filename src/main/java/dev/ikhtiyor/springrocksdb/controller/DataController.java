package dev.ikhtiyor.springrocksdb.controller;

import dev.ikhtiyor.springrocksdb.dto.ApiResult;
import dev.ikhtiyor.springrocksdb.dto.DataDTO;
import dev.ikhtiyor.springrocksdb.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService service;

    @PostMapping
    public ApiResult<DataDTO> save(
            @RequestBody DataDTO dto
    ) {
        DataDTO result = service.save(dto);
        return ApiResult.build(result);
    }

    @GetMapping("/{key}")
    public ApiResult<DataDTO> get(
            @PathVariable String key
    ) {
        DataDTO result = service.get(key);
        return ApiResult.build(result);
    }

    @DeleteMapping("/{key}")
    public ApiResult<?> delete(
            @PathVariable String key
    ) {
        service.delete(key);
        return ApiResult.build("Successfully deleted");
    }
}
