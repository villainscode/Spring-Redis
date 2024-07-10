package com.villainscode.redis.rediskey;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CodeVillains description :
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class RedisKeyController {

    private final RedisKeyService redisKeyService;

    @Autowired
    public RedisKeyController(RedisKeyService redisKeyService) {
        this.redisKeyService = redisKeyService;
    }

    // GET http://localhost:8080/api/v1/keys
    @GetMapping("/keys")
    public Set<String> getAllKeys() {
        log.info("# getAllKeys");
        return redisKeyService.getAllKeys();
    }

    @GetMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getAllKeysAndValues() {
        try {
            Map<String, Object> result = redisKeyService.getAllKeysAndValues();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("An error occurred while fetching keys and values from Redis", e);
            // Return an error response with a 500 Internal Server Error status
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching keys and values from Redis");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


    // POST http://localhost:8080/api/v1/save
    // Content-Type: application/x-www-form-urlencoded
    //
    //number=20231127&value=first
    @PostMapping("/save")
    public void saveData(@RequestParam Integer number, @RequestParam String value) {
        try {
            redisKeyService.save(number, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    // GET http://localhost:8080/api/v1/search?number=20231127&value=first
    @GetMapping("/search")
    public String searchData(@RequestParam Integer number, @RequestParam String value) {
        try {
            return redisKeyService.search(number, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return "no search data error";
        }
    }

    // PUT http://localhost:8080/api/v1/edit
    //Content-Type: application/json
    //
    //{
    //  "number": 123,
    //  "newValue": "updatedValue"
    //}
    @PutMapping("/edit")
    public void editData(@RequestParam Integer number, @RequestParam String newValue) {
        try {
            redisKeyService.edit(number, newValue);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // DELETE http://localhost:8080/api/v1/delete?number=123&value=testValue
    @DeleteMapping("/delete")
    public void deleteData(@RequestParam Integer number, @RequestParam String value) {
        try {
            redisKeyService.delete(number, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
