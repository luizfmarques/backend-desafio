package com.example.demo.controllers;

import com.example.demo.entities.Pedido;
import com.example.demo.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<?> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.ok("Nenhum pedido encontrado");
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }


    @PostMapping
    public ResponseEntity criarPedido(@RequestBody Pedido pedido) {
       try {
           Pedido novoPedido = pedidoService.criarPedido(pedido);
           return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
       }catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        try {
            Pedido pedido = pedidoService.atualizarPedido(id, pedidoAtualizado);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarPedido(@PathVariable Long id) {
        try {
            pedidoService.deletarPedido(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/calcular-imposto")
    public ResponseEntity<Pedido> calcularImposto(@RequestBody Pedido pedido) {
        Pedido pedidoCalculado = pedidoService.calcularImposto(pedido);
        return ResponseEntity.ok(pedidoCalculado);
    }
}

