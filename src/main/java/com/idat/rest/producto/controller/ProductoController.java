package com.idat.rest.producto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.rest.producto.model.Producto;

@RestController
@RequestMapping("api/producto")
public class ProductoController {

    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();
    
    public ProductoController() {
        initData();
    }

    private void initData() {
        Producto galaxys23 =  new Producto(contador.incrementAndGet(), "Galaxy s23 Ultra", "Celular", 3499.00, 15);
        Producto cargador45w = new Producto(contador.incrementAndGet(), "Cargador 45W Orico", "Cargador Rapido", 120.50, 50);
        Producto Huaweifreebuds3 = new Producto(contador.incrementAndGet(), "Huawei Freebuds Pro 3", "Auriculares TWS", 450.00, 65);
        Producto GalaxyBuds2 = new Producto(contador.incrementAndGet(), "Galaxy Buds Pro 2", "Auriculares TWS GALAXY", 490.00, 50);
        productos.add(galaxys23);
        productos.add(cargador45w);
        productos.add(Huaweifreebuds3);
        productos.add(GalaxyBuds2);
    }

    @GetMapping()
    public ResponseEntity<List<Producto>> listar() {
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }


    
    @GetMapping({"/{id}"})
    public ResponseEntity<Producto> obtener(@PathVariable long id) {
        Producto producto = productos.stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
        if(producto != null){
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<Producto> registrar(@RequestBody Producto producto) {
        Producto productoNuevo = new Producto(contador.incrementAndGet(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getCantidad());
        productos.add(productoNuevo);
        return new ResponseEntity<>(productoNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable long id, @RequestBody Producto p) {
        Producto productoActualizado = null;
        for(Producto producto: productos) {
            if(producto.getId() == id){
                producto.setNombre(p.getNombre());
                producto.setDescripcion(p.getDescripcion());
                producto.setPrecio(p.getPrecio());
                producto.setCantidad(p.getCantidad());
                productoActualizado = producto;
                break;
            }
        }
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<Producto> eliminar(@PathVariable Long id) {
        Producto producto = productos.stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
        if(producto != null) {
            productos.remove(producto);
        }
        return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);
    }



}
