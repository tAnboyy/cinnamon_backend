package com.cinnamon.backend.controller;

import com.cinnamon.backend.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> all() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(menuService.getMenuItems());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> item)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(menuService.createMenuItem(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable String id, @RequestBody Map<String, Object> item)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(menuService.updateMenuItem(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id)
            throws ExecutionException, InterruptedException {
        menuService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
